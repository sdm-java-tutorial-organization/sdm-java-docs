package main.basic;

/**
 * #Generic이뭐에요?
 * <p>
 * 제네릭은 클래스와 인터페이스 그리고 메소드를 정의할 때 타입을 파라미터로 사용할 수 있도록 하는 것입니다.
 * 타입 파라미터는 코드 작성시 구체적인 타입으로 대체되어 다양한 코드를 생성하도록 해줍니다.
 * - 타입 체크에 유용합니다. (컴파일시에 강한 "타입체크"를 할 수 있습니다.)
 * - 타입 변환(casting)을 제거합니다.
 */

public class Doc_Generic {
    public static void main(String[] args) {

        // #01 - 싱글타입파라미터
        class Box<T> {
            private T t;

            public T get() {
                return t;
            }

            public void set(T t) {
                this.t = t;
            }
        }

        // #02 - 멀티타입파라미터
        class Product<T, M> {
            private T t;

            public T getT() {
                return t;
            }

            public void setT(T t) {
                this.t = t;
            }

            private M m;

            public M getM() {
                return m;
            }

            public void setM(M m) {
                this.m = m;
            }
        }

        // #03 - 제네릭메소드
        class Util {
            public <T> Box<T> boxing(T t) {
                Box<T> box = new Box<T>();
                box.set(t);
                return box;
            }
        }
        Util util = new Util();
        Box<Integer> box1 = util.<Integer>boxing(100);
        System.out.println(box1.get());

        // #04 - 제한파라미터
        class Car {
            public <T extends Number> int compare(T t1, T t2) {
                double v1 = t1.doubleValue();
                double v2 = t2.doubleValue();
                return Double.compare(v1, v2);
            }
        }
        Car car = new Car();
        // System.out.println(car.compare("a", "b")); (X)
        System.out.println(car.compare(100, 100));
        System.out.println(car.compare(99, 100));
        System.out.println(car.compare(100, 99));

        // #05 - 와일드카드타입 (? - 와일드카드)
        // 제네릭타입<?> - 제한없음
        // 제네릭타입<? extends 상위타입> - 상위클래스제한
        // 제네릭타입<? super 하위타입> - 하위클래스제한

        // 상속 :: 사람 => (일꾼 / 학생 => (고등학생))
        class Course<T> {
            private String name;
            private T[] students;

            Course(String name, int capacity) {
                this.name = name;
                // this.students = new T[capacity]; (X)
                this.students = (T[]) (new Object[capacity]);
            }

            String getName() {
                return this.name;
            }

            T[] getStudents() {
                return students;
            }

            void add(T t) {
                for (int i = 0; i < students.length; i++) {
                    if (students[i] == null) {
                        students[i] = t;
                        break; // for문 break가능? (O)
                    }
                }
            }

            // void registerCourse(Course<?> course) { }
            // void registerCourseStudent(Course<? extends Student> course) { }
            // void registerCourseWorker(Course<? extends Worker> course) { }
        }

        // #05 제네릭클래스의 상속과 구현
        class ChildProduct<T, M, C> extends Product<T, M> {
            private C c;

            public C getC() {
                return c;
            }

            public void setC(C c) {
                this.c = c;
            }
        }

        // #06 제네릭인터페이스의 상속과 구현 (내부 인터페이스 불가)
        //  interface Storage<T> {
        //     public void add(T t, int index);
        //     public T get(int index);
        // }
        // class StorageImpl<T> implements Storage<T> {
        //    private T[] array;
        //     StorageImpl(int capacity) {
        //         this.array = (T[]) new Object[capacity];
        //     }
        // }

    }


}