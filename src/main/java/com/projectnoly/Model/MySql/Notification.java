package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity
@Table(name = "notification")
public class Notification {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_notification")
    private Long id;
    @Column(name = "affair")
    private String affair;
    @Column(name = "message")
    private String message;
    @Column(name = "date_notification")
    private LocalDateTime dateNotification;
    @Column(name = "is_read")
    private Boolean isRead;
    @Column(name = "is_critical")
    private Boolean isCritical;
    @ManyToOne
    @JoinColumn(name = "id_ingredient", nullable = false)
    private Ingredient ingredient;
}
