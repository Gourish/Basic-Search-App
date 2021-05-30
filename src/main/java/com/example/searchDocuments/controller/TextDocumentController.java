package com.example.searchDocuments.controller;

import com.example.searchDocuments.model.ResponseForSearch;
import com.example.searchDocuments.service.TextDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/")
public class TextDocumentController {
    @Autowired
private TextDocumentService textDocumentService;

@GetMapping("/search/{keyWord}")
public List<ResponseForSearch> getDocuments(@PathVariable String keyWord){
    List<ResponseForSearch> searchResults = textDocumentService.findDocuments(keyWord);
    return searchResults;
}
//@GetMapping("/addFile")
//public void addDocument()
//{
//
//    //textDocumentService.addTextDocument(textDocument);
//}
//@GetMapping("/search")
//public void findDocuments()
// {
//    List<String> webLinks = textDocumentService.findDocuments("your");
// }

 @GetMapping("/deleteAll")
 public void removeAllDocuments()
 {
     textDocumentService.removeAllDocuments();
 }
}
