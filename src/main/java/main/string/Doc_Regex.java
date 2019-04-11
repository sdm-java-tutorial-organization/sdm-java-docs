package main.string;

import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Doc_Regex {

    /**
     *
     * 정규식이해하기
     *  - 주의점
     *      - 역슬래시는 `\\\\` 로 표현됩니다. (정규식에선 역슬래시를 표현하기 위해서 `\\`가 사용됩니다)
     *
     * #기호
     *      - [abc] :: a,b,c 중 1개의 문자
     *      - [^abc] :: a,b,c 이외의 1개의 문자
     *      - [a-zA-Z] :: a~z, A~Z 중 하나의 문자
     *      - \d :: 한 개의 숫자, == [0-9]
     *      - \s :: 공백
     *      - \w :: 한 개의 알파벳 또는 한 개의 숫자, == [a-zA-Z_0-9]
     *      - ? :: 없은 또는 한개
     *      - * :: 없음 또는 한 개 이상
     *      - + :: 한 개 이상
     *      - {n} :: 정확히 n개
     *      - {n,} :: 최소 n개
     *      - {n,m} :: n개 ~ m개까지
     *      - () :: 그룹핑
     *
     * #TEL `(02|010)-\d{3,4}-\d{4}`
     *      - (02|010) :: 02 또는 010
     *      - - :: - 포함
     *      - \d{3,4} :: 3자리 또는 4자리
     *      - - :: - 포함
     *      - \d{4} :: 4자리
     *
     * #EMAIL `\w+@\w+\.\w(\.\w+)?`
     *      - \w+ :: 한개 이상의 알파벳 또는 숫자
     *      - @ :: @
     *      - \w+ :: 한개 이상의 알파벳 또는 숫자
     *      - \. :: .
     *      - \w+ :: 한개 이상의 알파벳 또는 숫자
     *      - (\.\w+)? :: \.\w+이 없거나, 한번더 올 수 있음
     *      => (\.)은 문자로서의 (.)을 말하고 (.)은 모든 문자 중에서 한 개의 문자를 뜻합니다.
     *
     * */

    public static void main(String[] args) {

        // Pattern 클래스를 사용해볼까.
        boolean result = Pattern.matches("정규식", "문자열");

        // 정규삭예제
//        System.out.println("a".matches("[a-zA-Z]"));
//        System.out.println("ㅁ".matches("[a-zA-Z]"));
//        System.out.println("1".matches("[a-zA-Z]"));

        // 태그예제
        // \\w+ => 1개이상의 알파벳또는숫자
        // ()? => 있을수도 없을수도
//        String regExpTag = "<(/)?\\w+(\\s+)?>"; // 기본태그양식
//        System.out.println("[TAG]");
//        System.out.println("<tag>".matches(regExpTag));     // trues
//        System.out.println("<tag  >".matches(regExpTag));   // true
//        System.out.println("</tag>".matches(regExpTag));    // true
//        System.out.println();

        // 그룹예제 (){0,} => 해당그룹이 0개이상
//        System.out.println("[GROUP]");
//        String regGroup = "(a){0,}";
//        String regGroupB = "(\\w+\\s){0,}";
//        System.out.println("".matches(regGroup));
//        System.out.println("a".matches(regGroup));
//        System.out.println("aa".matches(regGroup));
//        System.out.println("aaa".matches(regGroup));
//        System.out.println("aaaA".matches(regGroup));
//        System.out.println("".matches(regGroupB));
//        System.out.println("aa ".matches(regGroupB));
//        System.out.println("bb aa dd ".matches(regGroupB));
//        System.out.println();

        // 속성예제
//        System.out.println("[ATTRIBUTE]");
//        String regExpAttribute = "(((\\s+)?\\w+(\\s+)?=(\\s+)?\"(\\s+)?(\\w+)?(\\s+)?\")?){0,}"; // 속성양식
//        System.out.println("".matches(regExpAttribute));    // true
//        System.out.println("=\"hello\"".matches(regExpAttribute)); // false
//        System.out.println("class==\"hello\"".matches(regExpAttribute)); // false
//        System.out.println("class=\"\"".matches(regExpAttribute)); // true
//        System.out.println("class=\"hello\"".matches(regExpAttribute)); // true
//        System.out.println("  class   =   \"hello\"".matches(regExpAttribute)); // true
//        System.out.println("  class   =   \"hello\"id=\"ss\"".matches(regExpAttribute)); // true
//        System.out.println("  class   =   \"hello\" id = \"ss\"".matches(regExpAttribute)); // true
//        System.out.println("  class   =   \"hello\"id = \"ss\"".matches(regExpAttribute)); // true

        // 전체태그예제
        String regExpOpenTag = "<\\w+(\\s+)?>"; // 기본태그양식
        // String regExpOpenTagPlus = "<\\w+(\\s+)?((\\s+)?(\\w+(\\s+)?=(\\s+)?\"(\\s+)?((\\w+(\\s+)?){0,})?(\\s+)?\"|\\w+)?){0,}(\\s+)?>"; // 기본태그양식
        String regExpOpenTagPlus = "<\\w+(\\s+)?((\\s+)?(\\w+(\\s+)?=(\\s+)?\"[^\"]{0,}\"|\\w+)?){0,}(\\s+)?>"; // 기본태그양식
        String regExpCloseTag = "</\\w+(\\s+)?>"; // 기본태그양식
        String regExpOpenAndCloseTag = "<\\w+(\\s+)?((\\s+)?(\\w+(\\s+)?=(\\s+)?\"[^\"]{0,}\"|\\w+)?){0,}(\\s+)?/>"; // 기본태그양식

        // [오픈태그(기본형)]
        System.out.println("[오픈태그(기본형)]");
        System.out.println("<tag>".matches(regExpOpenTag));
        System.out.println("<tag >".matches(regExpOpenTag));
        System.out.println("<tag  >".matches(regExpOpenTag));

        // [오픈태그(속성형)] => src=\" ("이아닌어떤것도들어올수있다.) \"
        System.out.println("[오픈태그(속성형)]");
        System.out.println("<tag>".matches(regExpOpenTagPlus));
        System.out.println("<tag\n\n\n>".matches(regExpOpenTagPlus));
        System.out.println("<tag >".matches(regExpOpenTagPlus));
        System.out.println("<tag  >".matches(regExpOpenTagPlus));
        System.out.println("<tag src ggg disabled>".matches(regExpOpenTagPlus));
        System.out.println("<tag clss=\"a\">".matches(regExpOpenTagPlus));
        System.out.println("<tag    clss  =  \"  a  \"  >".matches(regExpOpenTagPlus));
        System.out.println("<tag    clss  =  \"  a  \"    id = \"a\">".matches(regExpOpenTagPlus));
        System.out.println("<tag    clss  =  \"  a  \"  disable  id = \"a\">".matches(regExpOpenTagPlus));
        System.out.println("<tag    clss  =  \"  a  \" hhh  disable  id = \"a\">".matches(regExpOpenTagPlus));
        System.out.println("<body id=\"body\" src = \" helloasd \" asds = \"asd\" ff = \"22\" >".matches(regExpOpenTagPlus));
        System.out.println("<body id=\"body\" src = \" hel loasd \" asds = \"a sd\" ff = \"22\" >".matches(regExpOpenTagPlus));
        System.out.println("<body id=\"body\" src = \" hel loasd.src \" asds = \"a=sd\" ff = \"22\" >".matches(regExpOpenTagPlus));

        // [닫기태그]
        System.out.println("[닫기태그]");
        System.out.println("</tag>".matches(regExpCloseTag));
        System.out.println("</tag >".matches(regExpCloseTag));
        System.out.println("</tag  >".matches(regExpCloseTag));
        System.out.println("</tag  a>".matches(regExpCloseTag));

        // [열고닫기태그]
        System.out.println("[열고닫히는태그]");
        System.out.println("<hello/>".matches(regExpOpenAndCloseTag));
        System.out.println("<hello src/>".matches(regExpOpenAndCloseTag));
        System.out.println("<hello src=\"hh\"/>".matches(regExpOpenAndCloseTag));
        System.out.println("<hello src=\"hh\" src = \"ss\"/>".matches(regExpOpenAndCloseTag));
        System.out.println("<hello src=\"h h.srchttP://\" ss = \" ss \"  />".matches(regExpOpenAndCloseTag));

        // == `[^]` 숫자를 제외하고 변환 ==
        String replaceAllExceptNum = replaceAllExceptNum("a1b2c3");
        System.out.println("[replaceAllExceptNum] a1b2c3 -> " + replaceAllExceptNum);
    }

    // `[^]` 숫자를 제외하고 변환
    public static String replaceAllExceptNum(String target) {
        return target.replaceAll("[^0-9]", "@");
    }

    /**
     * $1
     *
     * 괄호의 2가지 의미
     * (?:) <- non-capturing group
     * () <- captureing group : 매치되는 결과물을 나중에 사용할 수 있도록 저장
     * */
    public static String dollar(String target) {
        return target.replaceAll("\\+0([0-9]){1}\\:00", "+0$100");
    }

}
