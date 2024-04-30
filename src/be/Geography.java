package be;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

/**
 * Represents a geographical location.
 */
@Entity
@Table(name = "geography")
public class Geography {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String country;

    @OneToMany(mappedBy = "geography")
    private Set<Personal> residents;

    // Constructors

    /**
     * Default constructor.
     */
    public Geography() {
    }

    /**
     * Constructor to initialize a geography object with the given country.
     * @param country The name of the country.
     */
    public Geography(String country) {
        this.country = country;
    }

    /**
     * Constructor to initialize a geography object with the given id and country.
     * @param id The id of the geography.
     * @param name The name of the country.
     */
    public Geography(int id, String name) {
    }

    // Getters and Setters

    /**
     * Retrieves the id of the geography.
     * @return The id.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the geography.
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the country.
     * @return The country name.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets the name of the country.
     * @param country The country name to set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Retrieves the set of residents associated with this geography.
     * @return The set of residents.
     */
    public Set<Personal> getResidents() {
        return residents;
    }

    /**
     * Sets the set of residents associated with this geography.
     * @param residents The set of residents to set.
     */
    public void setResidents(Set<Personal> residents) {
        this.residents = residents;
    }

    // Equals and HashCode

    /**
     * Checks if this geography is equal to another object.
     * @param o The object to compare to.
     * @return True if equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Geography geography = (Geography) o;
        return id == geography.id && Objects.equals(country, geography.country);
    }

    /**
     * Generates a hash code for this geography.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, country);
    }

    // ToString

    /**
     * Returns a string representation of this geography.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "Geography{" +
                "id=" + id +
                ", country='" + country + '\'' +
                '}';
    }

    /**
     * Retrieves the name of the geography.
     * @return The name of the geography.
     */
    public String getName() {
        return country;
    }
}
