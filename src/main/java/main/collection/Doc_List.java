package main.collection;

import java.util.*;

/**
 * #리스트가뭐에요?
 * 자료구조에서리스트인지? 자바에서리스트인지?
 * 리스트는 객체가 일렬로 늘어놓은 선형구조를 가지고 있습니다.
 * 객체를 <인덱스로 관리>하기 때문에 인덱스를 통한 접근이 가능합니다.
 *
 * #ArrayList
 * List 인터페이스의 구현 클래스로 객체를 추가하면 객체가 인덱스로 관리됩니다.
 * Array 와 차이점은 저장용량을 초과한 객체들이 들어오면 자동적으로 저장용량(capacity)를 늘린다는 것입니다.
 * 삽입과삭제가 빈번하다면 "LinkedList"를 사용하는 것이 좋습니다.
 * List<String> list = new ArrayList<String>(30);
 * 다음과 같이 처음부터 저장용량을 가지고 시작하는 것도 방법이 될 수 있습니다.
 *
 * #Vector
 * 스레드안전(Thread Safety)을 보장하는 리스트입니다. ArrayList와 동일한 내부 구조를 가집니다.
 * 동기화된 메소드로 구성되어 있기 때문에 멀티 스레드가 동시에 이 메소드를 실행할 수 없고,
 * 하나의 스레드가 실행을 완료해야만 다른 스레드를 실행할 수 있습니다.
 *
 * #LinkedList  flo
 * 내부구조가 인접참조를 링크해서 체인처럼 관리합니다.
 * 다음과 같은 구조로 데이터 삽입 및 제거에 성능이 좋다는 장점이 있습니다.
 * 반대로 데이터 <탐색>에는 ArrayList보다 속도가 느립니다.
 *
 * #API
 * 객체추가
 * - add(E e) => boolean :: 객체추가
 * - add(int index, E element) => void :: 원하는 인덱스에 객체를 추가
 * - set(int index, E element) => void :: 원하는 인덱스에 객체를 수정
 * 객체검색
 * - contains(Object o) => boolean  :: 포함여부 확인
 * - get(int index) => E :: 객체 반환
 * - isEmpty() => boolean :: 비어있는 확인
 * - size() => int :: 크기
 * 객체삭제
 * - clear() => void :: 모두 삭제
 * - remove(int index) => E :: 인덱스 삭제
 * - remove(Object o) => boolean :: 객체 삭제
 *
 * #Arrays.asList로 만드는 리스트는 "add/remove"가 되지 않는다. => UnsupportedOperationException
 * String[] strArrays = {"a", "b", "c", "d", "f", "g", "h", "i"};
 * // 1
 * List<string> strList = Arrays.asList(strArrays);
 * // 2
 * List<string> strList = new ArrayList<string>();
 * Collections.addAll(strList, strArrays);
 * // 3
 * List<string> strList = new ArrayList<string>(Arrays.asList(strArrays));
 * System.out.println(strList.remove(0));
 */

public class Doc_List {

    public static void main(String[] args) {

        // #01. ArrayList
        // 제네릭을 선언해주는 것이 성능에 좋습니다. (제네릭을 선언하지 않아도 되나)
        List<String> dynamicList = new ArrayList<String>();
        dynamicList.add("JDBC");
        dynamicList.add("JSP");
        dynamicList.add(2, "SERVLET");
        dynamicList.size();

        // 고정객체 (Array.asList())
        List<String> staticList = Arrays.asList("heo", "jeong", "moon");

        // #02. Vector - 동기화
        List<String> vectorList = new Vector<String>();
        vectorList.add("J");
        vectorList.add("A");
        vectorList.add("V");
        vectorList.add("A");

        // #03. LinkedList - 체인형
        List<String> list = new LinkedList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        // list.push("d"); (X) // LinkedList 선언 변수에서만 지원
        System.out.println(list.get(0)); // a
        System.out.println(list.get(1)); // b
        System.out.println(list.get(2)); // c

        LinkedList<String> linkedList = new LinkedList<String>();
        linkedList.add("b");
        linkedList.add("c");
        linkedList.add("d");
        linkedList.push("a"); // 앞에추가 (시작노드로 처리해주는)
        System.out.println(list.get(0)); // a
        System.out.println(list.get(1)); // b
        System.out.println(list.get(2)); // c
        System.out.println(list.get(3)); // d
    }

    // == remove 사용기 ==
    public static void useRemove() {

    }

}
