package com.susanne.Susanne_eindopdrachtVA.controllers;

import com.susanne.Susanne_eindopdrachtVA.model.FileUploadResponse;
import com.susanne.Susanne_eindopdrachtVA.services.FileService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import java.io.IOException;
import java.util.Objects;

@RestController
@CrossOrigin
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService){
        this.fileService = fileService;
    }



    @PostMapping("/upload")
    public FileUploadResponse singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam Long assignmentId){
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/download/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString();
        String contentType = file.getContentType();
        String fileName = fileService.storeFile(file, uri, assignmentId);
        return new FileUploadResponse(fileName, contentType, uri );
    }


    @GetMapping("/download/{fileId}")
    public ResponseEntity<Resource> downLoadFile(@PathVariable Long fileId, HttpServletRequest request) {
        Resource resource = fileService.downLoadFile(fileId);
        String mimeType;
        try{
            mimeType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException e) {
            mimeType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
        }
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(mimeType)).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }
}



