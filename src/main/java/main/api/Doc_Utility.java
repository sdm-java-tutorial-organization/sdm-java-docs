
package main.api;

import java.awt.*;
import java.io.Console;
import java.util.Scanner;

public class Doc_Utility {
    public static void main(String[] args) {

        /**
         * 툴킷 (java.awt.*)
         *
         * #01. 비프음
         * */

        // #01.
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        toolkit.beep();

        /**
         * 유틸 (java.util.*)
         *
         * #01. 스캐너
         * */

        Scanner stdIn = new Scanner(System.in);
        int n = stdIn.nextInt();

        // nexBoolean();
        // nexByte();
        // nexShort();
        // nextInt();
        // nextLong();
        // nextFloat();
        // nextDouble();
        // nextLine();

        /**
         * 입출력 (java.io.*)
         *
         * #01. 콘솔
         * */

        Console console = System.console();

        System.out.println("아이디 : ");
        String strId = console.readLine();

        System.out.println("패스워드 : ");
        char[] charPassword = console.readPassword();
        String strPassword = new String(charPassword);

        System.out.println("===================");
        System.out.println(strId);
        System.out.println(strPassword);
    }
}