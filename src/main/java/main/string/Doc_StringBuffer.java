package main.string;

/**
 * #StringBuffer란?
 * <p>
 * #String의한계
 * 일단, String의 기본 개념을 이해해야 합니다.
 * String은 내부 문자열을 수정할 수 없습니다.
 * 수정하는 것 처럼 보일 수 있으나, 새로운 객체가 계속적으로 생성되는 것입니다.
 * 그래서 String의 잦은 수정은 메모리관리에 부담을 줄 수 있습니다.
 * <p>
 * #그래서나왔다!
 * 그래서 java.lang 패키지는 "StringBuffer/StringBuilder" 클래스를 제공합니다.
 * 이 두 클래스는 내부 버퍼( buffer:데이터를 임시로 저장하는 메모리 )에 문자열을 저장,
 * 그 안에 추가/수정/삭제 할 수 있도록 설계되어 있습니다.
 * <p>
 * #두개의차이는?
 * StringBuffer와 StringBuilder의 사용 방법은 동일한데
 * StringBuffer는 멀티 스레드 환경에서 사용할 수 있도록 동기화가 적용되어 있어 스레드에 안전하지만,
 * StringBuilder는 단일 스레드 환경에서만 사용할 수 있도록 설계가 되어 있다는 점이 다릅니다.
 * <p>
 * #API
 * - append(String str) :: 문자열 끝에 주어진 매개값을 추가
 * - insert(int offset, String str) :: 문자열 중간에 주어진 매개값을 추가
 * - delete(int start, int end) :: 문자열의 일부분 삭제
 * - deleteCharAt(int index) :: 문자열에서 주어진 index의 문자를 삭제
 * - replace(int start, int end, String str) :: 문자열의 일부분을 다른 문자열로 대치
 * - reverse() :: 문자열의 순서를 뒤바꿈
 * - setCharAt(int index, char ch) :: 문자열에서 index 문자를 다른 문자로 대치
 * - substring(int start, int end) :: 문자열추출
 * - toString() :: 문자열로 변환
 */

public class Doc_StringBuffer {
    public static void main(String[] args) {

        // #생성자
        StringBuilder sBuilder1 = new StringBuilder();
        StringBuilder sBuilder2 = new StringBuilder(16);
        StringBuilder sBuilder3 = new StringBuilder("Java");

        // ㅁㅁㅁㅁ(insert)ㅁㅁㅁㅁ(deleteCharAtㅁ)ㅁㅁㅁ(deleteㅁㅁㅁㅁㅁㅁㅁ)ㅁㅁㅁㅁㅁㅁ (append)

        StringBuilder sb = new StringBuilder();
        sb.append("java ");
        sb.append("programming ");
        sb.append("deprecated/study");
        System.out.println(sb.toString());

        sb.insert(4, "2");
        System.out.println(sb.toString());

        sb.setCharAt(4, '6');
        System.out.println(sb.toString());

        sb.replace(6, 8, "Book");
        System.out.println(sb.toString());

        sb.delete(2, 4);
        System.out.println();

        int len = sb.length();
        System.out.println(len);

        // #substring
        StringBuffer sbB = new StringBuffer("3333");
        String subA = sbB.substring(1);
        String subB = sbB.substring(1, 3);
        System.out.println(subA);
        System.out.println(subB);
        System.out.println(sbB.toString());

        // #clear
        sb.delete(0, sb.length());

    }
}