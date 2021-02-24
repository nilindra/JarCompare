package org.jarcompare.util;

/**
 * @author Nilindra.Fernando
 */
public class BeanUtils {

    private BeanUtils() { }

    public static String maskNull(String s) {
        return s != null ? s.trim() : "";
    }

}
