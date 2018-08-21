package com.oocode;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ReviewTest {
    @Test
    public void Should_CreateBook_WithCorrectTitle() {
        Review review = new Review("This is a Review");
        assertEquals("This is a Review", review.getText());
    }
}
