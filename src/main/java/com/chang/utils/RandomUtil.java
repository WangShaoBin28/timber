package com.chang.utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.*;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Random;
import java.util.UUID;

/**
 * 工具类，用于产生随机数和随机字符串
 * @ClassName RandomUtil
 * @Author yj.c
 * @Date 2018/8/20 16:52
 * @Version 1.0
 **/
public final class RandomUtil {

    private RandomUtil() {}

    private static final String ACCEPT_NUM = "1234567890";

    /**
     * 产生随机的UUID
     * @return   uuid字符串
     */
    public static String getRandomUUID()
    {
        return UUID.randomUUID().toString();
    }

    /**
     * 产生随机的带或者不带-的UUID
     * @param dash 是否带dash符号
     * @return  uuid字符串
     */
    public static String getRandomUUID(final boolean dash) {
        return dash ? getRandomUUID() : getRandomUUID().replace("-", "");
    }

    /**
     * 产生随机字符串
     * @param acceptChars  接受的字符列表
     * @param length       长度
     * @return 符合要求的字符串
     */
    public static String getRandomString(final String acceptChars, final int length) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomCharacter(acceptChars));
        }
        return sb.toString();
    }

    /**
     * 产生随机数字
     * @param length       长度
     * @return 符合要求的字符串
     */
    public static String getRandomNum(final int length) {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(getRandomCharacter(ACCEPT_NUM));
        }
        return sb.toString();
    }

    public static char getRandomCharacter(final String acceptChars) {
        final int index = new Random().nextInt(acceptChars.length());
        return acceptChars.charAt(index);
    }

    public static void main(String[] args) {
//        System.out.println(getRandomUUID(false));
//        System.out.println(getRandomNum(9));
        System.out.println(getFileName("abc.txt"));
    }


    /**
     * 获取文件后缀
     * @param fileName
     * @return
     */
    public static String getSuffix(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 生成新的文件名
     * @param fileOriginName 源文件名
     * @return
     */
    public static String getFileName(String fileOriginName){
        return getRandomUUID(false) + getSuffix(fileOriginName);
    }


    public static String getPingYin(String str) {
        char[] t1 = null;
        t1 = str.toCharArray();
        String[] t2 = new String[t1.length];
        HanyuPinyinOutputFormat t3 = new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4 = "";
        int t0 = t1.length;
        try {
            for (int i = 0; i < t0; i++) {
                // 判断能否为汉字?
                if (Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    t2 = PinyinHelper.toHanyuPinyinStringArray(t1[i],t3);// 将汉字的几种全拼都存到t2数组?
                    if (t2!=null && t2[0] !=null) {
                        t4 += t2[0];// 取出该汉字全拼的第一种读音并连接到字符串t4?
                    }
                } else {
                    // 如果不是汉字字符，间接取出字符并连接到字符串t4?
                    t4 += Character.toString(t1[i]);
                }
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return t4;
    }

}
