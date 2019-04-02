package main.basic;

import java.util.function.*;

/**
 * #람다가뭐에요?
 * 람다식은 익명함수를 생성하기 위한 식으로 객체지향 언어 보다는 함수지향 언어에 가깝습니다.
 * - "arrow function" 을 이용 코드를 간결하게 해줍니다.
 * <p>
 * #타겟타입과 함수적인터페이스
 * 인터페이스 변수 = 람다식; (익명구현객체를 생성합니다.)
 * <p>
 * #함수적인터페이스 @FunctionInterface
 * 람다는 하나의 메소드를 정의하기 때문에
 * 두개이상의 추상메소드가 선언된 인터페이스는 람다식을 이용해서 구현객체를 생성할 수 없습니다.
 * <p>
 * #표준API의 함수적인터페이스
 * 자바8부터 함수적인터페이스는 java.util.function 표준 API 패키지로 제공합니다.
 * - Consumer :: 매개값은있고, 리턴값은 없음
 * - Supplier :: 매개값은없고, 리턴값은 있음
 * - Function :: 매개값도있고, 리턴값도 있음 (주로매가값매핑리턴)
 * - Operator :: 매개값도있고, 리턴값도 있음 (주로매개값연산리턴)
 * - Predicate :: 매개값도있고, 리턴타입은 boolean
 * <p>
 * => 함수적인터페이스성질 :: 하나의 추상메소드를 가지고 람다식으로 익명구현객체를 생성할수있는것
 * => 디폴트 및 정적메소드는 추상메소드가 아니라서 함수적인터페이스성질을 잃지 않습니다. (andThen()/compose())
 */

public class Doc_Lambda {
    public static int outerField = 10;

