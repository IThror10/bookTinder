package com.binder.service;

import com.binder.entity.Book;
import com.binder.response.BookResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class BookService {
    private final static String API_KEY;
    private final String API_URL = "https://www.googleapis.com/books/v1/volumes?q=intitle:%s&key=%s&langRestrict=ru";
    private final RestTemplate template = new RestTemplate();

    static {
        Dotenv dotenv = Dotenv.configure().load();
        API_KEY = dotenv.get("KEY");
    }

    private Integer extractYear(Map<String, Object> info) {
        Integer year = null;
        if (info.containsKey("publishedDate")) {
            String str = info.get("publishedDate").toString();
            try {
                year = Integer.valueOf(str);
            } catch (NumberFormatException e) {
                if (str.charAt(4) == '-') {
                    try {
                        year = Integer.valueOf(str.substring(0, 4));
                    } catch (NumberFormatException e2) {
                        year = null;
                    }
                }
            }
        }
        return year;
    }

    public List<BookResponse> searchBooks(String name) {
        String url = String.format(API_URL, name, API_KEY);
        Map<String, Object> response = template.getForObject(url, Map.class);
        List<Object> items = (ArrayList<Object>) response.get("items");
        List<BookResponse> res = new ArrayList<>();


        for (Object item : items) {
            Map<String, Object> info = (Map<String, Object>) ((Map<String, Object>) item).get("volumeInfo");
            String author = info.getOrDefault("authors", "[unknown]").toString();

            Book book = Book.builder()
                    .title(String.valueOf(info.get("title")))
                    .year(extractYear(info))
                    .description(String.valueOf(info.getOrDefault("description", "unknown")))
                    .author(author.substring(1, author.length() - 1))
                    .build();

            res.add(new BookResponse(book));
        }
        return res;
    }
}
