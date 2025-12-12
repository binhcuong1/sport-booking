package com.theliems.sport_booking.repository;

import com.theliems.sport_booking.model.SportType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SportTypeRepository extends JpaRepository<SportType, Integer> {
}
