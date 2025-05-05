package com.artocons.carshop.service;

import com.artocons.carshop.persistence.model.Rating;
import com.artocons.carshop.persistence.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingService {

    private final RatingRepository ratingRepository;

    @Transactional
    public Rating addRating(Rating ratingData, Long productId)  {

        Rating newComment = new Rating();
        newComment.setProductId(productId);
        newComment.setRatingDate(LocalDate.now());
        newComment.setRating(ratingData.getRating());
        newComment.setComment(ratingData.getComment());
        newComment.setUserName(ratingData.getUserName());

        return ratingRepository.save(newComment);
    }

    public String calculateRatingStar(long productId) {

        BigDecimal avg = calculateAverageRating(productId);

        if (avg.compareTo(new BigDecimal("4.5")) >= 0 && avg.compareTo(new BigDecimal("5.0")) <= 0) {
            return "♥ ♥ ♥ ♥ ♥";
        } else if (avg.compareTo(new BigDecimal("3.5")) > 0 && avg.compareTo(new BigDecimal("4.5")) < 0) {
            return "♥ ♥ ♥ ♥";
        } else if (avg.compareTo(new BigDecimal("2.5")) > 0 && avg.compareTo(new BigDecimal("3.5")) < 0) {
            return "♥ ♥ ♥";
        } else if (avg.compareTo(new BigDecimal("1.5")) > 0 && avg.compareTo(new BigDecimal("2.5")) < 0) {
            return "♥ ♥";
        } else {
            return "♥";
        }
    }

    public BigDecimal calculateAverageRating(long productId) {

        List<Rating> ratingList = getRatingByProductId(productId);

        if (ratingList.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal total = BigDecimal.ZERO;
        for (Rating rating : ratingList) {
            total = total.add(BigDecimal.valueOf(rating.getRating()));
        }

        return total.divide(new BigDecimal(ratingList.size()), 2, BigDecimal.ROUND_HALF_UP);
    }

    public int getRatingCount(long productId) {
        List<Rating> ratingList = getRatingByProductId(productId);
        if (ratingList.isEmpty()) {
            return 0;
        }

        return ratingList.size();
    }

    public List<Rating> getRatingByProductId(long productId) {

        return ratingRepository.findByProductId(productId);
    }

    private List<Rating> getAllRatings() {
        return ratingRepository.findAll();
    }
}
