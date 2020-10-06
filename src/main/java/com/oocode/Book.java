package com.oocode;

import java.util.*;
import java.util.stream.Collectors;

public class Book {

    private String title;
    private List<Review> reviews = new ArrayList<Review>();

    public Book(String title){
        this.title  = title;
    }

    public String getTitle()
    {
        return this.title;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }
    public List<Review> getReviews()
    {
        return this.reviews;
    }

    private static final int DEAFAUlTYEARLIMIT = 1;

    public Boolean IsLastReviewOlderThanDefaultLimit()
    {
        Date mostRecentReviewDate = this.getMostRecentReviewDate();
        Calendar calendar  = Calendar.getInstance();
        calendar.add(Calendar.YEAR, -DEAFAUlTYEARLIMIT);
        Date limitDate = calendar.getTime();
        return mostRecentReviewDate.before(limitDate);
    }

    public Date getMostRecentReviewDate()
    {
        return this.reviews.stream().map(review -> review.getDate()).sorted(Comparator.reverseOrder()).collect(Collectors.toList()).get(0);
    }

    @Override
    public String toString() {
        return this.title;
    }
}
