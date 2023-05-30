package com.example.springcore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.http.MediaType;

public class FileModel {
    private String url;
    private String type;

    private String fileName;

    @JsonIgnore
    private byte[] byteArray;

    @JsonIgnore
    private MediaType mediaType;

    public FileModel() {
    }
    public FileModel(String url, String type, String fileName) {
        this.url = url;
        this.type = type;
        this.fileName = fileName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getByteArray() {
        return byteArray;
    }

    public void setByteArray(byte[] byteArray) {
        this.byteArray = byteArray;
    }

    public MediaType getMediaType() {
        return mediaType;
    }

    public void setMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
