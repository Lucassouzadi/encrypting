package br.unisinos.stream;

import java.io.IOException;
import java.io.OutputStream;

public class Writer {

    private final OutputStream out;

    public Writer(OutputStream out) {
        this.out = out;
    }

    private void writeByteToOutputStream(byte value) {
        try {
            out.write(value);
        } catch (IOException ioe) {
            throw new RuntimeException("Error while writing do OutputStream", ioe);
        }
    }

    public void writeByte(byte value) {
        writeByteToOutputStream(value);
    }

}
