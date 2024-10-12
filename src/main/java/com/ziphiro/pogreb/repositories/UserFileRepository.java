package com.ziphiro.pogreb.repositories;

import com.ziphiro.pogreb.entityes.UserFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFileRepository extends JpaRepository<UserFile, Long> {

    Optional<List<UserFile>> findByCreator(String creator);
    
    List<UserFile> findAllByCreator(String creator);

    List<UserFile> findFilesByCreator(String creator);

    Optional<UserFile> findUserFileByFileName(String fileName);
}
