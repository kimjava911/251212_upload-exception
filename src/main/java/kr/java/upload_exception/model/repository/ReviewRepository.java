package kr.java.upload_exception.model.repository;

import kr.java.upload_exception.model.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // 최신순으로 리뷰 목록 조회
    List<Review> findAllByOrderByCreatedAtDesc();
}
