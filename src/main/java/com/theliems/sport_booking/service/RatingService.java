package com.theliems.sport_booking.service;

import com.theliems.sport_booking.model.BookingStatus;
import com.theliems.sport_booking.model.Club;
import com.theliems.sport_booking.model.Profile;
import com.theliems.sport_booking.model.Rating;
import com.theliems.sport_booking.repository.BookingRepository;
import com.theliems.sport_booking.repository.ClubRepository;
import com.theliems.sport_booking.repository.ProfileRepository;
import com.theliems.sport_booking.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepo;
    private final ClubRepository clubRepo;
    private final ProfileRepository profileRepo;
    private final BookingRepository bookingRepo;

    public RatingService(RatingRepository ratingRepo, ClubRepository clubRepo, ProfileRepository profileRepo,BookingRepository bookingRepo) {
        this.ratingRepo = ratingRepo;
        this.clubRepo = clubRepo;
        this.profileRepo = profileRepo;
        this.bookingRepo = bookingRepo;
    }

    public List<Rating> getByClub(Integer clubId) {
        Club club = clubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CLB"));
        return ratingRepo.findByClub(club);
    }

    public Rating addRating(Integer clubId, Integer profileId, Integer score, String review) {
        if (!bookingRepo.hasCompletedBooking(
                profileId,
                clubId,
                BookingStatus.HOAN_THANH
        )) {
            throw new RuntimeException(
                    "Bạn phải đặt sân và hoàn thành tại CLB này mới được đánh giá"
            );
        }

        if (score == null || score < 1 || score > 5) {
            throw new RuntimeException("Điểm đánh giá phải từ 1 đến 5");
        }

        Club club = clubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CLB"));

        Profile profile = profileRepo.findById(profileId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy profile"));

        if (ratingRepo.existsActive(club, profile)) {
            throw new RuntimeException("Bạn đã đánh giá CLB này rồi");
        }

        Rating rating = new Rating();
        rating.setClub(club);
        rating.setProfile(profile);
        rating.setScore(score);
        rating.setReview(review);

        return ratingRepo.save(rating);
    }

    public Double getAverageScore(Integer clubId) {
        Club club = clubRepo.findById(clubId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy CLB"));

        Double avg = ratingRepo.getAverageScore(club);
        return avg == null ? 0.0 : avg;
    }

    public void delete(Integer ratingId) {
        Rating rating = ratingRepo.findById(ratingId)
                .orElseThrow(() -> new RuntimeException("Rating không tồn tại"));

        rating.setIs_deleted(true);
        ratingRepo.save(rating);
    }
}
