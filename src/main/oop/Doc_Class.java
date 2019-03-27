package main.oop;

/**
 * #객체지향프로그래밍
 * 객체란 존재하거나 추상적으로 생각할 수 있는것중에서 자신의 속성을 가지고 있는 것을 말합니다.
 * 프로그래밍에서 객체란 자신의 속성을 필드와 메소드로 구현되있는 것을 말합니다.
 * 객체지향프로그래밍이란 그런 객체를 중심으로 프로그래밍을 하는 기법을 말합니다.
 * <p>
 * #객체지향프로그래밍의특징
 * - 캡슐화 (정보은닉) - 실제구현내용을감추는것
 * - 상속
 * - 다형성
 * <p>
 * #클래스
 * 클래스란 객체를 생성하기위한 도구( 틀 )입니다.
 * - 속성
 * - 생성자
 * - 메소드
 */

abstract class Doc_Class {

    public static void main(String[] args) {

        printClassName();
    }

    static class BoxA {

    }

    static class BoxB extends BoxA {

    }

    public static void printClassName() {
        System.out.println("=== printClassName call ===");

        BoxA boxA = new BoxA();
        BoxB boxB = new BoxB();

        System.out.println(boxA.getClass().getName()); // main.oop.Doc_Class$BoxA
        System.out.println(boxB.getClass().getName()); // main.oop.Doc_Class$BoxB

        System.out.println(boxA.getClass().getSimpleName()); // BoxA
        System.out.println(boxB.getClass().getSimpleName()); // BoxB

    }

}