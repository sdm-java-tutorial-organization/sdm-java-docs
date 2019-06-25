package main.basic;

import java.util.function.Function;

/**
 * #ENUM
 *  - 열거타입변수로 한정된값만 갖을때 사용하는 자료형입니다.
 *  - IDE의 적극적인 지원가능
 *  - 허용 가능한 값을 제한할 수 있음
 *  - 리팩토링시 유리 (변경 범위가 최소화)
 *
 * #의문
 *  - `Enum` 파일로 관리해야 할까 ?
 *
 * #API
 *  - name() => String :: Enum 이름
 *  - ordinal() => int :: Enum 인덱스
 *  - compareTo() => int :: 비교갑
 *  - valueOf() => Enum :: Enum
 *  - values() => Enum[] :: Enum 배열
 *
 * #참조
 *  - [우아한형제들_기술블로그_Enum] http://woowabros.github.io/tools/2017/07/10/java-enum-uses.html
 *
 */

public class Doc_Enum {

    public static void main(String[] args) {

        // === `Enum` API 정보조회 ===
        /*MyEnum MyEnum = new MyEnum("MESSAGE_A", true);
        printEnumAPIInfo(MyEnum);*/

        // === 단건 `Enum` 정보조회 ===
        printEnumInfo(MyEnum.A);

        // message -> enum
        MyEnum MyEnumByEnum = MyEnum.A;
        /*MyEnum MyEnumByMessage = new MyEnum("MESSAGE_A");*/
    }

    // === `Enum` API 정보조회 (매개변수로 `enum`을 받을 수 없음) ===
    public static void printEnumAPIInfo() {
        System.out.println("[MyEnum.values()[0]] " + MyEnum.values()[0]);
        System.out.println("[MyEnum.valueOf(\"A\")] " + MyEnum.valueOf("A"));
    }

    // === 단건 `Enum` 정보조회 ===
    public static void printEnumInfo(MyEnum myEnum) {
        System.out.println("[MyEnum.A] " + myEnum);
        System.out.println("[MyEnum.A.ordinal()] " + myEnum.ordinal());
        System.out.println("[MyEnum.A.name()] " + myEnum.name());
        System.out.println("[MyEnum.A.toString()] " + myEnum.toString());
        System.out.println("[MyEnum.A.getMessage()] " + myEnum.getMessage());
        System.out.println("[MyEnum.A.isEnable()] " + myEnum.isEnable());
    }

    public String getMessage(MyEnum message) {
        return message.getMessage();
    }

    // #01. 기본
    /*enum MenuDefault {
        A, B, C;
    }*/

    // #02. 데이터 포함시키기
    /*enum MyEnum {
        A("MESSAGE_A"), B("MESSAGE_B"), C("MESSAGE_C");

        // 필드
        private final String message;

        // 생성자
        MyEnum(String message) {
            this.message = message;
        }

        // 메소드
        String getMessage() {
            return this.message;
        }
    }*/

    // #03. 데이터 2개 이상 포함시키기
    enum MyEnum {
        A("MESSAGE_A", true),
        B("MESSAGE_B", false),
        C("MESSAGE_C", true);

        // 필드
        private String message;
        private boolean enable;

        // 생성자
        MyEnum(String message, boolean enable) {
            this.message = message;
            this.enable = enable;
        }

        // 메소드
        String getMessage() {
            return this.message;
        }

        boolean isEnable() {
            return this.enable;
        }
    }

    // #04. 상태와 행위를 한곳에
    enum MyEnumWithCalculator {
        CALC_A(value -> value),
        CALC_B(value -> value * 10),
        CALC_C(value -> value * 3),
        CALC_ETC(value -> value);

        private Function<Long, Long> expr;

        MyEnumWithCalculator(Function<Long, Long> expr) {
            this.expr = expr;
        }

        public long calculate(long value) {
            return expr.apply(value);
        }
    }

}
