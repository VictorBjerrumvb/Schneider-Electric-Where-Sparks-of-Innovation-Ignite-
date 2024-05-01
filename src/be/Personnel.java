package be;

import org.mindrot.jbcrypt.BCrypt;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents a Personnel profile of an employee.
 */
@Entity
public class Personnel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private int roleId;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private double salary;

    @Column(nullable = false)
    private String picture;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "geography_id")
    private Geography geography;

    // Constructors

    /**
     * Constructs a Personnel profile object with the specified attributes.
     * @param id The ID of the Personnel profile.
     * @param username The username of the employee.
     * @param password The password of the employee.
     * @param roleId The ID of the role.
     * @param role The role of the employee.
     * @param salary The salary of the employee.
     * @param picture The picture of the employee.
     */
    public Personnel(int id, String username, String password, int roleId, String role, double salary, String picture) {
        this.id = id;
        this.username = validate(username, "Username must not be empty");
        this.password = BCrypt.hashpw(password, BCrypt.gensalt());
        this.roleId = roleId;
        this.role = role;
        this.salary = salary;
        this.picture = picture;
    }

    /**
     * Default constructor.
     */
    public Personnel() {
    }

    // Utility Methods

    /**
     * Hashes the password using BCrypt.
     */
    public void hashPassword() {
        this.password = BCrypt.hashpw(this.password, BCrypt.gensalt());
    }

    private String validate(String value, String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    // Getters and Setters

    /**
     * Retrieves the salary of the employee.
     * @return The salary.
     */
    public double getSalary() {
        return salary;
    }

    /**
     * Sets the salary of the employee.
     * @param salary The salary to set.
     */
    public void setSalary(double salary) {
        this.salary = salary;
    }

    /**
     * Retrieves the role of the employee.
     * @return The role.
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets the role of the employee.
     * @param role The role to set.
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Retrieves the ID of the role.
     * @return The role ID.
     */
    public int getRoleId() {
        return roleId;
    }

    /**
     * Sets the ID of the role.
     * @param roleId The role ID to set.
     */
    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    /**
     * Retrieves the ID of the Personnel profile.
     * @return The ID.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the ID of the Personnel profile.
     * @param id The ID to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the password of the employee.
     * @return The password.
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the employee.
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Retrieves the username of the employee.
     * @return The username.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username of the employee.
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Retrieves the picture of the employee.
     * @return The picture.
     */
    public String getPicture() {
        return picture;
    }

    /**
     * Sets the picture of the employee.
     * @param picture The picture to set.
     */
    public void setPicture(String picture) {
        this.picture = picture;
    }

    // Equals and HashCode

    /**
     * Checks if this Personnel profile is equal to another object.
     * @param o The object to compare to.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Personnel personnel = (Personnel) o;
        return id == personnel.id;
    }

    /**
     * Generates a hash code for this Personnel profile.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
