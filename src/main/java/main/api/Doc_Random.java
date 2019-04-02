package main.api;

public class Doc_Random {
    public static void main(String[] args) {


        /**
         * [Random클래스]
         *
         * java.util.Random 클래스는 난수를 얻어내기 위해 다양한 메소드를 제공합니다.
         *
         * #Math.random()과의차이점은?
         * Math.random() 메소드는 0.0에서 1.0사이의 double 난수를 얻는데만 사용한다면,
         * Random클래스는 boolean, int, long, float, double 난수를 얻을 수 있습니다.
         *
         * #종자값?
         * Random(); :: 호출사마다 다른 종자값이 자동 설정
         * Random(long seed); :: 매개값으로 주어진 종자값이 설정
         *
         * #API?
         * boolean nextBoolean() :: boolean타입의 난수를 리턴
         * double nextDouble() :: ...
         * int nextInt() :: ...
         * int nextInt(int n) :: int 타입의 난수를 리턴 ( 0<= ~ < n )
         *
         * */


    }
}