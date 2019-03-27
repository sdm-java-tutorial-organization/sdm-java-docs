package main.basic;


/**
 * 연산자 모음
 */
class Doc_Operator {

    public static void main(String[] args) {

        // ===================================================================

        // == 비트 ~ & | ^ (int를 기준으로 연산되기 때문에 결과값이 int) ==

        byte num1 = 45;
        byte num2 = 25;

        // & AND
        // byte result = num1 & num2;
        int resultA = num1 & num2;

        // | OR
        int resultB = num1 | num2;

        // ^ XOR
        int resultC = num1 ^ num2;

        // ~ NOT
        int v2 = ~num1 + 1; // NOT

        // == 32 비트를 문자열로 출력 (앞에 비트가 0이면 생략) ==
        String v1BinaryString = Integer.toBinaryString(v2);
        System.out.println(v1BinaryString);

        // ===================================================================

        // == 쉬프트 >> << >>> ==

        // << (제곱)
        int shiftA = 2 << 1;
        int shiftB = 2 << 2;
        int shiftC = 2 << 3;
        System.out.println(shiftA); // 4
        System.out.println(shiftB); // 8
        System.out.println(shiftC); // 16

        // >> (제급근) - 빈공간을 최상위 비트로 채움 ( 부호를 따라간다고 보면됨 )
        int shiftD = 8 >> 1;
        int shiftE = 8 >> 2;
        int shiftF = 8 >> 3;
        System.out.println(shiftD); // 4
        System.out.println(shiftE); // 2
        System.out.println(shiftF); // 1

        // >>> - 빈공간을 0으로 채움
        int shiftG = 16 >>> 1;
        int shiftH = -16 >>> 1;
        System.out.println(shiftG); // 8
        System.out.println(shiftH); // -8 (X)

        // ===================================================================

    }

    /* 앞에 0을 생략 시키지 않는 메소드 */
    public static String toBinaryString(int value) {
        String str = Integer.toBinaryString(value);
        while (str.length() < 32) {
            str = "0" + str;
        }
        return str;
    }
}