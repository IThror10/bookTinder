package com.binder.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="Book")
@Getter
@Setter
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

    @Column(name = "edition", nullable = false)
    private String edition;

    @Column(name = "description", nullable = false)
    private String description;
}
