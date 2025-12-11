package kr.java.upload_exception.service;

import kr.java.upload_exception.exception.FileStorageException;
import kr.java.upload_exception.exception.InvalidFileTypeException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * 로컬 디스크 저장 구현체
 *
 * @Primary: 같은 인터페이스의 여러 구현체 중 기본으로 사용할 것 지정
 * @ConditionalOnProperty: 특정 설정값이 있을 때만 Bean 등록
 */
@Service
@Primary
@ConditionalOnProperty(name = "file.storage.type", havingValue = "local", matchIfMissing = true)
public class LocalFileStorageService implements FileStorageService {

    // java.nio.file.Path;
    private final Path uploadPath;

    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/png", "image/gif", "image/webp"
    );

    // org.springframework.beans.factory.annotation.Value
    public LocalFileStorageService(@Value("${file.upload-dir}") String uploadDir) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.uploadPath);
        } catch (IOException e) {
            throw new FileStorageException("업로드 디렉토리 생성 실패");
        }
    }

    @Override
    public String store(MultipartFile file) {
        validateFile(file);

        String originalFilename = file.getOriginalFilename();
        String extension = extractExtension(originalFilename);
        String storedFilename = UUID.randomUUID() + extension;

        try {
            Path targetPath = this.uploadPath.resolve(storedFilename);
            file.transferTo(targetPath);
            return storedFilename;  // 키 = 파일명
        } catch (IOException e) {
            throw new FileStorageException("파일 저장 실패: " + e.getMessage());
        }
    }

    @Override
    public void delete(String key) {
        if (key == null || key.isBlank()) return;
        try {
            Path filePath = this.uploadPath.resolve(key);
            Files.deleteIfExists(filePath);
        } catch (IOException e) {
            // 로그만 남기고 진행
        }
    }

    @Override
    public String getUrl(String key) {
        // 로컬 저장소의 경우 /images/ 경로로 접근
        return "/images/" + key;
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
