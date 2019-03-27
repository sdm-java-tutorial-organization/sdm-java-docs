package main.collection;

import java.util.*;

/**
 * #Map이 뭐에요?
 * 자바 컬렉션프레임워크중 1개로써, 키와 값으로 구성된 "Entry 객체"를 저장하는 구조를 가지고 있습니다.
 * 키는 중복 저장될 수 없지만, 값은 중복 저장이 가능합니다.
 * 키는 기본타입을 사용할 수 없습니다.
 * <p>
 * #API
 * 객체추가
 * - put(K key, V value) => V :: 객체추가
 * 객체검색
 * - containsKey(Object key) => boolean :: 포함키 확인
 * - containsValue(Object value) => boolean :: 포함값 확인
 * - get(Object key) => V :: 키로 값확인
 * - isEmpty() => boolean :: 텅비었는지 확인
 * - entrySet() => Set<Map.Entry<K,V>> :: "Entry"를 Set으로 반환 ( 중복이 불가능 하기 때문 - Set )
 * - keySet() => Set<K> :: "Key"를 Set으로 반환 ( 중복이 불가능 하기 때문 - Set )
 * - values() => Collection<V> "Value"를 Collection으로 반환 ( 중복이 가능하기 때문 - Collection )
 * - size() => int :: 크기
 * 객체삭제
 * - clear() => void :: 모두 삭제
 * - remove(Object key) => boolean :: 키로 삭제
 * <p>
 * #HastMap ("hashCode()"비교 => "equals()"비교 => 저장(중복아님))
 * "Key"만 놓고보면 HashSet 처리와 비슷한 과정
 * 키의 "hashCode()" 메소드를 호출해서 해시코드를 얻어냅니다.
 * 그리고 이미 저장되어 있는 키들을 해시코드와 비교합니다.
 * 키의 해시코드가 같다면 키를 "equals()"메소드로 다시비교후 판단합니다.
 * <p>
 * #Hashtable
 * HashMap과 동일한 내부 구조를 가지고 있습니다.
 * 차이점은 동기화된 메소드로 구성되어 있기 때문에, 멀티 스레드가 동시에 이 메소드들을 실행할 수 없습니다.
 * 하나의 스레드가 실행을 완료해야만 다른 스레드를 실행할 수 있습니다.
 * 스레드 안전(thread safe)을 보장줍니다.
 * <p>
 * #Properties
 * Hashtable의 하위클래스로 Hashtable의 모든 특징을 그대로 가지고있습니다.
 * 단, Properties의 키값은 String으로 제한적입니다.
 * Properties는 애플리케이션의 옵션정보, DB정보, 다양한 국제화(다국어) 정보가 저장된 파일(.properties)을 읽을 때 주로 사용합니다.
 * <p>
 * #TreeMap
 * 이진트리를 기반으로 <검색기능>을 강화시킨 Map 컬렉션입니다.
 * 검색기능
 * - firstEntry() => Map.Entry<K,V> :: 제일낮은 객체리턴
 * - lastEntry() => Map.Entry<K,V> :: 제일높은 객체리턴
 * - lowerEntry(E e) => Map.Entry<K,V> :: 주어진객체 바로아래 객체리턴
 * - higherEntry(E e) => Map.Entry<K,V> :: 주어진객체 바로위에 객체리턴
 * - floorEntry(E e) => Map.Entry<K,V> :: 주어진객체와 동등한 객체가 있으면 리턴, 없으면 바로 아래
 * - ceilingEntry(E e) => Map.Entry<K,V> :: 주어진 객체와 동등한 객체가 있으면 리턴, 없으면 바로 위에
 * - pollFirstEntry() => Map.Entry<K,V> :: 제일 낮은 객체, 컬랙션 제거
 * - pollLastEntry() => Map.Entry<K,V> :: :: 제일 낮은 높은, 컬랙션 제거
 * 정렬기능
 * - descendingKeySet() => Iterator<E> :: 내림차순 키셋
 * - descendingMap() => NavigableSet<E> :: 내림차순 맵
 * 범위검색
 * - headMap(K toKey, boolean inclusive) => NavigableMap<K,V> :: 주어진객체보다 낮은 객체들을 리턴, 2번째 매개값이 자신포함여부
 * - tailMap(K fromKey, boolean inclusive) => NavigableMap<K,V> :: 주어진객체보다 높은 객체들을 리턴, 2번째 매개값이 자신포함여부
 * - subMap(K fromKey, boolean fromInclusive, K toKey, boolean toInclusive):: 주어진 두 객체 사이의 객체들을 리턴
 */

public class Doc_Map {
    public static void main(String[] args) {

        // #01. HashMap
        Map<String, Integer> map = new HashMap<String, Integer>();
        map.put("key1", 1);
        map.put("key2", 2);
        map.put("key3", 3);
        map.put("key4", 4);
        map.put("key5", 5);
        map.put("key1", 6); // 키중복 (key1 => 6)

        Set<String> keys = map.keySet();
        Set<Map.Entry<String, Integer>> entrys = map.entrySet();
        Collection<Integer> values = map.values();

        // #02. Hashtable

        // #03. Properties

        // #04. TreeMap
        TreeMap<Integer, String> treeMap = new TreeMap<Integer, String>();
        treeMap.put(Integer.valueOf(1), "a");
        treeMap.put(Integer.valueOf(2), "b");
        treeMap.put(Integer.valueOf(3), "a");
        treeMap.put(Integer.valueOf(4), "d");
        treeMap.put(Integer.valueOf(5), "e");

        Map.Entry<Integer, String> entry = null;
        entry = treeMap.firstEntry();
        System.out.println("[가장낮은] " + entry.getKey() + "-" + entry.getValue());

        entry = treeMap.lastEntry();
        System.out.println("[가장높은] " + entry.getKey() + "-" + entry.getValue());

        entry = treeMap.lowerEntry(Integer.valueOf(3));
        System.out.println("[3아래] " + entry.getKey() + "-" + entry.getValue());

        entry = treeMap.higherEntry(Integer.valueOf(3));
        System.out.println("[3우에] " + entry.getKey() + "-" + entry.getValue());

        // Map 반복문 (Loop)
        // entry.forEach((k, v)=-{ /*...*/});

    }
}
