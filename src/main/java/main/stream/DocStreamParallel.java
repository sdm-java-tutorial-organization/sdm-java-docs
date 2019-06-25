package main.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * # [동시성과 병렬성]
 * 동시성 :: 멀티 작업을 위해 멀티 스레드가 번갈아가며 실행하는 성질
 * 병렬성 :: 멀티 작업을 위해 멀티 코어를 이용해서 동시에 실행하는 성질
 * 데이터 병렬성 :: 전체 데이터를 쪼개서 병렬 처리 ( 스트림 )
 * 작업 병렬성 :: 서로 다른 작업을 병렬로 처리 ( 웹서버 )
 *
 * # [포크조인 프레임워크]
 * - 병렬 스트림을 이용하면 런타임시에 포크조인 프레임워크 동작
 * - 포크단계 :: 전체 데이터를 서브 데이터로 분리후 병렬처리
 * - 조인단게 :: 서브 데이터의 결과를 조합해서 최종결과 도출
 *
 * # [병렬처리의성능]
 *      스트림 병렬처리가 스트림 순차처리보다 항상 실행성능이 좋다고 판단해서는 안됩니다.
 *      병렬처리에 영향을 미치는 다음 3가지 요인을 보세요.
 * - 1. 요소의 수와 요소당 처리 시간
 *      컬렉션에 요소의 수가 적고 요소당 처리 시간이 짧으면 순차처리가 오히려 병렬처리보다 빠를수 있습니다.
 *      병렬처리는 스레드풀생성, 스레드 생성이라는 추가적인 비용이 발생합니다.
 * - 2. 스트림 소스의 종류
 *      [ArrayList, 배열]은 인덱스로 요소를 관리하기때문에 포크 단계에서 요소를 쉽게 분리할 수 있어 병렬 시간이 절약됩니다.
 *      [HashSet, TreeSet, LinkedList]은 요소의 분리가 쉽지 않습니다.
 * - 3. 코어의 수
 *      싱클다 코어 CPU일 경우에는 순차 처리가 빠릅니다.
 *      병렬 스트림을 사용할 경우 스레드의 수만 증가하고 동시성 작업으로 처리되기 때문에 좋지 못한 결과가 발생합니다.
 */
public class DocStreamParallel {

    public static void main(String[] args) {
        parallelStream();
        compare();
    }

    /**
     * 병렬로 스트림을 이용해서 커스텀리스트 만들기
     *
     * @date 2018.10.01
     * @author SDM
     * @version 1.0
     */
    public static void parallelStream() {
        List<Student> totalList = Arrays.asList(
                new Student("nameA", 10, Student.Zender.MALE, Student.City.Seoul),
                new Student("nameB", 20, Student.Zender.FEMALE, Student.City.Seoul),
                new Student("nameC", 30, Student.Zender.MALE, Student.City.Pusan),
                new Student("nameD", 40, Student.Zender.FEMALE, Student.City.Pusan),
                new Student("nameE", 50, Student.Zender.MALE, Student.City.Pusan)
        );

        MaleStudent maleStudent = totalList.parallelStream()
                .filter(s -> s.getZender() == Student.Zender.MALE)
                .collect(MaleStudent::new, MaleStudent::accumulate, MaleStudent::combine);

        maleStudent.getList().stream()
                .forEach(s -> System.out.println(s.getName()));
        System.out.println();
    }

    /**
     * 순차처리 vs 병렬처리
     *
     * @date 2018.10.01
     * @author SDM
     * @version 1.0
     */
    public static void compare() {
        List<Integer> list = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        // 순차
        long timeSequencial = sequencial(list);

        // 병렬
        long timeParallel = parallel(list);

        System.out.println("[순차] " + timeSequencial);
        System.out.println("[병렬] " + timeParallel);
        System.out.println();
    }

    /**
     * 작업메소드 (작업이 없을때 :: 순차 < 병렬)
     *
     * @date 2018.10.01
     * @author SDM
     * @version 1.0
     */
    public static void work(int value) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 순차처리
     *
     * @date 2018.10.01
     * @author SDM
     * @version 1.0
     */
    public static long sequencial(List<Integer> list) {
        long start = System.nanoTime();
        list.stream().forEach(a -> work(a));
        long end = System.nanoTime();
        long runTime = end - start;
        return runTime;
    }

    /**
     * 병렬처리
     *
     * @date 2018.10.01
     * @author SDM
     * @version 1.0
     */
    public static long parallel(List<Integer> list) {
        long start = System.nanoTime();
        list.stream().parallel().forEach(a -> work(a));
        long end = System.nanoTime();
        long runTime = end - start;
        return runTime;
    }

    static class Student implements Comparable<Student> {

        public enum Zender {MALE, FEMALE}

        ;

        public enum City {Seoul, Pusan}

        ;

        String name;
        int age;
        Zender zender;
        City city;


        public Student(String name, int age, Zender zender, City city) {
            this.name = name;
            this.age = age;
            this.zender = zender;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Zender getZender() {
            return zender;
        }

        public void setZender(Zender zender) {
            this.zender = zender;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        @Override
        public int compareTo(Student o) {
            return Integer.compare(age, o.getAge());
        }
    }

    static class MaleStudent {
        private List<Student> list; // 요소를 저장할 컬렉션

        public MaleStudent() {
            list = new ArrayList<Student>();
            System.out.println("[" + Thread.currentThread().getName() + "] MaleStudent()");
        }

        /**
         * 요소를 수집하는 메소드
         */
        public void accumulate(Student student) {
            list.add(student);
            System.out.println("[" + Thread.currentThread().getName() + "] accumulate()");
        }

        /**
         * 두 MaleStudent를 결합하는 메소드 ( 병렬처리시에만 호출 )
         */
        public void combine(MaleStudent other) {
            list.addAll(other.getList());
            System.out.println("[" + Thread.currentThread().getName() + "] combine()");
        }

        public List<Student> getList() {
            return list;
        }

        public void setList(List<Student> list) {
            this.list = list;
        }
    }
}
