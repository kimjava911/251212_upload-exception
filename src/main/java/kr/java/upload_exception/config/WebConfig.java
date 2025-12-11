package kr.java.upload_exception.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    // org.springframework.beans.factory.annotation.Value;
//    @Value("${file.upload-dir}")
//    private String uploadDir;

    /**
     * 정적 리소스 핸들러 설정
     *
     * URL 요청과 실제 파일 위치를 연결
     * 예: /images/abc.jpg 요청 → uploads/abc.jpg 파일 반환
     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        // uploadDir의 절대 경로 계산
//        String absolutePath = Paths.get(uploadDir).toAbsolutePath().normalize().toString();
//
//        registry.addResourceHandler("/images/**")  // URL 패턴
//                .addResourceLocations("file:" + absolutePath + "/")  // 실제 디렉토리
//                .setCachePeriod(3600);  // 캐시 유지 시간 (초) - 브라우저가 1시간 동안 이미지 재요청 안 함
//    }
}
