package global.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 식재료 유통기한 데이터가 없을때 발생하는 에러
    @ExceptionHandler(InvalidExpiryDateException.class)
    public ResponseEntity<ErrorResponse> handleInvalidExpiryDate(InvalidExpiryDateException e) {

        // 400 Bad Request 상태 코드+에러 메시지
        ErrorResponse errorResponse = new ErrorResponse("INVALID_EXPIRY_DATE", e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    // JSON으로 내려보낼 에러 껍데기(DTO)
    public record ErrorResponse(String errorCode, String errorMessage) {}
}
