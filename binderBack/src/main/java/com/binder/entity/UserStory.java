package com.binder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Userstory")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "liked")
    private boolean liked;
}
