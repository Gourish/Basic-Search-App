package com.example.searchDocuments.exception;


import java.security.GeneralSecurityException;

public class CloudAccessException extends Exception {

    public CloudAccessException(Exception e) {
        super(e);
    }
}
