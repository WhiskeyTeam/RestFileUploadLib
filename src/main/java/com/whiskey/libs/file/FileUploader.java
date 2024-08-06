package com.whiskey.libs.file;

import lombok.Getter;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.*;

import java.io.File;
import java.util.UUID;

@Getter
public class FileUploader {
    private final String filePath;

    private final String REQUEST_URL = "http://localhost:8080/file/upload";

    public FileUploader(String filePath) {
        this.filePath = filePath;

        // 파일명이 없을 경우 예외 발생
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("잘못된 파일 경로입니다.");
        }
    }


    public FileNameGroup upload() throws Exception {
        var targetFile = new File(filePath);
        var nameGroup = new FileNameGroup();

        // targetFile.fileName으로 UUID 생성
        String extension = targetFile.getName().substring(targetFile.getName().lastIndexOf("."));

        String uuid = UUID.randomUUID().toString();
        String newFileName = uuid + extension;

        nameGroup.setOriginalFileName(targetFile.getName());
        nameGroup.setUuidFileName(newFileName);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(
                "file",
                targetFile,
                ContentType.DEFAULT_BINARY,
                newFileName     // UUID로 변경된 파일명
        );

        HttpEntity multipart = builder.build();

        HttpPost httpPost = new HttpPost(REQUEST_URL);
        httpPost.setEntity(multipart);

        // 파일 업로드
        CloseableHttpClient httpClient = HttpClients.createDefault();
        var response = httpClient.execute(httpPost);

        return nameGroup;
    }
}
