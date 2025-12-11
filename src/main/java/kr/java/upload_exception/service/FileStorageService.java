package kr.java.upload_exception.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * 파일 저장소 추상화 인터페이스
 *
 * 인터페이스를 사용하는 이유:
 * 1. 구현체 교체 용이 (로컬 → S3 → Supabase)
 * 2. 테스트 시 Mock 객체로 대체 가능
 * 3. ReviewService는 "어디에 저장되는지" 몰라도 됨
 */
public interface FileStorageService {

    /**
     * 파일 저장
     * @return 저장된 파일의 키(식별자)
     */
    String store(MultipartFile file);

    /**
     * 파일 삭제
     */
    void delete(String key);

    /**
     * 파일 접근 URL 반환
     * 로컬: /images/xxx.jpg
     * S3: https://bucket.s3.region.amazonaws.com/xxx.jpg
     */
    String getUrl(String key);
}
