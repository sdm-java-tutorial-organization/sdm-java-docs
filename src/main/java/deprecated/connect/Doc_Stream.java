package deprecated.connect;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * #스트림이 무어에요?
 * 자바에서 스트림은 2가지 의미가 있습니다.
 * 첫번째는 입출력스트림이고,
 * 두번째는 지금 이야기할 컬렉션요소등 그룹데이터를
 * 하나씩 참조해서 람다식으로 처리할 수 있도록 해주는 반복자입니다.
 * <p>
 * #스트림의특징(Iterator대신왜쓰는거에요?)
 * Iterator와 비슷한 역할을 하지만, 다음과 같은 특징을 갖습니다.
 * - 1. [람다식]으료 요소 처리 코드를 제공하는 점
 * - 2. [내부반복자]를 사용해 [병렬처리]가 쉽다는 점
 * - [외부반복자] 개발자가 코드로 직접 컬렉션의 요소를 반복해서 가져오는 코드 (index, for, iterator, while)
 * - [내부반복자] 컬렉션 내부에서 요소들을 반복시켜, 개발자는 요소당 처리할 코드만 제공하는 코드 패턴
 * - 3. [중간처리와 최종처리]를 할 수 있다는 점
 * 중간처리 :: 매핑, 필터링, 정렬
 * 최종처리 :: 반복, 카운팅, 평균, 총합
 * - 과정
 * - => 컬렉션
 * - => 오리지널 스트림
 * - => 중간처리 ( 필터링처리 중간스트림 / 매핑처리 중간스트림 )
 * - => 최종처리 ( 집계처리 결과물 )
 * - => 결과
 * <p>
 * #스트림파이프라인
 * 대량의 데이터를 가공해서 축소하는 것을 일반적으로 리덕션이라고 합니다. (평균, 최대, 최소...)
 * 리덕션에는 필터링/매핑/정렬/그룹핑등 중간과정이 필요합니다.
 * 스트림에서는 처리하는 과정들을 스트림 파이프라인이라고 합니다.
 * <p>
 * #병렬처리
 * 병렬처리란 멀티코어 CPU환경에서 하나의 작업을 분할해서 각각의 코어가 병렬적으로 처리하는 것을 말합니다.
 * 병렬처리의 목적은 작업시간처리를 줄이기 위한 것입니다.
 * <p>
 * #동시성&병렬성
 * [동시성] 멀티작업을 위해 멀티스레드가 번갈아가며 실행하는 성질
 * [병렬성] 데이터 병렬성과 작업 방렬성으로 구분이 가능
 * [데이터 병렬성] 전체 데이터를 쪼개어 서브 데이터들로 만들고, 이 서브데이터들을 병렬처리해서 작업을 빨리 끝내는 것입니다.
 * [작업 병렬성] 서로 사른 작업을 병렬적으로 처리하는 것을 말합니다.
 * <p>
 * #포크조인(ForkJoin) 프레임워크
 * 병렬스트림은 요소들이 병렬 처리하기 위해 포크조인 프레임워크를 사용합니다.
 * 병렬스트림은 런타임시에 포크조인 프레임워크가 동작하는데
 * => 포크단계에서 전체데이터를 서브데이터로 분리합니다.
 * => 서브데이터를 멀티코어에서 병렬로 처리합니다.
 * => 조인단계에서는 서브결과를 결합해서 최종결과를 만들어냅니다.
 * <p>
 * #병렬처리의성능
 * 스트림 병렬처리가 스트림 순차처리보다 항상 실행성능이 좋다고 판단해서는 안됩니다.
 * 병렬처리에 영향을 미치는 다음 3가지 요인을 보세요.
 * - 요소의 수와 요소당 처리 시간
 * 컬렉션에 요소의 수가 적고 요소당 처리 시간이 짧으면 순차처리가 오히려 병렬처리보다 빠를수 있습니다.
 * 병렬처리는 스레드풀생성, 스레드 생성이라는 추가적인 비용이 발생합니다.
 * - 스트림 소스의 종류
 * [ArrayList, 배열]은 인덱스로 요소를 관리하기때문에 포크 단계에서 요소를 쉽게 분리할 수 있어 병렬 시간이 절약됩니다.
 * [HashSet, TreeSet, LinkedList]은 요소의 분리가 쉽지 않습니다.
 * - 코어의 수
 * 싱클 코어 CPU일 경우에는 순차 처리가 빠릅니다.
 * 병렬 스트림을 사용할 경우 스레드의 수만 증가하고 동시성 작업으로 처리되기 때문에 좋지 못한 결과가 발생합니다.
 */

