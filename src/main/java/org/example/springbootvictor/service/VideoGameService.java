package org.example.springbootvictor.service;

import org.example.springbootvictor.model.VideoGame;
import org.example.springbootvictor.repository.VideoGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VideoGameService {

    private final VideoGameRepository videoGameRepository;

    @Autowired
    public VideoGameService(VideoGameRepository videoGameRepository) {
        this.videoGameRepository = videoGameRepository;
    }

    // Récupérer tous les jeux
    public List<VideoGame> getAllVideoGames() {
        return videoGameRepository.findAll();
    }

    // Récupérer un jeu par ID
    public VideoGame getVideoGameById(Long id) {
        return videoGameRepository.findById(id).orElse(null);
    }

    // Rechercher par nom (retourne une liste)
    public List<VideoGame> searchByName(String name) {
        return videoGameRepository.findByNameContainingIgnoreCase(name);
    }

    // Rechercher par catégorie
    public List<VideoGame> searchByCategory(String categoryName) {
        return videoGameRepository.findByCategories_Name(categoryName);
    }

    // Rechercher par description
    public List<VideoGame> searchByDescription(String description) {
        return videoGameRepository.findByDescriptionContainingIgnoreCase(description);
    }

    // Créer un nouveau jeu
    public VideoGame createVideoGame(VideoGame videoGame) {
        return videoGameRepository.save(videoGame);
    }

    // Mettre à jour un jeu existant
    public VideoGame updateVideoGame(Long id, VideoGame gameDetails) {
        return videoGameRepository.findById(id)
                .map(videoGame -> {
                    videoGame.setName(gameDetails.getName());
                    videoGame.setDescription(gameDetails.getDescription());
                    videoGame.setReleaseDate(gameDetails.getReleaseDate());
                    videoGame.setImageUrl(gameDetails.getImageUrl());

                    // Si tu veux aussi mettre à jour les catégories
                    if (gameDetails.getCategories() != null) {
                        videoGame.setCategories(gameDetails.getCategories());
                    }

                    return videoGameRepository.save(videoGame);
                })
                .orElse(null);
    }

    // Supprimer un jeu
    public boolean deleteVideoGame(Long id) {
        if (videoGameRepository.existsById(id)) {
            videoGameRepository.deleteById(id);
            return true;
        }
        return false;
    }
}