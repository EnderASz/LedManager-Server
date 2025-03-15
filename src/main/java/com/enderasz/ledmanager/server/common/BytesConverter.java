package com.enderasz.ledmanager.server.common;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Scanner;

public class BytesConverter {
    public static byte[] stringToBytes(String input) {
        // Initialize a ByteArrayOutputStream to store the resulting bytes
        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();

        // Iterate over each character in the input string
        for (int i = 0; i < input.length(); i++) {
            char currentChar = input.charAt(i);

            // Check for \x prefix indicating a hex byte sequence
            if (currentChar == '\\' && i + 3 < input.length() && input.charAt(i + 1) == 'x') {
                // Ensure it is not escaped (\\x)
                if (i > 0 && input.charAt(i - 1) == '\\') {
                    continue;
                }

                char hex1 = input.charAt(i + 2);
                char hex2 = input.charAt(i + 3);

                // Ensure it's a valid hexadecimal pair
                if (isHexCharacter(hex1) && isHexCharacter(hex2)) {
                    // Convert the hex pair to a byte and add it to the byte stream
                    String hexValue = "" + hex1 + hex2;
                    byteStream.write(Integer.parseInt(hexValue, 16));

                    // Skip the next three characters
                    i += 3;
                    continue;
                }
            }

            // Convert the current character to its byte value and add it to the byte stream
            byteStream.write((byte) currentChar);
        }

        return byteStream.toByteArray();
    }

    private static boolean isHexCharacter(char c) {
        return (c >= '0' && c <= '9') || (c >= 'A' && c <= 'F') || (c >= 'a' && c <= 'f');
    }

    public static String charToHex(char ch) {
        return HexFormat.of().toHexDigits(ch).substring(2);
    }

    public static String stringifyHex(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
            hexString.append(" ");
        }
        return hexString.toString();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a string to convert to bytes:");
        String input = scanner.nextLine();

        byte[] result = stringToBytes(input);

        System.out.println("Converted byte array:");
        System.out.println(Arrays.toString(result));

        scanner.close();
    }
}
