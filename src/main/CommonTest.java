package main;

import java.util.Arrays;

public class CommonTest {

    public static void main(String[] args) {

//        ArrayList<MyArr> list = new ArrayList<>();
//        list.add(new MyArr(3,3));
//        list.add(new MyArr(2,2));
//        list.add(new MyArr(1,1));

        String str = "123";


        MyArr[] arr = new MyArr[3];
        arr[0] = new MyArr(3, 3);
        arr[1] = new MyArr(2, 2);
        arr[2] = new MyArr(1, 1);

        Arrays.sort(arr);
        for (MyArr a : arr) {
            System.out.println(a.min);
        }
    }

    public static class MyArr implements Comparable<MyArr> {
        long min;
        long max;

        public MyArr(long min, long max) {
            this.min = min;
            this.max = max;
        }

        @Override
        public int compareTo(MyArr o) {
            if (this.min > o.min) {
                return 1;
            } else if (this.min < o.min) {
                return -1;
            } else {
                return 0;
            }
        }
    }
}
