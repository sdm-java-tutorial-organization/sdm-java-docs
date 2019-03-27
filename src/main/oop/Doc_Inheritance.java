package main.oop;

public class Doc_Inheritance {

    // 상속
    static class A {
    }

    static class B extends A {
    }

    // 추상클래스
    static abstract class abstractClass {
        public abstract void abstractMethod(int a);
    }

    // 구현클래스
    static class implementClass extends abstractClass {
        @Override
        public void abstractMethod(int a) {

        }
    }

    // 메인
    public static void main(String[] args) {


    }
}
