package main.basic;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.function.DoubleConsumer;

public class Doc_System {

    public static void main(String[] args) throws Exception{
        System.out.println(getMethodName()); // main

        B b = new B();
        loopField(new B());
        inspect(String.class);
        inspect(String.class);
        inspect(b);
    }

    /**
     * Method Name 얻기
     *
     * */
    public static String getMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }

    public static void getStackTrack() {
        /*StackTraceElement[] strs = Thread.currentThread().getStackTrace();
        System.out.println("StackTraceElement");
        for(int i=0; i<strs.length; i++) {
            System.out.println(strs[i].getClassName());
            System.out.println(strs[i].getMethodName());
            System.out.println(strs[i].getFileName());
            System.out.println(strs[i].getLineNumber());
        }*/
    }

    public static void loopField(B b) {

    }

    static class A {
        int a = 10;
    }

    static class B extends A {
        int b = 20 ;
        double d = 0 ;
    }

    static <T> void inspect(Class<T> klazz) {
        Field[] fields = klazz.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            System.out.printf("%s - %s - %s%n",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName()
            );
        }
    }

    // == 부모필드노출 X ==
    static <T> void inspect(B b) throws Exception {
        Field[] fields = B.class.getDeclaredFields();
        System.out.printf("%d fields:%n", fields.length);
        for (Field field : fields) {
            System.out.printf("%s - %s - %s%n",
                    Modifier.toString(field.getModifiers()),
                    field.getType().getSimpleName(),
                    field.getName()
            );

            System.out.println(field.get(b) instanceof String);
            System.out.println(field.get(b) instanceof Byte);
            System.out.println(field.get(b) instanceof Short);
            System.out.println(field.get(b) instanceof Integer);
            System.out.println(field.get(b) instanceof Long);
            System.out.println(field.get(b) instanceof Double);
            System.out.println(field.get(b) instanceof Float);
            System.out.println(field.get(b));
            System.out.println(field.get(b));
        }
    }

}