    public static void main(String[] args) {

        // =======================================================================================

        /**
         * #람다사용법
         *
         * */

        // 기존식
        Runnable runnableA = new Runnable() {
            @Override
            public void run() {

            }
        };

        // 람다식 (매개변수) -> {실행코드}
        Runnable runnableB = () -> {
        };

        // (int a) -> { System.out.println(a); };
        // (a) -> { System.out.println(a); };
        // a -> { System.out.println(a); };
        // () -> {  };

        // (x, y) -> { return x + y; }
        // (x, y) -> x + y;

        // =======================================================================================

        /**
         * #함수적인터페이스
         *
         * */
        MyFunctionInterfaceA fiA = () -> {
            String str = "method call A";
            System.out.println(str);
        };
        fiA.method();

        fiA = () -> {
            System.out.println("method call B");
        };
        fiA.method();

        fiA = () -> System.out.println("method call C");
        ;
        fiA.method();

        // #매개변수가있는람다식
        MyFunctionInterfaceB fiB = (x) -> {
            int result = x * 5;
            System.out.println(result);
        };
        fiB.method(5); // 25;

        fiB = (x) -> {
            System.out.println(x * 5);
        };
        fiB.method(5);

        fiB = (x) -> System.out.println(x * 5);
        fiB.method(5);

        // #리턴값이있는람다식
        MyFunctionInterfaceC fiC = (x, y) -> {
            int result = x + y;
            return result;
        };
        System.out.println(fiC.method(10, 10));

        fiC = (x, y) -> {
            return x + y;
        };
        System.out.println(fiC.method(10, 10));

        fiC = (x, y) -> x + y;
        System.out.println(fiC.method(10, 10));

        // #클래스맴버와 로컬변수사용
        class Inner {
            int innerField = 20;

            void methodA() {
                // 람다
                MyFunctionInterfaceA fi = () -> {
                    System.out.println("outter Field : " + outerField);
                    System.out.println("outter Field : " + Doc_Lambda.outerField);
                    System.out.println("inner Field : " + innerField);
                    System.out.println("inner Field : " + this.innerField); // 상위
                };
                fi.method();
            }

            void methodB(int arg/*arg -> final*/) {
                int localVariable = 40; // localVariable -> final

                MyFunctionInterfaceA fi = () -> {
                    System.out.println("arg : " + arg);
                    System.out.println("localVariable : " + localVariable);

                };
                fi.method();
            }
        }
        Inner inner = new Inner();
        inner.methodA();
        inner.methodB(5);

        // =======================================================================================

        /**
         * #Consummer
         *  Consumer :: 매개값은있고, 리턴값은 없음
         *
         * #API
         *  - accept(T t) => void
         * */

        Consumer<String> consummer = t -> System.out.println("UTF-8");
        consummer.accept("hello consumer?");

        BiConsumer<String, String> biConsumer = (s1, s2) -> System.out.println(s1 + s2);
        biConsumer.accept("hello", "consumer?");

        DoubleConsumer doubleConsumer = d -> System.out.println("java" + d);
        doubleConsumer.accept(8.0);

        ObjIntConsumer<String> objIntConsumer = (t, i) -> System.out.println(t + i);
        objIntConsumer.accept("Java", 8);

        // # andThen()
        Consumer<String> consumerA = (m) -> System.out.println("consumerA " + m);
        Consumer<String> consumerB = (m) -> System.out.println("consumerB " + m);
        Consumer<String> consumerC = (m) -> System.out.println("consumerC " + m);
        Consumer<String> consumerABC = consumerA.andThen(consumerB).andThen(consumerC);
        consumerABC.accept("hello???");

        // =======================================================================================

        /**
         * #Supplier
         *  Supplier :: 매개값은없고, 리턴값은 있음
         *
         * #API
         *  - get() => T
         *  - getAsBoolean() => boolean
         *  - getAsDouble() => double
         *  - getAsInt() => int
         *  - getAsLong() => long
         *
         * */

        Supplier<String> sA = () -> "only return!";
        System.out.println(sA.get());

        IntSupplier sB = () -> (int) Math.random() * 6 + 1;
        System.out.println(sB.getAsInt());

        // =======================================================================================

        /**
         * #Function
         *  Function :: 매개값도있고, 리턴값도 있음 (주로매가값매핑리턴)
         *
         * #API
         *  - apply(T t) => R
         *  - apply(T t, U u) => R
         *
         *      - Function
         *      - BiFunction
         *      - DoubleFunction
         *      - IntFunction
         *      - IntToDoubleFunction
         *      - IntToLongFunction
         *      - LongToDoubleFunction
         *      - LongToIntFunction
         *      - ToIntFunction
         *      - ToDoubleFunction
         *      - ToLongFunction
         *      - ToIntBiFunction
         *      - ToDoubleBiFunction
         *      - ToLongBiFunction
         *
         * */

        printString(t -> t + " ? ");
        printInt(t -> Integer.parseInt(t) + 100);

        // Function<String, Member> test; // String => Member 매핑
        // Function<String, String, Member> test; // String, String => Member 매핑

        // # andThen() & compose()
        Function<String, String> fA; // String => String으로 매핑
        Function<String, String> fB;
        Function<String, String> fAB;
        Function<String, String> fBA;
        fA = (m) -> m + " fA";
        fB = (m) -> m + " fB";
        fAB = fA.andThen(fB);
        String strFAB = fAB.apply("function andThen");
        System.out.println(strFAB);
        fBA = fA.compose(fB);
        String strFBA = fBA.apply("function compose");
        System.out.println(strFBA);

        // =======================================================================================

        /**
         * #Operator
         *  Operator :: 매개값도있고, 리턴값도 있음 (주로매개값연산리턴)
         *
         * #API
         *  - applyAsInt(int) => int
         *  - applyAsInt(int, int) => int
         *  - applyAsDouble(double) => double
         *  - applyAsDouble(double, double) => double
         *  - applyAsLong(long) => long
         *  - applyAsLong(long, long) => long
         *
         * */

        BinaryOperator<String> bo = BinaryOperator.minBy(
                (f1, f2) -> Integer.compare(Integer.parseInt(f1), Integer.parseInt(f2))
        );
        String strA = bo.apply("12", "13");
        System.out.println("min(12, 13) : " + strA);

        bo = BinaryOperator.maxBy(
                (f1, f2) -> Integer.compare(Integer.parseInt(f1), Integer.parseInt(f2))
        );
        String strB = bo.apply("12", "13");
        System.out.println("max(12, 13) : " + strB);

        // =======================================================================================

        /**
         * #Predicate
         *  Predicate :: 매개값도있고, 리턴타입은 boolean
         *
         * #API
         *  - test(T t) => boolean
         *      - Predicate
         *      - BiPredicate
         *      - IntPredicate
         *      - DoublePredicate
         *      - LongPredicate
         *
         * */

        // AND/OR/NOT
        IntPredicate predicateA = a -> a % 2 == 0;
        IntPredicate predicateB = a -> a % 3 == 0;
        IntPredicate predicateAND = predicateA.and(predicateB);
        IntPredicate predicateOR = predicateA.or(predicateB);
        IntPredicate predicateNOT = predicateA.negate();

        boolean result = predicateAND.test(6);
        System.out.println("[AND] " + 6 + " " + result);
        result = predicateAND.test(4);
        System.out.println("[AND] " + 4 + " " + result);

        result = predicateOR.test(4);
        System.out.println("[OR] " + 4 + " " + result);

        result = predicateOR.test(5);
        System.out.println("[OR] " + 5 + " " + result);

        result = predicateNOT.test(2);
        System.out.println("[NOT] " + 2 + " " + result);

        result = predicateNOT.test(3);
        System.out.println("[NOT] " + 3 + " " + result);

        // Equal
        Predicate<String> strP = Predicate.isEqual(null);
        System.out.println("[isEqual()] null, null : " + strP.test(null));
        System.out.println("[isEqual()] null, gg : " + strP.test("gg"));

        strP = Predicate.isEqual("java");
        System.out.println("[isEqual()] java, java : " + strP.test("java"));
        System.out.println("[isEqual()] java, jsp : " + strP.test("jsp"));

        System.out.println();
        // =======================================================================================

        /**
         * #메소드참조(::)
         *
         * */
        class Calc {
            public int instanceMethod(int x, int y) {
                return x + y;
            }
        }

        IntBinaryOperator ibo;
        ibo = (x, y) -> Doc_Lambda.staticMethod(x, y);
        System.out.println("[메소드참조 결과] " + ibo.applyAsInt(1, 2));

        ibo = Doc_Lambda::staticMethod;
        System.out.println("[메소드참조 결과] " + ibo.applyAsInt(1, 2));

        Calc c = new Calc();
        ibo = (x, y) -> c.instanceMethod(x, y);
        System.out.println("[메소드참조 결과] " + ibo.applyAsInt(3, 4));

        ibo = c::instanceMethod;
        System.out.println("[메소드참조 결과] " + ibo.applyAsInt(3, 4));

        // 매개변수의 매소드참조

        ToIntBiFunction<String, String> tibf;
        tibf = (a, b) -> a.compareToIgnoreCase(b); // 사전순체크
        System.out.println(tibf.applyAsInt("Java", "Java"));
        System.out.println(tibf.applyAsInt("JAVA", "Java"));
        System.out.println(tibf.applyAsInt("JAVAA", "Java"));

        tibf = String::compareToIgnoreCase; // 사전순체크
        System.out.println(tibf.applyAsInt("Java", "Java"));
        System.out.println(tibf.applyAsInt("JAVA", "Java"));
        System.out.println(tibf.applyAsInt("JAVAA", "Java"));

        // 생성자참조
        class Member {
            private String id;
            private String name;

            Member(String id) {
                this.id = id;
            }

            Member(String name, String id) {
                this.name = name;
                this.id = id;
            }
        }

        Function<String, Member> f = Member::new;
        Member mA = f.apply("new id");

        BiFunction<String, String, Member> bf = Member::new;
        Member mB = bf.apply("name", "new id");
    }

    @FunctionalInterface
    interface MyFunctionInterfaceA {
        public void method();
        // public void otherMethod(); // 컴파일오류
    }

    @FunctionalInterface
    interface MyFunctionInterfaceB {
        public void method(int a);
        // public void otherMethod(); // 컴파일오류
    }

    @FunctionalInterface
    interface MyFunctionInterfaceC {
        public int method(int a, int b);
        // public void otherMethod(); // 컴파일오류
    }


    public static void printString(Function<String, String> function) {
        String message = function.apply("Hello World");
        System.out.println(message);
    }

    public static void printInt(ToIntFunction<String> function) {
        int message = function.applyAsInt("12");
        System.out.println(message);
    }

    static int staticMethod(int x, int y) {
        return x + y;
    }


}