public class Doc_Stream {

    public static String DIR_DATA = System.getProperty("user.dir") + "/src/data";
    public static int MALE = 0;
    public static int FEMALE = 1;
    public static int sum = 0;

    public static void main(String[] args) {

        // =======================================================================================

        /**
         * #스트림의기본
         *
         * */

        // 기존식
        List<String> list = Arrays.asList("a", "b", "c");
        Iterator<String> i = list.iterator();
        System.out.printf("[Iterator(기존식)] ");
        while (i.hasNext()) {
            String name = i.next();
            System.out.printf(name + " ");
        }
        System.out.println();

        // 람다식
        System.out.printf("[Stream(람다식)] ");
        Stream<String> stream = list.stream();
        stream.forEach(name -> System.out.printf(name + " "));
        System.out.println();
        System.out.println();

        // =======================================================================================

        /**
         * #내부반복자 (병렬처리의효과)
         *  parallelStream() => Stream<T>
         *
         * */

        stream = list.stream(); // @Stream은 한번 사용시 다시사용할 수 없습니다. (재선언)
        stream.forEach(String::toString);
        System.out.println();

        Stream<String> parallelStream = list.parallelStream();
        parallelStream.forEach(String::toString);

        // =======================================================================================

        /**
         * #스트림의종류 (컬렉션, 배열, 숫자, 파일, 디렉토리)
         *  [컬랙션]
         *      - java.util.stream() => Stream<T>
         *      - java.util.parallelStream() => Stream<T>
         *  [배열]
         *      - Arrays.stream(T[]), Stream.of(T[]) => Stream<T>
         *      - Arrays.stream(int[]), IntStream.of(int[]) => IntStream
         *      - ...
         *  [숫자범위]
         *      - IntStream.range(int, int); => IntStream
         *      - intStream.rangeClosed(int, int); => IntStream
         *  [디렉토리]
         *      - File.find(Path, int BiPredicate, FileVisitOption); => Stream<File>
         *      - File.list(Path); => Stream<File>
         *  [파일]
         *      - File.lines(Path, Charset); => Stream<String>
         *      - BufferedReader.lines() => Stream<String>
         *  [랜덤수]
         *      - Random.doubles() => DoubleStream
         *      - Random.ints() => IntStream
         *      - Random.longs() => LongStream
         * */

        // [컬렉션]으로 스트림얻기
        // List<Integer> intList = Arrays.asList(1,2,3);

        // [배열]로 스트림얻기
        String[] strArray = {"J", "A", "V", "A"};
        Stream<String> stringStream = Arrays.stream(strArray);
        stringStream.forEach(a -> System.out.printf(a + " "));
        System.out.printf("[배열스트림(문자)] ");
        System.out.println();

        // [숫자범위]로 스트림얻기
        IntStream intStream = IntStream.rangeClosed(0, 100);
        intStream.forEach(a -> sum += a);
        System.out.printf("[숫자범위스트림(평균)] " + sum);
        System.out.println();

        // [파일]로 스트림얻기 (File.lines()/BufferedReader-lines())
        Path path = Paths.get(DIR_DATA, "/tutorial.txt");

        // [CASE1] File.lines()
        try {
            Stream<String> fileStream = Files.lines(path, Charset.defaultCharset());
            fileStream.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

        // [CASE2] BufferedReader lines()
        try {
            File file = path.toFile();
            FileReader fileReader = new FileReader(file);
            BufferedReader br = new BufferedReader(fileReader);
            stream = br.lines();
            stream.forEach(System.out::println);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println();

        // [디렉토리]로부터 스트림얻기
        try {
            Path dirPath = Paths.get(DIR_DATA);
            Stream<Path> dirStream = Files.list(dirPath);
            System.out.printf("[디렉토리스트림] ");
            dirStream.forEach(p -> System.out.print(p.getFileName() + " "));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();

        // =======================================================================================

        /**
         * #스트림파이프라인 (중간처리 & 최종처리)
         *
         * #API (중간처리)
         *  필터링
         *      - distinct() :: 중복제거
         *      - filter() :: 매개값으로 주어진 Predicate가 true를 리턴하는 요소만 필터링
         *  매핑
         *      - flatMapXXX() :: 요소를 대체하는 복수개의 요소들로 구성된 새로운 스트림 리턴
         *      - flatMap()
         *      - flatMapToDouble()
         *      - flatMapToInt()
         *      - flatMapToLong()
         *      - mapXXX() :: 새로운 스트림을 리턴
         *      - map()
         *      - mapToDouble()
         *      - mapToInt()
         *      - mapToLong()
         *      - mapToObj()
         *      - asXXXStream() :: :: IntStream int 또는 LongStream long 요소를 double 요소로 타입 변환해 DoubleStream을 생성
         *      - asDoubleStream()
         *      - asLongStream()
         *      - boxed() :: :: int, long, double 요소를 Integer, Long, Double 요소로 박싱해서 Stream 합니다.
         *  정렬
         *      - sorted()
         *  루핑
         *      - peek() :: 중간처리단계에서 전체요소를 푸링하며 추가적인 작업을 하기위해사용
         *
         * */

        // 중간처리 => 평균계산하기 (숫자)
        List<Integer> intList = Arrays.asList(1, 2, 3);
        double avg = intList.stream()
                .mapToInt(Integer::byteValue)
                .average()
                .getAsDouble();
        System.out.println("[평균 (숫자)] " + avg);

        // 중간처리 => 평균계산하기 (객체)
        class Member {
            private String name;
            private int sex;
            private int age;

            public Member(String name, int sex, int age) {
                this.name = name;
                this.sex = sex;
                this.age = age;
            }

            public int getSex() {
                return this.sex;
            }

            public int getAge() {
                return this.age;
            }
        }
        List<Member> memList = Arrays.asList(
                new Member("a", MALE, 30),
                new Member("b", MALE, 31),
                new Member("c", FEMALE, 32),
                new Member("d", FEMALE, 33),
                new Member("e", MALE, 34),
                new Member("f", FEMALE, 35)
        );
        double ageAvg = memList.stream()
                .filter(m -> m.getSex() == MALE)
                .mapToInt(Member::getAge)
                .average()
                .getAsDouble();
        System.out.println("[평균 (객체)] " + ageAvg);
        System.out.println();

        List<String> strList = Arrays.asList("a", "a", "a", "b", "b", "b", "c", "d d", "e e");

        // distinct()
        strList.stream()
                .distinct()
                .forEach(n -> System.out.print(n + ' '));
        System.out.println();

        // filter()
        strList.stream()
                .filter(n -> n.startsWith("a"))
                .forEach(n -> System.out.printf(n + " "));
        System.out.println();

        // #매핑(flatMapXXX(), mapXXX(), asXXXStream(), boxed())

        // flatMapXXX()
        strList.stream()
                .flatMap(data -> Arrays.stream(data.split(" ")))
                .forEach(n -> System.out.printf(n + " "));
        System.out.println();
        System.out.println();

        // mapXXX()
        memList.stream()
                .mapToInt(Member::getAge) // 인스턴스메소드만되는듯(타입도맞아야하고)
                .forEach(n -> System.out.printf(n + " "));
        System.out.println();
        System.out.println();

        // asDoubleStream(), asLongStream(), boxed()

        // @다음코드실행되지않음 (WrapperClass가 아니라 그런것으로 판단됨)
        //         List<Integer> intListB = Arrays.asList(1,2,3);
        //         IntStream intStreamA = intListB.stream();
        //         intListB.stream()
        //                .asDoubleStream() // 실행불가(메소드없음)
        //               .forEach(n -> System.out.print(n  + " "));

        int[] intArray = {1, 2, 3};
        IntStream intStreamB = Arrays.stream(intArray);
        intStreamB
                .asDoubleStream()
                .forEach(n -> System.out.printf(n + " "));
        System.out.println();
        System.out.println();

        IntStream.rangeClosed(0, 5)
                .boxed()
                .forEach(obj -> System.out.printf(obj.intValue() + " "));
        System.out.println();
        System.out.println();

        // #정렬 (sorted())
        // sorted();
        // sorted((a, b) -> a.comapre(b));
        // sorted(Comparator.naturalOrder());

        // #루핑 peek()

        // =======================================================================================

        /**
         * #스트림파이프라인 (최종처리)
         *
         * #API (최종처리)
         *  매칭
         *      - allMatch() => boolean :: 모든 요소들이 매개값으로 주어진 predicate 조건을 만족하는지
         *      - anyMatch() => boolean :: 최소한 한개의 요소가 매개값으로 주거진 precicate 조건을 만족하는지
         *      - nonMatch() => boolean :: 모두 predicate 조건에 만족하지 않는지
         *  집계
         *      - count() => long
         *      - findFirst() => OptionalXXX
         *      - max() => OptionalXXX
         *      - max(Comparator<T>) => OptionalXXX
         *      - min() => OptionalXXX
         *      - min(Comparator<T>) => OptionalXXX
         *      - average() => OptionalXXX
         *      - reduce() => OptionalXXX
         *      - sum() => int, long, double
         *  루핑
         *      - forEach() => void :: 최종처리단계 루핑
         *  수집
         *      - collect() => R
         *
         * */

        // #매칭 (allMatch, anyMatch, noneMath)
        boolean bA = Arrays.stream(intArray).allMatch(a -> a % 2 == 0);
        System.out.println("[allMatch] " + bA);
        boolean bB = Arrays.stream(intArray).anyMatch(a -> a % 3 == 0);
        System.out.println("[anyMatch] " + bB);
        boolean bC = Arrays.stream(intArray).noneMatch(a -> a % 3 == 0);
        System.out.println("[noneMatch] " + bC);
        System.out.println();

        System.out.println("[기본집계함수] { 1, 2, 3, 4 ,5 } 비교");
        // count()
        long count = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .filter(n -> n % 2 == 0)
                .count();
        System.out.println("[count()] " + count);

        // sum()
        long sum = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .filter(n -> n % 2 == 0)
                .sum();
        System.out.println("[sum()] " + sum);

        // average()
        double average = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .filter(n -> n % 2 == 0)
                .average()
                .getAsDouble();
        System.out.println("[average()] " + average);

        // max()
        int max = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .filter(n -> n % 2 == 0)
                .max()
                .getAsInt();
        System.out.println("[max()] " + max);

        // min()
        int min = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .filter(n -> n % 2 == 0)
                .max()
                .getAsInt();
        System.out.println("[min()] " + min);

        // findFirst()
        int findFirst = Arrays.stream(new int[]{1, 2, 3, 4, 5})
                .filter(n -> n % 2 == 0)
                .findFirst()
                .getAsInt();
        System.out.println("[findFirst()] " + findFirst);

        System.out.println();
        // =======================================================================================

        /**
         * #Optional클래스
         *  단순히 집계값만 저장하는 것이 아니라,
         *  집계값이 존재하지 않을 경우 디폴트값을 설정할 수 있고,
         *  집계값이 처리하는 Consumer도 등록할 수 있습니다.
         *
         * #API
         *  - isParent() => boolean :: 값이 저장되어 있는지 여부
         *  - orElse(T) => T :: 값이 저장되어 있지 않을 경우 디폴트값 설정
         *  - orElse(double) => double :: 값이 저장되어 있지 않을 경우 디폴트값 설정
         *  - orElse(int) => int :: 값이 저장되어 있지 않을 경우 디폴트값 설정
         *  - orElse(lomg) => long :: 값이 저장되어 있지 않을 경우 디폴트값 설정
         *  - isPresent(Consumer) => void :: 값이 저장되어 있을 경우 Consumer에서 처리
         *  - isPresent(DoubleConsumer) => void :: 값이 저장되어 있을 경우 Consumer에서 처리
         *  - isPresent(IntConsumer) => void :: 값이 저장되어 있을 경우 Consumer에서 처리
         *  - isPresent(LongConsumer) => void :: 값이 저장되어 있을 경우 Consumer에서 처리
         *
         * */

        List<Integer> listInteger = new ArrayList<Integer>();

        /*
        [E] NoSuchElemetException
        double averageB = listInteger.stream()
                .mapToInt(Integer::intValue)
                .average().
                getAsDouble();
        */

        OptionalDouble od = listInteger.stream()
                .mapToInt(Integer::intValue)
                .average();

        if (od.isPresent()) {
            System.out.println("[방법1 평균] " + od.getAsDouble());
        } else {
            System.out.println("[방법2 평균] 0.0");
        }

        double averageB = listInteger.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(0.0);
        System.out.println("[방법2 평균] " + averageB);

        System.out.println();
        // =======================================================================================

        /**
         * #커스컴집계 (reduce())
         *  프로그램화해서 다양한 집계 결과물을 만들 수 있도록 하는 메소드
         *
         * #API
         *  - reduce(BinaryOperator<T> accumulator) => Optional<T> ::
         *  - reduce(T identity, BinaryOperator<T> accumulator) => T ::
         *  - reduce(IntBinaryOperator op) => OptionalInt ::
         *  - reduce (int identity, IntBinaryOperator op) => int ::
         *  - reduce(LongBinaryOperator op) => OptionalLong ::
         *  - reduce (long identity, LongBinaryOperator op) => long ::
         *  - reduce(DoubleBinaryOperator op) => OptionalDouble ::
         *  - reduce (double identity, DoubleBinaryOperator op) => double ::
         *
         * */

        List<Student> studentList = Arrays.asList(
                new Student("a", 10),
                new Student("b", 20),
                new Student("c", 30)
        );

        int sum1 = studentList.stream()
                .mapToInt(Student::getScore)
                .sum();

        int sum2 = studentList.stream()
                .map(Student::getScore)
                .reduce((a, b) -> a + b)
                .get();

        int sum3 = studentList.stream()
                .map(Student::getScore)
                .reduce(0, (a, b) -> a + b);

        System.out.println("[합계] " + sum1);
        System.out.println("[합계] " + sum2);
        System.out.println("[합계] " + sum3);

        System.out.println();
        // =======================================================================================

        /**
         * #수집 (collect())
         *  필터링 또는 매핑을 진행한후에 요소들을 수집하는 최종 처리 메소드인 collect()를 제공하고 있습니다.
         *
         * #Collectors 정적메소드 API
         *  - collect(Collector<T,A,R> collector) => R
         *  - toList() :: T를 List에 저장
         *      => Collector<T, ?, List<T>>
         *  - toSet() :: T를 Set에 저장
         *      => Collector<T, ?, Set<T>>
         *  - toMap(Function<T, K> keyMapper, Function<T,U> valueMapper) :: T를 "K&U" 로 매핍해서 K를 키로, U를 값으로 Map에 저장
         *      => Collecter<T, ?, ConcurrentMap<K, U>>
         *  - toCollection(Supplier<Collection<T>>) :: T를 Supplier가 제공한 Collection에 저장
         *      => Collector<T, ?, Collection<T>>
         *  - toCorrentMap(Function<T, K> keyMapper, Function<T,U> valueMapper) :: T와 K로 매핑헤서 K를 키로, U를 값으로 ConcurrentMap에 저장
         *      => Collecter<T, ?, ConcurrentMap<K, U>>
         *
         *
         * #사용자정의컨테이너에수집하기
         *  - collect(Supplier<R>, BiConsumer<R, ? super T>, BiConsumer<R, R>)  => R
         *  - collect(Supplier<R>, ObjIntConsumer<R, ? super T>, BiConsumer<R, R>)  => R
         *  - collect(Supplier<R>, ObjLongConsumer<R, ? super T>, BiConsumer<R, R>)  => R
         *  - collect(Supplier<R>, ObjDoubleConsumer<R, ? super T>, BiConsumer<R, R>)  => R
         *
         * #요소를그루핑해서수집
         *  컬렉션의 요소들을 그룹핑해서 Map 객체를 생성하는 기능도 제공합니다.
         *  groupingBy()는 스레드 안전하지 않은 Map 생성하고
         *  groupingByConcurrent() 스레드 안전한 ConcurrentMap 생성합니다.
         *
         * #API
         *  - groupingBy(Function<T, K> classifier) :: T를 K로 매핑하고 K키에 저장된 List에 T를 저장한 Map생성
         *      => Collector<T, ?, Map<K, List<T>>>
         *  - groupingByCurrent(Function<T, K> classifier) :: T를 K로 매핑하고 K키에 저장된 List에 T를 저장한 Map생성
         *      => Collector<T, ?, ConcurrentMap<K, List<T>>>
         *  - groupingBy(Function<T, K> classifier, Collector<T, A, D> collector) :: T를 K로 매핑하고 K키에 저장된 D 객체에 T를 누적한 Map생성
         *      => Collector<T, ?, Map<K, D>>
         *  - groupingByCurrent(Function<T, K> classifier, Collector<T, A, D> collector) :: :: T를 K로 매핑하고 K키에 저장된 D 객체에 T를 누적한 Map생성
         *      => => Collector<T, ?, ConcurrentMap<K, D>>
         *  - groupingBy(Function<T, K> classifier, Supplier<Map<K,D>> mapFactory, Collector<T,A,D> collector) :: T를 K로 매핍하고 Supplier가 제공하는 MapDPTJ K키에 저장된 D객체를 누적
         *      => Collector<T, ?, Map<K,D>>
         *  - groupingByConcurrent(Function<T, K> classifier, Supplier<Map<K,D>> mapFactory, Collector<T,A,D> collector) :: :: T를 K로 매핍하고 Supplier가 제공하는 MapDPTJ K키에 저장된 D객체를 누적
         *      => Collector<T, ?, ConcurrentMap<K,D>>
         *
         * #그룹핑후 매핑 및 집계
         *  - mapping(Function<T, U> mapper, Collector) => Collector<T, ?, R> :: T를 U로 매핑한 후, U를 R에 수집
         *  - averagingDouble(ToDoubleFunction<T> mapper) => Collector<T, ?, Double> :: T를 Double로 매핍한 후, Double의 평균값을 산출
         *  - counting() => Collector<T, ?, Long> :: T의 카운팅 수를 산출
         *  - joining(CharSequence delimiter) => Collector<CharSequence, ? , String> :: CharSequence를 구분자 (delimeter)로 연결한 String을 산출
         *  - maxBy(Comparator<T> comparator) => Collector<T, ?, Optional<T> :: Comparator를 이용해서 최대 T를 산출
         *  - minBy(Comparator<T> comparator) => Collector<T, ?, Optional<T> :: Comparator를 이용해서 최소 T를 산출
         *  - summingInt(ToIntFunction) => Collector<T, ?, Integer> :: int, long, double타입의 합계 산출
         *  - summingLong(ToLongFunction) => Collector<T, ?, Integer> :: int, long, double타입의 합계 산출
         *  - summingDouble(ToDoubleFunction) => Collector<T, ?, Integer> :: int, long, double타입의 합계 산출
         *
         *
         * */

        List<Student> totalList = Arrays.asList(
                new Student("a", 10, Student.Sex.MALE, Student.City.Seoul),
                new Student("b", 20, Student.Sex.MALE, Student.City.Seoul),
                new Student("c", 30, Student.Sex.FEMALE, Student.City.Seoul),
                new Student("d", 40, Student.Sex.FEMALE, Student.City.Pusan),
                new Student("d", 40, Student.Sex.FEMALE, Student.City.Pusan)
        );

        // List
        List<Student> maleList = totalList.stream()
                .filter(s -> s.getSex() == Student.Sex.MALE)
                .collect(Collectors.toList());
        maleList.stream()
                .forEach(s -> System.out.println("[남학생필터(List)] " + s.getName()));
        System.out.println();

        // Set
        Set<Student> femaleList = totalList.stream()
                .filter(s -> s.getSex() == Student.Sex.FEMALE)
                .collect(Collectors.toCollection(HashSet::new));

        femaleList.stream()
                .forEach(s -> System.out.println("[여학생필터(Set)] " + s.getName()));
        System.out.println();

        // 사용자지정 (랃다) -
        //        MaleStudent msA = totalList.stream()
        //                .filter(s -> s.getSex() == MALE)
        //                .collect(
        //                        () -> new MaleStudent(),
        //                        (r, t) -> r.accumulate(t),
        //                        (r1, r2) -> r1.combine(r2)
        //                );

        // 사용자지정 (메소드참조)
        MaleStudent msB = totalList.stream()
                .filter(s -> s.getSex() == Student.Sex.MALE)
                .collect(
                        MaleStudent::new,
                        MaleStudent::accumulate,
                        MaleStudent::combine
                );

        // [main] MaleStudent()
        // [main] accumulate() 남성1
        // [main] accumulate() 남성2

        // combine ... ?

        msB.getList().stream()
                .forEach(s -> System.out.println("[사용자지정 Supplier] " + s.getName()));
        System.out.println();

        // grouping

        Map<Student.Sex, List<Student>> mapBySex = totalList.stream()
                .collect(Collectors.groupingBy(Student::getSex));

        System.out.println("[남학생(그룹)] ");
        mapBySex.get(Student.Sex.MALE).stream()
                .forEach(s -> System.out.printf(s.getName() + " "));

        Map<Student.City, List<String>> mapByCity = totalList.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getCity,
                                Collectors.mapping(
                                        Student::getName,
                                        Collectors.toList()
                                )
                        )
                );
        System.out.println();

        System.out.println("[서울(그룹)] ");
        mapByCity.get(Student.City.Seoul).stream()
                .forEach(s -> System.out.printf(s + " "));
        System.out.println();

        System.out.println("[서울(부산)] ");
        mapByCity.get(Student.City.Pusan).stream()
                .forEach(s -> System.out.printf(s + " "));
        System.out.println();

        // grouping -> mapping & reduce

        Map<Student.Sex, Double> mapBySexB = totalList.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getSex,
                                Collectors.averagingDouble(Student::getScore)
                        )
                );
        System.out.println("[남학우평균점수] " + mapBySexB.get(Student.Sex.MALE));
        System.out.println("[여학우평균점수] " + mapBySexB.get(Student.Sex.FEMALE));

