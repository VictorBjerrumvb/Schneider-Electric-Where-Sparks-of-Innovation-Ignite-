package be;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TeamTest {

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team(1, "Development Team");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        assertEquals(1, team.getId());
    }

    @Test
    void setId() {
        team.setId(2);
        assertEquals(2, team.getId());
    }

    @Test
    void getName() {
        assertEquals("Development Team", team.getName());
    }

    @Test
    void setName() {
        team.setName("Testing Team");
        assertEquals("Testing Team", team.getName());
    }
    @Test
    void setMembers() {
        team.setMembers(null);
        assertNull(team.getMembers());
    }

    @Test
    void testToString() {
        assertEquals("Development Team", team.toString());
    }
}
