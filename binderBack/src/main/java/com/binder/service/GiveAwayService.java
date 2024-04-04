package com.binder.service;


import com.binder.entity.*;
import com.binder.exception.ForbiddenException;
import com.binder.exception.NotFoundException;
import com.binder.repository.BookRepository;
import com.binder.repository.GiveAwayRepository;
import com.binder.repository.MatchResultRepository;
import com.binder.repository.UserStoryRepository;
import com.binder.request.BookRequest;
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
    private final UserStoryRepository userStoryRepository;

    @Transactional
    public Book addBook(BookRequest request) {

        Optional<Book> optionalBook = bookRepository.findByTitleAndAuthorAndDescription(
                request.title(),
                request.author(),
                request.description()
        );
        Book book;

        if (optionalBook.isEmpty()) {
            book = Book.builder()
                    .description(request.description())
                    .author(request.author())
                    .title(request.title())
                    .year(request.year())
                    .build();
            book = bookRepository.save(book);
        } else {
            book = optionalBook.get();
        }

        return book;
    }

    @Transactional
    public GiveAwayResponse addGiveAway(Long uid, GiveAwayRequest request) {
        User user = User.builder().id(uid).build();
        Book book = addBook(request.book());

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
        Set<GiveAway> foundNew = giveAwayRepository.getAvailableUnreadGiveAway(uid);

        return foundNew.stream().map(GiveAwayResponse::new).collect(Collectors.toList());
//        Set<GiveAwayResponse> likedUs = matchResultRepository.likedUs(uid);
//        return likedUs.stream()
//                .filter(el -> found.stream().anyMatch(cur -> cur.getId().equals(el.getId())))
//                .collect(Collectors.toSet());
    }

    @Transactional
    public void swipeGiveAway(Long uid, Long giveAwayId, boolean like) {
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
        List<MatchResponse> matches =  matchResultRepository.getMatches(uid);
        return matches;
    }

    @Transactional
    public void rateBook(Long uid, Long bid, boolean like) {
        User user = User.builder().id(uid).build();
        Book book = bookRepository
                .findById(bid)
                .orElseThrow(() -> new NotFoundException("Book Not Found"));

        UserStory userStory = UserStory
                .builder()
                .user(user)
                .book(book)
                .liked(like)
                .build();

        userStoryRepository.save(userStory);
    }

    @Transactional
    public void deleteGiveAway(Long uid, Long gid) {
        GiveAway giveAway = giveAwayRepository
                .findById(gid)
                .orElseThrow(() -> new NotFoundException("Advertisement Not Found"));

        if (!giveAway.getUser().getId().equals(uid)) {
            throw new ForbiddenException("User cannot delete advertisement of another user");
        }

        matchResultRepository.deleteAllByGiveaway(giveAway);
        giveAwayRepository.delete(giveAway);
    }
}
