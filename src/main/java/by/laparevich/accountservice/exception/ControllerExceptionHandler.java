package by.laparevich.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage resourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(HttpStatus.NOT_FOUND.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        return message;
    }

    @ExceptionHandler(AlreadyExistException.class)
    @ResponseStatus(value = HttpStatus.CONFLICT)
    public ErrorMessage alreadyExistException(AlreadyExistException ex, WebRequest request) {
        ErrorMessage message = ErrorMessage
                .builder()
                .statusCode(HttpStatus.CONFLICT.value())
                .timestamp(new Date())
                .message(ex.getMessage())
                .description(request.getDescription(false))
                .build();
        return message;
    }
}
