package org.example.springbootvictor.controller;

import org.example.springbootvictor.model.VideoGame;
import org.example.springbootvictor.service.VideoGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videogames")
public class VideoGameController {

    private final VideoGameService videoGameService;

    @Autowired
    public VideoGameController(VideoGameService videoGameService) {
        this.videoGameService = videoGameService;
    }

    // GET - Récupérer tous les jeux
    @GetMapping
    public ResponseEntity<List<VideoGame>> getAllVideoGames() {
        List<VideoGame> games = videoGameService.getAllVideoGames();
        return ResponseEntity.ok(games);
    }

    // GET - Récupérer un jeu par ID
    @GetMapping("/{id}")
    public ResponseEntity<VideoGame> getVideoGameById(@PathVariable Long id) {
        VideoGame game = videoGameService.getVideoGameById(id);
        if (game == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(game);
    }

    // GET - Rechercher par nom (utilise un paramètre de requête)
    @GetMapping("/search")
    public ResponseEntity<List<VideoGame>> searchByName(@RequestParam String name) {
        List<VideoGame> games = videoGameService.searchByName(name);
        return ResponseEntity.ok(games);
    }

    // GET - Rechercher par catégorie
    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<VideoGame>> searchByCategory(@PathVariable String categoryName) {
        List<VideoGame> games = videoGameService.searchByCategory(categoryName);
        return ResponseEntity.ok(games);
    }

    // GET - Rechercher par description
    @GetMapping("/search/description")
    public ResponseEntity<List<VideoGame>> searchByDescription(@RequestParam String description) {
        List<VideoGame> games = videoGameService.searchByDescription(description);
        return ResponseEntity.ok(games);
    }

    // POST - Créer un nouveau jeu
    @PostMapping
    public ResponseEntity<VideoGame> createVideoGame(@RequestBody VideoGame videoGame) {
        VideoGame createdGame = videoGameService.createVideoGame(videoGame);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
    }

    // PUT - Mettre à jour un jeu existant
    @PutMapping("/{id}")
    public ResponseEntity<VideoGame> updateVideoGame(@PathVariable Long id, @RequestBody VideoGame gameDetails) {
        VideoGame updatedGame = videoGameService.updateVideoGame(id, gameDetails);
        if (updatedGame == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedGame);
    }

    // DELETE - Supprimer un jeu
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVideoGame(@PathVariable Long id) {
        boolean deleted = videoGameService.deleteVideoGame(id);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}