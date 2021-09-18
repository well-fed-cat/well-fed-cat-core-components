package xyz.dsemikin.wellfedcat.utils;

import com.ibm.icu.text.Transliterator;

public class Utils {

    public static void assertState(final boolean statement, final String commentOnError) {
        assert statement : commentOnError;
        //noinspection ConstantConditions
        if (!statement) {
            throw new IllegalStateException(commentOnError);
        }
    }

    public static void assertState(final boolean statement) {
        assertState(statement, "");
    }

    public static String translit(final String rusString) {
        Transliterator transliterator = Transliterator.getInstance("Russian-Latin/BGN");
        return transliterator.transliterate(rusString);
    }
}
