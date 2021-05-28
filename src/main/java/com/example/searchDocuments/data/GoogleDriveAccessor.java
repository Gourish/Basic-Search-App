package com.example.searchDocuments.data;

import com.example.searchDocuments.controller.CloudStorageController;
import com.example.searchDocuments.model.Resource;
import com.example.searchDocuments.parser.PDFFileParser;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import org.apache.tika.exception.TikaException;
import org.springframework.stereotype.Component;
import org.xml.sax.SAXException;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class GoogleDriveAccessor implements CloudStorageAccessor {
    private static final String APPLICATION_NAME = "basic search engine";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/static/keys/credentials.json";
    final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();

    public GoogleDriveAccessor() throws GeneralSecurityException, IOException {
    }

    // this function reads files that are listed in my drive folder of google drive
    public List<Resource> readFiles() throws GeneralSecurityException, IOException {
        List<Resource> resources = new ArrayList<>();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        FileList result = drive.files().list()
                .setQ("'1_IqFONBXVqx3MtvKsL-VFM2hSQGTDW_M' in parents")
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name,webViewLink)")
                .execute();
        List<File> files = result.getFiles();
        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            for (File file : files) {
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                drive.files().get(file.getId())
                        .executeMediaAndDownloadTo(outputStream);
                String link = file.getWebViewLink();
                String name = file.getName();
                resources.add(new Resource(name, link ,outputStream.toByteArray()));
            }
        }
        return resources;
    }
    //function returns the credentials if authorized
    public Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        InputStream in = CloudStorageController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8080).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
    //function creates folder on Google Drive
    public void createFolder() throws GeneralSecurityException, IOException {
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        File fileMetadata = new File();
        fileMetadata.setName("Text Documents");
        fileMetadata.setMimeType("application/vnd.google-apps.folder");

        File file = drive.files().create(fileMetadata)
                .setFields("id")
                .execute();
    }

    //function uploads the file in the specified folder . folder id is taken as parameter to identify the folder.
    public void uploadFile(String folderId) throws IOException {
        folderId = "1_IqFONBXVqx3MtvKsL-VFM2hSQGTDW_M";
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        File fileMetadata = new File();
        fileMetadata.setName("sample.pdf");
        fileMetadata.setParents(Collections.singletonList(folderId));
        java.io.File filePath = new java.io.File("/home/gourish/sample.pdf");
        FileContent mediaContent = new FileContent("text/pdf", filePath);
        File file = drive.files().create(fileMetadata, mediaContent)
                .setFields("id, parents")
                .execute();
    }
}