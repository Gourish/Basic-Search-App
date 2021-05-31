package com.example.searchDocuments.data;

import com.example.searchDocuments.exception.CloudAccessException;
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
    public List<Resource> downloadFilesfromCloud() throws CloudAccessException {
        try {
            return cloudStorageAccessor.readFiles();
        } catch (GeneralSecurityException | IOException e) {
            throw new CloudAccessException(e);
        }

    }

    //creates files on cloud storage
    public void createFilesOnCloud() throws CloudAccessException {
        try {
            cloudStorageAccessor.createFolder();
        } catch (IOException | GeneralSecurityException e) {
             throw new CloudAccessException(e);
        }
    }

    //function to uploadFile onto cloud storages
    public void uploadFile() throws IOException {
        cloudStorageAccessor.uploadFile();
    }
    public void deleteFile() throws IOException {
        cloudStorageAccessor.deleteFile();
    }

}
