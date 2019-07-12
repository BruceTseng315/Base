package com.kevin.util;


import org.apache.poi.hssf.model.InternalWorkbook;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 用于将数据以文件下载的方式导出
 *
 * @author: kevin
 * Date: 2018-08-06
 * Time: 13:53
 */
public class DataExportUtils {

   private static Logger logger = LoggerFactory.getLogger(DataExportUtils.class);

    /**
     * 将数据导入excell
     * @param dataMode excell数据模型
     * @param datas 待导入的数据，每一个List<T>对应一个sheet</>
     * @param sheetNames 每个sheet的名字
     * @param <T> 数据bean
     * @return
     */
    public static <T> Workbook writeExcel(DataMode<T> dataMode, List<List<T>> datas, List<String>sheetNames)  {

        HSSFWorkbook workbook= HSSFWorkbook.create(InternalWorkbook.createWorkbook());
        for(int i=0;i<datas.size();i++) {
            List<T> dataList = datas.get(i);
            String sheetName = sheetNames.get(i);
            writeASheetToWorkbook(workbook, dataMode, dataList,i,sheetName);
        }

        return workbook;
    }

    /**
     * 创建一个sheet
     * @param workbook excell文件
     * @param dataMode excell数据模型
     * @param datas 待写入的数据
     * @param sheetNum sheet在excell中的序号，用于设置sheetname
     * @param sheetName sheetname
     * @param <T> 待写入的数据bean
     */
    private static <T> void writeASheetToWorkbook(Workbook workbook, DataMode<T> dataMode, List<T> datas,int sheetNum,String sheetName) {

        Sheet sheet = workbook.createSheet();
        sheet.setDefaultColumnWidth(25);

        workbook.setSheetName(sheetNum, sheetName);
        final Row headRow = sheet.createRow(dataMode.getHeadLine());
        Map<String, String> filenames = dataMode.getColumnandFieldNameMap();
        String[] fieldNames = new String[filenames.size()];
        int i=0;
        Iterator<Map.Entry<String, String>> iterator=
                filenames.entrySet().iterator();
        //设置表头
        while(iterator.hasNext()) {
            Map.Entry<String, String> e=iterator.next();
            fieldNames[i]=e.getKey();
            String colname = fieldNames[i];
            Cell cell = headRow.createCell(i);
            setCellValue(cell,colname);
            i++;
        }
        if(datas == null || datas.size() < 1) {
            return ;
        }
        //数据行起始行
        i = dataMode.getDataStart();

        for(T t:datas) {
            Row dataRow = sheet.createRow(i);//创造当前行
            try {
                writeDataRow(dataRow, t, filenames, fieldNames);
            } catch (Exception e) {
                logger.warn("写入异常,行号:"+i, e);
            }
            i++;

            if(i>=dataMode.getDataEnd()) {
                break;
            }
        }

    }

    /**
     * 写入数据到指定行
     * @param row 待写入数据的行
     * @param object 待写入数据的类型
     * @param filenames 表头与待写入的数据的属性之间的映射关系
     * @param fieldNames 待写入的数据的属性
     * @throws Exception
     */
    private static void writeDataRow(Row row, Object object, Map<String, String> filenames, String[] fieldNames)
            throws Exception {

        for (int i = 0; i < fieldNames.length; i++) {
            String colname = fieldNames[i];
            if (!filenames.containsKey(colname)) {
                continue;
            }
            Cell cell = row.createCell(i);
            Object cellValue = null;
            try {
                cellValue = BeanUtils.getPropertyDescriptor(object.getClass(), filenames.get(colname)).getReadMethod()
                        .invoke(object);
            } catch (Exception e) {
                logger.warn("cell设置属性异常,列号:" + i, e);
            }

            if (cellValue != null) {
                setCellValue(cell, cellValue);
            }
        }

    }

    /**
     * 设置单元格的值
     * @param cell
     * @param value
     */
    private static void setCellValue(Cell cell, Object value) {

        if (value instanceof String || value instanceof Date) {
            //cell.setCellType(CellType.STRING);
            cell.setCellValue(TypeConvertUtil.convertIfNecessary(value, String.class));
        }

        else if (value instanceof Number) {

            //cell.setCellType(CellType.NUMERIC);
            cell.setCellValue(TypeConvertUtil.convertIfNecessary(value, Double.class));
        }

        else if(value instanceof Collection) {

            Collection<?> collection=(Collection<Object>)value;
            //cell.setCellType(CellType.STRING);
            StringBuilder builder=new StringBuilder();
            for(Object object:collection) {
                builder.append(String.valueOf(object));
                builder.append(",");
            }

            cell.setCellValue(builder.toString());
        }

    }

    /**
     *
     * @param dataMode
     * @param datas
     * @param sheetNames
     * @param password
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> POIFSFileSystem writeExcelEncrpt(DataMode<T> dataMode, List<List<T>> datas, List<String>sheetNames, String password) throws Exception {
        Workbook wb = writeExcel(dataMode,datas,sheetNames);
        //把工作薄输出到字节里面
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        wb.write(bout);
        bout.flush();
        ByteArrayInputStream Workbookinput = new ByteArrayInputStream(bout.toByteArray());
        //创建POIFS文件系统 加密文件
        POIFSFileSystem fs = new POIFSFileSystem();
        EncryptionInfo info = new EncryptionInfo(fs, EncryptionMode.agile);
        Encryptor enc = info.getEncryptor();
        enc.confirmPassword(password);
        //然后把字节输入到输入流，然后输入到OPC包里面
        OPCPackage opcp = OPCPackage.open(Workbookinput);
        OutputStream os = enc.getDataStream(fs);
        opcp.save(os);
        opcp.close();

        return fs;
    }
}
