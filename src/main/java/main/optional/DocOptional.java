package main.optional;

import java.util.Optional;
import java.util.function.Supplier;

public class DocOptional {

    /**
     * [Using and avoiding null]
     * null can be ambiguous, can cause confusing errors, and is sometimes just plain unpleasant. Many Guava utilities reject and fail fast on nulls, rather than accepting them blindly.
     */

    public static void main(String[] args) {

        optionalOf();
        optionalOfNullable();
        optionalEmpty();
        optionalIsPresent();
        optionalGet();
        optionalOrElse();
        optionalOrElseThrow();
    }


    /* of() */
    public static void optionalOf() {
        System.out.println("[optionalOf]");
        Optional<Integer> optIntA = Optional.of(1);
        // Optional<Integer> optIntB = Optional.of(null); // NPE
        System.out.println(optIntA);
    }

    /* ofNullable() */
    public static void optionalOfNullable() {
        System.out.println("[optionalOfNullable]");
        Optional<Integer> optIntA = Optional.ofNullable(null);
        Optional<Integer> optIntB = Optional.ofNullable(1);
        System.out.println(optIntA); // Optional.emtpy
        System.out.println(optIntB); // Optional[1]
    }

    /* empty()  */
    public static void optionalEmpty() {
        System.out.println("[optionalEmpty]");
        System.out.println(Optional.empty()); // Optional.empty
        Optional<String> optString = Optional.empty();
    }

    /* isPresent() */
    public static void optionalIsPresent() {
        System.out.println("[optionalIsPresent]");
        Optional<Integer> optIntA = Optional.of(1);
        Optional<Integer> optIntB = Optional.ofNullable(null);
        System.out.println(optIntA.isPresent()); // true
        System.out.println(optIntB.isPresent()); // false
    }

    /* get() : isPresent()와 같이 사용 */
    public static void optionalGet() {
        System.out.println("[optionalGet]");
        Optional<Integer> optIntA = Optional.of(1);
        Optional<Integer> optIntB = Optional.ofNullable(null);
        System.out.println(optIntA.get()); // 1
        // System.out.println(optIntB.get()); // NoSuchElementException
    }

    /* orElse() */
    public static void optionalOrElse() {
        System.out.println("[optionalOrElse]");
        Optional<Integer> optIntA = Optional.of(1);
        Optional<Integer> optIntB = Optional.ofNullable(null);
        System.out.println(optIntA.orElse(0/*null default*/)); // 1
        System.out.println(optIntB.orElse(0/*null default*/)); // 0
    }

    /*  orElseGet(Supplier<? extends T> other) */
    public static void optionalOrElseGet() {
        System.out.println("[optionalOrElseGet]");
        Optional<Integer> optIntA = Optional.of(1);
        Optional<Integer> optIntB = Optional.ofNullable(null);

        Supplier<Integer> supplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return 0; /*null default*/
            }
        };
        System.out.println(optIntA.orElseGet(supplier)); // 1
        System.out.println(optIntB.orElseGet(supplier)); // 0
    }

    /*  orElseThrow() */
    public static void optionalOrElseThrow() {
        System.out.println("[optionalOrElseThrow]");
        Optional<Integer> optIntA = Optional.of(1);
        Optional<Integer> optIntB = Optional.ofNullable(null);

        Supplier<Exception> supplier = new Supplier<Exception>() {
            @Override
            public Exception get() {
                return new Exception("test_exception");
            }
        };

        try {
            System.out.println(optIntA.orElseThrow(supplier)); // 1
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(optIntB.orElseThrow(supplier)); // Exception
        } catch (Exception e) {
            System.out.println(e.getMessage()); // test_exception
        }
    }


}
