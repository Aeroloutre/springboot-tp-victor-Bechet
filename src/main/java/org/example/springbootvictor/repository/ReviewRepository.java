package org.example.springbootvictor.repository;

import org.example.springbootvictor.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Récupère tous les avis d'un jeu spécifique
    List<Review> findByVideoGameId(Long videoGameId);

    // Récupère les avis par auteur
    List<Review> findByAuthorName(String authorName);

    // Optionnel : récupère les avis avec une note minimale
    List<Review> findByRatingGreaterThanEqual(int rating);
}