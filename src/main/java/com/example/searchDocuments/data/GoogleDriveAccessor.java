package com.example.searchDocuments.data;

import com.example.searchDocuments.controller.CloudStorageController;
import com.example.searchDocuments.model.Resource;
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
import org.springframework.stereotype.Component;

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
                String id = file.getId();
                String link = file.getWebViewLink();
                String name = file.getName();

                resources.add(new Resource(id,name, link ,outputStream.toByteArray()));

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
    public void uploadFile() throws IOException {
        String folderId = "1_IqFONBXVqx3MtvKsL-VFM2hSQGTDW_M";
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        java.io.File[] files = new java.io.File("/home/gourish/documents").listFiles();
        for(java.io.File file: files) {
            String extensionType = getExtension(file.getName());
            if(!extensionType.equals("")) {
                File fileMetadata = new File();
                fileMetadata.setName(file.getName());
                fileMetadata.setParents(Collections.singletonList(folderId));
                java.io.File filePath = new java.io.File(file.getAbsolutePath());
                FileContent mediaContent = new FileContent(extensionType, filePath);
                File gFile = drive.files().create(fileMetadata, mediaContent)
                        .setFields("id, parents")
                        .execute();
            }
        }
    }
    private String getExtension(String fileName)
    {
        int dotPosition = fileName.lastIndexOf(".");
        String extension="" ;
        if(dotPosition!=-1)
        {
            extension = fileName.substring(dotPosition+1);
        }
        if(!extension.equals(""))
        {
            extension= extension.toLowerCase();
            extension = "text/"+extension;
        }
        return extension;
    }
    public void deleteFile() throws IOException {
        String FileToDelete = "steganography.pdf";
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        FileList result = drive.files().list()
                .setQ("name='The Da Vinci Code.PDF'")
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> file = result.getFiles();
        drive.files().delete(file.get(0).getId()).execute();

    }
}