package com.example.clickbus.service;

import com.example.clickbus.model.Location;

import java.util.List;

public interface LocationService {

    Location createLocation (Location location);
    Location updateLocation (Long id, Location location);
    Location getLocationById (Long id);
    List <Location> getAllLocations();
    List <Location> getLocationsByName(String name);

}
