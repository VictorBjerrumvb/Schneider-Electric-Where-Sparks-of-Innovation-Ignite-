package gui.controller;

import be.CreateTeamMapping;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class CreateTeamMappingTest {
    private CreateTeamMapping mapping1;
    private CreateTeamMapping mapping2;
    private CreateTeamMapping mapping3;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        mapping1 = new CreateTeamMapping(1, 1, 1);
        mapping2 = new CreateTeamMapping(2, 2, 2);
        mapping3 = new CreateTeamMapping(1, 1, 1);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
    }

    @org.junit.jupiter.api.Test
    void getPersonnelId() {
        assertEquals(1, mapping1.getPersonnelId());
    }

    @org.junit.jupiter.api.Test
    void setPersonnelId() {
        mapping1.setPersonnelId(10);
        assertEquals(10, mapping1.getPersonnelId());
    }

    @org.junit.jupiter.api.Test
    void getTeamId() {
        assertEquals(1, mapping1.getTeamId());
    }

    @org.junit.jupiter.api.Test
    void setTeamId() {
        mapping1.setTeamId(20);
        assertEquals(20, mapping1.getTeamId());
    }

    @org.junit.jupiter.api.Test
    void getMappingId() {
        assertEquals(1, mapping1.getMappingId());
    }

    @org.junit.jupiter.api.Test
    void setMappingId() {
        mapping1.setMappingId(30);
        assertEquals(30, mapping1.getMappingId());
    }

    @org.junit.jupiter.api.Test
    void testEquals() {
        assertEquals(mapping1, mapping3);
        assertNotEquals(mapping1, mapping2);
    }

    @org.junit.jupiter.api.Test
    void testHashCode() {
        assertEquals(mapping1.hashCode(), mapping3.hashCode());
        assertNotEquals(mapping1.hashCode(), mapping2.hashCode());
    }

    @org.junit.jupiter.api.Test
    void testToString() {
        assertEquals("CreateTeamMapping{personnelId=1, teamId=1, mappingId=1}", mapping1.toString());
    }
}
