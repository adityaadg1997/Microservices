package com.adityagautam.FacilitiesService.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Facilities {

    @Id
    private String facilityId;
    private String hotelId;
    @Column(name = "name")
    private String facilityName;
    @Column(name = "description")
    private String facilityDescription;
}
