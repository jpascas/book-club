package com.oocode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

public class BookClub {
    private final Map<String, List<String>> listMap = new HashMap<>();

    public void addReview(String bookTitle, String review) {
        listMap.putIfAbsent(bookTitle, new ArrayList<>());
        listMap.get(bookTitle).add(review);
    }

    public List<String> reviewsFor(String bookTitle) {
        return listMap.getOrDefault(bookTitle, Collections.emptyList());
    }

    public List<String> classics(String bookTitle) {
        return search(bookTitle).stream().filter(BookClub::isClassic).sorted(Comparator.naturalOrder())
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
        return bookTitles.stream()
                .filter(b -> Arrays.stream(b.split(" "))
                                .map(e -> ("" + e.charAt(0)).toLowerCase())
                                .collect(Collectors.joining())
                        .startsWith(bookTitleInitials.toLowerCase()))
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
    }

    private static boolean isClassic(String bookTitle) {
        URLConnection conn;
        try {
            conn = new URL("https://pure-coast-78546.herokuapp.com/isClassic/"
                    + URLEncoder.encode(bookTitle, "UTF-8")).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                conn.getInputStream(), StandardCharsets.UTF_8))) {
            return reader.lines().collect(Collectors.joining("\n"))
                    .contains("true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
