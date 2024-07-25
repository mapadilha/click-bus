package com.example.clickbus.service;

import com.example.clickbus.model.Location;
import com.example.clickbus.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LocationServiceImpl implements LocationService {
    @Autowired
    private LocationRepository locationRepository;

    @Override
    public Location createLocation(Location location) {
        location.setCreatedAt(LocalDateTime.now());
        location.setUpdatedAt(LocalDateTime.now());
        return locationRepository.save(location);
    }

    @Override
    public Location updateLocation(Long id, Location location) {
        Optional<Location> existingLocationOpt = locationRepository.findById(id);
        if (existingLocationOpt.isPresent()) {
            Location existingLocation = existingLocationOpt.get();
            existingLocation.setName(location.getName());
            existingLocation.setSlug(location.getSlug());
            existingLocation.setCity(location.getCity());
            existingLocation.setState(location.getState());
            existingLocation.setUpdatedAt(LocalDateTime.now());
            return locationRepository.save(existingLocation);
        }
        return null;
    }

    @Override
    public Location getLocationById(Long id) {
        return locationRepository.findById(id).orElse(null);
    }

    @Override
    public List<Location> getAllLocations() {
       return locationRepository.findAll();
    }

    @Override
    public List<Location> getLocationsByName(String name) {
        return null;
    }

}
