package cursoSpringBoot.utils;

import java.text.Normalizer;

public class TextUtils {
    public static String normalizeText(String input) {
        if (input == null) return null;
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        // Elimina los diacríticos (acentos)
        return normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "").toLowerCase();
    }
}
