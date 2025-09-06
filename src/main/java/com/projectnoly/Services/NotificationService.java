package com.projectnoly.Services;

import com.projectnoly.DTO.Ingredient.IngredientNotificationDto;
import com.projectnoly.DTO.Notification.NotificationAlertDto;
import com.projectnoly.DTO.Notification.NotificationResponseDto;
import com.projectnoly.Model.MySql.Ingredient;
import com.projectnoly.Model.MySql.Notification;
import com.projectnoly.Repositories.NotificationRepository;
import com.projectnoly.util.FormatDates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final Logger log = LoggerFactory.getLogger(NotificationService.class);
    public NotificationService(NotificationRepository notificationRepository, SimpMessagingTemplate messagingTemplate) {
        this.notificationRepository = notificationRepository;
        this.messagingTemplate = messagingTemplate;
    }

    public List<NotificationResponseDto> getAllNotifications(){
        return notificationRepository.findAll().stream().
                map(notification -> new NotificationResponseDto(
                        notification.getId(),
                        notification.getAffair(),
                        notification.getMessage(),
                        FormatDates.formatElapsedTime(notification.getDateNotification()),
                        notification.getDateNotification(),
                        notification.getIsRead(),
                        notification.getIsCritical(),
                        new IngredientNotificationDto((long)notification.getIngredient().getId_ingredient(),notification.getIngredient().getName_ingredient())
                )).toList();
    }
    @Transactional
    public void saveNotification(Ingredient ingredient, int stock){
        String message;
        String affair;
        boolean isCritical;
        if(stock <= 0){
            message = "Stock agotado para el producto "+ingredient.getName_ingredient();
            affair = "Stock agotado";
            isCritical = true;
        }else{
            message = "El producto "+ingredient.getName_ingredient()+" tiene bajo stock. Quedan "+stock+" unidades disponibles";
            affair= "Bajo stock";
            isCritical = false;
        }
        Notification notification = new Notification(null,affair,message, LocalDateTime.now(),false,isCritical,ingredient);
        notificationRepository.save(notification);
        log.info("Se creo una nueva notificacion");
        messagingTemplate.convertAndSend("/topic/notifications", new NotificationAlertDto(message,isCritical));
        log.info("Sen envio un mensaje por websocket");
    }
}
