package com.binder.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="Book")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name="author", nullable = false)
    private String author;

    @Column(name = "year")
    private Integer year;

    @Column(name = "description", nullable = false)
    private String description;
}
