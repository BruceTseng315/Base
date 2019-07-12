package com.kevin.util;

import java.io.File;
import java.io.FileWriter;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: kevin
 * Date: 2019-04-28
 * Time: 16:55
 */
public class FileUtils {
    public static String path = "E:\\temp";

    public static void writeToFile(String filaName,String str){
        File file = new File(path+"\\"+filaName);
        try {
            if (file.exists() == false) {
                file.createNewFile();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            FileWriter fw = new FileWriter(file,true);
            fw.write(str);
            fw.write("\n");
            fw.flush();
            fw.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
