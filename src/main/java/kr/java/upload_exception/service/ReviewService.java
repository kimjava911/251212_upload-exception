package kr.java.upload_exception.service;

import kr.java.upload_exception.model.entity.Review;
import kr.java.upload_exception.model.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor  // final 필드를 받는 생성자 자동 생성
// org.springframework.transaction.annotation.Transactional;
@Transactional(readOnly = true)  // 기본적으로 읽기 전용 (성능 최적화)
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final FileStorageService fileStorageService;

    /**
     * 전체 리뷰 목록 조회 (최신순)
     */
    public List<Review> findAll() {
        return reviewRepository.findAllByOrderByCreatedAtDesc();
    }

    /**
     * ID로 리뷰 단건 조회
     */
    public Review findById(Long id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("리뷰를 찾을 수 없습니다. ID: " + id));
    }

    /**
     * 새 리뷰 등록 (파일 업로드 포함)
     */
    @Transactional  // 쓰기 작업이므로 readOnly 해제
    public Review create(Review review, MultipartFile imageFile) {
        // 이미지 파일이 있으면 저장
        if (imageFile != null && !imageFile.isEmpty()) {
            String storedFilename = fileStorageService.store(imageFile);
            // 웹에서 접근 가능한 URL 경로 설정
            review.setImageUrl("/images/" + storedFilename);
        }

        return reviewRepository.save(review);
    }

    /**
     * 리뷰 수정 (이미지 교체 가능)
     */
    @Transactional
    public Review update(Long id, Review updateData, MultipartFile newImageFile) {
        Review review = findById(id);

        // 기본 정보 업데이트
        review.setTitle(updateData.getTitle());
        review.setContent(updateData.getContent());
        review.setRating(updateData.getRating());

        // 새 이미지가 업로드된 경우
        if (newImageFile != null && !newImageFile.isEmpty()) {
            // 기존 이미지 삭제
            deleteOldImage(review.getImageUrl());

            // 새 이미지 저장
            String storedFilename = fileStorageService.store(newImageFile);
            review.setImageUrl("/images/" + storedFilename);
        }

        return review;  // 트랜잭션 종료 시 자동으로 UPDATE 실행 (더티 체킹)
    }

    /**
     * 리뷰 삭제 (이미지 파일도 함께 삭제)
     */
    @Transactional
    public void delete(Long id) {
        Review review = findById(id);

        // 이미지 파일 삭제
        deleteOldImage(review.getImageUrl());

        reviewRepository.delete(review);
    }

    /**
     * 기존 이미지 파일 삭제 헬퍼 메서드
     */
    private void deleteOldImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isBlank()) {
            // "/images/abc.jpg" → "abc.jpg" 추출
            String filename = imageUrl.replace("/images/", "");
            fileStorageService.delete(filename);
        }
    }
}
