package br.unisinos.utils;

import java.lang.reflect.Field;

public class Properties {

    public String action = "encryptAndDecrypt";
    public String file = "alice29.txt";
    public String key = "01020304";
    public final int USER_KEY_SIZE = 4; // 32 bits
    public static final int BLOCK_SIZE = 6; // 48 bits

    public void setProp(String key, String value) {
        try {
            Field field = Properties.class.getDeclaredField(key);
            switch (field.getType().toString()) {
                case "boolean":
                    field.set(this, Boolean.valueOf(value));
                    break;
                case "int":
                    field.set(this, Integer.valueOf(value));
                    break;
                default:
                    field.set(this, value);
                    break;
            }
        } catch (NoSuchFieldException e) {
            System.err.printf("Parâmetro desconhecido: \"%s\"\n", key);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
