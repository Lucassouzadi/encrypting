package br.unisinos.stream;

import br.unisinos.exception.EndOfFileException;

import java.io.IOException;
import java.io.InputStream;

public class Reader {

    private final InputStream in;

    public Reader(InputStream in) {
        this.in = in;
    }

    public byte readByte() throws EndOfFileException {
        int read = readByteFromInputStream();
        if (read == -1) {
            throw new EndOfFileException();
        }
        return (byte) read;
    }

    private int readByteFromInputStream() {
        try {
            return in.read();
        } catch (IOException ioe) {
            throw new RuntimeException("Error while reading from InputStream", ioe);
        }
    }

}
