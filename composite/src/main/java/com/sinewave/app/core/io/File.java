package com.sinewave.app.core.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class File {
    private BufferedReader read = null;
    private BufferedWriter write = null;
    private String filePath;
    private boolean writable = false;
    public File(String filePath) {
        this.filePath = filePath;
        try {
            read = new BufferedReader(new FileReader(filePath));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("\nCreating File!");
            try {
                write = new BufferedWriter(new FileWriter(filePath));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            writable = true;
        }
    }
    public void writeable() {
        if (read != null) {
            try {
                read.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            write = new BufferedWriter(new FileWriter(filePath));
            writable = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void readable() {
        if (write != null) {
            try {
                write.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            read = new BufferedReader(new FileReader(filePath));
            writable = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String readLine() {
        if (writable) {
            System.err.println("Cannot read from writable File!\nFile must be readable to be able to read from it!");
            return null;
        }
        try {
            return read.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void writeLine(String line) {
        if (!writable) {
            System.err.println("Cannot write to readable File!\nFile must be writable to be able to write to it!");
            return;
        }
        try {
            write.write(line + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
