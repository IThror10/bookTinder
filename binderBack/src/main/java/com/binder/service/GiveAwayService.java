package com.binder.service;


import com.binder.entity.Book;
import com.binder.entity.GiveAway;
import com.binder.entity.MatchResult;
import com.binder.entity.User;
import com.binder.exception.NotFoundException;
import com.binder.repository.BookRepository;
import com.binder.repository.GiveAwayRepository;
import com.binder.repository.MatchResultRepository;
import com.binder.request.GiveAwayRequest;
import com.binder.response.GiveAwayResponse;
import com.binder.response.MatchResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GiveAwayService {
    private final GiveAwayRepository giveAwayRepository;
    private final BookRepository bookRepository;
    private final MatchResultRepository matchResultRepository;

//    @Transactional
//    public Book addBook(BookRequest request) {
//
//    }

    @Transactional
    public GiveAwayResponse addGiveAway(Long uid, GiveAwayRequest request) {
        User user = User.builder().id(uid).build();
        Book book = Book.builder()
                .id(request.book().id())
                .description(request.book().description())
                .author(request.book().author())
                .title(request.book().title())
                .edition(request.book().edition())
                .build();

        Optional<Book> optionalBook = bookRepository.findById(request.book().id());
        if (optionalBook.isEmpty()) {
            book = bookRepository.save(book);
        } else {
            book = optionalBook.get();
        }

        GiveAway giveAway = GiveAway.builder()
                .book(book)
                .user(user)
                .description(request.description())
                .photo(request.photo())
                .build();

        giveAway = giveAwayRepository.save(giveAway);
        return new GiveAwayResponse(giveAway);
    }

    @Transactional
    public List<GiveAwayResponse> getGiveAwayByUser(Long uid) {
        User user = User.builder().id(uid).build();
        return giveAwayRepository.findAllByUser(user).stream()
                .map(GiveAwayResponse::new)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<GiveAwayResponse> getAvailableAdvertisements(Long uid) {
        Set<GiveAway> found = giveAwayRepository.getAvailableGiveAway(uid);
        return found.stream().map(GiveAwayResponse::new).collect(Collectors.toList());
//        Set<GiveAwayResponse> likedUs = matchResultRepository.likedUs(uid);
//        return likedUs.stream()
//                .filter(el -> found.stream().anyMatch(cur -> cur.getId().equals(el.getId())))
//                .collect(Collectors.toSet());
    }

    @Transactional
    public void swipeGiveAway(Long uid, Long giveAwayId, boolean like) {
        System.out.println(giveAwayId);
        User user = User.builder().id(uid).build();
        GiveAway giveAway = giveAwayRepository
                .findById(giveAwayId)
                .orElseThrow(() -> new NotFoundException("Advertisement Not Found"));

        MatchResult matchResult = MatchResult.builder()
                .giveaway(giveAway)
                .user(user)
                .liked(like)
                .build();

        matchResultRepository.save(matchResult);
    }

    @Transactional
    public List<MatchResponse> getMatches(Long uid) {
        return matchResultRepository.getMatches(uid);
    }
}
