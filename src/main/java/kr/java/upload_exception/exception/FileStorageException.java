package kr.java.upload_exception.exception;
/**
 * 파일 저장 중 발생하는 일반적인 예외
 * RuntimeException을 상속: 체크 예외가 아니므로 try/catch 강제 안 됨
 */
public class FileStorageException extends RuntimeException {

    public FileStorageException(String message) {
        super(message);
    }
}