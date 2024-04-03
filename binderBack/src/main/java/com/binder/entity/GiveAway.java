package com.binder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "GiveAway")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GiveAway {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "giveaway_seq")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "photo")
    private byte[] photo;
}
