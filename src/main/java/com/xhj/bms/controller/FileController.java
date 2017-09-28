package com.xhj.bms.controller;

import com.xhj.bms.util.ImageUtils;
import com.xhj.bms.util.OSSUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;

/**
 * Created by Projack
 * 上传视图
 */
@Controller
@RequestMapping(value = "/file")
public class FileController {

    @Value("${images.domain.url}")
    private String IMAGES_DOMAIN_URL;
    @Value("${images.localhost.url}")
    private String IMAGES_LOCALHOST_URL;

    //获取分隔符（不同系统不同）
    String sep = System.getProperty("file.separator");

    /**
     * 上传文件到阿里云OSS(加水印)
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = { RequestMethod.POST })
    @ResponseBody
    public String uploadimg(@RequestParam(value = "file", required = false) MultipartFile file) {
    	String imagePath = "";
        try {
            byte[] bytes = file.getBytes();
            String fileName = OSSUtil.createImgName(file.getOriginalFilename());
            String url = IMAGES_LOCALHOST_URL + "/" + fileName;
            FileCopyUtils.copy(bytes, new File(url));//将图片写入临时目录
            
            //给图片添加水印
            InputStream is = getClass().getResourceAsStream("/static/img/xinfangshuiyin.png");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int rc = 0;
            while((rc = is.read(buff, 0, 1024)) > 0){
            	baos.write(buff,0,rc);
            }
            byte[] in2b = baos.toByteArray();
            ImageUtils.markImageByIcon(in2b,url,url);
            //关流
            baos.close();
            is.close();
            
            File localfile = new File(url);
            imagePath = "xhj/images/" + fileName;
            OSSUtil.uploadFile(localfile, imagePath);//将文件上传到文件服务器
            FileSystemUtils.deleteRecursively(new File(url)); //删除本地服务器的图片
        } catch (Exception e) {
            e.printStackTrace();
        }
        return IMAGES_DOMAIN_URL + "/" + imagePath;
    }
    
    
    /**
     * 上传文件到阿里云OSS(不加水印)
     * @param file
     * @return
     */
    @RequestMapping(value = "/uploadnotwatermark", method = { RequestMethod.POST })
    @ResponseBody
    public String uploadnotwatermark(@RequestParam(value = "file", required = false) MultipartFile file) {
    	String imagePath = "";
    	try {
    		byte[] bytes = file.getBytes();
    		String fileName = OSSUtil.createImgName(file.getOriginalFilename());
    		String url = IMAGES_LOCALHOST_URL + "/" + fileName;
    		FileCopyUtils.copy(bytes, new File(url));//将图片写入临时目录
    		File localfile = new File(url);
    		imagePath = "xhj/images/" + fileName;
    		OSSUtil.uploadFile(localfile, imagePath);//将文件上传到文件服务器
    		FileSystemUtils.deleteRecursively(new File(url)); //删除本地服务器的图片
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    	return IMAGES_DOMAIN_URL + "/" + imagePath;
    }

    /**
     * 上传文件到七牛服务器
     * @param file
     * @return
     */
    //    @RequestMapping(value = "/upload")
    //    @ResponseBody
    //    public String upload(@RequestParam(value="file", required=false) MultipartFile file){
    //        String imgPath = "";
    //        try{
    //            String fileName = file.getOriginalFilename();
    //            String localPath = IMAGES_LOCALHOST_URL + sep + fileName;
    //            FileCopyUtils.copy(file.getBytes(), new File(localPath));     //图片保存本地服务器
    //            ImageUtils.HdImages(localPath,localPath,600,450,70);          //裁剪缩略图
    //            imgPath = QiNiuUtils.upload(localPath);                       //图片上传七牛云服务器
    //            FileSystemUtils.deleteRecursively(new File(localPath));      //删除本地服务器的图片
    //            imgPath = IMAGES_DOMAIN_URL + "/" + imgPath;
    //        }catch(Exception e){
    //            e.printStackTrace();
    //        }
    //        return imgPath;
    //    }

}
