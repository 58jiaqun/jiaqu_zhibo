package com.xhj.bms.util;

import org.im4java.core.*;
import org.im4java.process.ArrayListOutputConsumer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Projack
 * 图片处理工具类
 */
public class ImageUtils {
    /** 是否使用 GraphicsMagick **/
    private static final boolean USE_GRAPHICS_MAGICK_PATH = true;
    /** ImageMagick 安装目录 **/
    private static final String IMAGE_MAGICK_PATH = "E:\\ImageMagick-7.0.1-Q16";
    /** GraphicsMagick 安装目录 **/
    private static final String GRAPHICS_MAGICK_PATH = "E:\\GraphicsMagick-1.3.24-Q8";

    /**
     * 获取 ImageCommand
     * @param comm 命令类型（convert, identify）
     * @return
     */
    private static ImageCommand getImageCommand(String comm) {

        ImageCommand cmd = null;
        if ("convert".equalsIgnoreCase(comm)) {
            cmd = new ConvertCmd(USE_GRAPHICS_MAGICK_PATH);
        } else if ("identify".equalsIgnoreCase(comm)) {
            cmd = new IdentifyCmd(USE_GRAPHICS_MAGICK_PATH);
        } // else if....

        if (cmd != null && System.getProperty("os.name").toLowerCase().indexOf("windows") != -1) {
            cmd.setSearchPath(USE_GRAPHICS_MAGICK_PATH ? GRAPHICS_MAGICK_PATH : IMAGE_MAGICK_PATH);
        }
        return cmd;
    }

    /**
     * 获取图片宽度
     * @param path 图片路径
     * @return 宽度
     * @throws Exception
     */
    public static int getImageWidth(String path) throws Exception {

        return getImageWidthHeight(path)[0];
    }

    /**
     * 获取图片高度
     * @param path 图片路径
     * @return 高度
     * @throws Exception
     */
    public static int getImageHeight(String path) throws Exception {

        return getImageWidthHeight(path)[1];
    }

    /**
     * 获取图片宽度和高度
     * @param path 图片路径
     * @return [0]：宽度，[1]：高度
     * @throws Exception
     */
    public static int[] getImageWidthHeight(String path) throws Exception {

        Map<String, Object> info = getImageInfo(path);
        return new int[] { (Integer) info.get("width"), (Integer) info.get("width") };
    }

    /**
     * 获取图片信息
     * @param path 图片路径
     * @return Map {height=, filelength=, directory=, width=, filename=}
     * @throws Exception
     */
    public static Map<String, Object> getImageInfo(String path) throws Exception {

        IMOperation op = new IMOperation();
        op.format("%w,%h,%d,%f,%b");
        op.addImage(path);
        IdentifyCmd identifyCmd = (IdentifyCmd) getImageCommand("identify");
        IdentifyCmd.setGlobalSearchPath(IMAGE_MAGICK_PATH);
        ArrayListOutputConsumer output = new ArrayListOutputConsumer();
        identifyCmd.setOutputConsumer(output);
        identifyCmd.run(op);
        ArrayList<String> cmdOutput = output.getOutput();
        if (cmdOutput.size() != 1) {
            return null;
        }
        String line = cmdOutput.get(0);
        String[] arr = line.split(",");
        Map<String, Object> info = new HashMap<String, Object>();
        info.put("width", Integer.parseInt(arr[0]));
        info.put("height", Integer.parseInt(arr[1]));
        info.put("directory", arr[2]);
        info.put("filename", arr[3]);
        info.put("filelength", Integer.parseInt(arr[4]));
        return info;
    }

