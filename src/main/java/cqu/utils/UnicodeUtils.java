package cqu.utils;

public class UnicodeUtils {
    public static String decodeUnicode(final String dataStr) {
        String s = dataStr.replace(";", "");
        String [] seqs = s.split("&#");
        final StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < dataStr.length(); i++) {
            char letter = (char) Integer.parseInt(seqs[i], 10); // 16进制parse整形字符串。
            buffer.append(new Character(letter).toString());
        }
        return buffer.toString();
    }

}

