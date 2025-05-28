package cursoSpringBoot.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.text.Normalizer;

@RestController
public class IsPalindromeRestController {

    /**
     * Endpoint para verificar si un texto es un palindromo
     * @param str el texto a revisar si es un palindromo
     * @return Un mensaje si el texto es un palindromo o no
     */
    @GetMapping("/palindrome/{str}")
    public String isPalindrome(@PathVariable String str) {
        return checkPalindrome(str) ? "\"" + str + "\" es un palíndromo" : "\"" + str + "\" NO es un palíndromo";
    }

    private boolean checkPalindrome(String input) {
        if (input == null || input.isEmpty()) return false;

        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        String withoutDiacritics = normalized.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String cleaned = withoutDiacritics.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();

        int start = 0;
        int end = cleaned.length() - 1;

        while (start <= end) {
            if (cleaned.charAt(start) != cleaned.charAt(end)) {
                return false;
            }
            start++;
            end--;
        }
        return true;
    }

    private boolean checkPalindrome2(String str) {
        for (int i = 0, len = str.length(); i < len / 2; i++) {
            if (str.charAt(i) != str.charAt(len - 1)) {
                return false;
            }
        }
        return true;
    }
}
