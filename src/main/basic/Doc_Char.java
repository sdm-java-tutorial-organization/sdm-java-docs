package main.basic;

public class Doc_Char {

    public static boolean isProbablyArabic(String s) {
        for (int i = 0; i < s.length(); ) {
            int c = s.codePointAt(i);
            if (c >= 0x0600 && c <= 0x06E0)
                return true;
            i += Character.charCount(c);
        }
        return false;
    }

    public static boolean textContainsArabic(String text) {
        for (char charac : text.toCharArray()) {
            if (Character.UnicodeBlock.of(charac) == Character.UnicodeBlock.ARABIC) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {

        System.out.println(isProbablyArabic("a"));
        System.out.println(isProbablyArabic("b"));
        System.out.println(isProbablyArabic("c"));
        System.out.println(isProbablyArabic("A"));
        System.out.println(isProbablyArabic("B"));
        System.out.println(isProbablyArabic(","));
        System.out.println(isProbablyArabic("\""));
        System.out.println(isProbablyArabic("@"));
        System.out.println(isProbablyArabic("!"));
        System.out.println(isProbablyArabic("ㅁ"));
        System.out.println(isProbablyArabic("}"));

        System.out.println(textContainsArabic("a"));
        System.out.println(textContainsArabic("b"));
        System.out.println(textContainsArabic("c"));
        System.out.println(textContainsArabic("A"));
        System.out.println(textContainsArabic("B"));
        System.out.println(textContainsArabic(","));
        System.out.println(textContainsArabic("\""));
        System.out.println(textContainsArabic("@"));
        System.out.println(textContainsArabic("!"));
        System.out.println(textContainsArabic("ㅁ"));
        System.out.println(textContainsArabic("}"));

    }
}
