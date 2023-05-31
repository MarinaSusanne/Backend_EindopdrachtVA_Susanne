package com.susanne.Susanne_eindopdrachtVA.services;

import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import com.susanne.Susanne_eindopdrachtVA.repository.FileRepository;
import jakarta.annotation.Resource;
import lombok.Value;
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

@Service
public class FileService {

    public class PhotoService {
        private final Path fileStoragePath;
        private final String fileStorageLocation;
        private final FileRepository fileRepository;

        public FileServiceService(@Value("${my.upload_location}") String fileStorageLocation, FileRepository fileRepository) {
            fileStoragePath = Paths.get(fileStorageLocation).toAbsolutePath().normalize();
            this.fileStorageLocation = fileStorageLocation;
            this.fileRepository = fileRepository;

            try {
                Files.createDirectories(fileStoragePath);
            } catch (IOException e) {
                throw new RuntimeException("Issue in creating file directory");
            }



            public String storeFile (MultipartFile file, String uri) {

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

            public Resource downLoadFile (Long FileId) {

                Path path = Paths.get(fileStorageLocation).toAbsolutePath().resolve(FileId);

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
