package com.oocode;

import java.util.List;

public interface BookClubManager {
    void addReview(String bookTitle, Review review);

    List<String> getReviewsFor(String bookTitle);

    List<String> searchForClassicsOnly(String bookTitle);

    List<String> search(String bookTitleSearch);
}
