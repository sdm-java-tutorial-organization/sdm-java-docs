package main.oop;

/**
 * #정적이란?
 * <p>
 * #정적클래스
 * 외부클래스는 일단 기본적으로 정적입니다.
 * 그래서 static을 사용하면 컴파일 에러 발생
 * <p>
 * #왜내부정적클래스는인스턴스생성이가능할까요?
 */

public class Doc_Static {

    // 정적맴버
    static class Static {
        static int a = 10;
        static int b = 10;
        static int c;

        // 정적블록 ( 인스턴스맴버를 초기화 할 수는 없다 )
        static {
            c = a + b;
        }
    }

    // 내부 [Static] 클래스
    static class A {
        public A() {
            System.out.println("A");
        }

        public void methodA() {
            System.out.println("mA");
        }

        // 가능 (Static-Class)
        static public void methodC() {

        }
    }

    // 내부 [Static] 클래스 ( Static --(상속가능)--> Static )
    static class C extends A {
        public void methodA() {
            super.methodA();
        }
        // static 메소드 오버라이딩이 가능할까 ?
    }

    // 내부 [Static] 아닌 클래스 ( Static --(상속가능)--> Static아닌 )
    class B extends A {
        // 불가능 (Non-Static-Class)
        // static int a = 0;
        public B() {
            super();
            System.out.println("B");
        }

        @Override
        public void methodA() {
            super.methodA();
            System.out.println("mB");
        }

        public void methodB() {
            super.methodA();
            System.out.println("mB");
        }
    }


    // static class C extends B { }

    public static void main(String[] args) {

        // [Static]은 언제 생성될까요 !?
        //  => Static 키워드는 클래스가 메모리에 올라갈때 이미 자동적으로 생성 !!!

        // [Static이 아닌 클래스]는 [Static클래스]의 부모클래스가 될 수 없다. ( Static 클래스는 미리 메모리에 올라가서 Static이 아닌 클래스가 상속받으려 할때 받을 수 있다.
        // [Static클래스]는 [Static이 아닌 클래스]의 부모가 될 수 있다. ( Static 클래스는 미리 메모리에 올라가는데 아직 올라가지 않은 Stiatc아닌 클래스를 상속받으려고 해서 그런다. )

        // 왜 내부클래스는 Static 이여야 인스턴스가 생성이 가능할까요 ?
        // [inner NonStatic class]
        //  => 반드시 부모의 인스턴스가 있는 경우만 해당 inner class의 인스턴스를 만들 수 있다는 제약 조건을 명시할 수 있다.
        //  => 그러므로 반드시 (부모의 인스턴스).new 로 생성해야 한다.
        // [inner Static class]
        //  => 부모의 인스턴스의 생성과는 상관없이 독립적인 생성이 가능하다.

        Doc_Static ds = new Doc_Static();

        Doc_Static.A InnerA;
        Doc_Static.B InnerB;

        A aA = new Doc_Static.A();
        A aB = new A();
        B bA = (ds).new B();

        // 기본클래스[이미Static]에서는 클래스가 [Static]하지 않더라도 [Static]메소드를 생성할 수 있습니다.
        // 하지만 내부클래스에서는 클래스가 [Static]하지 않으면 [Static] 맴버를 생성할 수 없습니다.

        // [Static 사용지침]
        // 먼저 클래스 맴버변수중에서 모든 인스턴스가 공통으로 사용하는 값들을 추려서 static으로 사용
        // 작성한 메서드중에서 인스턴스변수를 사용하지 않는다면 static을 붙일수도 있습니다.

        // [Static] 매소드 오버라이딩 ???
        //  => Static 메소드는 다형성을 지원하지 않아요 !
        // 컴파일러와 JVM은 static 메서드에 대해서는 실제 객체를 찾는 작업을 시행하지 않기 때문에,
        // class method(static method)의 경우, 컴파일 시점에 선언된 타입의 메소드를 호출합니다.

        // http://rockdrumy.tistory.com/214

    }

    static void method() {

    }

}