    /**
     * 去除Exif信息，可减小文件大小
     * @param path 原文件路径
     * @param des 目标文件路径
     * @throws Exception
     */
    public static void removeProfile(String path, String des) throws Exception {

        createDirectory(des);
        IMOperation op = new IMOperation();
        op.addImage(path);
        op.profile("*");
        op.addImage(des);
        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);
    }

    /**
     * 降低品质，以减小文件大小
     * @param path 原文件路径
     * @param des 目标文件路径
     * @param quality 保留品质（1-100）
     * @throws Exception
     */
    public static void reduceQuality(String path, String des, double quality) throws Exception {

        createDirectory(des);
        IMOperation op = new IMOperation();
        op.addImage(path);
        op.quality(quality);
        op.addImage(des);
        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);
    }

    /**
     * 改变图片大小
     * @param path 原文件路径
     * @param des 目标文件路径
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param sample 是否以缩放方式，而非缩略图方式
     * @throws Exception
     */
    public static void resizeImage(String path, String des, int width, int height, boolean sample) throws Exception {

        createDirectory(des);
        if (width == 0 || height == 0) { // 等比缩放
            scaleResizeImage(path, des, width == 0 ? null : width, height == 0 ? null : height, sample);
            return;
        }

        IMOperation op = new IMOperation();
        op.addImage(path);
        if (sample) {
            op.resize(width, height, "!");
        } else {
            op.sample(width, height);
        }
        op.addImage(des);

        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);
    }

    /**
     * 等比缩放图片（如果width为空，则按height缩放; 如果height为空，则按width缩放）
     * @param path 原文件路径
     * @param des 目标文件路径
     * @param width 缩放后的宽度
     * @param height 缩放后的高度
     * @param sample 是否以缩放方式，而非缩略图方式
     * @throws Exception
     */
    public static void scaleResizeImage(String path, String des, Integer width, Integer height, boolean sample) throws Exception {

        createDirectory(des);
        IMOperation op = new IMOperation();
        op.addImage(path);
        if (sample) {
            op.resize(width, height);
        } else {
            op.sample(width, height);
        }
        op.addImage(des);
        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);
    }

    /**
     * 从原图中裁剪出新图
     * @param path 原文件路径
     * @param des 目标文件路径
     * @param x 原图左上角
     * @param y 原图左上角
     * @param width 新图片宽度
     * @param height 新图片高度
     * @throws Exception
     */
    public static void cropImage(String path, String des, int x, int y, int width, int height) throws Exception {

        createDirectory(des);
        IMOperation op = new IMOperation();
        op.addImage(path);
        op.crop(width, height, x, y);
        op.addImage(des);
        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);
    }

    /**
     * 将图片分割为若干小图
     * @param path 原文件路径
     * @param des 目标文件路径
     * @param width 指定宽度（默认为完整宽度）
     * @param height 指定高度（默认为完整高度）
     * @return 小图路径
     * @throws Exception
     */
    public static List<String> subsectionImage(String path, String des, Integer width, Integer height) throws Exception {

        createDirectory(des);
        IMOperation op = new IMOperation();
        op.addImage(path);
        op.crop(width, height);
        op.addImage(des);

        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);

        return getSubImages(des);
    }

    /**
     * <ol>
     * <li>去除Exif信息</li>
     * <li>按指定的宽度等比缩放图片</li>
     * <li>降低图片品质</li>
     * <li>将图片分割分指定高度的小图</li>
     * </ol>
     * @param path 原文件路径
     * @param des 目标文件路径
     * @param width 指定宽度
     * @param height 指定高度
     * @param quality 保留品质
     * @return 小图路径
     * @throws Exception
     */
    public static List<String> HdImages(String path, String des, int width, int height, double quality) throws Exception {

        createDirectory(des);
        IMOperation op = new IMOperation();
        op.addImage(path);
        op.resize(width, null);
        op.quality(quality);
        op.crop(null, height);
        op.addImage(des);
        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op);
        return getSubImages(des);
    }

    /**
     * 创建目录
     * @param path
     */
    private static void createDirectory(String path) {

        File file = new File(path);
        if (file.exists()) {
            return;
        }
        file.getParentFile().mkdirs();
    }

    /**
     * 获取图片分割后的小图路径
     * @param des 目录路径
     * @return 小图路径
     */
    private static List<String> getSubImages(String des) {

        String fileDir = des.substring(0, des.lastIndexOf(File.separatorChar)); // 文件所在目录
        String fileName = des.substring(des.lastIndexOf(File.separatorChar) + 1); // 文件名称
        String n1 = fileName.substring(0, fileName.lastIndexOf(".")); // 文件名（无后缀）
        String n2 = fileName.replace(n1, ""); // 后缀

        List<String> fileList = new ArrayList<String>();
        String path = null;
        for (int i = 0;; i++) {
            path = fileDir + File.separatorChar + n1 + "-" + i + n2;
            if (new File(path).exists()) {
                fileList.add(path);
            } else {
                break;
            }
        }
        return fileList;
    }

    /**
     * 给图片加水印
     * @param srcPath       源文件路径
     * @param descPath      生成的文件路径
     * @param x              X坐标
     * @param y              Y坐标
     * @param font          水印文字
     * @throws Exception
     */
    public static void addImgText(String srcPath, String descPath, Integer x, Integer y, String font) throws Exception {

        String _font = new String(font.getBytes(), "GBK");
        GMOperation op = new GMOperation();
        //linux下需要把字体拷贝至服务器目录
        op.font("C:\\Windows\\Fonts\\simhei.ttf").gravity("southeast").pointsize(32).fill("#CCC").draw("text " + x + "," + y + " '" + _font + "'");
        op.addImage();
        op.addImage();
        ConvertCmd cmd = (ConvertCmd) getImageCommand("convert");
        cmd.run(op, srcPath, descPath);
    }

    //    public static void main(String[] args) throws Exception {
    // reduceQuality("C:\\img\\i.jpg", "C:\\img\\i_.jpg", 80);
    // System.out.println(getImageInfo("C:\\img\\2.jpg"));
    // scaleResizeImage("C:\\img\\2.jpg", "C:\\img\\3.jpg", 100, 50, false);
    // removeProfile("C:\\img\\3.jpg", "C:\\img\\3.jpg");
    // reduceQuality("C:\\img\\3.jpg", "C:\\img\\3.jpg", 80);
    //
    // List<String> list = subsectionImage("C:\\2.jpg", "C:\\img\\1.jpg",
    // null, 1000);
    // System.out.println(list);
    //
    // cropImage("C:\\2.jpg", "C:\\img\\1.jpg", 1000, 1000, 1600, 1000);
    //
    // scaleResizeImage("C:\\2.jpg", "C:\\img\\3.jpg", null, 1000, false);
    // scaleResizeImage("C:\\2.jpg", "C:\\img\\3_.jpg", null, 1000, true);

    // List<String> list = ____Hd("C:\\2.jpg", "C:\\img1\\1.jpg", 1600,
    // 1000, 90);
    // for (String s : list) {
    // System.out.println(s);
    // }

    // System.out.println(new File("C:\\4.jpg").isDirectory());
    //        HdImages("e:\\sample1.jpg", "D:\\123.jpg", 600, 450, 70);
    //        addImgText("e:\\sample1.jpg","e:\\sample1.jpg",230,100,"WWW.XHJ.COM");  //水印
    //    }
    
    
    /**  
     * 给图片添加水印  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     */  
    public static void markImageByIcon(byte[] iconPath, String srcImgPath,   
            String targerPath) {   
        markImageByIcon(iconPath, srcImgPath, targerPath, null);   
    }   
  
    /**  
     * 给图片添加水印、可设置水印图片旋转角度  
     * @param iconPath 水印图片路径  
     * @param srcImgPath 源图片路径  
     * @param targerPath 目标图片路径  
     * @param degree 水印图片旋转角度  
     */  
    public static void markImageByIcon(byte[] iconPath, String srcImgPath,   
            String targerPath, Integer degree) {   
        OutputStream os = null;   
        try {   
            Image srcImg = ImageIO.read(new File(srcImgPath));   
  
            BufferedImage buffImg = new BufferedImage(srcImg.getWidth(null),   
                    srcImg.getHeight(null), BufferedImage.TYPE_INT_RGB);   
  
            // 得到画笔对象   
            // Graphics g= buffImg.getGraphics();   
            Graphics2D g = buffImg.createGraphics();   
  
            // 设置对线段的锯齿状边缘处理   
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,   
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);   
  
            g.drawImage(srcImg.getScaledInstance(srcImg.getWidth(null), srcImg   
                    .getHeight(null), Image.SCALE_SMOOTH), 0, 0, null);   
  
            if (null != degree) {   
                // 设置水印旋转   
                g.rotate(Math.toRadians(degree),   
                        (double) buffImg.getWidth() / 2, (double) buffImg   
                                .getHeight() / 2);   
            }   
  
            // 水印图象的路径 水印一般为gif或者png的，这样可设置透明度   
            ImageIcon imgIcon = new ImageIcon(iconPath);   
  
            // 得到Image对象。   
            Image img = imgIcon.getImage();   
  
            float alpha = 0.5f; // 透明度   
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,   
                    alpha));   
  
            // 表示水印图片的位置   
            g.drawImage(img, 150, 300, null);   
  
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));   
  
            g.dispose();   
  
            os = new FileOutputStream(targerPath);   
  
            // 生成图片   
            ImageIO.write(buffImg, "JPG", os);   
          
        } catch (Exception e) {   
            e.printStackTrace();   
        } finally {   
            try {   
                if (null != os)   
                    os.close();   
            } catch (Exception e) {   
                e.printStackTrace();   
            }   
        }   
    }   
    
    
    
    
}