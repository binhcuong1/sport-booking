package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.Club;
import com.theliems.sport_booking.model.Favorite;
import com.theliems.sport_booking.model.FavoriteId;
import com.theliems.sport_booking.model.Profile;
import com.theliems.sport_booking.repository.ClubRepository;
import com.theliems.sport_booking.repository.FavoriteRepository;
import com.theliems.sport_booking.repository.ProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepo;
    private final ProfileRepository profileRepo;
    private final ClubRepository clubRepo;

    public FavoriteService(FavoriteRepository favoriteRepo,
                           ProfileRepository profileRepo,
                           ClubRepository clubRepo) {
        this.favoriteRepo = favoriteRepo;
        this.profileRepo = profileRepo;
        this.clubRepo = clubRepo;
    }

    public List<Club> getFavoriteClubs(Integer profileId) {
        return favoriteRepo.findFavoriteClubs(profileId);
    }

    public List<Integer> getFavoriteClubIds(Integer profileId) {
        return favoriteRepo.findFavoriteClubIds(profileId);
    }

    @Transactional
    public boolean add(Integer profileId, Integer clubId) {
        FavoriteId id = new FavoriteId(clubId, profileId);

        if (favoriteRepo.existsById(id)) return false;

        Profile p = profileRepo.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Profile not found: " + profileId));
        Club c = clubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Club not found: " + clubId));

        Favorite f = new Favorite(id);
        f.setProfile(p);
        f.setClub(c);

        favoriteRepo.save(f);
        return true;
    }

    @Transactional
    public boolean remove(Integer profileId, Integer clubId) {
        FavoriteId id = new FavoriteId(clubId, profileId);

        if (!favoriteRepo.existsById(id)) return false;

        favoriteRepo.deleteById(id);
        return true;
    }

    @Transactional
    public boolean toggle(Integer profileId, Integer clubId) {
        FavoriteId id = new FavoriteId(clubId, profileId);

        if (favoriteRepo.existsById(id)) {
            favoriteRepo.deleteById(id);
            return false;
        } else {
            Profile p = profileRepo.findById(profileId)
                    .orElseThrow(() -> new RuntimeException("Profile not found: " + profileId));
            Club c = clubRepo.findById(clubId)
                    .orElseThrow(() -> new RuntimeException("Club not found: " + clubId));

            Favorite f = new Favorite(id);
            f.setProfile(p);
            f.setClub(c);

            favoriteRepo.save(f);
            return true;
        }
    }
}
