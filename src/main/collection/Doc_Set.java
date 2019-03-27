package main.collection;

import java.util.*;

/**
 * #Set이뭔가요?
 * Set은 주머니에 구슬을 담는 것과 같이, 집합과 유사합니다.
 * Set컬렉션은 저장순서가 유지되지 않으며, 객체를 중복해서 저장할 수 없는 데이터 컬렉션입니다.
 * null 또한 1개밖에 저장할 수 없습니다.
 * <p>
 * #Iterator (반복자)
 * Iterator<String> iterator = set.iterator();
 * 반복자는 저장된 객체를 한번씩 가져오는 역할을 합니다.
 * - hasNext() => boolean
 * - next() => E
 * - remove() => void
 * <p>
 * #HastSet ("hashCode()"비교 => "equals()"비교 => 저장 (중복아님))
 * 객체들을 순서없이 저장하고 중복 저장하지 않습니다.
 * "hashCode()" 메소드를 호출해서 해시코드를 얻어냅니다.
 * 그리고 이미 저장되어 있는 객체들을 해시코드와 비교합니다.
 * 해시코드가 같다면 "equals()" 메소드로 다시비교후 판단합니다.
 * 그래서 필요에 따라 "hashCode & equals" 메소드를 오버라이드 해서 사용할 수 있습니다.
 * <p>
 * #TreeSet
 * <검색기능>을 강화시킨 Set 컬렉션으로 "이진트리구조"를 사용합니다.
 * 검색기능
 * - first() => E :: 제일낮은 객체를 리턴
 * - last() => E :: 제일높은 객체를 리턴
 * - lower(E e) => E :: 주어진객체 바로아래 객체를 리턴
 * - higher(E e) => E :: 주어진객체 바로위에 객체를 리턴
 * - floor(E e) => E :: 주어진객체와 동등한 객체가 있으면 리턴, 없으면 바로 아래 리턴
 * - ceiling(E e) => E :: 주어진 객체와 동등한 객체가 있으면 리턴, 없으면 바로 위에 리턴
 * - pollFirst() => E :: 제일 낮은 객체, 컬랙션 제거
 * - pollLast() => E :: :: 제일 낮은 높은, 컬랙션 제거
 * 정렬기능
 * - descendingIterator() => Iterator<E> :: 내림차순으로 정렬된 Iterator 리턴
 * - descendingSet() => NavigableSet<E> :: 내림차순으로 정렬된 Set 리턴
 * 범위검색
 * - headSet(E toElement, boolean inclusive) => NavigableSet<E> :: 주어진객체보다 낮은 객체들을 리턴, 2번째 매개값이 자신포함여부
 * - tailSet(E fromElement, boolean inclusive) => NavigableSet<E> :: 주어진객체보다 높은 객체들을 리턴, 2번째 매개값이 자신포함여부
 * - subSet(E fromElement, boolean fromInclusive, E toElement, boolean toInclusive) :: 시작과 끝으로 주어진 객체들을 리턴
 * <p>
 * #API
 * 객체추가
 * - add(E e) => boolean
 * 객체검색
 * - contains(Object o) => boolean
 * - isEmpty() => boolean
 * - iterator() => Iterator<E>
 * - size() => int
 * 객체삭제
 * - clear() => void
 * - remove(Object o) => boolean
 */

public class Doc_Set {

    public static void main(String[] args) {

        // #01. Iterator
        Set<String> set = new HashSet<String>();
        Iterator<String> i = set.iterator();
        while (i.hasNext()) {
            String str = i.next();
        }

        // #02. HashSet
        Set<String> hashSet = new HashSet<String>();
        set.add("J");
        set.add("A");
        set.add("V");
        set.add("A"); // 한번만저장
        set.size();   // 3

        // #03. TreeSet
        Set<String> treeSet = new TreeSet<String>();
        treeSet.add("Java");
        treeSet.add("Jsp");
        treeSet.add("JavaScript");
        treeSet.add("Python");
        treeSet.add("Ruby");
        treeSet.add("Scala");
        treeSet.add("c");
        treeSet.add("c++");
        treeSet.add("c#");
        treeSet.add("foo");

        System.out.println("[c~f] 사이 단어 검색");
        // NavigableSet<String> rangeSet = treeSet.subSet("c", true, "f", true);

        // "subSet"
        NavigableSet<String> rangeSet = ((TreeSet<String>) treeSet)
                .subSet("c", true, "f", true);

        for (String word : rangeSet) {
            System.out.println(word);
        }

    }
}
