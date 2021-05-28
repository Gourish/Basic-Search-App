package com.example.searchDocuments;
import com.example.searchDocuments.controller.CloudStorageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.security.GeneralSecurityException;

@SpringBootApplication
public class SearchDocumentsApplication {

	public static void main(String[] args) throws IOException, GeneralSecurityException {


		SpringApplication.run(SearchDocumentsApplication.class, args);
	}

}
