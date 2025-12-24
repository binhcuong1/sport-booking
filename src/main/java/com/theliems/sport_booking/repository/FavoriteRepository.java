package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.Favorite;
import com.theliems.sport_booking.model.FavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, FavoriteId> {

    List<Favorite> findById_ProfileId(Integer profileId);

    boolean existsById(FavoriteId id);
}

