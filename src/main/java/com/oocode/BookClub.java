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

    public void addReview(String z, String n) {
        listMap.putIfAbsent(z, new ArrayList<>());
        listMap.get(z).add(n);
    }

    public List<String> reviewsFor(String z) {
        return listMap.getOrDefault(z, Collections.emptyList());
    }

    public List<String> classics(String n) {
        return search(n).stream().filter(BookClub::isClassic).sorted()
                .collect(Collectors.toList());
    }

    public List<String> search(String z) {
        if (z.equals("")) {
            throw new IllegalArgumentException();
        }
        if (z.toUpperCase().equals(z)) {
            return listMap.keySet().stream()
                    .filter(b -> Arrays.stream(b.split(" "))
                                    .map(e -> ("" + e.charAt(0)).toLowerCase())
                                    .collect(Collectors.joining())
                            .startsWith(z.toLowerCase()))
                    .collect(Collectors.toList());
        }
        return listMap.keySet().stream()
                .filter(b -> b.toLowerCase().startsWith(z.toLowerCase()))
                .sorted()
                .collect(Collectors.toList());
    }

    private static boolean isClassic(String z) {
        URLConnection conn;
        try {
            conn = new URL("https://pure-coast-78546.herokuapp.com/isClassic/"
                    + URLEncoder.encode(z, "UTF-8")).openConnection();
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
