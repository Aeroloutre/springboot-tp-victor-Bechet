package org.example.springbootvictor.service;

import org.example.springbootvictor.model.Review;
import org.example.springbootvictor.repository.ReviewRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ReviewService {

    private static ReviewRepository reviewRepository = null;

    public ReviewService(ReviewRepository categoryRepository) {
        this.reviewRepository = categoryRepository;
    }

    public static List<Review> getReviews() {
        return reviewRepository.findAll();
    }

    public static Review getReview(@PathVariable Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    public static Review addReview(@RequestBody Review review) {
        return reviewRepository.save(review);
    }

    public static Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        review.setDate(reviewDetails.getDate());
        review.setRating(reviewDetails.getRating());
        review.setAuthorName(reviewDetails.getAuthorName());
        review.setComment(reviewDetails.getComment());

        return reviewRepository.save(review);
    }

    public static void deleteReview(@PathVariable Long id) {
        reviewRepository.deleteById(id);
    }
}
