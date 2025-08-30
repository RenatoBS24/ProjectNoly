package com.projectnoly.DTO.Notification;

import com.projectnoly.DTO.Ingredient.IngredientNotificationDto;

import java.time.LocalDateTime;

public record NotificationResponseDto(Long id, String affair, String message, String elapsedTime,LocalDateTime dateNotification, Boolean isRead,Boolean isCritical,
                                      IngredientNotificationDto ingredientNotificationDto) {
}
