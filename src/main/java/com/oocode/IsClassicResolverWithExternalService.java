package com.oocode;

import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

public class IsClassicResolverWithExternalService implements IsClassicResolver {

    private final String apiURL;

    public IsClassicResolverWithExternalService(String apiURL) {
        this.apiURL = apiURL;
    }

    public IsClassicResolverWithExternalService() {
        this("https://pure-coast-78546.herokuapp.com/isClassic/");
    }

    @Override
    public boolean isClassic(String bookTitle) {
        URLConnection conn;
        try {
            conn = new URL(this.apiURL
                    + URLEncoder.encode(bookTitle, "UTF-8")).openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (
                //this is a "try-with-resources Statement"
                //all the resources inside that implement java.io.Closeable and java.lang.AutoCloseable will be closed automatically
                //so, no need to include a finally statement
                //https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html
                InputStream inputStream = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(
                        inputStream, StandardCharsets.UTF_8))
            )
        {
            return reader.lines().collect(Collectors.joining("\n"))
                    .contains("true"); //possible bug, contains is too broad
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
