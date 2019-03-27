package main.basic;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * #Exception vs (Error - JVM 실행에문제가발생)
 * 예외란 사용자의 잘못된 조작 또는 개발자에 잘못된 코딩으로 인해 발 생하는 프로그램 오류
 * <p>
 * - 일반예외 (컴파일러 체크예외) :: 컴파일하는 과정에서 예외처리코드가 필요한지 검사
 * - 실행예외 :: 컴파일과정에서 예외 처리코드를 검사하지않는 예외
 * <p>
 * #java.lang.Exception
 * java.lang.ClassNotFoundException (일반예외)
 * java.lang.InterruptException (일반예외)
 * ... (일반예외)
 * java.lang.RuntimeException (실행예외)
 * <p>
 * #실행예외
 * 자바컴파일러가 체크를 하지 않기 떄문에 오로지 개발자의 경험에 의해서 예외처리코드를 삽입
 * - NullPointException :: 객체가 없느 상태에서 객체에 접근
 * - ArrayIndexOutofBoundsException :: 배열의 인덱스범위를 초과
 * - NumberFormatException :: 숫자로 변환될 수 없는 문자를 숫자로 변경
 * - ClassCastException :: 강제로 타입변환을 시도할때 발생
 * <p>
 * #스레드의예외처리
 * 추가적인 콜벡메서드로 받을 수 있다.
 */

class Doc_Exception {

    public static void main(String[] args) {

        // # [Catch순서]
        try {
            System.out.println();
        }
        // 하위예외클래스 ( 상위에 있어야 )
        catch (ArrayIndexOutOfBoundsException errB) {
            System.out.println(errB.getMessage());
        }
        // 상위예외클래스 ( 하위에 있어야 )
        catch (Exception errA) {
            System.out.println(errA.getMessage());
        }

        // # [멀티Catch]
        try {
            System.out.println();
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException errA) {
            System.out.println(errA.getMessage());
        } catch (Exception errB) {
            System.out.println(errB.getMessage());
        }

        // # [자동리소스닫기] - try-with-data.html
        //  implement AutoCloseable 인터페이스를 구현하는 클래스에서만 가능

        // [기존]
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("File.txt");
        } catch (IOException e) {

        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                }
            }
        }

        // [자동]
        try (FileInputStream fisB = new FileInputStream("File.txt")) {

        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        try (
                FileInputStream fisB = new FileInputStream("File.txt");
                FileInputStream fisC = new FileInputStream("File.txt");
        ) {

        } catch (IOException err) {
            System.out.println(err.getMessage());
        }

        // # [예외떠넘기기]
        try {
            findClass();
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage()); // 예외메세지출력
            e.printStackTrace(); // 예외위치를추척
        }

        // [JVM에게 에러떠넘기기]
        // public static void main(String[] args) throws ClassNotFoundException {}

    }

    // # [예외떠넘기기]
    public static void findClass() throws ClassNotFoundException {
        Class clazz = Class.forName("java.lang.String2");
    }

    // # [사용자정의예외]
    public static class UserException extends Exception {
        public UserException() {
        }

        public UserException(String m) {
            super(m);
        }
    }

    public static class UserRuntimeException extends RuntimeException {
        public UserRuntimeException() {
        }

        public UserRuntimeException(String m) {
            super(m);
        }
    }

    // # [스레드의예외처리]
    public static void threadMehtod() {
        Thread thread = new Thread() {
            @Override
            public void run() {
                throw new RuntimeException("in thread error");
            }
        };
        thread.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // 스레드에서 던지는 예외
                System.out.println(e.getMessage());
            }
        });
        thread.start();
    }
}