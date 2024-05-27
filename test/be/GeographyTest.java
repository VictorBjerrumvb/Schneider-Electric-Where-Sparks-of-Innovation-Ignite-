package be;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GeographyTest {
    private Geography geography1;
    private Geography geography2;
    private Geography geography3;

    @BeforeEach
    void setUp() {
        geography1 = new Geography(1, "Belgium", "1000", 10.5);
        geography2 = new Geography(2, "France", "2000", 15.2);
        geography3 = new Geography(1, "Belgium", "1000", 10.5);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        assertEquals(1, geography1.getId());
    }

    @Test
    void setId() {
        geography1.setId(10);
        assertEquals(10, geography1.getId());
    }

    @Test
    void getCountry() {
        assertEquals("Belgium", geography1.getCountry());
    }

    @Test
    void setCountry() {
        geography1.setCountry("Netherlands");
        assertEquals("Netherlands", geography1.getCountry());
    }

    @Test
    void getResidents() {
        Set<Personnel> residents = new HashSet<>();
        // Add some test personnel
        geography1.setResidents(residents);
        assertEquals(residents, geography1.getResidents());
    }

    @Test
    void setResidents() {
        Set<Personnel> residents = new HashSet<>();
        // Add some test personnel
        geography1.setResidents(residents);
        assertEquals(residents, geography1.getResidents());
    }

    @Test
    void getCountryGross() {
        assertEquals("1000", geography1.getCountryGross());
    }

    @Test
    void setCountryGross() {
        geography1.setCountryGross("1500");
        assertEquals("1500", geography1.getCountryGross());
    }

    @Test
    void getCountryMargin() {
        assertEquals(10.5, geography1.getCountryMargin());
    }

    @Test
    void setCountryMargin() {
        geography1.setCountryMargin(12.3);
        assertEquals(12.3, geography1.getCountryMargin());
    }

    @Test
    void testEquals() {
        assertEquals(geography1, geography3);
        assertNotEquals(geography1, geography2);
    }

    @Test
    void testHashCode() {
        assertEquals(geography1.hashCode(), geography3.hashCode());
        assertNotEquals(geography1.hashCode(), geography2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("Belgium", geography1.toString());
    }
}
