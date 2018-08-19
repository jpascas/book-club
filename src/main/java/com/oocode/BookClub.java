package com.oocode;

import java.util.*;
import java.util.stream.Collectors;

public class BookClub {
    private final Map<String, List<String>> listMap = new HashMap<>();
    private IsClassicResolver isClassicResolver;
    public BookClub(IsClassicResolver resolver){
        this.isClassicResolver=resolver;
    }
    public BookClub(){
        this(new IsClassicResolverWithExternalService());
    }
    public void addReview(String bookTitle, String review) {
        listMap.putIfAbsent(bookTitle, new ArrayList<>());
        listMap.get(bookTitle).add(review);
    }

    public List<String> reviewsFor(String bookTitle) {
        return listMap.getOrDefault(bookTitle, Collections.emptyList());
    }

    public List<String> classics(String bookTitle) {
        return search(bookTitle).stream().filter(isClassicResolver::isClassic).sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    public List<String> search(String bookTitleSearch) {
        if (bookTitleSearch == null  || bookTitleSearch.trim().equals("")) {
            throw new IllegalArgumentException();
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
        test();
        return bookTitles.stream()
                .filter(b -> Arrays.stream(b.split(" "))
                                .map(e -> ("" + e.charAt(0)).toLowerCase())
                                .collect(Collectors.joining())
                        .startsWith(bookTitleInitials.toLowerCase()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    private Boolean test()
    {
        return true;
    }

}
