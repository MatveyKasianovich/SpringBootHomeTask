package com.example.demo;

import java.time.LocalDateTime;

public record ServerErrorDto(
        String message,
        String detailedMessage,
        LocalDateTime localDateTime
) {


}
