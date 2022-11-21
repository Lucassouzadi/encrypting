package br.unisinos.cipher;

import br.unisinos.utils.Utils;

import java.util.Arrays;
import java.util.List;

public class Cipher {

    // TODO: Criar nova matriz de transposicao caso o tamanho do bloco mude
    static int[] transpositionMatrix = new int[]{3, 2, 0, 4, 5, 1};
    // Matriz arbitraria gerada aleatoriamente
    static List<Integer> substitutionMatrix = Arrays.asList(122, 118, 139, 151, 191, 25, 104, 65, 195, 217, 93, 40, 120, 57, 76, 54, 45, 162, 226, 198, 70, 90, 48, 202, 32, 46, 10, 141, 144, 107, 194, 136, 210, 128, 169, 110, 2, 5, 167, 238, 184, 78, 53, 250, 135, 249, 253, 181, 160, 185, 0, 243, 166, 44, 187, 251, 91, 153, 105, 42, 19, 69, 201, 196, 134, 220, 155, 177, 4, 158, 108, 221, 161, 80, 190, 12, 85, 33, 205, 126, 192, 236, 176, 172, 149, 77, 235, 228, 209, 94, 37, 170, 112, 200, 219, 229, 242, 218, 103, 97, 142, 189, 116, 138, 129, 87, 88, 1, 193, 30, 15, 79, 239, 68, 82, 140, 86, 47, 24, 43, 123, 84, 223, 246, 96, 173, 157, 165, 168, 113, 63, 7, 6, 212, 147, 254, 14, 227, 39, 109, 137, 95, 208, 62, 49, 145, 21, 180, 13, 8, 83, 114, 18, 131, 244, 214, 51, 71, 101, 132, 182, 34, 29, 9, 26, 89, 175, 252, 234, 213, 99, 17, 41, 197, 11, 143, 188, 102, 186, 111, 23, 174, 106, 247, 233, 148, 179, 22, 224, 222, 61, 154, 199, 178, 67, 64, 211, 36, 59, 92, 121, 241, 60, 124, 216, 100, 115, 16, 171, 81, 203, 52, 20, 215, 237, 74, 204, 117, 152, 127, 240, 164, 146, 225, 245, 125, 133, 35, 66, 255, 183, 248, 163, 55, 159, 31, 98, 130, 56, 28, 156, 50, 230, 232, 73, 75, 150, 3, 72, 58, 231, 119, 206, 38, 27, 207);

    public static byte[] encrypt(byte[] block, byte[][] keys) {
        byte[] encryptedBlock = block.clone();

        for (byte[] key : keys) {
            encryptedBlock = Utils.xor(encryptedBlock, key);
            encryptedBlock = substitutionCipher(encryptedBlock);
            encryptedBlock = transpositionCipher(encryptedBlock);
        }

        return encryptedBlock;
    }

    public static byte[] decrypt(byte[] block, byte[][] keys) {
        byte[] decryptedBlock = block.clone();

        for (int i = keys.length - 1; i >= 0; i--) {
            decryptedBlock = inverseTranspositionCipher(decryptedBlock);
            decryptedBlock = inverseSubstitutionCipher(decryptedBlock);
            decryptedBlock = Utils.xor(decryptedBlock, keys[i]);
        }

        return decryptedBlock;
    }

    public static byte[] substitutionCipher(byte[] bytes) {
        byte[] ciphered = new byte[bytes.length];
        for (int i = 0; i < ciphered.length; i++) {
            ciphered[i] = substitutionMatrix.get(Byte.toUnsignedInt(bytes[i])).byteValue();
        }
        return ciphered;
    }

    public static byte[] inverseSubstitutionCipher(byte[] bytes) {
        byte[] ciphered = new byte[bytes.length];
        for (int i = 0; i < ciphered.length; i++) {
            ciphered[i] = (byte) substitutionMatrix.indexOf(Byte.toUnsignedInt(bytes[i]));
        }
        return ciphered;
    }

    public static byte[] transpositionCipher(byte[] bytes) {
        byte[] ciphered = new byte[bytes.length];
        for (int i = 0; i < transpositionMatrix.length; i++) {
            ciphered[transpositionMatrix[i]] = bytes[i];
        }
        return ciphered;
    }

    public static byte[] inverseTranspositionCipher(byte[] bytes) {
        byte[] ciphered = new byte[bytes.length];
        for (int i = 0; i < transpositionMatrix.length; i++) {
            ciphered[i] = bytes[transpositionMatrix[i]];
        }
        return ciphered;
    }

}
