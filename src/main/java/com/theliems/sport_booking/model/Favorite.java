package com.theliems.sport_booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "favorite")
public class Favorite {

    @EmbeddedId
    private FavoriteId id;

    @JsonIgnore
    @MapsId("clubId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club club;

    @JsonIgnore
    @MapsId("profileId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    private Profile profile;

    public Favorite() {}

    public Favorite(FavoriteId id) {
        this.id = id;
    }

    public FavoriteId getId() {
        return id;
    }

    public void setId(FavoriteId id) {
        this.id = id;
    }

    public Integer getClubId() {
        return id != null ? id.getClubId() : null;
    }

    public Integer getProfileId() {
        return id != null ? id.getProfileId() : null;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }
}
