package main.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * #Class 클래스
 * 자바는 클래스와 인터페이스의 메타 데이터를 java.lang 패키지에 소속된 Class로 관리함
 * 여기서 메타 데이터란 클래스의 이름, 생성자 정보, 필드 정보, 메소드 정보를 말합니다.
 * <p>
 * #API
 * - getClass() :: Class 객체 얻기
 * - forName() :: Class 객체 얻기
 * - getDeclaredConstructors() :: 생성자의 정보 얻기
 * - getDeclaredFields() :: 필드의 정보 얻기
 * - getDeclaredMethods() :: 메소드의 정보 얻기
 * - newInstance() :: 동적 객체 생성, 클래스 이름을 결정할 수 없고, 런타임 시에 클래스 이름이 결정되는 경우에 매우 유용하게 사용
 */

public class Doc_Class {

    Doc_Class(int a) {
    }

    ;

    Doc_Class(int a, String b) {
    }

    ;

    int a;
    String b;

    static class C {
    }

    public static void main(String[] args) throws Exception {
        // #getClass()
        Object obj = new Object();
        Class classA = obj.getClass();
        System.out.println("[getName()] " + classA.getName());
        System.out.println("[getSimpleName()] " + classA.getSimpleName());
        System.out.println("[getPackage().getName()] " + classA.getPackage().getName());

        // #forName()
        try {
            Class classB = Class.forName("main.api.Doc_Class"); // 클래스에 해당
            System.out.println("[getName()] " + classB.getName());
            System.out.println("[getSimpleName()] " + classB.getSimpleName());
            System.out.println("[getPackage().getName()] " + classB.getPackage().getName());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        // #getDeclaredConstructor() :: 생성자
        // #getDeclaredFields() :: 필드
        // #getDeclaredMethods() :: 메소드

        Class classC = Class.forName("main.api.Doc_Class");

        // 생성자정보
        System.out.println("[생성자정보]");
        Constructor[] constructors = classC.getDeclaredConstructors();
        for (Constructor c : constructors) {
            Class[] parameters = c.getParameterTypes();

            System.out.printf(c.getName() + "(");
            System.out.printf(getStringParams(parameters));
            System.out.printf(")");
            System.out.println();
        }
        System.out.println();

        // 필드정보
        System.out.println("[필드정보]");
        Field[] fields = classC.getDeclaredFields();
        for (Field f : fields) {
            System.out.println(f.getType().getSimpleName() + " " + f.getName());
        }
        System.out.println();

        // 메소드정보
        System.out.println("[메소드정보]");
        Method[] methods = classC.getDeclaredMethods();
        for (Method m : methods) {
            Class[] parameters = m.getParameterTypes();

            System.out.printf(m.getName() + "(");
            System.out.printf(getStringParams(parameters));
            System.out.printf(")");
            System.out.println();
        }
        System.out.println();

        // #newInstance() - 동적객체생성 (desparated)
        // => 런타임시에 클래스 이름이 결정되는 경우 유용
        Class classD = Class.forName("main.api.Doc_Class");
        Object newInstance = classD.newInstance();

        // Class.newInstance() => Constructor.newInstance()

    }

    // 매개변수를리턴하는함수
    static public String getStringParams(Class[] parameters) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < parameters.length; i++) {
            sb.append(parameters[i].getName());
            if (i < parameters.length - 1) {
                sb.append(",");
            }
        }
        //        if(sb.length() > 0) {
        //            sb.delete(sb.length() - 2, sb.length()); // 뒤에2칸제거
        //        }
        return sb.toString();
    }


}
