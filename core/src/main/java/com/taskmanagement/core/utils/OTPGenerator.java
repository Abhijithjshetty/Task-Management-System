package com.taskmanagement.core.utils;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class OTPGenerator {
    private OTPGenerator(){}
    private static final Random random = new Random();
    public static String generateRandomNumericOTP(int numberOfDigits) {
        // Ensure that the number of digits is at least 1
        if (numberOfDigits < 1) {
            throw new IllegalArgumentException("Number of digits must be at least 1");
        }

        // Generate a random numeric OTP of the specified number of digits using IntStream
        return IntStream.range(0, numberOfDigits)
                .mapToObj(i -> String.valueOf(random.nextInt(10)))
                .collect(Collectors.joining());
    }
}
