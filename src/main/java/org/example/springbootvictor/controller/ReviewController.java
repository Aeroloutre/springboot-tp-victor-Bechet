package org.example.springbootvictor.controller;

import org.example.springbootvictor.model.Review;
import org.example.springbootvictor.service.ReviewService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @GetMapping
    public List<Review> getReviews() {
        return ReviewService.getReviews();
    }

    @GetMapping("/{id}")
    public Review getReview(@PathVariable Long id) {
        return ReviewService.getReview(id);
    }

    @PostMapping
    public Review addReview(@RequestBody Review review) {
        return ReviewService.addReview(review);
    }

    @PutMapping("/{id}")
    public Review updateReview(@PathVariable Long id, @RequestBody Review reviewDetails) {
        return ReviewService.updateReview(id, reviewDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteReview(@PathVariable Long id) {
        ReviewService.deleteReview(id);
    }
}
