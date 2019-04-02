package main.oop;

public class Doc_Interface {

    public static interface I {
        public int constant = 10; // (상수) => static final 내장

        public void abstractMethod(); // #강제 (추상메소드) => abstract 내장

        public default void defaultMethod() {
        } // #강제아님 (디폴트메소드)

        public static void staticMethod() {
        } // (정적메소드)
    }

    public static class C implements I {

        // 강제
        @Override
        public void abstractMethod() {

        }

        // 강제아님
        //        @Override
        //        public void defaultMethod() {
        //
        //        }
    }
}
