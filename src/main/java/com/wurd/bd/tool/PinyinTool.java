package com.wurd.bd.tool;

import net.sourceforge.pinyin4j.PinyinHelper;

public class PinyinTool {

    public static String toPinyin(String str) {
        StringBuilder stringBuilder = new StringBuilder();
        if (str != null) {
            for (int i = 0; i < str.length(); i++) {
                char c = str.charAt(i);
                if (isChinese(c)) {
                    String[] arr = PinyinHelper.toHanyuPinyinStringArray(c);
                    if (arr != null) {
                        stringBuilder.append(String.join("", arr));
                    }
                } else {
                    stringBuilder.append(c);
                }
            }
        }
        return stringBuilder.toString();
    }

    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A) {
            return true;
        }
        return false;
    }
}
