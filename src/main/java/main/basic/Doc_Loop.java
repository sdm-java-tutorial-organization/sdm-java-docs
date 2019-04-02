package main.basic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Doc_Loop {

    public static void main(String[] args) {
        /*Doc_Loop.loopInDeleteElement();*/
        Doc_Loop.loopBreak();
    }

    /**
     * 만약 앞에 데이터가 사라진다면 에러가 날까
     *
     * - Arrays.asList( .. ) 메소드는 [add / remove]를 할 수 없다.
     * - ConcurrentModificationException
     * */
    public static void loopInDeleteElement() {

        /* 리스트 생성하는 부분 */
        /*List<String> listOfString = Arrays.asList("hello", "world", "bye", "world");*/
        List<String> listOfString = getSampleList();

        /* 반복문 1번을 사용해서 제거 */
        try {
            for(int i=0; i<listOfString.size(); i++) {
                System.out.println(listOfString.get(i));
                if(listOfString.get(i).equals("hello")) {
                    listOfString.remove(i);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=============");

        listOfString = getSampleList();

        /* 반복문 2번을 사용해서 제거 */
        try {
            for(String element : listOfString) {
                System.out.println(element);
                if(element.equals("hello")) {
                    listOfString.remove(element);
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println("=============");

        listOfString = getSampleList();

        /* iterator 를 사용해서 제거 */
        try {
            for(Iterator<String> iterator = listOfString.iterator(); iterator.hasNext();) {
                String value = iterator.next();
                System.out.println(value);
                if(value.equals("hello")) {
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 반복문도 break가 되나요 ?
     *
     * */
    public static void loopBreak() {
        List<String> listOfString = getSampleList();

        for(int i=0; i<listOfString.size(); i++) {
            System.out.println(listOfString.get(i));
            if(listOfString.get(i).equals("hello")) {
                break;
            }
        }
    }

    public static List<String> getSampleList() {
        List<String> listOfString = new ArrayList<>();
        listOfString.add("hello");
        listOfString.add("world");
        listOfString.add("bye");
        listOfString.add("world");
        return listOfString;
    }
}
