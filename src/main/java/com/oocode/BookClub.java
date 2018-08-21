package com.oocode;

import java.util.*;
import java.util.stream.Collectors;

public class BookClub implements BookClubManager {

    private final Map<String, Book> listMap = new HashMap<>();
    private IsClassicResolver isClassicResolver;

    public BookClub(
            IsClassicResolver resolver){
        this.isClassicResolver=resolver;
    }
    public BookClub(){
        this(new IsClassicResolverWithExternalService());
    }

    @Override
    public void addReview(String bookTitle, Review review) {
        listMap.putIfAbsent(bookTitle, new Book(bookTitle));
        listMap.get(bookTitle).addReview(review);
    }

    @Override
    public List<String> getReviewsFor(String bookTitle) {
        Book book = listMap.getOrDefault(bookTitle,null);
        if (book!=null) {
            return book.getReviews().stream().map(review -> review.getText()).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<String> searchForClassicsOnly(String bookTitle) {
        return search(bookTitle).stream().filter(isClassicResolver::isClassic).sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> search(String bookTitleSearch) {
        if (bookTitleSearch == null  || bookTitleSearch.trim().equals("")) {
            throw new IllegalArgumentException("Book Title cannot be null or empty");
        }
        if (bookTitleSearch.toUpperCase().equals(bookTitleSearch)) {
            return searchByTitleInitials(bookTitleSearch);
        }
        return searchByTitle(bookTitleSearch);
    }

    private List<String> searchByTitle(String bookTitle) {
        Set<String> bookTitles = listMap.keySet();
        return bookTitles.stream()
                .filter(b -> b.toLowerCase().startsWith(bookTitle.toLowerCase()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    private List<String> searchByTitleInitials(String bookTitleInitials) {
        Set<String> bookTitles = listMap.keySet();
        return bookTitles.stream()
                .filter(b -> Arrays.stream(b.split(" "))
                                .map(e -> ("" + e.charAt(0)).toLowerCase())
                                .collect(Collectors.joining())
                        .startsWith(bookTitleInitials.toLowerCase()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }
}
