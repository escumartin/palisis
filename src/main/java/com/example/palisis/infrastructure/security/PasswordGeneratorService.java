package com.example.palisis.infrastructure.security;


import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PasswordGeneratorService {

    private static final String UPPERCASE_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWERCASE_CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static final String DIGIT_CHARACTERS = "0123456789";
    private static final String SPECIAL_CHARACTERS = "!@#$%^&*(),.?\":;{}|<>";
    private static final String ALL_ALLOWED_CHARACTERS = UPPERCASE_CHARACTERS
            + LOWERCASE_CHARACTERS
            + DIGIT_CHARACTERS
            + SPECIAL_CHARACTERS;

    private final SecureRandom random = new SecureRandom();

    public String generateSecurePassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("Password length must be at least 8 characters.");
        }

        StringBuilder password = new StringBuilder();

        password.append(getRandomCharacter(UPPERCASE_CHARACTERS));
        password.append(getRandomCharacter(LOWERCASE_CHARACTERS));
        password.append(getRandomCharacter(DIGIT_CHARACTERS));
        password.append(getRandomCharacter(SPECIAL_CHARACTERS));

        for (int i = 4; i < length; i++) {
            password.append(getRandomCharacter(ALL_ALLOWED_CHARACTERS));
        }

        return shuffleString(password.toString());
    }

    private char getRandomCharacter(String characters) {
        return characters.charAt(random.nextInt(characters.length()));
    }

    private String shuffleString(String input) {
        List<Character> characters = input.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        Collections.shuffle(characters, random);

        StringBuilder shuffled = new StringBuilder();
        for (char c : characters) {
            shuffled.append(c);
        }
        return shuffled.toString();
    }
}
