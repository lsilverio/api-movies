package br.com.teste.backend.exception;

public class FileCsvNotFoundException extends RuntimeException {

    public FileCsvNotFoundException(String message) {
        super(message);
    }

    public FileCsvNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

