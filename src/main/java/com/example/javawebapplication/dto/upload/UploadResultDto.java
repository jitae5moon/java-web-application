package com.example.javawebapplication.dto.upload;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResultDto {

    private String uuid;
    private String fileName;
    private boolean isImage;

    public String getLink() {
        if (isImage) return "s_" + uuid + "_" + fileName;
        else return uuid + "_" + fileName;
    }

}
