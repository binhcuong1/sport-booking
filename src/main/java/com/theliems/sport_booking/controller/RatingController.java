package com.theliems.sport_booking.controller;

import com.theliems.sport_booking.model.Rating;
import com.theliems.sport_booking.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService service;

    public RatingController(RatingService service) {
        this.service = service;
    }

    // ================== GET: LIST RATING THEO CLUB ==================
    // GET /api/ratings/club/1
    @GetMapping("/club/{clubId}")
    public ResponseEntity<?> getByClub(@PathVariable Integer clubId) {

        List<Rating> list = service.getByClub(clubId);
        Double avg = service.getAverageScore(clubId);

        Map<String, Object> res = new HashMap<>();
        res.put("avgScore", avg);
        res.put("list", list);

        return ResponseEntity.ok(res);
    }

    // ================== POST: ADD RATING ==================
    // POST /api/ratings
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Map<String, Object> body) {

        Integer clubId = (Integer) body.get("clubId");
        Integer profileId = (Integer) body.get("profileId");
        Integer score = (Integer) body.get("score");
        String review = (String) body.get("review");

        Rating saved = service.addRating(clubId, profileId, score, review);
        return ResponseEntity.ok(saved);
    }

    // ================== DELETE: SOFT DELETE ==================
    // DELETE /api/ratings/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);

        Map<String, String> res = new HashMap<>();
        res.put("message", "Xoá đánh giá thành công");

        return ResponseEntity.ok(res);
    }
}
