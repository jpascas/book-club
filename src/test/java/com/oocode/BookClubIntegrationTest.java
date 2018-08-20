
        package com.oocode;

        import io.specto.hoverfly.junit.core.model.RequestFieldMatcher;
        import org.junit.Test;

        import static io.specto.hoverfly.junit.core.SimulationSource.dsl;
        import static io.specto.hoverfly.junit.dsl.HoverflyDsl.service;
        import static io.specto.hoverfly.junit.dsl.ResponseCreators.success;
        import static io.specto.hoverfly.junit.verification.HoverflyVerifications.times;
        import static java.util.Arrays.asList;
        import static java.util.Collections.singletonList;
        import static org.junit.Assert.assertEquals;

        import org.junit.ClassRule;
        import io.specto.hoverfly.junit.rule.HoverflyRule;

public class BookClubIntegrationTest {

     private static final String matcher = "*.isclassicbooktesolver.com";

    @ClassRule
    public static HoverflyRule hoverflyRule = HoverflyRule.inSimulationMode(dsl(
            service(RequestFieldMatcher.newGlobMatcher(matcher))
                    .get("/isClassic/BookTitle1")
                    .willReturn(success().body("true"))
                    .get("/isClassic/BookTitle2")
                    .willReturn(success().body("false"))
            )
    );


    @Test
    public void Should_ReturnOnlyClassicBookTitles_WhenSearchByTitleOrByInitialsAndConnectingUsingHTTP() {
        IsClassicResolverWithExternalService externalService = new IsClassicResolverWithExternalService("http://www.isclassicbooktesolver.com/isClassic/");
        BookClub bookClub = new BookClub(externalService);
        bookClub.addReview("BookTitle1", "great");

        assertEquals(singletonList("BookTitle1"),
                bookClub.classics("B"));
        assertEquals(singletonList("BookTitle1"),
                bookClub.classics("Boo"));

        hoverflyRule.verify(service(RequestFieldMatcher.newGlobMatcher(matcher)).get("/isClassic/BookTitle1"), times(2));
    }

    @Test
    public void ShouldNot_ReturnBookTitles_WhenSearchByTitleOrByInitialsAndConnectingUsingHTTP() {
        IsClassicResolverWithExternalService externalService = new IsClassicResolverWithExternalService("http://www.isclassicbooktesolver.com/isClassic/");
        BookClub bookClub = new BookClub(externalService);
        bookClub.addReview("BookTitle2", "great");

        assertEquals(asList(),
                bookClub.classics("B"));
        assertEquals(asList(),
                bookClub.classics("Boo"));

        hoverflyRule.verify(service(RequestFieldMatcher.newGlobMatcher(matcher)).get("/isClassic/BookTitle2"), times(2));
    }
}