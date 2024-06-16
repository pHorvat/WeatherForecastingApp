package com.phorvat.weatherforecastingapp.models.location.response;

import com.phorvat.weatherforecastingapp.models.location.Location;
import lombok.Data;

import java.util.List;

@Data
public class LocationDetails {
    private Integer id;
    private String name;
    private double latitude;
    private double longitude;
    private String country;
}
