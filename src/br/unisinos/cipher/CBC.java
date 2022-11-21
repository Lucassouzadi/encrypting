package br.unisinos.cipher;

import br.unisinos.utils.Properties;
import br.unisinos.utils.Utils;

public class CBC {

    public static final byte[] INITIALIZATION_VECTOR = Utils.expand(new byte[]{55}, Properties.BLOCK_SIZE); // 01010101

    public static byte[][] encrypt(byte[][] blocks, byte[][] keySchedule) {
        byte[][] encryptedBlocks = new byte[blocks.length][blocks[0].length];

        encryptedBlocks[0] = Cipher.encrypt(Utils.xor(INITIALIZATION_VECTOR, blocks[0]), keySchedule);

        for (int i = 1; i < blocks.length; i++) {
            encryptedBlocks[i] = Cipher.encrypt(Utils.xor(blocks[i], encryptedBlocks[i - 1]), keySchedule);
        }

        return encryptedBlocks;
    }

    public static byte[][] decrypt(byte[][] message, byte[][] keySchedule) {
        byte[][] decrypted = new byte[message.length][message[0].length];

        decrypted[0] = Utils.xor(INITIALIZATION_VECTOR, Cipher.decrypt(message[0], keySchedule));

        for (int i = 1; i < message.length; i++) {
            decrypted[i] = Utils.xor(Cipher.decrypt(message[i], keySchedule), message[i - 1]);
        }

        return decrypted;
    }

}