        Map<Student.Sex, String> mapByName = totalList.stream()
                .collect(
                        Collectors.groupingBy(
                                Student::getSex,
                                Collectors.mapping(
                                        Student::getName,
                                        Collectors.joining(",")
                                )
                        )
                );
        System.out.println("[남학우전체이름] " + mapByName.get(Student.Sex.MALE));
        System.out.println("[여학우전체이름] " + mapByName.get(Student.Sex.FEMALE));


        System.out.println();
        // =======================================================================================

        /**
         * #병렬처리
         *
         * #병렬스트림생성
         *  - parallelStrema() => Stream
         *  - parallel() => Stream
         *  - parallel() => IntStream
         *  - parallel() => LongStream
         *  - parallel() => DoubleStreams
         *
         * */

        // 여러스레드가도는걸 확인할수있음
        MaleStudent maleStudent = totalList.parallelStream()
                .filter(s -> s.getSex() == Student.Sex.MALE)
                .collect(
                        MaleStudent::new,
                        MaleStudent::accumulate,
                        MaleStudent::combine
                );

        maleStudent.getList().stream().forEach(s -> System.out.println(s.getName()));

        // 순차처리와 병렬처리 성능비교
        List<Integer> parallelTestList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
        long timeSequencial = testSequencial(parallelTestList);
        long timeParallel = testParallel(parallelTestList);

