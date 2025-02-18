package com.projectnoly.Model.MySql;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Entity

public class Notification {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    @Column(name = "id_notification")
    private int id_notification;
    @Column(name = "affair")
    private String affair;
    @Column(name = "message")
    private String message;
    @Column(name = "date_notification")
    private LocalDateTime date_notification;
    @ManyToOne
    @JoinColumn(name = "id_menu", nullable = false)
    private Menu menu;
}
