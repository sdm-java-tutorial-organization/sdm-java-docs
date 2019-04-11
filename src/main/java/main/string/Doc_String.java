package main.string;


import java.nio.charset.Charset;

/**
 * #String 클래스
 *  String 생성자는 매개타입에 따라 다양하게 존재
 *
 * #API
 *  - charAt(int index) => char :: 특정 위치의 문자 리턴
 *  - equals(Object obj) => boolean :: 두 문자열(문자열만) 비교, (자바는 문자열 리터럴이 동일하다면 동일한 String 객체를 참조하도록 되어 있습니다.)
 *  - getBytes() => byte[] :: byte[]로 리턴
 *  - getBytes(Charset charset) :: 특정 문자셋으로 인코딩된 바이트 배열을 얻으려면
 *  - indexOf(String str) => int :: 문자열 내에서 주어진 문자열의 위치를 리턴
 *  - length() => int :: 길이 리턴
 *  - replace(CharSequeence target, CharSequence replacement) => String :: target부분을 replacement로 대치한 새로운 문자열 리턴
 *  - substring(int beginIndex) => String :: beginIndex 위치에서 끝까지 잘라낸 새로운 문자열을 리턴
 *  - substring(int beginIndex, int endIndex) => String
 *  - toLowerCase() => String
 *  - toUpperCase() => String
 *  - trim() => String
 *  - valueOf(primitive type) => String :: 기본 타입값 (primitive type)을 문자열로 리턴
 */

class Doc_String {

    static Charset cs = Charset.defaultCharset();

    public static void main(String[] args) {

        // #String 생성자
        String str = "abcdefg";
        byte[] bytes = str.getBytes();

        // 바이트 배열 전체를 String으로 생성
        String strA = new String(bytes);

        // 지정한 문자셋으로 디코딩
        String strB = new String(bytes, cs);

        // 바이트 배열의 offset 인덱스 위치부터 length만큼 String 객체 생성
        String strC = new String(bytes, 0, 3);

        // 지정한 문자셋으로 디코딩
        String strD = new String(bytes, 0, 3, cs);

        // #charAt() - 문자추출

        // #equals() - 문자비교 ( 깊은비교 - overriding )
        // "==" - 주소비교

        System.out.println("string".equals("string")); //true
        System.out.println("string" == "string");      //true

        // #getBytes()
        try {
            byte[] arrA = "str".getBytes(cs);
            byte[] arrB = "str".getBytes("EUC-KR");
            byte[] arrC = "str".getBytes("UTF-8");
        } catch (Exception e) {
        }

        // EUK-KR은 getBytes()와 마찬가지로 알파벳은 1바이트 한글은 2바이트로 변환
        // UTF-8은 알파벳은 1바이트 한글은 3바이트로 변환

        // #indexOf()

        String indexString = "0123456789";
        System.out.println("[앞에서 인덱스 검색] " + indexString.indexOf("0"));
        System.out.println("[앞에서 인덱스 검색 (띠어서)] " + indexString.indexOf("0", 2));

        System.out.println("[뒤에서 인덱스 검색] " + indexString.lastIndexOf("9"));
        System.out.println("[뒤에서 인덱스 검색] " + indexString.lastIndexOf("9", 2));

        // #length()

        // #replace() - 기본적으로 모든 문자열을 바꿔줍니다.
        String oldStr = "JAVA";
        String newStr = oldStr.replace("A", "C");
        String newStrAll = oldStr.replaceAll("A", "C");
        System.out.println("[Old] " + oldStr);
        System.out.println("[New] " + newStr);
        System.out.println("[NewAll] " + newStrAll);

        // #substring()
        String targetStr = "0123456789";
        String subStrA = targetStr.substring(5);
        String subStrB = targetStr.substring(2, 3);
        String subStrC = targetStr.substring(6, 6);
        System.out.println("[target] " + targetStr);
        System.out.println("[substringA] " + subStrA);
        System.out.println("[substringB] " + subStrB);
        System.out.println("[substringC] " + subStrC);

        // #toLowerCase()
        // #toUpperCase()

        // #trim()

        // #valueOf()
        System.out.println(String.valueOf(10));
        System.out.println(String.valueOf(10.5));
        System.out.println(String.valueOf(true));

        // #split() - 정규식사용가능
        // String[] result = "str".split("정규표현식");

        String sampleA = "A&B&C&D";
        String[] arrStrA = sampleA.split("&");

        String sampleB = "AAA&BBB,CCC,DDD-EEE";
        String[] arrStrB = str.split("&|,|-"); // & 거나 , 거나 - 로 잘르세여.

        // == replace ==
        String myString = "abc";
        String newMyString = replace(myString);
        System.out.println(myString + " " + newMyString);
    }

    // == replace - 새로운객체로반환 ==
    public static String replace(String target) {
        return target.replace("a", "b");
    }

    // == replaceAll - 정규식을활용 ==
    public static String replaceAll(String target) {
        return target.replaceAll(null/*regex*/, null/*replacement*/);
    }

}
