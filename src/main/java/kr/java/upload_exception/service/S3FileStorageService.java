package kr.java.upload_exception.service;

import kr.java.upload_exception.exception.FileStorageException;
import kr.java.upload_exception.exception.InvalidFileTypeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * AWS S3 저장 구현체
 *
 * 사용하려면:
 * 1. build.gradle에 AWS SDK 의존성 추가 (spring-cloud-aws-starter-s3)
 * 2. application.yaml에 aws.s3.* 설정
 * 3. file.storage.type=s3 설정
 */
@Service
@ConditionalOnProperty(name = "file.storage.type", havingValue = "s3")
@Slf4j
@RequiredArgsConstructor
public class S3FileStorageService implements FileStorageService {

    // SDK가 알아서 구성해서 주입해줌
    private final S3Client s3Client;

    // org.springframework.beans.factory.annotation.Value
    @Value("${aws.s3.bucket}")
    private String bucketName;

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    @Override
    public String store(MultipartFile file) {
        validateFile(file);

        String extension = extractExtension(file.getOriginalFilename());
        // S3 키: 폴더구조/UUID.확장자
        String key = "reviews/" + UUID.randomUUID() + extension;

        try {
            // S3에 업로드할 요청 생성
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            // 파일 업로드
            s3Client.putObject(request,
                    // software.amazon.awssdk.core.sync.RequestBody
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));

            return key;
        } catch (IOException | S3Exception e) {
            throw new FileStorageException("S3 업로드 실패: " + e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        if (key == null || key.isBlank()) return;
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(request);
        } catch (S3Exception e) {
            // 로그만 남기고 진행
            log.error("S3 삭제 실패: {}", e.getMessage());
        }
    }

    @Override
    public String getUrl(String key) {
        return "/s3/" + key;
    }

    private void validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileStorageException("빈 파일입니다.");
        }
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_TYPES.contains(contentType)) {
            throw new InvalidFileTypeException("허용되지 않는 파일 형식입니다.");
        }
    }

    private String extractExtension(String filename) {
        if (filename == null || !filename.contains(".")) return "";
        return filename.substring(filename.lastIndexOf("."));
    }
}
