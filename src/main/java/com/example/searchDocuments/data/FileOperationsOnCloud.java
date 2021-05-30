package com.example.searchDocuments.data;

import com.example.searchDocuments.model.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class FileOperationsOnCloud {
    @Autowired
    private CloudStorageAccessor cloudStorageAccessor;

    //reads file from cloud storage
    public List<Resource> downloadFilesfromCloud() throws GeneralSecurityException, IOException {
      return cloudStorageAccessor.readFiles();
    }

    //creates files on cloud storage
    public void createFilesOnCloud() throws GeneralSecurityException, IOException {
        cloudStorageAccessor.createFolder();
    }

    //function to uploadFile onto cloud storages
    public void uploadFile() throws IOException {
        cloudStorageAccessor.uploadFile();
    }
    public void deleteFile() throws IOException {
        cloudStorageAccessor.deleteFile();
    }

}
