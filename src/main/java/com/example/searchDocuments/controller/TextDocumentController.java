package com.example.searchDocuments.controller;

import com.example.searchDocuments.exception.DocumentIndexDeleteException;
import com.example.searchDocuments.model.ResponseForSearch;
import com.example.searchDocuments.service.TextDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class TextDocumentController {
    @Autowired
private TextDocumentService textDocumentService;

@GetMapping("/search/{keyWord}")
public ResponseEntity<List<ResponseForSearch>> searchDocuments(@PathVariable String keyWord)  {
    List<ResponseForSearch> searchResults = textDocumentService.findDocuments(keyWord);
    return new ResponseEntity<>(searchResults,HttpStatus.OK);
}


 @GetMapping("/deleteAll")
 public ResponseEntity<Object> removeAllDocuments() throws DocumentIndexDeleteException {
     textDocumentService.removeAllDocuments();
     return  new ResponseEntity<>(HttpStatus.NO_CONTENT);
 }
}
