package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Rating;
import com.artocons.carshop.persistence.repository.RatingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
public class RatingServiceIntegrationTest {

    @Autowired
    private RatingService ratingService;

    @Autowired
    private RatingRepository ratingRepository;

    @BeforeEach
    public void setUp() {
        ratingRepository.deleteAll();
    }

    @Test
    @Rollback
    public void testAddRating() {
        Rating rating = new Rating();
        rating.setProductId(1L);
        rating.setUserName("test");
        rating.setRatingDate(LocalDate.now());
        rating.setRating(5);

        Rating savedRating = ratingService.addRating(rating, 1L);

        assertThat(savedRating).isNotNull();
        assertThat(savedRating.getRatingId()).isNotNull();
        assertThat(savedRating.getProductId()).isEqualTo(1L);
        assertThat(savedRating.getRating()).isEqualTo(5);
    }

    @Test
    @Rollback
    public void testCalculateAverageRating() {
        Rating rating1 = new Rating();
        rating1.setProductId(1L);
        rating1.setUserName("test1");
        rating1.setRatingDate(LocalDate.now());
        rating1.setRating(5);

        Rating rating2 = new Rating();
        rating2.setProductId(1L);
        rating2.setUserName("test2");
        rating2.setRatingDate(LocalDate.now());
        rating2.setRating(4);

        ratingRepository.save(rating1);
        ratingRepository.save(rating2);

        BigDecimal average = ratingService.calculateAverageRating(1L);

        assertThat(average).isEqualTo(new BigDecimal("4.50"));
    }

    @Test
    @Rollback
    public void testCalculateRatingStar() {
        Rating rating1 = new Rating();
        rating1.setProductId(1L);
        rating1.setUserName("test1");
        rating1.setRatingDate(LocalDate.now());
        rating1.setRating(5);

        Rating rating2 = new Rating();
        rating2.setProductId(1L);
        rating2.setUserName("test2");
        rating2.setRatingDate(LocalDate.now());
        rating2.setRating(4);

        ratingRepository.save(rating1);
        ratingRepository.save(rating2);

        String stars = ratingService.calculateRatingStar(1L);

        assertThat(stars).isEqualTo("♥ ♥ ♥ ♥");
    }

    @Test
    @Rollback
    public void testGetRatingCount() {
        Rating rating1 = new Rating();
        rating1.setProductId(1L);
        rating1.setUserName("test1");
        rating1.setRatingDate(LocalDate.now());
        rating1.setRating(5);

        Rating rating2 = new Rating();
        rating2.setProductId(1L);
        rating2.setUserName("test2");
        rating2.setRatingDate(LocalDate.now());
        rating2.setRating(4);

        ratingRepository.save(rating1);
        ratingRepository.save(rating2);

        int count = ratingService.getRatingCount(1L);

        assertThat(count).isEqualTo(2);
    }
}
