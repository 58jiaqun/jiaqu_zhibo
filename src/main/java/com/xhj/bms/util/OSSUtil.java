package com.xhj.bms.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.ObjectMetadata;
import org.springframework.util.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Properties;
import java.util.UUID;

public class OSSUtil {

    //阿里云OSS服务器参数
    private static String ossAccessKeyId;
    private static String ossAccessKeySecret;
    private static String ossEndpoint;
    private static String ossBucketName;
    public static String fileUrl; //访问普通文件的url前缀
    public static String imageUrl; //访问图片文件的url前缀

    private static OSSClient ossClient = null;

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    static {
        Properties props = new Properties();
        InputStream propFile;
        try {
            propFile = OSSUtil.class.getClassLoader().getResourceAsStream("application.properties");
            props.load(propFile);
            ossAccessKeyId = (String) props.get("oss.accessKeyId");
            ossAccessKeySecret = (String) props.get("oss.accessKeySecret");
            ossEndpoint = (String) props.get("oss.endpoint");
            ossBucketName = (String) props.get("oss.bucketName");
            fileUrl = (String) props.get("file.basepath");
            imageUrl = (String) props.get("oss.imageDomain");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 删除文件。
     * @param fileName
     */
    public static void deleteFile(String fileName) {
        getOSSClient().deleteObject(ossBucketName, fileName);
    }

    public static String createImgName(String filename) {
        filename = filename.substring(filename.indexOf("."), filename.length());
        String id = UUID.randomUUID().toString();
        filename = id + filename;
        return filename;
    }

    /**
     * 存储文件到目标位置。
     * @param file
     * @param destFileName
     * @throws IOException
     */
    public static void uploadFile(File file, String destFileName) {
        try {
            InputStream content = new FileInputStream(file);
            // 创建上传Object的Metadata
            ObjectMetadata meta = new ObjectMetadata();
            // 必须设置ContentLength
            meta.setContentLength(file.length());
            // 上传Object.
            getOSSClient().putObject(ossBucketName, destFileName, content, meta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getImageUrl(String fileName) {
        return imageUrl + fileName;
    }

    public static String getImageUrl(String fileName, String param) {
        return imageUrl + fileName + "@" + param;
    }

    public static void setImageUrl(String imageUrl) {
        OSSUtil.imageUrl = imageUrl;
    }

    public static void setOssAccessKeyId(String ossAccessKeyId) {
        OSSUtil.ossAccessKeyId = ossAccessKeyId;
    }

    public static void setOssAccessKeySecret(String ossAccessKeySecret) {
        OSSUtil.ossAccessKeySecret = ossAccessKeySecret;
    }

    public static void setOssEndpoint(String ossEndpoint) {
        OSSUtil.ossEndpoint = ossEndpoint;
        if (StringUtils.hasText(ossBucketName)) {
            fileUrl = "http://" + ossBucketName + "." + ossEndpoint.substring(7) + "/";
        }
    }

    public static void setOssBucketName(String ossBucketName) {
        OSSUtil.ossBucketName = ossBucketName;
        if (StringUtils.hasText(ossEndpoint)) {
            fileUrl = "http://" + ossBucketName + "." + ossEndpoint.substring(7) + "/";
        }
    }

    public static OSSClient getOSSClient() {
        if (ossClient == null) {
            ossClient = new OSSClient(ossEndpoint, ossAccessKeyId, ossAccessKeySecret);
        }
        return ossClient;
    }
}
