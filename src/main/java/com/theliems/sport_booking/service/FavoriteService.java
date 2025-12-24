package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.Favorite;
import com.theliems.sport_booking.model.FavoriteId;
import com.theliems.sport_booking.repository.FavoriteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;

    public FavoriteService(FavoriteRepository favoriteRepository) {
        this.favoriteRepository = favoriteRepository;
    }

    public List<Favorite> getFavoritesByProfile(Integer profileId) {
        return favoriteRepository.findById_ProfileId(profileId);
    }

    public void addFavorite(Integer clubId, Integer profileId) {
        FavoriteId id = new FavoriteId(clubId, profileId);
        if (favoriteRepository.existsById(id)) return;

        Favorite fav = new Favorite();
        fav.setId(id);
        favoriteRepository.save(fav);
    }

    public void removeFavorite(Integer clubId, Integer profileId) {
        FavoriteId id = new FavoriteId(clubId, profileId);
        favoriteRepository.deleteById(id);
    }

    public boolean isFavorite(Integer clubId, Integer profileId) {
        return favoriteRepository.existsById(new FavoriteId(clubId, profileId));
    }
}
