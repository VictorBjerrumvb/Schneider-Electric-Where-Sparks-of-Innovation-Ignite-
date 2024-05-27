package be;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonnelTest {

    private Personnel personnel1;
    private Personnel personnel2;
    private Personnel personnel3;

    @BeforeEach
    void setUp() {
        personnel1 = new Personnel(1, "john_doe", "password123", 1, "Employee", 50000.0, "profile.jpg");
        personnel2 = new Personnel(2, "jane_doe", "password456", 2, "Manager", 75000.0, "avatar.png");
        personnel3 = new Personnel(1, "john_doe", "password123", 1, "Employee", 50000.0, "profile.jpg");
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void hashPassword() {
        String originalPassword = personnel1.getPassword();
        personnel1.hashPassword();
        assertNotEquals(originalPassword, personnel1.getPassword());
    }

    @Test
    void getSalary() {
        assertEquals(50000.0, personnel1.getSalary());
    }

    @Test
    void setSalary() {
        personnel1.setSalary(55000.0);
        assertEquals(55000.0, personnel1.getSalary());
    }

    @Test
    void getRole() {
        assertEquals("Employee", personnel1.getRole());
    }

    @Test
    void setRole() {
        personnel1.setRole("Manager");
        assertEquals("Manager", personnel1.getRole());
    }

    @Test
    void getRoleId() {
        assertEquals(1, personnel1.getRoleId());
    }

    @Test
    void setRoleId() {
        personnel1.setRoleId(2);
        assertEquals(2, personnel1.getRoleId());
    }

    @Test
    void getId() {
        assertEquals(1, personnel1.getId());
    }

    @Test
    void setId() {
        personnel1.setId(10);
        assertEquals(10, personnel1.getId());
    }

    @Test
    void getPassword() {
        assertEquals("password123", personnel1.getPassword());
    }

    @Test
    void setPassword() {
        personnel1.setPassword("newPassword");
        assertEquals("newPassword", personnel1.getPassword());
    }

    @Test
    void getUsername() {
        assertEquals("john_doe", personnel1.getUsername());
    }

    @Test
    void setUsername() {
        personnel1.setUsername("new_username");
        assertEquals("new_username", personnel1.getUsername());
    }

    @Test
    void getPicture() {
        assertEquals("profile.jpg", personnel1.getPicture());
    }

    @Test
    void setPicture() {
        personnel1.setPicture("new_profile.jpg");
        assertEquals("new_profile.jpg", personnel1.getPicture());
    }

    @Test
    void testEquals() {
        assertEquals(personnel1, personnel3);
        assertNotEquals(personnel1, personnel2);
    }

    @Test
    void testHashCode() {
        assertEquals(personnel1.hashCode(), personnel3.hashCode());
        assertNotEquals(personnel1.hashCode(), personnel2.hashCode());
    }

    @Test
    void testToString() {
        assertEquals("john_doe | Employee", personnel1.toString());
    }
}
