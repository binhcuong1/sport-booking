package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.Club;
import com.theliems.sport_booking.model.Favorite;
import com.theliems.sport_booking.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    @Query("""
        SELECT c
        FROM Favorite f
        JOIN f.club c
        WHERE f.profile.profile_id = :profileId
    """)
    List<Club> findFavoriteClubs(@Param("profileId") Integer profileId);

    @Query("""
        SELECT f.id.clubId
        FROM Favorite f
        WHERE f.profile.profile_id = :profileId
    """)
    List<Integer> findFavoriteClubIds(@Param("profileId") Integer profileId);
}
