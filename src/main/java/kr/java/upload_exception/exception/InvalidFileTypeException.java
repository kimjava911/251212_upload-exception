package kr.java.upload_exception.exception;

/**
 * 허용되지 않는 파일 타입 업로드 시 발생하는 예외
 */
public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException(String message) {
        super(message);
    }
}
