package br.unisinos.cipher;

import br.unisinos.utils.Properties;
import br.unisinos.utils.Utils;

public class KeyScheduler {

    public static final byte[] INITIALIZATION_KEY = CBC.INITIALIZATION_VECTOR;
    private static final int KEY_AMOUNT = 10;

    public static byte[][] generateKeys(byte[] userKey, int keySize) {
        byte[][] keys = new byte[KEY_AMOUNT][keySize];

        byte[] initialKey = Utils.xor(Utils.expand(userKey, keySize), INITIALIZATION_KEY);

        for (int i = 0; i < keys.length; i++) {
            for (int j = 0; j < keys[i].length; j++) {
                keys[i][j] = initialKey[(j + i) % Properties.BLOCK_SIZE];
            }
        }

        return keys;
    }

}
