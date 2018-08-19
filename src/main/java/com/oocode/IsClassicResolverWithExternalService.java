package com.oocode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class IsClassicResolverWithExternalService implements IsClassicResolver {
    @Override
    public boolean isClassic(String bookTitle) {
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
