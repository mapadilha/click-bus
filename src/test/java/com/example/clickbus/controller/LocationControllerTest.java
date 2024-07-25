package com.example.clickbus.controller;

import com.example.clickbus.model.Location;
import com.example.clickbus.service.LocationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

class LocationControllerTest {

    @InjectMocks
    private LocationController locationController;

    @Mock
    private LocationService locationService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(locationController).build();
    }

    @Test
    void createLocation() throws Exception {
        Location location = new Location();
        location.setName("Test Location");

        when(locationService.createLocation(any(Location.class))).thenReturn(location);

        mockMvc.perform(post("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Test Location\", \"slug\": \"test-location\", \"city\": \"Test City\", \"state\": \"Test State\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Location"));

        verify(locationService, times(1)).createLocation(any(Location.class));
    }

    @Test
    void updateLocation() throws Exception {
        Location location = new Location();
        location.setId(1L);
        location.setName("Updated Location");

        when(locationService.updateLocation(anyLong(), any(Location.class))).thenReturn(location);

        mockMvc.perform(put("/api/locations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\": \"Updated Location\", \"slug\": \"updated-location\", \"city\": \"Updated City\", \"state\": \"Updated State\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated Location"));

        verify(locationService, times(1)).updateLocation(anyLong(), any(Location.class));
    }

    @Test
    void getLocationById() throws Exception {
        Location location = new Location();
        location.setId(1L);
        location.setName("Test Location");

        when(locationService.getLocationById(anyLong())).thenReturn(location);

        mockMvc.perform(get("/api/locations/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Location"));

        verify(locationService, times(1)).getLocationById(anyLong());
    }

    @Test
    void getAllLocations() throws Exception {
        Location location1 = new Location();
        Location location2 = new Location();
        List<Location> locations = Arrays.asList(location1, location2);

        when(locationService.getAllLocations()).thenReturn(locations);

        mockMvc.perform(get("/api/locations")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(locationService, times(1)).getAllLocations();
    }

    @Test
    void getLocationsByName() throws Exception {
        Location location1 = new Location();
        Location location2 = new Location();
        List<Location> locations = Arrays.asList(location1, location2);

        when(locationService.getLocationsByName(anyString())).thenReturn(locations);

        mockMvc.perform(get("/api/locations/search")
                        .param("name", "Test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").exists())
                .andExpect(jsonPath("$[1]").exists());

        verify(locationService, times(1)).getLocationsByName(anyString());
    }
}

