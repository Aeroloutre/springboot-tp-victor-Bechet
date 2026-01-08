package org.example.springbootvictor.repository;

import org.example.springbootvictor.model.VideoGame;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideoGameRepository extends JpaRepository<VideoGame, Long> {

    // Recherche par nom exact
    List<VideoGame> findByName(String name);

    // Recherche par nom contenant une chaîne (plus pratique)
    List<VideoGame> findByNameContainingIgnoreCase(String name);

    // Recherche par catégorie
    List<VideoGame> findByCategories_Name(String categoryName);

    // Recherche par description
    List<VideoGame> findByDescriptionContainingIgnoreCase(String description);
}