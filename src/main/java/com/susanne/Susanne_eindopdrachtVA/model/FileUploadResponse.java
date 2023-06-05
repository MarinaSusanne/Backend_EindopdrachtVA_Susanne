package com.susanne.Susanne_eindopdrachtVA.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class FileUploadResponse {

    @Id
    private String fileName;

    private String contentType;

    private String uri;

    public FileUploadResponse(String fileName, String contentType, String uri) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.uri = uri;
    }

    public FileUploadResponse() {
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public String getUri() {
        return uri;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }
}