package main.stream;

import java.util.Arrays;
import java.util.List;

public class DocStream_MaxMin {

    public static void main(String[] args) {

        DocStream_MaxMin.manualStreamMax();
    }

    public static void manualStreamMax() {
        List<Integer> list = Arrays.asList(1,2,3,4);
        System.out.println(list.stream().max((a, b) -> a - b)); // Optional[4]
        System.out.println(list.stream().max((a, b) -> a - b).orElse(0)); // 4
        System.out.println(list.stream().max((a, b) -> a - b).get()); // 4
    }
}
