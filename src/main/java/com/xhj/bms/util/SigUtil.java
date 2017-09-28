package com.xhj.bms.util;


import org.springframework.util.Base64Utils;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by wujian on 2017-05-17.
 */
public class SigUtil {
  /*  public static void main(String[] args) throws Exception {
        String subAccountSid = "5d99429fbbe140d6797211a3c9e6d6bb";
        String subAccountToken="d2a9979c69bf12a69028f02337f8794d";
        String dateformat="yyyyMMddHHmmss";
        String datetime =  DateUtil.formatDate(new Date(),dateformat);

        String sig = SigUtil.createSigStr(subAccountSid,subAccountToken,datetime);
        subAccountSid="6cc014bfa2ef9efb4d3f88bb813f024b";
        SimpleDateFormat format = new SimpleDateFormat(dateformat);
        Date d =new Date();
        datetime =format.format(d);
        datetime="20170517064512";
        String authorization =SigUtil.createAuthorization(datetime,subAccountSid);

        System.out.println("sig="+sig+"\nauthorization="+authorization);
    }*/

    /**
     * 生成REST API验证参数
     * @param subAccountSid
     * @param subAccountToken
     * @param datetime
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String createSigStr(String subAccountSid,String subAccountToken,String datetime) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        //sig = MD5（账号Id+账户授权令牌+时间戳），转成大写
        MessageDigest md5=MessageDigest.getInstance("MD5");
        String str=subAccountSid+subAccountToken+datetime;
        String sig=getPwd(str).toUpperCase();
        return sig;
    }

    /**
     * 创建报头验证信息
     * @param datetime
     * @param subAccountSid
     * @return
     */
    public static String createAuthorization(String datetime,String subAccountSid){
        String auth=subAccountSid+ ":" + datetime;
        String authorization= Base64Utils.encodeToString(auth.getBytes());
        return authorization;
    }

    /**
     12      * 获取MD5加密
     13      *
     14      * @param pwd
     15      *            需要加密的字符串
     16      * @return String字符串 加密后的字符串
     17      */
     public static String getPwd(String pwd) {
                 try {
                         // 创建加密对象
                         MessageDigest digest = MessageDigest.getInstance("md5");
                         // 调用加密对象的方法，加密的动作已经完成
                         byte[] bs = digest.digest(pwd.getBytes());
                         // 接下来，我们要对加密后的结果，进行优化，按照mysql的优化思路走
                         // mysql的优化思路：
                         // 第一步，将数据全部转换成正数：
                        String hexString = "";
                         for (byte b : bs) {
                                // 第一步，将数据全部转换成正数：
                                // 解释：为什么采用b&255
                                 /*
33                  * b:它本来是一个byte类型的数据(1个字节) 255：是一个int类型的数据(4个字节)
34                  * byte类型的数据与int类型的数据进行运算，会自动类型提升为int类型 eg: b: 1001 1100(原始数据)
35                  * 运算时： b: 0000 0000 0000 0000 0000 0000 1001 1100 255: 0000
36                  * 0000 0000 0000 0000 0000 1111 1111 结果：0000 0000 0000 0000
37                  * 0000 0000 1001 1100 此时的temp是一个int类型的整数
38                  */
                                int temp = b & 255;
                                // 第二步，将所有的数据转换成16进制的形式
                                 // 注意：转换的时候注意if正数>=0&&<16，那么如果使用Integer.toHexString()，可能会造成缺少位数
                                 // 因此，需要对temp进行判断
                                if (temp < 16 && temp >= 0) {
                                         // 手动补上一个“0”
                                         hexString = hexString + "0" + Integer.toHexString(temp);
                                     } else {
                                         hexString = hexString + Integer.toHexString(temp);
                                     }
                             }
                         return hexString;
                     } catch (NoSuchAlgorithmException e) {
                         e.printStackTrace();
                     }
                 return "";
     }


}
