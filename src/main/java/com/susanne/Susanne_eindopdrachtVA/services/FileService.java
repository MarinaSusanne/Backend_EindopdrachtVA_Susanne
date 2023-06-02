package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.exceptions.RecordNotFoundException;
import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import com.susanne.Susanne_eindopdrachtVA.repository.FileRepository;
import jakarta.annotation.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.Optional;

@Service
public class FileService {
    private Path fileStoragePath;
    private String fileStorageLocation;
    private FileRepository fileRepository;

    public FileServiceService(String fileStorageLocation, FileRepository fileRepository) {
        this.fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
        this.fileStorageLocation = fileStorageLocation;
        this.fileRepository = fileRepository;

        try {
            Files.createDirectories(fileStoragePath);
        } catch (IOException e) {
            throw new RuntimeException("Issue in creating file directory");
        }
    }

    public FileService(){
    }

            public String storeFile(MultipartFile file, String uri, Long assignmentId) {

                String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
                Path filePath = Paths.get(fileStoragePath + "/" + fileName);
                try {
                    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
                } catch (IOException e) {
                    throw new RuntimeException("Issue in storing the file", e);
                }
                fileRepository.save(new FileUploadResponse(fileName, file.getContentType(), uri));

                return fileName;
            }




            public Resource downLoadFile(Long fileId) {

                Optional<FileUploadResponse> optionalFile = fileRepository.findById(fileId);
                if (optionalFile.isEmpty()) {
                    throw new RecordNotFoundException("No homework found with this id");
                }
                FileUploadResponse f = optionalFile.get();
                String fileName = f.getFileName();

                Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(fileName);
                Resource resource;
                try {
                    resource = new UrlResource(path.toUri());
                } catch (MalformedURLException e) {
                    throw new RuntimeException("Issue in reading the file", e);
                }
                if(resource.exists()&& resource.isReadable()) {
                    return resource;
                } else {
                    throw new RuntimeException("the file doesn't exist or not readable");
                }
            }
        }
