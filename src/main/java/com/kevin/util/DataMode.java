package com.kevin.util;

import java.util.Map;

/**
 * 
 * @author kevin
 * 用于定义数据存入excell的格式
 * @param <T>
 */
public class DataMode<T> {

	/**
	 * 表头行
	 */
	int headLine=0;
	/**
	 * 数据起始行
	 */
	int dataStart=1;
	/**
	 * 每页数据最大数量限制
	 */
	int dataEnd = 5000;

	/**
	 * xls  xlsx
	 */
	int excelType;

	/**
	 * 每行对应一个数据对象model
	 */
	Class<T> dataClass;


	/**
	 * keySet为表头字段的集合，valueSet为数据对象中对应的数据成员的集合
	 */
	Map<String, String> columnandFieldNameMap;
	

	public int getHeadLine() {
		return headLine;
	}

	public void setHeadLine(int headLine) {
		this.headLine = headLine;
	}

	public int getDataStart() {
		return dataStart;
	}

	public void setDataStart(int dataStart) {
		this.dataStart = dataStart;
	}

	public int getDataEnd() {
		return dataEnd;
	}

	public void setDataEnd(int dataEnd) {
		this.dataEnd = dataEnd;
	}

	public Class<T> getDataClass() {
		return dataClass;
	}

	public void setDataClass(Class<T> dataClass) {
		this.dataClass = dataClass;
	}

	
	
	public int getExcelType() {
		return excelType;
	}

	public void setExcelType(int excelType) {
		this.excelType = excelType;
	}

	public Map<String, String> getColumnandFieldNameMap() {
		return columnandFieldNameMap;
	}

	public void setColumnandFieldNameMap(Map<String, String> columnandFieldNameMap) {
		this.columnandFieldNameMap = columnandFieldNameMap;
	}

	@Override
	public String toString() {
		return "DataMode [headLine=" + headLine + ", dataStart=" + dataStart + ", dataEnd=" + dataEnd + ", dataClass="
				+ dataClass + ", columnandFieldNameMap=" + columnandFieldNameMap + "]";
	}

	
	
	
}
