package com.example.clickbus.repository;

import com.example.clickbus.model.Location;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Sql(scripts = "/data.sql")
class LocationRepositoryTest {

    @Autowired
    private LocationRepository locationRepository;

    @BeforeEach
    void setUp() {
        locationRepository.deleteAll();
        Location location1 = new Location();
        location1.setName("Test Location 1");
        location1.setSlug("test-location-1");
        location1.setCity("Test City 1");
        location1.setState("Test State 1");
        locationRepository.save(location1);

        Location location2 = new Location();
        location2.setName("Test Location 2");
        location2.setSlug("test-location-2");
        location2.setCity("Test City 2");
        location2.setState("Test State 2");
        locationRepository.save(location2);
    }

    @Test
    void findByNameContaining() {
        List<Location> locations = locationRepository.findByNameContaining("Test Location");
        assertNotNull(locations);
        assertEquals(2, locations.size());
    }

    @Test
    void findById() {
        Location location = new Location();
        location.setName("FindById Location");
        location.setSlug("findbyid-location");
        location.setCity("FindById City");
        location.setState("FindById State");
        location = locationRepository.save(location);

        Location foundLocation = locationRepository.findById(location.getId()).orElse(null);
        assertNotNull(foundLocation);
        assertEquals(location.getName(), foundLocation.getName());
    }

    @Test
    void save() {
        Location location = new Location();
        location.setName("Save Location");
        location.setSlug("save-location");
        location.setCity("Save City");
        location.setState("Save State");

        Location savedLocation = locationRepository.save(location);
        assertNotNull(savedLocation);
        assertEquals("Save Location", savedLocation.getName());
    }

    @Test
    void deleteById() {
        Location location = new Location();
        location.setName("Delete Location");
        location.setSlug("delete-location");
        location.setCity("Delete City");
        location.setState("Delete State");
        location = locationRepository.save(location);

        locationRepository.deleteById(location.getId());
        Location deletedLocation = locationRepository.findById(location.getId()).orElse(null);
        assertNull(deletedLocation);
    }

    @Test
    void findAll() {
        List<Location> locations = locationRepository.findAll();
        assertNotNull(locations);
        assertTrue(locations.size() > 0);
    }
}
