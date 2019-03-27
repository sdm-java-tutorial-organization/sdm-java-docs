package main.string;

import java.util.StringTokenizer;

/**
 * #StringTokenizer
 * 문자열이 특정 구분자로 (delimiter)로 연결되어 있을 경우,
 * 구분자를 기준으로 부분 문자열을 분리하기위해서는 Spring.split()을 이용하거나
 * StringTokenizer를 이용해야 합니다.
 * <p>
 * #String.split과 StringTokenizer의 차이 ?
 * - String의 Split 메소드를 이용하거나 (정규 표현식으로 구분)
 * - StringTokenizer 클래스를 이용할 수 있습니다. (문자로 구분)
 * - split은 정규식이용 / stringtokenizer 는 문자이용
 * - split은 배열로 리턴 / stringtokenizer 는 이터레이러로 이용
 * <p>
 * <p>
 * #API
 * - countTokens() => int :: 꺼내지 않고 남아 있는 토큰 수
 * - hasMoreTokens() => boolean :: 남아 있는 토근이 있는지 여부
 * - nextToken() => String :: 토큰을 1개씩 꺼내옴
 */

class Doc_StringTokenizer {

    public static void main(String[] args) {

        // StringTokenizer st = new StringTokenizer("문자열", "구분자");

        String sample = "AAA/BBB/CCC";
        StringTokenizer st = new StringTokenizer(sample, "/");

        int countTokens = st.countTokens();
        for (int i = 0; i < countTokens; i++) {
            String token = st.nextToken();
            System.out.println(token);
        }
        System.out.println();

        StringTokenizer stB = new StringTokenizer(sample, "/");
        while (st.hasMoreTokens()) {
            String token = st.nextToken();
            System.out.println(token);
        }
    }


}