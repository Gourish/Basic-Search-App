package com.example.searchDocuments.controller;

import com.example.searchDocuments.data.FileOperationsOnCloud;

import com.example.searchDocuments.model.Resource;
import com.example.searchDocuments.parser.PDFFileParser;
import org.apache.http.protocol.HTTP;
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

@Controller
@RequestMapping("/")
public class CloudStorageController {

     @Autowired
     private FileOperationsOnCloud fileOperationsOnCloud;
     @Autowired
     private PDFFileParser pdfFileParser;

     @GetMapping("/download")
     public ResponseEntity<Object> downloadFilesfromCloud() throws GeneralSecurityException, IOException, TikaException, SAXException {
          List<Resource> resources = fileOperationsOnCloud.downloadFilesfromCloud();
          pdfFileParser.parseFile(resources);
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

}
