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

    /**
     * Manipulador de exceção genérico para Exception.
     * Registra um erro no log, cria uma mensagem de erro personalizada e retorna uma resposta HTTP apropriada.
     *
     * @param exception Exceção genérica lançada.
     * @param request   A solicitação da web.
     * @return Resposta HTTP com detalhes do erro.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGenericException(Exception exception, WebRequest request) {
        logger.error("An unexpected failure occurred.", exception);
        String complementMessage = "An unexpected failure occurred, please contact the administrator: ";
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, complementMessage + exception.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }

    /**
     * Manipulador de exceção para IOException.
     * Registra um erro no log, cria uma mensagem de erro personalizada e retorna uma resposta HTTP apropriada.
     *
     * @param exception Exceção IOException lançada.
     * @param request   A solicitação da web.
     * @return Resposta HTTP com detalhes do erro.
     */
    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponseDto> handleIOException(IOException exception, WebRequest request) {
        logger.error("Failed to read the contents of the file.", exception);
        String complementMessage = "Failed to read the contents of the file: ";
        ErrorResponseDto response = new ErrorResponseDto(HttpStatus.BAD_REQUEST, complementMessage + exception.getMessage());
        return new ResponseEntity<>(response, new HttpHeaders(), response.getStatus());
    }

}
