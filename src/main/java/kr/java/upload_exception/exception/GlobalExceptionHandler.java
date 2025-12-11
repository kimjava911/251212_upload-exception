package kr.java.upload_exception.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 전역 예외 처리기
 *
 * @ControllerAdvice: 모든 컨트롤러에서 발생하는 예외를 한 곳에서 처리
 * 장점: 코드 중복 제거, 일관된 에러 응답
 */
@ControllerAdvice
@Slf4j  // Lombok: Logger 자동 생성 (log.error(...) 사용 가능)
public class GlobalExceptionHandler {

    /**
     * 파일 크기 초과 예외 처리
     *
     * MaxUploadSizeExceededException: Spring이 발생시키는 예외
     * application.yml의 max-file-size, max-request-size 초과 시 발생
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handleMaxSizeException(MaxUploadSizeExceededException e,
                                         // org.springframework.ui.Model
                                         Model model,
                                         HttpServletRequest request) {
        // 로그 기록 (WARN 레벨: 사용자 실수지만 시스템 이상은 아님)
        log.warn("파일 크기 초과 - IP: {}, URL: {}",
                request.getRemoteAddr(), request.getRequestURI());

        model.addAttribute("errorMessage", "파일 크기가 너무 큽니다. (최대 10MB)");
        model.addAttribute("errorDetail", "더 작은 파일을 선택해주세요.");

        return "error/error";
    }

    /**
     * 잘못된 파일 타입 예외 처리
     */
    @ExceptionHandler(InvalidFileTypeException.class)
    public String handleInvalidFileType(InvalidFileTypeException e, Model model) {
        log.info("잘못된 파일 타입 업로드 시도: {}", e.getMessage());

        model.addAttribute("errorMessage", e.getMessage());
        model.addAttribute("errorDetail", "JPG, PNG, GIF, WebP 형식의 이미지만 업로드 가능합니다.");

        return "error/error";
    }

    /**
     * 파일 저장 예외 처리
     */
    @ExceptionHandler(FileStorageException.class)
    public String handleFileStorage(FileStorageException e, Model model) {
        // ERROR 레벨: 시스템 문제 가능성
        log.error("파일 저장 실패: {}", e.getMessage());

        model.addAttribute("errorMessage", "파일 저장 중 문제가 발생했습니다.");
        model.addAttribute("errorDetail", "잠시 후 다시 시도해주세요.");

        return "error/error";
    }

    /**
     * 그 외 모든 예외 처리 (최후의 방어선)
     *
     * 위에서 처리되지 않은 예외가 여기로 옴
     * Whitelabel Error Page 대신 사용자 친화적 페이지 표시
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception e, Model model) {
        // 예상치 못한 예외는 ERROR로 기록
        log.error("예상치 못한 예외 발생", e);

        model.addAttribute("errorMessage", "서비스 이용 중 문제가 발생했습니다.");
        model.addAttribute("errorDetail", "문제가 지속되면 관리자에게 문의해주세요.");

        return "error/error";
    }
}
