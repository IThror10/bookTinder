package com.binder.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "Matchresult")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "giveaway_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private GiveAway giveaway;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "liked")
    private boolean liked;
}