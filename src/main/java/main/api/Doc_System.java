package main.api;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * #System
 * 자바프로그램은 운영체제 위에서 실행되는 것이 아니라 JVM위에서 실행되게 됩니다.
 * 따라서 운영체제의 모든 기능을 자바 코드로 직접접근하기는 어렵습니다.
 * 하지만 System 클래스를 이용하면 운영체제의 일부 기능을 이용할 수 있습니다.
 * <p>
 * #기능
 * - exit() :: 프로그램종료 , exit(0) 정상종료일 경우 / 그 외에는 비정상 종료
 * - gc() :: 쓰레기 수집기 실행 , 직접실행은 할 수 없고 실행 요청 대기의 개념에 가까움
 * - currentTimeMillis() :: 밀리세컨드 단위 (1/1000초)
 * - nanoTime() :: 나노세컨드 단위 (1/109초)
 * - getProperty() :: 시스템 프로퍼티 읽기
 * - getProperties(); :: 모든 속성의 키와 값을 출력
 * - getenv() :: 환경변수읽기, 환경변수란 프로그램상의 변수가 아니라 운영체제에서 이름(name)과 값(value)로 관리되는 문자열 정보입니다.
 */

public class Doc_System {
    public static void main(String[] args) throws Exception {

        // #exit => checkExit 자동호출
        System.exit(0); // 0은 정상종료를 의미합니다. (그외엔 비정상종료)

        // #정상종료의 상태값을 직접 수정할 수 있습니다.
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                if (status != 5) {
                    throw new SecurityException(); // 비정상종료
                }
                // == 5 정상종료
            }
        });

        // #gc() - 쓰레기수집기 실행
        System.gc();

        // #currentTimeMiles(), nanoTime()
        long timeA = System.currentTimeMillis(); // ( 1 / 1000 )
        long timeB = System.nanoTime(); // ( 1/ 109 )

        // #getProperty() - 시스템자원읽기
        System.out.println("[java.version] " + System.getProperty("java.version"));
        System.out.println("[java.home] " + System.getProperty("java.home"));
        System.out.println("[os.name] " + System.getProperty("os.name"));
        System.out.println("[file.separator] " + System.getProperty("file.separator"));
        System.out.println("[user.name] " + System.getProperty("user.name"));
        System.out.println("[user.home] " + System.getProperty("user.home"));
        System.out.println("[user.dir] " + System.getProperty("user.dir"));

        // #getenv()
        String value = System.getenv("JAVA_HOME");
        System.out.println("[JAVA_HOME] " + value);

        // #01. 콘솔

        // #01. System.in
//        InputStream is = System.in;
//        try {
//            // * "1byte"만 읽을수 있어 한글은 읽을 수 없음
//
////            // 아스키코드 :: 1byte(256가지의 숫자)에 영어, 알파벳, 숫자, 특수기호가 있는 코드
////            System.out.print("ascii :");
////            int inputAscii = is.read();
////            System.out.println(inputAscii);
////
////            // char
////            System.out.print("char :");
////            char inputChar = (char) is.read();
////            System.out.println(inputChar);
//
//            // 한글
////            byte[] datas = new byte[100];
////
////            System.out.print("한글 :");
////            int nameBytes = System.in.read(datas);
////            String name = new String(datas, 0, nameBytes - 2);
////
////            System.out.print("목표 :");
////            int goalBytes = System.in.read(datas);
////            String goal = new String(datas, 0, goalBytes - 2);
////
////            System.out.println(name);
////            System.out.println(goal);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // #02. System.out

        // OutputStream
        OutputStream os = System.out;

        // byte => ascill 출력
        byte b = 97;
        os.write(b);
        os.flush();

        // string 출력
        String name = "홍길동";
        byte[] nameBytes = name.getBytes();
        os.write(nameBytes);
        os.flush();

        // PrintStream
        PrintStream ps = System.out;
        ps.println("홍홍홍");

        // System.err

    }
}