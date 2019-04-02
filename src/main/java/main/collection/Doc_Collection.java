package main.collection;

import java.util.*;

/**
 * #컬렉션프레임워크가 뭐에요?
 * 객체를 어떻게 효율적으로 추가, 검색, 삭제 할지 고민해야하게 됩니다.
 * 배열은 쉽게 생성하고 사용할 수 있지만, 저장할 수 있는 객체의 수가 배열의 크기를 넣는다면 좋은 방법이 될 수 없습니다.
 * 컬렉션이란, 요소를 수집해서 저장하는 것을 말하는데, 자바 컬렉션은 객체를 수집해서 저장하는 역할을 합니다.
 * <p>
 * #각각은 어떤특징이 있어요?
 * Collection (객체를 추가 삭제 검색하는 방법에 공통점)
 * List
 * - 순서유지
 * - 중복저장
 * Set
 * - 순서유지안함
 * - 중복안됨
 * Map
 * - 키와값으로저장
 * - 키는중복안됨
 * <p>
 * #컬렉션프레임워크에 뭐가 있어요?
 * List
 * - ArrayList
 * - Vector
 * - LinkedList
 * Set
 * - HashSet
 * - TreeSet
 * Map
 * - HashMap
 * - Hashtable
 * - TreeMap
 * - Properties
 * <p>
 * #Comparable & Comparator
 * [이진트리]를 사용하는 "TreeSet/TreeMap" 객체는 저장할때 위치를 비교하기 때문에 자동으로 오름차순으로 정렬이 될 것입니다.
 * "TreeSet/TreeMap"은 정렬을 위해 java.lang.Comparable을 구현한 객체를 요구합니다.
 * 그리고 다음 함수를 오버라이드 해야합니다.
 * - compareTo(T o) => int
 * <p>
 * <p>
 * #동기화된컬렉션
 * "Vector, Hashtable"은 동기화된 메소드로 구성되어 있어 멀티 스레드 환경에서도 스레드 안전을 보장합니다.
 * 싱글스레드환경작업 => 멀티스레드환경작업이 된다면 데이터를 동기화 해주는 리스트로 바꿔줘야 합니다.
 * - synchronizedList(List<T> list) => List<T>
 * - synchronizedMap(Map<K,V> m) => Map<K, V>
 * - synchronizedSet(Set<T> s) => Set<T>
 * 동기화된컬렉션이 항상 정답은 아닙니다.
 * 동기화된 메소드를 사용한다는 것은 다른 스레드의 처리를 제한하는 거기 때문에 성능에 문제가 있을 수 있습니다.
 * <p>
 * #병렬처리를위한컬렉션
 * 동기화된 컬렉션은 멀티 스레드 환경에서 하나의 스레드 요소를 안전하기 처리하도록 도와주지만,
 * 전체요소를 빠르게 처리하도록 도와주지는 못합니다.
 * <p>
 * 그래서 멀티스레드가 컬렉션의 요소를 병렬적으로 처리할 수 있도록
 * java.util.concurent => ConcurrentHashMap, ConcurentLinkedQueue를 지원합니다.
 * <p>
 * ConcurrentHashMap
 * 스레드에 안전하면서도 멀티 스레드가 요소를 병렬적으로 처리
 * segment(잠금)을 이용해서 처리 (부분잠금)
 * ConcurentLinkedQueue
 * 락-프리 알고리즘을 구현한 컬렉션
 * 락-프리 알고리즘은 여러개의 스레드가 동시에 접근할 경우,
 * 잠금을 사용하지 않고도 최소한 하나의 스레드가 안전하게 요소를 저장하거나 얻도록 해줍니다.
 */

public class Doc_Collection {

    public static void main(String[] args) {

        // # Comparator (Comparable 구현)
        class Person implements Comparable<Person> {
            public String name;
            public int age;

            Person(String name, int age) {
                this.name = name;
                this.age = age;
            }

            @Override
            public int compareTo(Person o) {
                if (age < o.age) return -1;
                else if (age == o.age) return 0;
                else return 1;
            }
        }

        // # TreeSet & TreeMap 정렬
        TreeSet<Person> treeSet = new TreeSet<Person>();
        treeSet.add(new Person("a", 1));
        treeSet.add(new Person("b", 2));
        treeSet.add(new Person("c", 3));

        Iterator<Person> iterator = treeSet.iterator();
        while (iterator.hasNext()) {
            Person person = iterator.next();
            System.out.println(person.name + "-" + person.age);
        }

        // #No-Comparator (Comparator 구현)
        class descendingComparator implements Comparator<Person> {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.age < o2.age) return 1;
                else if (o1.age == o2.age) return 0;
                else return -1;
            }
        }

        class AscendingComparator implements Comparator<Person> {
            @Override
            public int compare(Person o1, Person o2) {
                if (o1.age > o2.age) return 1;
                else if (o1.age == o2.age) return 0;
                else return -1;
            }
        }

        TreeSet<Person> noTreeSet = new TreeSet<Person>(new descendingComparator());
        noTreeSet.add(new Person("a", 1));
        noTreeSet.add(new Person("b", 2));
        noTreeSet.add(new Person("c", 3));
        Iterator<Person> i = noTreeSet.iterator();
        while (i.hasNext()) {
            Person person = i.next();
            System.out.println(person.name + "-" + person.age);
        }
        // TreeMap<Person, String> noTreeMap = new TreeMap<Person, String>(new descendingComparator());
        // noTreeSet.add(new Person("a", 1));
        // noTreeSet.add(new Person("b", 2));
        // noTreeSet.add(new Person("c", 3));

        // # 컬렉션동기화시키기
        List<String> sList = Collections.synchronizedList(new ArrayList<String>());
        Set<String> sSet = Collections.synchronizedSet(new HashSet<String>());
        Map<String, Integer> sMap = Collections.synchronizedMap(new HashMap<String, Integer>());

        // # 병렬처리를 위한 컬렉션
        // Map<K,V> map = new ConcurrentHashMap<K, V>();
        // Queue<E> queue = new ConcurrentLinkedQueue<E>();

    }
}