        System.out.println("[순차처리시간] " + timeSequencial);
        System.out.println("[병렬처리시간] " + timeParallel);

        // 자료형에 따른 성능비교
        List<Integer> arrayList = new ArrayList<Integer>();
        List<Integer> linkedList = new LinkedList<Integer>();
        for (int j = 0; j < 1000; j++) {
            arrayList.add(j);
            linkedList.add(j);
        }
        long timeArrayParallel = testParallel(arrayList);
        long timeLinkedParallel = testParallel(linkedList);

        System.out.println("[ArrayList 처리시간] " + timeArrayParallel);
        System.out.println("[LinkedList 처리시간] " + timeLinkedParallel);


    }

    public static void print(String str) {
        System.out.println(str + " : " + Thread.currentThread().getName());
    }

    public static int appendA(String str) {
        return 1;
    }

    static class Student {
        public enum Sex {MALE, FEMALE}

        public enum City {Seoul, Pusan}

        private String name;
        private int score;
        private Sex sex;
        private City city;

        public Student(String name, int score) {
            this.name = name;
            this.score = score;
        }

        public Student(String name, int score, Sex sex, City city) {
            this.name = name;
            this.score = score;
            this.sex = sex;
            this.city = city;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        public Sex getSex() {
            return sex;
        }

        public City getCity() {
            return city;
        }

    }

    static class MaleStudent {
        private List<Student> list;

        public MaleStudent() {
            list = new ArrayList<Student>();
            System.out.printf("[%s] MaleStudent()\n", Thread.currentThread().getName());
        }

        public void accumulate(Student s) {
            list.add(s);
            System.out.printf("[%s] accumulate()\n", Thread.currentThread().getName());
        }

        public void helloworld(Student s) {
            list.add(s);
            System.out.printf("[%s] helloworld()\n", Thread.currentThread().getName());
        }

        public void combine(MaleStudent other) {
            list.addAll(other.getList());
            System.out.printf("[%s] combine()\n", Thread.currentThread().getName());
        }

        public List<Student> getList() {
            return list;
        }

    }

    static public void work(int value) {
        try {
            Thread.sleep(1);
        } catch (Exception e) {
        }
    }

    static public long testSequencial(List<Integer> list) {
        long start = System.nanoTime();
        list.stream().forEach(a -> work(a));
        long end = System.nanoTime();
        long runTime = end - start;
        return runTime;
    }

    static public long testParallel(List<Integer> list) {
        long start = System.nanoTime();
        list.stream().parallel().forEach(a -> work(a));
        long end = System.nanoTime();
        long runTime = end - start;
        return runTime;
    }
}
