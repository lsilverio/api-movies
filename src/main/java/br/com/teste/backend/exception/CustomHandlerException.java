package br.com.teste.backend.exception;

import br.com.teste.backend.dto.ErrorResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@ControllerAdvice
public class CustomHandlerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException exception, WebRequest request) {
        String complementMessage = "Failed to read the contents of the file: ";
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, complementMessage + exception.getMessage());
        return new ResponseEntity<ErrorResponseDto>(response, new HttpHeaders(), response.getStatus());
    }

}
