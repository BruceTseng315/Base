package com.kevin.common;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import com.kevin.Exception.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;


/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2019-05-09
 * Time: 15:13
 */
public class OssUtil {
    public static final String accessKeyId = "LTAI97pIf7S2cVBd";
    public static final String accessKeySecret = "bTLNI8xVTCQpPWkDW1FegDVEa6sSAm";
    //oss bucket name
    public static final String bucketName = "ayixueshetest";
    public static final String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    public static Logger log = LoggerFactory.getLogger(OssUtil.class);
    public static OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
    public static void main(String[] args) {
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        String objectName = "file.txt";

        String filePath = "C:/Users/zdy31/Documents/video/"+"WeChat_201905091633278.mp4";
        String key = "WeChat_20190509163327.mp4";
       // uploadFile();
        // 上传内容到指定的存储空间（bucketName）并保存为指定的文件名称（objectName）。
      //  String content = "Hello OSS";
        //ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(content.getBytes()));
// 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, key), new File(filePath));
       // 关闭OSSClient。
        //ossClient.shutdown();

    }

    public static void uploadImg2Oss(String url) {
        File fileOnServer = new File(url);
        FileInputStream fin;
        try {
            fin = new FileInputStream(fileOnServer);
            String[] split = url.split("/");
            uploadFile2OSS(fin, split[split.length - 1]);
        } catch (FileNotFoundException e) {
            throw new ServiceException(500,"图片上传失败");
        }
    }
    public static String uploadFile2OSS(InputStream instream, String fileName) {
        String ret = "";
        try {
            //创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(instream.available());
            objectMetadata.setCacheControl("no-cache");
            objectMetadata.setHeader("Pragma", "no-cache");
            objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentDisposition("inline;filename=" + fileName);
            //上传文件
            PutObjectResult putResult = ossClient.putObject(bucketName,  "image/"+fileName, instream, objectMetadata);
            ret = putResult.getETag();
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        } finally {
            try {
                if (instream != null) {
                    instream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ret;
    }
    /**
     * Description: 判断OSS服务文件上传时文件的contentType
     *
     * @param FilenameExtension 文件后缀
     * @return String
     */
    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpeg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        return "image/jpeg";
    }
    public static void uploadFile(){
        final String key = "test.jpg";
        String filePath = "C:/Users/zdy31/Documents/video/"+"WeChat_20190509163327.mp4";
        try {
            File file = new File(filePath);

            String[] split = filePath.split("/");
            UploadFileRequest uploadFileRequest = new UploadFileRequest(bucketName, split[split.length-1]);
            uploadFileRequest.setUploadFile(file.getAbsolutePath());
            uploadFileRequest.setTaskNum(10);

            UploadFileResult uploadRes = ossClient.uploadFile(uploadFileRequest);

        } catch (Throwable e) {
            e.printStackTrace();

        }
    }
}
