package com.theliems.sport_booking.model;

import jakarta.persistence.*;

@Entity
@Table(name = "sport_type")
public class SportType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sport_type_id")
    private Integer id;

    @Column(name = "sport_name", nullable = false, length = 100)
    private String sportName;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    public SportType() {}

    public SportType(Integer id, String sportName, Boolean isDeleted) {
        this.id = id;
        this.sportName = sportName;
        this.isDeleted = isDeleted;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getSportName() { return sportName; }
    public void setSportName(String sportName) { this.sportName = sportName; }

    public Boolean getIsDeleted() { return isDeleted; }
    public void setIsDeleted(Boolean isDeleted) { this.isDeleted = isDeleted; }
}
