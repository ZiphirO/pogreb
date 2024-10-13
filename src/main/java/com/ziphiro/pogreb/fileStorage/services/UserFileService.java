package com.ziphiro.pogreb.fileStorage.services;

import com.ziphiro.pogreb.fileStorage.entityes.UserFile;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface UserFileService {

    public List<UserFile> getAllUserFilesPaths(String name);
    public CompletableFuture<ResponseEntity<InputStreamResource>> downloadFile(String fileName,
                                                                               String userName) throws IOException;
    public CompletableFuture<String> uploadFile(MultipartFile file, String userName) throws IOException;
    public void deleteFileFromStorage(String fileName, String userName) throws IOException;
    public void deleteFileFromDataBase(Long fileId);
    public String generateFileName (String filename);

}
