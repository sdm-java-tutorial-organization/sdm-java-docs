package main.oop;

import lombok.Data;

public class Doc_abstract {

    public static void main(String[] args) {

        B b = new B();
        System.out.println(b.toString());
        System.out.println(b.a);
        System.out.println(b.b);

        GenericB genericB = new GenericB();
        System.out.println(genericB.toString());
        System.out.println(genericB.a);
        System.out.println(genericB.b);

        LombokB lombokB = new LombokB();
        System.out.println(lombokB.toString());
        System.out.println(lombokB.a);
        System.out.println(lombokB.b);


    }

    /**
     * Method Name 얻기
     *
     * */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    // 추상클래스
    public static abstract class A {
        public int a = 10;
    }

    // 구현클래스
    public static class B extends A {
        public int b = 20;
    }

    // 추상클래스 (WithGeneric)
    public static abstract class GenericA<T> {
        public int a = 10;
    }

    // 구현클래스 (WithGeneric)
    public static class GenericB extends GenericA<GenericB> {
        public int b = 20;
    }

    // 추상클래스 (WithGeneric)
    @Data
    public static abstract class LombokA<T> {
        public int a = 10;
    }

    // 구현클래스 (WithGeneric)
    @Data
    public static class LombokB extends LombokA<GenericB> {
        public int b = 20;
    }

}
