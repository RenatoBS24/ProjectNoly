package com.projectnoly.DTO;

import java.time.LocalDateTime;

public record ExceptionResponse(int value, LocalDateTime date, String message) {
}
