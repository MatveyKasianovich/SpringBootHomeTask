package com.example.demo;

import com.example.demo.PetController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    public static final Logger log = LoggerFactory.getLogger(PetController.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ServerErrorDto> handleNotValidArgument(MethodArgumentNotValidException e) {
        log.error("Got validation exception", e);

        String detailedMessage=e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->error.getField()+": "+error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        var errorDto =  new ServerErrorDto(
                "Ошибка валидации",
                detailedMessage,
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorDto);
    }


    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ServerErrorDto> handleNotValidArgument(NoSuchElementException e) {
        log.error("Got NoSuchElementexception", e);

        var errorDto =  new ServerErrorDto(
                "не существует сущности с таким id",
                e.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(errorDto);
    }


}
