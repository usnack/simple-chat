package io.usnack.simplechat.util.binary;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;


@ConditionalOnProperty(name = "binary-storage.mode", havingValue = "s3")
@Component
@RequiredArgsConstructor
public class S3BinaryStorage implements BinaryStorage {
    private final S3Client s3Client;
    @Value("${aws.s3.bucket}")
    private String bucket;

    @Override
    public UUID saveBinary(UUID key, byte[] bytes) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key.toString())
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(bytes));

        return key;
    }

    @Override
    public String resolvePath(UUID fileKey) {
        try (S3Presigner presigner = S3Presigner.builder()
                .region(s3Client.serviceClientConfiguration().region())
                .credentialsProvider(s3Client.serviceClientConfiguration().credentialsProvider())
                .build()) {

            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucket)
                    .key(fileKey.toString())
                    .build();

            GetObjectPresignRequest getObjectPresignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(Duration.ofMinutes(5))  // URL 유효시간 5분
                    .getObjectRequest(getObjectRequest)
                    .build();

            PresignedGetObjectRequest presignedRequest = presigner.presignGetObject(getObjectPresignRequest);
            return presignedRequest.url().toString();
        }
    }

    @Override
    public InputStream loadBinary(String path) {
        return null;
    }
}