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
        List<String> preEliminarBookTitlesResult = bookTitles.stream()
                .filter(b -> b.toLowerCase().startsWith(bookTitle.toLowerCase()))
                .collect(Collectors.toList());
        //Since we are dependant on a external resource for resource if its classic, i think it will be more efficient for now to do this
        //after the text filter si completed
       return applyIsClassicOrIsLastReviewOlderThanDefaultLimitFilter(preEliminarBookTitlesResult);
    }

    private List<String> searchByTitleInitials(String bookTitleInitials) {
        Set<String> bookTitles = listMap.keySet();
        List<String> preEliminarBookTitlesResult =  bookTitles.stream()
                .filter(b -> Arrays.stream(b.split(" "))
                                .map(e -> ("" + e.charAt(0)).toLowerCase())
                                .collect(Collectors.joining())
                        .startsWith(bookTitleInitials.toLowerCase()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        return applyIsClassicOrIsLastReviewOlderThanDefaultLimitFilter(preEliminarBookTitlesResult);
    }

    private List<String> applyIsClassicOrIsLastReviewOlderThanDefaultLimitFilter(List<String> preEliminarBookTitlesResult) {
        return preEliminarBookTitlesResult.stream().map(bt -> listMap.get(bt))
                .filter(book -> isClassicResolver.isClassic(book.getTitle()) || !book.IsLastReviewOlderThanDefaultLimit())
                .map(b -> b.getTitle())
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }
}
