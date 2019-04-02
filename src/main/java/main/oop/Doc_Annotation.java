package main.oop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * #어노테이션이 뭐에요?
 * 어노테이션은 메타데이터라고 볼 수 있습니다.
 * 메타데이터는 앱에서 처리할 데이터가 아니라,
 * 컴파일과정 실행과정에서 코드를 어떻게 컴파일하고 처리할지 알려주는 정보입니다.
 * <p>
 * #어노테이션유지정책
 * Source :: 소스상에서만
 * CLASS :: 바이트코드파일까지
 * RUNTIME :: 런타임시까지
 */

public class Doc_Annotation {

    // 어노테이션의선언
    @Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD}) // <= 타입 / 필드 / 메서드에 적용되고
    @Retention(RetentionPolicy.RUNTIME) // <= 런타임시에
    public @interface AnnotationName {
        String elementNameA();

        int elementNameB() default 5;
    }

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    public @interface PrintAnnotation {
        String value() default "-";

        int number() default 15;
    }

    @AnnotationName(elementNameA = "^", elementNameB = 5)
    private String fieldName;

    @PrintAnnotation()
    public void methodA() {
        System.out.println("실행내용1");
    }

    @PrintAnnotation("*")
    public void methodB() {
        System.out.println("실행내용2");
    }

    @PrintAnnotation(value = "#", number = 20)
    public void methodC() {
        System.out.println("실행내용3");
    }
}
