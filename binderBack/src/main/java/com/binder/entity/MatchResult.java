package com.binder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class MatchResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "giveaway_id")
    private GiveAway giveaway;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "liked")
    private boolean liked;

    @Column(name = "time", columnDefinition = "TIMESTAMP")
    private java.sql.Timestamp time;
}
