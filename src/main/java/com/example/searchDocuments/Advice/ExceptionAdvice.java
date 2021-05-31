package com.example.searchDocuments.Advice;

import com.example.searchDocuments.exception.CloudAccessException;
import com.example.searchDocuments.exception.DocumentIndexDeleteException;
import com.example.searchDocuments.exception.DocumentParseException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ControllerAdvice
public class ExceptionAdvice extends Exception{


     @ExceptionHandler(value = {InvalidDataAccessApiUsageException.class})
     public ResponseEntity<Object> handleInvalidDataAccessApiUsageException(InvalidDataAccessApiUsageException ex)
     {
         return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
     }
     @ExceptionHandler(value = {CloudAccessException.class})
     public ResponseEntity<Object> handleCloudAccessException(CloudAccessException ex)
     {
         return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
     }
    @ExceptionHandler(value = {DocumentIndexDeleteException.class})
    public ResponseEntity<Object> handleDocumentIndexDeleteException(DocumentIndexDeleteException ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(value = {DocumentParseException.class})
    public ResponseEntity<Object> handleDocumentParseException(DocumentParseException ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(value = {IOException.class})
    public ResponseEntity<Object> handleIOException(IOException ex)
    {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.BAD_REQUEST);
    }
}
