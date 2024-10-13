package com.ziphiro.pogreb.fileStorage.services.impl;

import com.ziphiro.pogreb.fileStorage.entityes.UserFile;
import com.ziphiro.pogreb.fileStorage.repositories.UserFileRepository;
import com.ziphiro.pogreb.fileStorage.services.UserFileService;
import com.ziphiro.pogreb.util.StrVal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserFileServiceImpl {
    private final UserFileRepository userFileRepository;
    private final Logger LOGGER = LoggerFactory.getLogger(UserFileService.class);

    public List<UserFile> getAllUserFilesPaths(String name){
        return userFileRepository.findFilesByCreator(name);
    }

    @Async("asyncDownload")
    public CompletableFuture<ResponseEntity<InputStreamResource>> downloadFile(String fileName, String userName) throws IOException {
        LOGGER.info(userName + StrVal.STARTING_DOWNLOAD.getValue() + fileName);
        Path filePath  = Path.of(StrVal.STORAGE_DIR.getValue(), userName, fileName);
        InputStream fileStream = new FileInputStream(filePath.toFile());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, StrVal.ATTACHMENT_FILENAME.getValue() +
                fileName + StrVal.SLASH.getValue());
        LOGGER.info(userName + StrVal.COMPLETE_DOWNLOAD.getValue() + fileName);
        return CompletableFuture.completedFuture(ResponseEntity.ok().headers(headers).contentLength(filePath.toFile().length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(new InputStreamResource(fileStream)));
    }
    @Async("asyncUpload")
    public CompletableFuture<String> uploadFile(MultipartFile file, String userName) throws IOException {
        LOGGER.info(userName + StrVal.UPLOAD_START.getValue() + file.getOriginalFilename());
        if (file.isEmpty()){
            throw new IOException(StrVal.UPLOAD_ERROR.getValue());
        } else {
            file.transferTo(Path.of(StrVal.STORAGE_DIR.getValue(), userName, file.getOriginalFilename()));
            String uploadFileDir = StrVal.STORAGE_DIR.getValue() + file.getOriginalFilename();
            UserFile uploadFile = UserFile.builder().fileName(generateFileName(file.getOriginalFilename()))
                    .filePath(uploadFileDir)
                    .creator(userName).build();
            userFileRepository.save(uploadFile);
        }
        LOGGER.info(userName + StrVal.UPLOAD_SUCCESS.getValue() + file.getOriginalFilename());
        return CompletableFuture.completedFuture(StrVal.UPLOAD_SUCCESS.getValue());
    }

    public void deleteFileFromStorage(String fileName, String userName) throws IOException {
        Files.delete(Path.of(StrVal.STORAGE_DIR.getValue() + userName + StrVal.SLASH.getValue() + fileName));
    }
    public void deleteFileFromDataBase(Long fileId){
        userFileRepository.deleteById(fileId);
    }
    public String generateFileName (String filename){
        if (userFileRepository.findUserFileByFileName(filename).isPresent()){
            return filename + StrVal.PLUS.getValue();
        }
        else return filename;
    }
}
