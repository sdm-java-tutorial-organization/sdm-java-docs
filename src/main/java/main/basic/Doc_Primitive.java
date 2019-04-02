package main.basic;

/**
 * [Primitive란?]
 * - 자료형의 가장 기본적인 구조를 이루는 단위를 말합니다.
 * - 종류로는 boolean, byte, char, short, int, long, float, double 8가지로 구성되어 있습니다.
 * <p>
 * [Literal이란?]
 * - 변수에 할당되는 값 자체를 Literal이라 말합니다.
 * <p>
 * [타입변환]
 * - 자동타입변환
 * 작은크기 => 큰크기로 타입변환할때는 자동으로 타입변환이 일어남
 * 데이터 손실이 없음
 * - 강제타입변환
 * 큰크기 => 작은크기로 타입변환할때는 강제로 타입변환을 시켜줘야함
 * 데이터 손실이 있을 수 있음
 */

public class Doc_Primitive {
    public static void main(String[] args) {

        // #원시타입과 참조타입의 비교 ( "Equals" 와 "==" 의 차이)

        // #원시타입비교
        System.out.println(1 == 1); // true
        // ... 이하생략 ...

        // #문자열타입비교 ( 문자열의 equals() 함수는 객체의 깊은 비교를 하도록 Override된 상태 )
        System.out.println("1" == "1"); // true
        System.out.println(new String("1") == "1"); // false
        System.out.println(new String("1") == new String("1")); // false

        System.out.println("1".equals("1")); // true
        System.out.println("1".equals(new String("1"))); // true
        System.out.println(new String("1").equals(new String("1"))); // true

        // #참조타입
        System.out.println(new Object() == new Object()); // false
        System.out.println(new Object().equals(new Object())); // false


    }
}