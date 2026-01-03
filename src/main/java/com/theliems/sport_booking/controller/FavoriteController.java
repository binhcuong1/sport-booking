package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.model.Club;
import com.theliems.sport_booking.service.FavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles/{profileId}/favorites")
public class FavoriteController {

    private final FavoriteService service;

    public FavoriteController(FavoriteService service) {
        this.service = service;
    }

    // GET clubs detail
    @GetMapping
    public List<Club> getFavoriteClubs(@PathVariable Integer profileId) {
        return service.getFavoriteClubs(profileId);
    }

    // GET ids
    @GetMapping("/ids")
    public List<Integer> getFavoriteClubIds(@PathVariable Integer profileId) {
        return service.getFavoriteClubIds(profileId);
    }

    // POST /api/profiles/{profileId}/favorites/{clubId}
    @PostMapping("/{clubId}")
    public ResponseEntity<Void> add(@PathVariable Integer profileId,
                                    @PathVariable Integer clubId) {
        boolean created = service.add(profileId, clubId);
        return created ? ResponseEntity.status(HttpStatus.CREATED).build()
                : ResponseEntity.noContent().build();
    }

    // DELETE /api/profiles/{profileId}/favorites/{clubId}
    @DeleteMapping("/{clubId}")
    public ResponseEntity<Void> remove(@PathVariable Integer profileId,
                                       @PathVariable Integer clubId) {
        service.remove(profileId, clubId);
        return ResponseEntity.noContent().build();
    }

    // POST /toggle -> trả JSON để FE set icon
    @PostMapping("/{clubId}/toggle")
    public Map<String, Object> toggle(@PathVariable Integer profileId,
                                      @PathVariable Integer clubId) {
        boolean isFav = service.toggle(profileId, clubId);
        return Map.of("favorite", isFav);
    }
}
