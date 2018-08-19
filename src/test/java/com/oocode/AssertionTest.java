package com.oocode;

import org.junit.Test;

import java.util.List;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AssertionTest {

    @Test
    public void CheckThatTwoListAreEqualsIncludingTheOrderOfTheirElements() {
        List<String> expectedResult = asList("Hello Wally", "Hello World");
        List<String> realResult = asList("Hello Wally", "Hello World");

        assertEquals("Check that both list are on the same order",
                expectedResult,
                realResult);
    }

    @Test
    public void CheckThatTwoListAreNotEqualsIfOrderOfElementsIsDiferent() {
        List<String> expectedResult = asList("Hello Wally", "Hello World");
        List<String> realResult = asList("Hello World" ,"Hello Wally");

        assertNotEquals("Check that both list are on the same order",
                expectedResult,
                realResult);
    }
}
