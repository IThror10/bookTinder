package com.binder.service;

import com.binder.repository.BookRepository;
import com.binder.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {
    private final BookRepository repository;

    public List<BookResponse> searchBooks(String name) {
        return repository.searchBooksLike(name);
    }
}
