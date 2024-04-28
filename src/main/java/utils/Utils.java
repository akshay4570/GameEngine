package utils;

import java.util.function.Supplier;

public class Utils {

    public static Object singleTon(Object o, Supplier<Object> supplier) {
        if (o == null) {
            return supplier.get();
        }
        return o;
    }
}
