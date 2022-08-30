package com.free.decision.server.utils;

import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.OSSObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

/**
 * 阿里云oss
 * @author Xingyf
 */
@Component
public class AliOssUtils {

    private static Logger logger = LoggerFactory.getLogger(AliOssUtils.class);

    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    @Value("${aliyun.oss.accessKeyId}")
    private String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    private String accessKeySecret;

    /**
     * 上传
     * @param prefix
     * @param file
     * @return
     */
    public String upload(String bucketName, String prefix, File file){
        if(file == null || file.isDirectory() || !file.exists()){
            throw new IllegalArgumentException("参数不正确");
        }
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String suffix = ".jpg";
            if(file.getName().contains(".")){
                suffix = file.getName().substring(file.getName().lastIndexOf("."));
            }
            String fileName = prefix + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS") + RandomStringUtils.randomNumeric(6) + suffix;
            ossClient.putObject(bucketName, fileName, file);
            return fileName;
        }catch (Exception e){
            logger.error("上传阿里云oss出错", e);
            throw new RuntimeException(e);
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 上传
     * @param prefix
     * @param suffix 后缀 eg: .jpg
     * @param inputStream
     * @return
     */
    public String upload(String bucketName, String prefix, String suffix, InputStream inputStream){
        if(inputStream == null){
            throw new IllegalArgumentException("参数不正确");
        }
        if(!StringUtils.startsWith(suffix, ".")){
            throw new IllegalArgumentException("参数不正确");
        }
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String fileName = prefix + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS") + RandomStringUtils.randomNumeric(6) + suffix;
            ossClient.putObject(bucketName, fileName, inputStream);
            return fileName;
        }catch (Exception e){
            logger.error("上传阿里云oss出错", e);
            throw new RuntimeException(e);
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 上传
     * @param prefix
     * @param suffix 后缀 eg: .jpg
     * @param bytes
     * @return
     */
    public String upload(String bucketName, String prefix, String suffix, byte[] bytes){
        if(bytes == null){
            throw new IllegalArgumentException("参数不正确");
        }
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            String fileName = prefix + DateFormatUtils.format(new Date(), "yyyyMMddHHmmssS") + RandomStringUtils.randomNumeric(6) + suffix;
            ossClient.putObject(bucketName, fileName, new ByteArrayInputStream(bytes));
            return fileName;
        }catch (Exception e){
            logger.error("上传阿里云oss出错", e);
            throw new RuntimeException(e);
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 授权访问
     * @param bucketName
     * @param fileName
     * @param expirationSecond 有效时间（秒）
     * @param isWater 是否生成水印
     * @return
     */
    public String getAuthUrl(String bucketName, String fileName, long expirationSecond, boolean isWater){
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            // 设置URL过期时间
            Date expiration = new Date(new Date().getTime() + expirationSecond*1000);
            GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, fileName, HttpMethod.GET);
            req.setExpiration(expiration);
            if(isWater){
                // 图片处理样式-设置水印文字（资付）
                String style = "image/watermark,text_6LWE5LuY,type_d3F5LXplbmhlaQ,size_20,t_100,shadow_100,color_fdfdfd,g_se,y_10,x_10";
                req.setProcess(style);
            }
            // 生成URL。
            URL url = ossClient.generatePresignedUrl(req);
            return url.toString();
        }catch (Exception e){
            logger.error("上传阿里云oss出错", e);
            throw new RuntimeException(e);
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 上传报告
     * @param bucketName
     * @param fileName
     * @param content
     * @return
     */
    public String uploadReport(String bucketName, String fileName, String content){
        if(StringUtils.isBlank(content) || StringUtils.isBlank(fileName)){
            throw new IllegalArgumentException("参数不正确");
        }
        // 创建OSSClient实例。
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            InputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"));
            ossClient.putObject(bucketName, fileName, is);
            return fileName;
        }catch (Exception e){
            logger.error("上传阿里云oss出错", e);
            throw new RuntimeException(e);
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 读取阿里云oss 报告--Xingyf
     * @param bucketName
     * @param fileName
     * @return
     */
    public String readReport(String bucketName, String fileName){
        if(StringUtils.isBlank(bucketName) || StringUtils.isBlank(fileName)){
            return "";
        }
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            OSSObject ossObject = ossClient.getObject(bucketName, fileName);
            if(ossObject == null){
                return "";
            }
            return IOUtils.toString(ossObject.getObjectContent(), "UTF-8");
        }catch (Exception e){
            logger.error("读取阿里云oss报告出错", e);
            return "";
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }
    }

    /**
     * 删除
     * @param bucketName
     * @param fileName
     */
    public void delete(String bucketName, String fileName){
        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        try {
            ossClient.deleteObject(bucketName, fileName);
        }catch (Exception e){
            logger.error("删除阿里云oss报告出错", e);
        }finally {
            // 关闭Client。
            if(ossClient != null){
                ossClient.shutdown();
            }
        }

    }
}
