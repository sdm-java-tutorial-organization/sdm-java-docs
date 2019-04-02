

package main.api;

import java.util.Arrays;

/**
 * #Array란?
 * 변수는 한 개의 데이터만 저장할 수 있습니다. 따라서 저장해야할 데이터의 수가 많아지면 그만큼 많은 변수가 필요합니다.
 * <p>
 * #Arrays클래스
 * 다음 클래스는 "배열조작기능"을 가지고 있습니다. 배열의 복사, 항목 정렬, 항목 검색과 같은 기능을 말합니다.
 * 단순한 배열 복사는 "System.arraycopy()" 메소드를 사용할 수도 있습니다.
 * <p>
 * #API암기가자
 * - binarySearch(arr, value) => int :: 전체 배열 항목에서 찾는 값이 있는 인덱스 리턴
 * - copyOf(originArr, length) => arr :: 원본배열의 0번 인덱스에서 복사할 길이만큼 복사한 배열 리턴, 복사할 길이는 원본 배열의 길이보다 커도되며 타겟 배열의 길이가 됩니다.
 * - copyOfRange(originArr, sIndex, eIndex) => arr :: 원복 배열의 시작 인덱스에서 끝 인덱스까지 복사한 배열 리턴
 * - (arr1, arr2) => boolean :: 두 배열의 깊은 비교
 * - equals(arr, arr2) => boolean :: 두 배열의 얕은 비교
 * - fill(arr, value) => void :: 전체 배열 항목에 동일한 값을 저장
 * - fill(arr, sIndex, eIndex, value) :: 시작 인덱스부터 끝 인덱스까지의 항목에만 동일한 값을 저장
 * - sort(arr) :: 배열의 전체 항목을 오름차순으로 정렬
 * - toString(arr) :: "[value1, value2, ...]"와 같은 값으로 리턴
 */

public class Doc_Arrays {
    public static void main(String[] args) {

        // # 배열의 선언 (목록으로)
        int[] arrInt1 = {1, 2, 3};
        char[] arrStr1 = new char[]{'J', 'A', 'V', 'A'};

        // # 배열의 선언 (new)
        int[] arrInt2 = new int[3]; // { 0, 0, 0 };

        // # 배열의 길이
        System.out.println(arrInt1.length); // 3
        System.out.println(arrInt2.length); // 3
        System.out.println(arrStr1.length); // 4

        // API를 써볼까...

        // # [copyOf() & copyOfRange()] => 깊은복사
        char[] arr1 = {'J', 'A', 'V', 'A'}; // "" (string) / '' (char)
        char[] arr2 = Arrays.copyOf(arr1, arr1.length);         // [J, A, V, A]
        char[] arr3 = Arrays.copyOfRange(arr1, 1, 3); // [A, V] 1~2까지

        arr2[0] = '!';
        arr3[0] = '!';
        System.out.println("[기본배열] " + Arrays.toString(arr1));
        System.out.println("[전체복사배열] " + Arrays.toString(arr2));
        System.out.println("[부분복사배열] " + Arrays.toString(arr3));

        // # [System.arraycopy()] => 깊은복사
        // System.arraycopy(Object 복사배열, int 복사배열시작위치, Object 타겟배열, int 타겟배열시작위치, int 복사할량);

        char[] arr4 = new char[arr1.length];
        System.arraycopy(arr1, 0, arr4, 0, arr1.length);
        arr4[0] = '!';
        System.out.println("[기본배열] " + Arrays.toString(arr1));
        System.out.println("[전체복사배열] " + Arrays.toString(arr4));
        System.out.println();

        // # [ Object.equals() (얕은) ] - 주소값만 비교 ( == )
        // # [ equals (깊은) ] - 1차배열까지 깊은비교
        // # [ deepEquals (깊은) ] - 2차배열이상 까지 깊은비교

        // 원시배열
        System.out.println("[==(int[]) 주소비교] " +
                (new int[]{1, 2} == new int[]{1, 2})); // false
        System.out.println("[Object.equals(int[]) 주소비교] " +
                (new int[]{1, 2}).equals(new int[]{1, 2})); // false
        System.out.println("[Arrays.equals(int[]) 값비교] " +
                Arrays.equals(new int[]{1, 2}, new int[]{1, 2})); // true
        System.out.println("[Arrays.deepEquals(int[][]) 값비교] " +
                Arrays.deepEquals(new int[][]{{1, 2}}, new int[][]{{1, 2}})); // true

        // 문자열배열
        System.out.println("[==(String[]) 주소비교] " +
                (new String[]{"a", "b"} == new String[]{"a", "b"})); // false
        System.out.println("[Object.equals(String[]) 값비교] " +
                (new String[]{"a", "b"}).equals(new String[]{"a", "b"})); // false
        System.out.println("[Arrays.equals(String[]) 같은값] " +
                Arrays.equals(new String[]{"a", "b"}, new String[]{"a", "b"})); // true
        System.out.println("[Arrays.deepEquals(String[]) 같은값] " +
                Arrays.deepEquals(new String[]{"a", "b"}, new String[]{"a", "b"})); // true

        // # [sort] => [binarySearch]
        class Member implements Comparable<Member> { // (public 선언 안됨 - illegal start of expression)
            String name;

            Member(String name) {
                this.name = name;
            }

            @Override
            public int compareTo(Member o) {
                return name.compareTo(o.name);
            }
        }

        // # [binarySearch (T[] arr, T t)]
        // # 검색해서 찾지 못한 값이 -1만 나오는 것은 아니다.

        char[] charArr = new char[]{'J', 'V', 'A', 'C', 'P'};
        System.out.println("[binarySearch] " + Arrays.binarySearch(charArr, 'V')); // -6 (정렬이되어 있지 않으면 없는값으로 인식)
        Arrays.sort(charArr);
        System.out.println("[binarySearch (정렬후)] " + Arrays.binarySearch(charArr, 'A')); // 0
        System.out.println("[binarySearch (정렬후)] " + Arrays.binarySearch(charArr, 'C')); // 1
        System.out.println("[binarySearch (정렬후)] " + Arrays.binarySearch(charArr, 'J')); // 2
        System.out.println("[binarySearch (정렬후)] " + Arrays.binarySearch(charArr, 'P')); // 3
        System.out.println("[binarySearch (정렬후)] " + Arrays.binarySearch(charArr, 'V')); // 4
        System.out.println("[binarySearch (없는값)] " + Arrays.binarySearch(charArr, 'G')); // -3
        System.out.println("[binarySearch (없는값)] " + Arrays.binarySearch(charArr, 'H')); // -3

        // 최대최소

        // 01. sort
        int[] arr = new int[]{3, 2, 1};
        Arrays.sort(arr);
        System.out.println(arr[0]);


    }
}
