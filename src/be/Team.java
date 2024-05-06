package be;

import javax.persistence.*;
import java.util.Set;

/**
 * Represents a team in the organization.
 */
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "team")
    private Set<Personnel> members;

    // Constructors

    /**
     * Constructs a team with the specified id and name.
     * @param id The id of the team.
     * @param name The name of the team.
     */
    public Team(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Default constructor.
     */
    public Team() {
    }

    // Getters and Setters

    /**
     * Retrieves the id of the team.
     * @return The id of the team.
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of the team.
     * @param id The id to set.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Retrieves the name of the team.
     * @return The name of the team.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the team.
     * @param name The name to set.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieves the members of the team.
     * @return A set of Personnel objects representing team members.
     */
    public Set<Personnel> getMembers() {
        return members;
    }

    /**
     * Sets the members of the team.
     * @param members A set of Personnel objects representing team members.
     */
    public void setMembers(Set<Personnel> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return name;
    }
}
