package main.api;

import java.util.Comparator;
import java.util.Objects;

class Doc_Objects {

    /**
     * #Objects
     * 연산을 수행하는 정적 메소드들로 구성된 Object Utility 클래스
     * <p>
     * #API
     * compare(T a, T b, Comparator<T> c) => int :: 두 객체 a와 b를 Comparator로 비교
     * deepEquals(Object a, Object b) => boolean :: 두 객체의 깊은 비교 (배열의 항목까지 비교)
     * equals(Object a, Object b) => boolean :: 두 객체의 얕은 비교 (번지수만 비교)
     * hash(Object... values) => int :: 매개값으로 주어진 값들을 이용해서 해시 코드를 생성하는 역할
     * hashCode(Object o) => int :: 객체의 해시코드 생성
     * isNull(Object o) => boolean :: 객체가 null인지 조사
     * nonNull(Object o) => boolean :: 객체가 null이 아닌지 조사
     * requireNonNull(T obj) => T :: 객체가 null인 경우 예외 발생
     * requireNonNull(T obj, String message) => T :: 객체가 null인 경우 예외 발생 (주어진 예외 메시지 포함)
     * requireNonNull(T obj, Supplier<String> messageSupplier) => T :: 객체가 null인 경우 예외 발생 (람다식이 만든 예외 메시지 포함)
     * toString(Object o) => String :: 객체의 toString() 리턴값 리턴
     * toString(Object o, String nullDefault) => String :: 객체의 toString() 리턴값 리턴, 첫번째 매개값이 null이라면 두번째 인자 리턴
     */

    public static void main(String[] args) {


        // #compare
        class Student {
            int sno;

            Student(int sno) {
                this.sno = sno;
            }
        }
        class StudentComparator implements Comparator<Student> {
            @Override
            public int compare(Student a, Student b) {
                if (a.sno < b.sno) return -1;
                else if (a.sno == b.sno) return 0;
                else return 1;
                // 다음 코드와 같음
                // return Interger.compare(a.sno, b.sno);
            }
        }
        Student a = new Student(1);
        Student b = new Student(2);
        int result = Objects.compare(a, b, new StudentComparator());
        System.out.println("[Comparator를 이용한 비교] " + result);

        // #equals
        Objects.equals(null, null); // true
        Objects.equals(new Object(), null); // false
        Objects.equals(null, new Object()); // false

        // #deepEquals
        Objects.deepEquals(null, null); // true
        Objects.deepEquals(new Object(), null); // false
        Objects.deepEquals(null, new Object()); // false

        // 대상에 따라 역할이 다름
        // Objects.deepEquals(Object a, Object b); => a.equals(b); 동등 비교
        // Objects.deepEquals(Object[] a, Object[] b); => Array.deepEquals(a, b); 각각의 항목까지 비교

        boolean resultA = Objects.deepEquals(new Object(), new Object());
        boolean resultB = Objects.deepEquals(new int[]{1, 2, 3}, new int[]{1, 2, 3});
        System.out.println("[객체의 deepEquals비교] " + resultA); // false
        System.out.println("[배열의 deepEquals비교] " + resultB); // true

        // #hash - 매개값으로 주어진 값들을 이용, 해시코드를 생성
        class HashClass {
            int sno;
            String name;

            HashClass(int sno, String name) {
                this.sno = sno;
                this.name = name;
            }

            @Override
            public int hashCode() {
                return Objects.hash(sno, name);
            }
        }
        System.out.println("[임의로 만든 해시 ] " + Objects.hash("abc", "def"));
        System.out.println("[임의로 만든 해시 ] " + Objects.hash("abc", "def"));

        // #null 관리하기
        Object obj = new Object();

        System.out.println(Objects.isNull(obj));
        System.out.println(Objects.isNull(null));

        System.out.println(Objects.nonNull(obj));
        System.out.println(Objects.nonNull(null));

        try {
            Object rnnA = Objects.requireNonNull(obj);        // NullPointExcetion 발생
            Object rnnB = Objects.requireNonNull(null);   // NullPointExcetion 발생
        } catch (Exception e) {
            System.out.println("에러발생");
        }

        try {
            Object rnnA = Objects.requireNonNull(obj, "message");
            Object rnnB = Objects.requireNonNull(null, "message");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            Objects.requireNonNull(obj, () -> "이름이없어요");
            Objects.requireNonNull(null, () -> "이름이없어요");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}