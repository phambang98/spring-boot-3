package com.example.springchat.service;


import com.example.springchat.error.InternalServerErrorException;
import com.example.springcore.model.FileModel;
import com.example.springcore.repository.FileRepository;
import com.example.springcore.utils.FileUtils;
import org.apache.tika.Tika;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.PathResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.*;

@Service
public class FileStorageService {
    Logger logger = LoggerFactory.getLogger(FileStorageService.class);

    @Autowired
    private FileRepository fileRepository;

    public List<FileModel> store(List<MultipartFile> files, Long messageId) {
        var uploadedFiles = new ArrayList<FileModel>();
        final Tika tika = new Tika();
        files.stream().forEach(x -> {
            var path = Path.of(new PathResource(FileUtils.UPLOAD_FILE_CHAT).getPath(), String.valueOf(messageId));
            try {
                Path resolve = path.resolve(new Date().getTime() + "_" + x.getOriginalFilename());
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                Files.copy(x.getInputStream(), resolve, StandardCopyOption.REPLACE_EXISTING);
                uploadedFiles.add(new FileModel(resolve.toString(), tika.detect(x.getInputStream()), new Date().getTime() + "_" + x.getOriginalFilename()));
            } catch (IOException e) {
                throw new InternalServerErrorException(e);
            }
        });
        return uploadedFiles;
    }

    public FileModel readFileByName(String fileName) throws IOException {
        com.example.springcore.entity.File fileEntity = fileRepository.findByFileName(fileName);
        Path cwd = Path.of("").toAbsolutePath().getRoot();
        MediaType mediaType;
        if (fileEntity.getType().contains("image")) {
            mediaType = MediaType.IMAGE_JPEG;
        } else {
            mediaType = MediaType.TEXT_HTML;
        }
        FileModel fileModel = new FileModel();
        try {
            fileModel.setByteArray(Files.readAllBytes(new File(cwd.toString() + fileEntity.getUrl()).toPath()));
        } catch (Exception e) {
            logger.error("", e);
        }
        fileModel.setMediaType(mediaType);
        return fileModel;
    }

}
