package kr.java.upload_exception.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter
@NoArgsConstructor
public class Review extends BaseEntity {
    // 리뷰 제목 (최대 100자, 필수)
    @NotBlank(message = "리뷰 제목은 필수입니다.")
    @Column(nullable = false)
    private String title;

    // 리뷰 내용 (긴 텍스트 저장)
    @NotBlank(message = "리뷰 내용은 필수입니다.")
    @Column(nullable = false)
    private String content;

    // 평점 (1~5 사이 정수)
    @Min(value = 1, message = "평점은 최소 1점 이상입니다.")
    @Min(value = 5, message = "평점은 최대 5점 이하입니다.")
    @Column(nullable = false)
    private Integer rating;

    // 업로드된 이미지 파일 경로
    private String imageUrl;
}
