package org.example.springbootvictor.repository;

import org.example.springbootvictor.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    // Recherche une catégorie par nom
    Optional<Category> findByName(String name);

    // Vérifie si une catégorie existe
    boolean existsByName(String name);
}