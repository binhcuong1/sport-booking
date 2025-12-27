package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.model.Favorite;
import com.theliems.sport_booking.service.FavoriteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;

    public FavoriteController(FavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }

    // GET /api/favorites/{profileId}
    @GetMapping("/{profileId}")
    public List<Favorite> getByProfile(@PathVariable Integer profileId) {
        return favoriteService.getFavoritesByProfile(profileId);
    }

    // POST /api/favorites?clubId=1&profileId=2
    @PostMapping
    public ResponseEntity<?> add(@RequestParam Integer clubId,
                                 @RequestParam Integer profileId) {
        favoriteService.addFavorite(clubId, profileId);
        return ResponseEntity.ok(Map.of("message", "Đã thêm yêu thích"));
    }

    // DELETE /api/favorites?clubId=1&profileId=2
    @DeleteMapping
    public ResponseEntity<?> remove(@RequestParam Integer clubId,
                                    @RequestParam Integer profileId) {
        favoriteService.removeFavorite(clubId, profileId);
        return ResponseEntity.ok(Map.of("message", "Đã xóa yêu thích"));
    }

    // GET /api/favorites/exists?clubId=1&profileId=2
    @GetMapping("/exists")
    public ResponseEntity<?> exists(@RequestParam Integer clubId,
                                    @RequestParam Integer profileId) {
        boolean ok = favoriteService.isFavorite(clubId, profileId);
        return ResponseEntity.ok(Map.of("favorite", ok));
    }
}
