package com.susanne.Susanne_eindopdrachtVA.repository;

import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<FileUploadResponse, String> {
    Optional<FileUploadResponse> findByFileId(Long FileId);
}