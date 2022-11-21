package br.unisinos;

import br.unisinos.cipher.CBC;
import br.unisinos.cipher.KeyScheduler;
import br.unisinos.exception.EndOfFileException;
import br.unisinos.stream.Reader;
import br.unisinos.stream.Writer;
import br.unisinos.utils.Properties;
import br.unisinos.utils.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encrypter {

    public static int BLOCK_SIZE = Properties.BLOCK_SIZE;

    public Encrypter() {
    }

    public void encryptFile(String filePath, byte[] userKey) {
        File origin = new File(filePath);
        File encrypted = new File(Utils.switchFileExtension(filePath, "ecc"));

        encryptFile(origin, encrypted, userKey);
    }

    public void decryptFile(String filePath, byte[] userKey) {
        File origin = new File(filePath);
        File decrypted = new File(Utils.switchFileExtension(filePath, "dcc"));

        decryptFile(origin, decrypted, userKey);
    }

    private void encryptFile(File origin, File target, byte[] userKey) {
        try (FileInputStream inputStream = new FileInputStream(origin);
             FileOutputStream outputStream = new FileOutputStream(target);
        ) {
            Writer writer = new Writer(outputStream);
            Reader reader = new Reader(inputStream);
            byte[][] rawBlocks = getBlocks(reader, true);
            byte[][] keySchedule = KeyScheduler.generateKeys(userKey, BLOCK_SIZE);
            byte[][] encryptedBlocks = CBC.encrypt(rawBlocks, keySchedule);

            for (byte[] block : encryptedBlocks) {
                for (byte bite : block) {
                    writer.writeByte(bite);
                }
            }

            System.out.printf("Encrypted %s to %s%n", origin.getAbsolutePath(), target.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Error while encrypting " + origin.getAbsolutePath(), e);
        }
    }

    private void decryptFile(File origin, File target, byte[] userKey) {
        try (FileInputStream inputStream = new FileInputStream(origin);
             FileOutputStream outputStream = new FileOutputStream(target);
        ) {
            Writer writer = new Writer(outputStream);
            Reader reader = new Reader(inputStream);
            byte[][] encryptedBlocks = getBlocks(reader, false);
            byte[][] keySchedule = KeyScheduler.generateKeys(userKey, BLOCK_SIZE);
            byte[][] decryptedBlocks = CBC.decrypt(encryptedBlocks, keySchedule);

            for (int i = 0; i < decryptedBlocks.length - 1; i++) {
                for (byte bite : decryptedBlocks[i])
                    writer.writeByte(bite);
            }

            byte[] lastBlock = decryptedBlocks[decryptedBlocks.length - 1];
            int padding = lastBlock[BLOCK_SIZE - 1];

            for (int i = 0; i < lastBlock.length - padding; i++) {
                writer.writeByte(lastBlock[i]);
            }
            System.out.printf("Decrypted %s to %s%n", origin.getAbsolutePath(), target.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException("Error while encrypting " + origin.getAbsolutePath(), e);
        }
    }

    private byte[][] getBlocks(Reader reader, boolean insertPadding) {
        List<byte[]> blockList = new ArrayList<>();
        byte[] block = new byte[BLOCK_SIZE];
        byte padding = 0;

        try {
            while (true) {
                block = new byte[BLOCK_SIZE];
                blockList.add(block);
                padding = (byte) BLOCK_SIZE;
                for (int i = 0; i < BLOCK_SIZE; i++) {
                    byte bite = reader.readByte();
                    block[i] = bite;
                    padding--;
                }
            }
        } catch (EndOfFileException e) {
            if (insertPadding && padding != 0)
                block[BLOCK_SIZE - 1] = padding;
            if (!insertPadding && padding == BLOCK_SIZE)
                blockList.remove(blockList.size() - 1);
        }

        byte[][] blocks = new byte[blockList.size()][BLOCK_SIZE];
        blockList.toArray(blocks);

        return blocks;
    }

}
