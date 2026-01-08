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

    // GET All
    public List<VideoGame> getAllVideoGames() {
        return videoGameRepository.findAll();
    }

    // GET game by id
    public VideoGame getVideoGameById(Long id) {
        return videoGameRepository.findById(id).orElse(null);
    }

    // GET games by name
    public List<VideoGame> searchByName(String name) {
        return videoGameRepository.findByName(name);
    }

    // GET games by category
    public List<VideoGame> searchByCategory(String categoryName) {
        return videoGameRepository.findByCategories_Name(categoryName);
    }

    // GET games by description
    public List<VideoGame> searchByDescription(String description) {
        return videoGameRepository.findByDescriptionContainingIgnoreCase(description);
    }

    // POST game
    public VideoGame createVideoGame(VideoGame videoGame) {
        return videoGameRepository.save(videoGame);
    }

    // PUT game
    public VideoGame updateVideoGame(Long id, VideoGame gameDetails) {
        return videoGameRepository.findById(id)
                .map(videoGame -> {
                    videoGame.setName(gameDetails.getName());
                    videoGame.setDescription(gameDetails.getDescription());
                    videoGame.setReleaseDate(gameDetails.getReleaseDate());
                    videoGame.setImageUrl(gameDetails.getImageUrl());

                    if (gameDetails.getCategories() != null) {
                        videoGame.setCategories(gameDetails.getCategories());
                    }

                    return videoGameRepository.save(videoGame);
                })
                .orElse(null);
    }

    // DELETE game
    public boolean deleteVideoGame(Long id) {
        if (videoGameRepository.existsById(id)) {
            videoGameRepository.deleteById(id);
            return true;
        }
        return false;
    }
}