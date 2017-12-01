package com.springboot.study.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by ps on 2017/12/1.
 */
public class FileUtils {

    private static final Logger logger = LoggerFactory.getLogger(FileUtils.class);

    /**
     * 在指定文件夹下查找文件(包括子文件夹)
     */
    public static File findFileByFileName(File directory, String fileName) {

        if (fileName == null || directory == null || !directory.exists() || !directory.isDirectory()) {                 // 非空校验
            return null;
        }

        try {
            for (File file : directory.listFiles()) {                                                                       // 遍历文件夹
                if (file.isFile() && file.getName().equals(fileName)) {                                                     // 如果找到文件直接返回
                    return file;

                } else if (file.isDirectory()) {                                                                            // 如果是文件夹则递归查找
                    File inner = findFileByFileName(file, fileName);
                    if (inner != null) {
                        return inner;
                    }
                }
            }
        } catch (Exception e) {
            logger.error("=== 在指定文件夹下查找文件(包括子文件夹) [异常] ===", e);
        }

        return null;                                                                                                    // 如果没有找到则返回null
    }

    /**
     *  读取groovy文件  0 md5 ; 1 groovySourceFile
     * @param filePath 文件完整路径
     * @return 文件String
     */
    public static String fileToString(String filePath, String separator) {

        BufferedReader br = null;
        File file = new File(filePath);
        if (!file.exists()) {
            logger.error(filePath + "文件不存在！");
            return null;
        }

        try {

            br = new BufferedReader(new FileReader(file));
            String line = null;
            StringBuilder sb = new StringBuilder();

            while((line = br.readLine()) != null){
                sb.append(line + separator);
            }

            return sb.toString();

        } catch (Exception e) {
            logger.error(e.getMessage());
        }finally{
            try {
                br.close();
            } catch (Exception e2) {
            }
        }

        return  null;
    }
}
