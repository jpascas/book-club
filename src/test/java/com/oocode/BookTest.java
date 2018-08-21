package com.oocode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BookTest {

    @Test
    public void Should_CreateBook_WithCorrectTitle() {
        Book book = new Book("This is a Title");
        assertEquals("This is a Title", book.getTitle());
    }
    @Test
    public void Should_AddandGetReviews_WithCorrectValue() {
        Book book = new Book("This is a Title");

        Review review = new Review("This is a Review");

        book.addReview(review);
        assertEquals(1,book.getReviews().size());
        assertEquals(review, book.getReviews().get(0));
    }
}