package se.viia.quest.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author affe 2018-04-25
 */
public class ApiUtils {

    private ApiUtils() {}

    public static <F, T> List<T> transform(Iterable<F> iterable, Function<F, T> function) {
        return StreamSupport.stream(iterable.spliterator(), false).map(function).collect(Collectors.toList());
    }
}
