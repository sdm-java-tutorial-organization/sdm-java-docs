package main.optional;

import java.util.List;
import java.util.Optional;

public class DocOptionalFunctional {


    public static void main(String[] args) {

        optionalMap();
        optionFilter();
        optionalIfPresent();
        optionalFlatMap();
    }

    /* Optional<U> map(Function<? super T, Optional<U>> mapper) : auto optional return */
    public static void optionalMap() {
        System.out.println("[optionalMap]");
        /*
        [sample]
        return Optional.ofNullable(order)
			.map(Order::getMember)
			.map(Member::getAddress)
			.map(Address::getCity)
			.orElse("Seoul");
        */

        int length = Optional.ofNullable("hello")
                .map(String::trim)
                .map(String::length)
                .orElse(0);

        System.out.println(length); // 5
    }

    /* Optional<U> flatMap(Function<? super T, Optional<U>> mapper) : raw object return */
    public static void optionalFlatMap() {
        System.out.println("[optionalFlatMap]");
        Optional<String> optString = Optional.of("input");
        System.out.println(optString.map(str -> Optional.of("hello"))); // Optional[Optional[hello]]
        System.out.println(optString.map(str -> "hello")); // Optional[hello]
        // System.out.println(optString.flatMap(str->"hello"));
        System.out.println(optString.flatMap(str -> Optional.of("hello"))); // Optional[hello]
    }

    /* filter() */
    public static void optionFilter() {
        System.out.println("[optionFilter]");
        /*
         [sample]
        return Optional.ofNullable(order)
			.filter(o -> o.getDate().getTime() > System.currentTimeMillis() - min * 1000)
			.map(Order::getMember);
        */

        int length = Optional.ofNullable("hello")
                .filter(o -> o.length() > 5) // Optional.empty
                .map(String::length)
                .orElse(0);

        System.out.println(length); // 0
    }

    /* ifPresent(Consumer<? super T> consumer) : 비동기의 콜백과 같이 동작 */
    public static void optionalIfPresent() {
        System.out.println("[optionalIfPresent]");
        Optional<String> optString = Optional.of("hello");
        optString.ifPresent(str -> {
            System.out.println("length : " + str.length());
        });
    }

    /* 샘플 getAsOptional */
    public static <T> Optional<T> getAsOptional(List<T> list, int index) {
        try {
            return Optional.of(list.get(index));
        } catch (ArrayIndexOutOfBoundsException e) {
            return Optional.empty();
        }
    }

    /* http://www.daleseo.com/java8-optional-effective/ */
}
