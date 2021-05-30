package com.example.searchDocuments.controller;

import com.example.searchDocuments.data.FileOperationsOnCloud;

import com.example.searchDocuments.model.Resource;
import com.example.searchDocuments.parser.FileParser;
import com.example.searchDocuments.service.TextDocumentService;
import org.apache.tika.exception.TikaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/data")
public class CloudStorageController {

     @Autowired
     private FileOperationsOnCloud fileOperationsOnCloud;
     @Autowired
     private FileParser fileParser;
     @Autowired
     private TextDocumentService textDocumentService;

     @GetMapping("/download")
     public ResponseEntity<Object> downloadFilesfromCloud() throws GeneralSecurityException, IOException, TikaException, SAXException {
          List<Resource> resources = fileOperationsOnCloud.downloadFilesfromCloud();
          Map<Resource,String> resourceStringMap = fileParser.parseFile(resources);
          textDocumentService.createTextDocumentFromResource(resourceStringMap);
          return new ResponseEntity<>(HttpStatus.CREATED);
     }
     @GetMapping("/creatFolder")
     public ResponseEntity<Object> createFoldersOnCloud() throws GeneralSecurityException, IOException {
          fileOperationsOnCloud.createFilesOnCloud();
          return new ResponseEntity<>(HttpStatus.CREATED);
     }
     @GetMapping("/uploadFile")
     public  ResponseEntity<Object> createFilesOnCloud() throws IOException {
          fileOperationsOnCloud.uploadFile();
          return new ResponseEntity<>(HttpStatus.CREATED);
     }
     @GetMapping("/deleteFile")
     public void deleteFile() throws IOException {
          fileOperationsOnCloud.deleteFile();
     }


}
