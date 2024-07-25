package com.example.clickbus.service;

import com.example.clickbus.model.Location;
import com.example.clickbus.repository.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @InjectMocks
    private LocationServiceImpl locationService;

    @Mock
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createLocation() {
        Location location = new Location();
        location.setName("Test Location");

        when(locationRepository.save(any(Location.class))).thenReturn(location);

        Location createdLocation = locationService.createLocation(location);

        assertNotNull(createdLocation);
        assertEquals("Test Location", createdLocation.getName());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void updateLocation() {
        Location existingLocation = new Location();
        existingLocation.setId(1L);
        existingLocation.setName("Existing Location");

        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(existingLocation));
        when(locationRepository.save(any(Location.class))).thenReturn(existingLocation);

        Location updatedLocation = locationService.updateLocation(1L, existingLocation);

        assertNotNull(updatedLocation);
        assertEquals("Existing Location", updatedLocation.getName());
        verify(locationRepository, times(1)).findById(anyLong());
        verify(locationRepository, times(1)).save(any(Location.class));
    }

    @Test
    void getLocationById() {
        Location location = new Location();
        location.setId(1L);

        when(locationRepository.findById(anyLong())).thenReturn(Optional.of(location));

        Location foundLocation = locationService.getLocationById(1L);

        assertNotNull(foundLocation);
        assertEquals(1L, foundLocation.getId());
        verify(locationRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllLocations() {
        Location location1 = new Location();
        Location location2 = new Location();
        List<Location> locations = Arrays.asList(location1, location2);

        when(locationRepository.findAll()).thenReturn(locations);

        List<Location> allLocations = locationService.getAllLocations();

        assertNotNull(allLocations);
        assertEquals(2, allLocations.size());
        verify(locationRepository, times(1)).findAll();
    }

    @Test
    void getLocationsByName() {
        Location location1 = new Location();
        Location location2 = new Location();
        List<Location> locations = Arrays.asList(location1, location2);

        when(locationRepository.findByNameContaining(anyString())).thenReturn(locations);

        List<Location> foundLocations = locationService.getLocationsByName("Test");

        assertNotNull(foundLocations);
        assertEquals(2, foundLocations.size());
        verify(locationRepository, times(1)).findByNameContaining(anyString());
    }
}
