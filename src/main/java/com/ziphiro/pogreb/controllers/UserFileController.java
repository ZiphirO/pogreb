package com.ziphiro.pogreb.controllers;

import com.ziphiro.pogreb.entityes.UserFile;
import com.ziphiro.pogreb.services.UserFileService;
import com.ziphiro.pogreb.services.impl.UserFileServiceImpl;
import jakarta.annotation.Resource;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/files")
public class UserFileController {

    @Autowired
    private UserFileServiceImpl userFileService;

    @GetMapping("/userName/{creator}")
    public List<UserFile> getAllUserFilesByUserName(@PathVariable String creator) {
        return userFileService.getAllUserFilesPaths(creator);
    }

    @GetMapping(value = "/download/{userName}/{fileName}", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public CompletableFuture<ResponseEntity<InputStreamResource>> downloadFile(@PathVariable String fileName,
                                                                               @PathVariable String userName) throws IOException {
        return userFileService.downloadFile(fileName, userName);
    }

    @PostMapping("/upload/{userName}")
    public CompletableFuture<String> uploadFile(@RequestParam MultipartFile file,
                                                @PathVariable String userName) throws IOException {
        return userFileService.uploadFile(file, userName);
    }

    @GetMapping("/del/{fileName}/{fileId}/{userName}")
    public void deleteFileFromStorage(@PathVariable String fileName, @PathVariable Long fileId,
                                      @PathVariable String userName) throws IOException {
        userFileService.deleteFileFromStorage(fileName, userName);
        userFileService.deleteFileFromDataBase(fileId);
    }
}
