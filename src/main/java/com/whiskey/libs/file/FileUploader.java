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
    private final String ENDPOINT = "/file/upload";

    private String DEFAULT_HOST = "http://localhost:8083";
    private String userHost;

    public FileUploader(String filePath) {
        this.filePath = filePath;

        // 파일명이 없을 경우 예외 발생
        if (filePath == null || filePath.isEmpty()) {
            throw new IllegalArgumentException("잘못된 파일 경로입니다.");
        }
    }

    public FileUploader(String filePath, String host) {
        this.userHost = host;
        this.filePath = filePath;

        // host앞에 http://가 없을 경우 예외 발생
        if (!host.startsWith("http://")) {
            throw new IllegalArgumentException("http://를 포함한 host를 입력해주세요.");
        }

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

        // Request Body 생성
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        builder.addBinaryBody(
                "file",
                targetFile,
                ContentType.DEFAULT_BINARY,
                newFileName     // UUID로 변경된 파일명
        );

        HttpEntity multipart = builder.build();

        // user_host가 null일 경우 기본 host로 설정
        HttpPost httpPost;
        if (userHost == null) {
            httpPost = new HttpPost(DEFAULT_HOST + ENDPOINT);
        } else {
            httpPost = new HttpPost(userHost + ENDPOINT);
        }
        httpPost.setEntity(multipart);

        // 파일 업로드
        CloseableHttpClient httpClient = HttpClients.createDefault();
        var response = httpClient.execute(httpPost);

        // response code가 200이 아닐 경우 예외 발생
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new Exception("파일 업로드에 실패했습니다.");
        }

        return nameGroup;
    }
}
