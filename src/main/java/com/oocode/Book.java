package com.oocode;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public String toString() {
        return this.title;
    }
}
