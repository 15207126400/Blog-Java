package com.ivan.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/*
 *  @Author: Ivan
 *  @Description:
 *  @Date: 2019/11/29 17:01
 */
@Slf4j
public class PicUploudUtil {
    /**
     * 原图片链接前缀
     */
    private static final String original_prefix = "http://www.ivan.group/files/";


    public static String uploadOriginalImageBySftp(MultipartFile uploadFile) {
        SFtpUtil sftp = new SFtpUtil();
        String newFilePath = "";
        try {
            // 获取新文件名称
            String newFileName = getNewFileName(uploadFile);
            //因为是网络流, 将输入流加上BufferedInputStream, 提升上传速度
            BufferedInputStream input = new BufferedInputStream(new ByteArrayInputStream(uploadFile.getBytes()));
            //上传的ftp文件路径前缀
            String imagePath = getFtpFilePathPrefix();
            //新的路径(访问)
            newFilePath = original_prefix + imagePath + newFileName;
            sftp.upload(input, newFileName, imagePath);
            sftp.closeChannel();
        } catch (Exception e) {
            log.info(e.toString());
        }

        return newFilePath;
    }

    /**
     * 生成新文件名称
     * @param uploadFile
     * @return
     */
    private static String getNewFileName(MultipartFile uploadFile){
        // 获取旧的名字
        String oldName = "";
        if(StringUtils.isNotEmpty(uploadFile.getOriginalFilename())){
            oldName = uploadFile.getOriginalFilename();
        }else{
            oldName = uploadFile.getName();
        }

        // 生成随机名称前缀
        String namePrefix = genImageName();
        // 生成文件名称
        String newFileName = namePrefix + oldName.substring(oldName.lastIndexOf("."));

        return newFileName;
    }

    /**
     * 图片名称生成
     * @return  图片名
     */
    public static String genImageName() {
        // 取当前时间的长整形包含毫秒
        long millis = System.currentTimeMillis();
        // long millis = System.nanoTime();
        // 加上三位随机数
        Random random = new Random();
        int end3 = random.nextInt(999);
        // 如果不足三位前面�?0
        String str = millis + String.format("%03d", end3);
        return str;
    }

    /**
     * 生成ft文件路径前缀
     * @return
     */
    public static String getFtpFilePathPrefix(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd/");
        String imagePath = df.format(new Date());
        return imagePath;
    }

}
