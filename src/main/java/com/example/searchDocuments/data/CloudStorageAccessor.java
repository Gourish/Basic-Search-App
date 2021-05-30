package com.example.searchDocuments.data;

import com.example.searchDocuments.model.Resource;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

public interface CloudStorageAccessor {
    public List<Resource> readFiles() throws IOException, GeneralSecurityException;
    public void createFolder() throws GeneralSecurityException, IOException;

    void uploadFile() throws IOException;
    public void deleteFile() throws IOException;
}
