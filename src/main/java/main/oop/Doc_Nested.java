package main.oop;

import javafx.scene.Parent;

public class Doc_Nested {

    public int a = 10;
    public static int b = 20;

    // #01. 인스턴스클래스
    class instanceClass {
        void instanceMethod() {
            a = 10;
        }
    }

    // #02. 정적클래스
    static class staticClass {
        void instanceMethod() {
            // a = 10; (X) <- 인스턴스에 접근불가
        }
    }

    void localMethod(int argsA, final int argsB) {
        int localA = 10;
        final int localB = 20;
        // localA += 30; // (final) 속성을 바꿔버림

        // #03. 로컬클래스
        //  [바깥클래스의 필드나 메소드]는 접근가능
        //  문제는 [메소드의 매개변수나 로컬변수]를 로컬클래스에서 사용할 경우 컴파일에러 발생
        //      :: 로컬변수는 메소드 실행이 끝나면, 스택메모리에서 사라지기때문에 문제가 발생
        //  자바는 컴파일시 로컬클래스에서 [메소드의 매개변수나 로컬변수]의 기억장소를 로컬클래스 내부에 복사해두고 사용합니다.
        //  그렇기 때문에 [final]로 해서 값이 변하지 않는다는 것을 명시해 줘야 합니다.

        // 결론 => 자바8부터는 로컬클래스에서 매개변수와 로컬변수를 사용할때 final 특성이 자동으로 선언됨 !
        class localClass {
            void method() {
                a = 10; // (O)
                b = 10; // (O)
                System.out.println(argsA);
                System.out.println(localA);
                System.out.println(argsB);
                System.out.println(localB);
            }
        }
    }

    // #04. 중첩인터페이스

    static class Button {
        OnClickListener listener;

        void setOnClickListener(OnClickListener listener) {
            this.listener = listener;
        }

        void touch() {
            // setOnClickListener 가 선행되야함
            listener.onClick();
        }

        // 인터페이스는 무조건 static입니다. ( 그래서 외부 클래스를 스태틱으로 하지 않았더니 에러가 났습니다. )
        interface OnClickListener {
            void onClick();
        }

        class Test {
        }
    }

    // #04-1. 중접인터페이스를 사용하는 클래스

    static class CallListener implements Button.OnClickListener {
        @Override
        public void onClick() {
            System.out.println("내부인터페이스를 구현했어요 !");
        }
    }

    // #05. 익명객체 ( 만약 클래스가 재사용되지 않고, 변수의 초기값으로만 사용할 경우라면 ? )

    class annoymousClass {
        // 익명객체의 구현
        // 부모클래스 [필드] = new 부모클래스(매개값) { ... }
        Parent field = new Parent() {
            int child;

            void childMethod() {
            }

            ;
        };

        // 로컬변수의 익명객체
        void method() {
            Parent localField = new Parent() {
            };

            // 매개변수의 익명객체
            methodB(new Parent() {
            });
        }

        void methodB(Parent p) {
        }
    }


    public static void main(String[] args) {
        Button btn = new Button();
        btn.setOnClickListener(new CallListener());
        btn.touch();
    }


}
