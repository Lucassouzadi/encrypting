package br.unisinos;

import br.unisinos.utils.Properties;
import br.unisinos.utils.Utils;

import java.io.File;
import java.lang.reflect.Field;

public class Main {

    public static Properties PROPERTIES;

    public static void main(String[] args) throws IllegalAccessException {
        setProperties(args);

        if (!new File(PROPERTIES.file).exists()) {
            System.err.printf("Arquivo \"%s\" não encontrado", PROPERTIES.file);
            System.exit(1);
        }

        byte[] userKey = Utils.hexStringToByteArray(PROPERTIES.key);

        Encrypter encrypter = new Encrypter();
        switch (PROPERTIES.action) {
            case "encrypt":
                encrypter.encryptFile(PROPERTIES.file, userKey);
                break;
            case "decrypt":
                encrypter.decryptFile(PROPERTIES.file, userKey);
                break;
            case "encryptAndDecrypt":
                encrypter.encryptFile(PROPERTIES.file, userKey);
                encrypter.decryptFile(Utils.switchFileExtension(PROPERTIES.file, "ecc"), userKey);
                break;
            default:
                System.out.printf("Ação \"%s\" não conhecida\n", PROPERTIES.action);
                System.exit(1);
        }
    }

    private static void setProperties(String[] args) throws IllegalAccessException {
        PROPERTIES = new Properties();
        for (String arg : args) {
            int separatorIndex = arg.indexOf(':');
            if (separatorIndex != -1) {
                String key = arg.substring(0, separatorIndex);
                String value = arg.substring(separatorIndex + 1);
                PROPERTIES.setProp(key, value);
            }
        }

        System.out.println("\n---- Parâmetros aplicados: ----");
        for (Field field : Properties.class.getFields()) {
            System.out.printf("\t%s:%s\n", field.getName(), field.get(PROPERTIES));
        }
        System.out.println("-------------------------------\n");
    }

}
