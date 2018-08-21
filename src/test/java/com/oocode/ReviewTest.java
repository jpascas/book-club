package com.oocode;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class ReviewTest {
    @Test
    public void Should_CreateBook_WithCorrectTitle() {
        Date date = new Date();
        Review review = new Review("This is a Review", date);
        assertEquals("This is a Review", review.getText());
        assertEquals(date, review.getDate());
    }
}
