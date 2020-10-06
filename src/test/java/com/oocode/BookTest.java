package com.oocode;

import com.fasterxml.jackson.databind.node.BooleanNode;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class BookTest {

    @Test
    public void Should_CreateBook_WithCorrectTitle() {
        Book book = new Book("This is a Title");
        assertEquals("This is a Title", book.getTitle());
    }
    @Test
    public void Should_AddandGetReviews_WithCorrectValue() {
        Book book = new Book("This is a Title");

        Review review = ReviewUtil.getNewReview();

        book.addReview(review);
        assertEquals(1,book.getReviews().size());
        assertEquals(review, book.getReviews().get(0));
    }

    @Test
    public void Should_getMostRecentReviewDate_NoMatterOrderOfInsertion() {
        Book book = new Book("This is a Title");

        Review newReview = ReviewUtil.getNewReview();
        Review oldReview = ReviewUtil.getOlderThanOneYearReview();

        book.addReview(newReview);
        book.addReview(oldReview);
        assertEquals(newReview.getDate(),book.getMostRecentReviewDate());
    }

    @Test
    public void Should_getMostRecentReviewDate_NoMatterOrderOfInsertion2() {
        Book book = new Book("This is a Title");

        Review newReview = ReviewUtil.getNewReview();
        Review oldReview = ReviewUtil.getOlderThanOneYearReview();

        book.addReview(oldReview); //diferent order
        book.addReview(newReview);
        assertEquals(newReview.getDate(),book.getMostRecentReviewDate());
    }

    @Test
    public void Should_ReturnTrue_WhenReviewIsOlderThanOneYear() {
        Book book = new Book("This is a Title");

        Review oldReview = ReviewUtil.getOlderThanOneYearReview();

        book.addReview(oldReview);

        assertEquals(true,book.IsLastReviewOlderThanDefaultLimit());
    }

    @Test
    public void Should_ReturnFalse_WhenReviewIsOlderThanOneYear() {
        Book book = new Book("This is a Title");

        Review newReview = ReviewUtil.getNewReview();

        book.addReview(newReview);

        assertEquals(false,book.IsLastReviewOlderThanDefaultLimit());
    }
}