package com.example.javawebapplication.controller;

import com.example.javawebapplication.dto.upload.UploadFileDto;
import com.example.javawebapplication.dto.upload.UploadResultDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Slf4j
@RestController
public class SimpleFileController {

    @Value("${upload.path}")
    private String uploadPath;

    @ApiOperation("View file")
    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable String fileName) {
        log.info("SimpleFileController :: getFile :: fileName = {}", fileName);

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);

        String resourceName = resource.getFilename();
        HttpHeaders headers = new HttpHeaders();

        try {
            headers.add("Content-Type", Files.probeContentType(resource.getFile().toPath()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }

        return ResponseEntity.ok().headers(headers).body(resource);
    }

    @ApiOperation("Upload POST")
    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public List<UploadResultDto> upload(UploadFileDto uploadFileDto) {
        log.info("SimpleFileController :: upload :: uploadFileDto = {}", uploadFileDto);

        if (uploadFileDto.getFiles() != null) {

            List<UploadResultDto> uploadResultDtoList = new ArrayList<>();

            uploadFileDto.getFiles().forEach(file -> {
                String originalFilename = file.getOriginalFilename();
                String uuid = UUID.randomUUID().toString(); // To avoid same file name

                Path savedPath = Paths.get(uploadPath, uuid + "_" + originalFilename);
                boolean isImage = false;

                try {
                    file.transferTo(savedPath);

                    if (Files.probeContentType(savedPath).startsWith("image")) {
                        isImage = true;
                        File thumbnailFile = new File(uploadPath, "s_" + uuid + "_" + originalFilename);

                        Thumbnailator.createThumbnail(savedPath.toFile(), thumbnailFile, 200, 200);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                uploadResultDtoList.add(UploadResultDto.builder()
                        .uuid(uuid)
                        .fileName(originalFilename)
                        .isImage(isImage)
                        .build()
                );
            });

            return uploadResultDtoList;
        }

        return List.of();
    }

    @ApiOperation("Remove File")
    @DeleteMapping("/{fileName}")
    public Map<String, Boolean> deleteFile(@PathVariable String fileName) {
        log.info("SimpleFileController :: deleteFile :: fileName = {}", fileName);

        Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
        String resourceName = resource.getFilename();

        Map<String, Boolean> result = new HashMap<>();
        boolean isDeleted = false;

        try {
            String contentType = Files.probeContentType(resource.getFile().toPath());
            isDeleted = resource.getFile().delete();

            if (contentType.startsWith("image")) {
                File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);

                thumbnailFile.delete();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        result.put("result", isDeleted);

        return result;
    }

}
