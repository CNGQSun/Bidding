package com.dsmpharm.bidding.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 处理数据的工具类
 */
public class DataUtils {

    /**
     * 分页使用，判断是否是大于0的数字
     */
    private static Pattern pattern = Pattern.compile("^[1-9][0-9]*$");

    /**
     * 获得页码
     *
     * @return 返回>=1的数字
     * 如果给定的字符串不合法，返回1
     */
    public static int getPageCode(String str) {
        if (isNumber(str)) {
            return Integer.parseInt(str);
        }
        return 1;
    }

    /**
     * 是不是数字
     *
     * @param num
     */
    public static boolean isNumber(String num) {
        if (!isValid(num)) {
            return false;
        }
        Matcher matcher = pattern.matcher(num);
        return matcher.matches();
    }

    /**
     * 判断多个字符串是否是整数类型
     *
     * @param nums
     * @return 全部是整数时返回true
     */
    public static boolean isNumber(String... nums) {
        for (String num : nums) {
            if (!isNumber(num)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValid(String str) {
        return str != null && !str.trim().equals("");
    }


    /**
     * 判断字符串是否为空
     *
     * @param checkStr
     * @return
     */
    public static boolean isEmpty(Object checkStr) {
        if (checkStr == null) {
            return true;
        } else {
            return "".equals(checkStr) ? true : false;
        }
    }

    public static int getStartRecord(int currentPage, int pageSize) {
        if (currentPage > 1) {
            return (currentPage - 1) * pageSize;
        }
        return 0;
    }

    /**
     * 获取总页数
     *
     * @param count    数据总个数
     * @param pageSize 每页大小
     * @return 总个数
     */
    public static int getPageCount(int count, int pageSize) {
        int max = (count + pageSize - 1) / pageSize;
        return max;
    }

    /**
     * 是否是英文
     *
     * @param charaString
     * @return
     */
    public static boolean isEnglish(String charaString) {
        return charaString.matches("^[a-zA-Z]*");
    }
}
