package main.basic;

/**
 * #ENUM자료형이뭔가요?
 * <p>
 * 열거타입변수로 한정된값만 갖을때 사용하는 자료형입니다.
 * <p>
 * #API
 * - name() => String ::
 * - ordinal() => int ::
 * - compareTo() => int ::
 * - valueOf() => 열거타입 ::
 * - values() => 열거배열 ::
 *
 * @참조1 :: http://woowabros.github.io/tools/2017/07/10/java-enum-uses.html
 */

public class Doc_Enum {

    public static void main(String[] args) {

        // #인덱스조회 :: indexOf => values()[ordinal()]

        System.out.println(MenuMessage.A);
        System.out.println(MenuMessage.A.getMessage());


    }

    public String getMessage(MenuMessage message) {
        return message.getMessage();
    }

    // #01. 기본
    enum MenuDefault {
        A, B, C;
    }

    // #02. 메세지 포함시키기
    enum MenuMessage {
        A("HELLO_A"), B("HELLO_B"), C("HELLO_C");

        // 필드
        private final String message;

        // 생성자
        MenuMessage(String message) {
            this.message = message;
        }

        // 메소드
        String getMessage() {
            return this.message;
        }
    }

}
