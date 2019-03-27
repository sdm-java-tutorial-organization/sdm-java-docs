package main;

import deprecated.study.feature.SDM_Array;

public class ArrayMain {

    public static void main(String[] args) {

        SDM_Array sArray = new SDM_Array(2);

        deprecated.study.Grade g1 = new deprecated.study.Grade("허씨", "id0001");
        g1.inputGrade(100, 100, 100);
        sArray.put(0, g1);

        deprecated.study.Grade g2 = new deprecated.study.Grade("민씨", "id0002");
        g2.inputGrade(100, 100, 100);
        sArray.put(1, g2);

        deprecated.study.Grade g3 = new deprecated.study.Grade("오씨", "id0003");
        g3.inputGrade(100, 100, 100);
        sArray.put(2, g3);

        // System.out.println(sArray.get(0).print());
        // System.out.println(sArray.get(1).print());
        // System.out.println(sArray.get(2).print());
        System.out.println(sArray.length());
    }
}
