package com.xhj.bms.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * Created by Projack
 * 汉字转换位汉语拼音，英文字符不变
 */
public class PinYinUtils {
    /**
    * 汉字转换位汉语拼音首字母，英文字符不变
    * @param chines 汉字
    * @return 拼音
    */
    public static String converterToFirstSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char element : nameChar) {
            if (element > 128) {
                try {
                    if (isParticular("" + element)) {
                        pinyinName += element;
                    } else {
                        pinyinName += PinyinHelper.toHanyuPinyinStringArray(element, defaultFormat)[0].charAt(0);
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += element;
            }
        }
        return pinyinName;
    }

    /**
    * 汉字转换位汉语拼音，英文字符不变
    * @param chines 汉字
    * @return 拼音
    */
    public static String converterToSpell(String chines) {
        String pinyinName = "";
        char[] nameChar = chines.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (char element : nameChar) {
            if (element > 128) {
                try {
                    if (isParticular("" + element)) {
                        pinyinName += element;
                    } else {
                        pinyinName += PinyinHelper.toHanyuPinyinStringArray(element, defaultFormat)[0];
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pinyinName += element;
            }
        }
        return pinyinName;
    }

    public static boolean isParticular(String str) {
        String regEx = "[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.find();
    }

    //	    public static void main(String[] args) {
    //	    	System.out.println(converterToSpell(""));
    //		}
}
