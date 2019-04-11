package main.string;

public class Doc_StringUtil {

    public static void main(String[] args) {

        // == replace 함수 만약 앞에 어떤 문자가 있다면 바꾸지 않음 ==
        String replaceExceptFrontChar = replaceExceptFrontChar(
                "1234\"1234\\\"1234\"\"1234",
                '"',
                '\\',
                "\\\"");
        String replaceExceptFrontChar_testA = replaceExceptFrontChar(
                "",
                '"',
                '\\',
                "\\\"");
        System.out.println("[replaceExceptFrontChar] " + replaceExceptFrontChar);
        System.out.println("[replaceExceptFrontChar_testA] " + replaceExceptFrontChar_testA);

        // == `raw json string`에서 파싱에러가 발생하지 않도록 정규식 처리` ==
        System.out.println(parseDoubleQuotes("abc\\\\\"\"abc"));
    }

    /**
     * replace 함수 만약 앞에 어떤 문자가 있다면 바꾸지 않음
     *
     * @date 2019.04.04
     * @author SDM
     * @param target - 대상 문자열
     * @param oldChar - 이전 글자
     * @param frontExceptChar - 앞의 예외 문자
     * @param newString - 새로운 문자
     * @return 치환된 문자열
     */
    public static String replaceExceptFrontChar(String target, char oldChar, char frontExceptChar, String newString) {
        if(target.length()>0) {
            StringBuffer sb = new StringBuffer();
            // index : 0
            if(target.charAt(0) == oldChar) {
                sb.append(newString);
            } else {
                sb.append(target.charAt(0));
            }
            // index : 1 ~ n-1
            for(int i=1; i<target.length(); i++) {
                if(target.charAt(i) == oldChar && target.charAt(i-1) != frontExceptChar) {
                    sb.append(newString);
                } else {
                    sb.append(target.charAt(i));
                }
            }
            return sb.toString();
        } else {
            return new String("");
        }
    }

    /**
     * `raw json string`에서 파싱에러가 발생하지 않도록 정규식 처리
     *
     * @date 2019.04.04
     * @author SDM
     * @param target - 대상 문자열
     * @return 치환된 문자열
     */
    public static String parseDoubleQuotes(String target) {
        return target
                .replace("\"", "\\\"")
                .replaceAll("\\\\{1,}", "\\\\");
    }

}
