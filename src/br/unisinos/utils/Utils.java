package br.unisinos.utils;

import br.unisinos.Main;

import java.util.Arrays;

public class Utils {

    public static String switchFileExtension(String filePath, String newExtension) {
        int lastPeriod = filePath.lastIndexOf('.');
        lastPeriod = lastPeriod != -1 ? lastPeriod : filePath.length();

        String fileNameWithoutExtension = filePath.substring(0, lastPeriod);

        return fileNameWithoutExtension + '.' + newExtension;
    }

    public static byte[] expand(byte[] key, int size) {
        byte[] expandedKey = new byte[size];
        int remaining = size;
        while (remaining > 0) {
            System.arraycopy(key, 0, expandedKey, size - remaining, Math.min(key.length, remaining));
            remaining -= key.length;
        }
        return expandedKey;
    }

    public static byte xor(byte a, byte b) {
        return (byte) (a ^ b);
    }

    public static byte[] xor(byte[] block1, byte[] block2) {
        byte[] array = new byte[block1.length];

        for (int i = 0; i < array.length; i++) {
            array[i] = xor(block1[i], block2[i]);
        }

        return array;
    }

    public static byte[] hexStringToByteArray(String string) {
        byte[] key = new byte[string.length() / 2];
        for (int i = 0; i < string.length() / 2; i++) {
            char nibble1 = string.charAt(i * 2);
            char nibble2 = string.charAt(i * 2 + 1);
            key[i] = (byte) (Character.digit(nibble1, 16) * 16 + Character.digit(nibble2, 16));
        }
        if (key.length != Main.PROPERTIES.USER_KEY_SIZE) {
            System.out.println("Chave inválida: " + string + ", deve ter 4 bytes (hex) de tamanho");
            System.exit(1);
        } else {
            System.out.println("key: " + Arrays.toString(key));
        }
        return key;
    }
}
