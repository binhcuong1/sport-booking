package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Integer> {

    @Query("""
        SELECT r
        FROM Rating r
        WHERE r.club = :club
          AND r.is_deleted = false
        ORDER BY r.created_at DESC
    """)
    List<Rating> findByClub(@Param("club") com.theliems.sport_booking.model.Club club);

    @Query("""
        SELECT COUNT(r) > 0
        FROM Rating r
        WHERE r.club = :club
          AND r.profile = :profile
          AND r.is_deleted = false
    """)
    boolean existsActive(@Param("club") com.theliems.sport_booking.model.Club club,
                         @Param("profile") com.theliems.sport_booking.model.Profile profile);

    @Query("""
        SELECT AVG(r.score)
        FROM Rating r
        WHERE r.club = :club
          AND r.is_deleted = false
    """)
    Double getAverageScore(@Param("club") com.theliems.sport_booking.model.Club club);
}
