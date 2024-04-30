package be;

import javax.persistence.*;

/**
 * Represents a mapping between a personal profile and a team.
 */
@Entity
@Table(name = "personal_team_mapping")
public class PersonalTeamMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mappingId;

    @ManyToOne
    @JoinColumn(name = "personal_id")
    private Personal personal;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Constructors

    /**
     * Constructs a personal-team mapping with default values.
     */
    public PersonalTeamMapping() {
    }

    /**
     * Constructs a personal-team mapping with the specified personal profile and team.
     * @param personal The personal profile to map.
     * @param team The team to map to.
     */
    public PersonalTeamMapping(Personal personal, Team team) {
        this.personal = personal;
        this.team = team;
    }

    // Getters and Setters

    /**
     * Retrieves the ID of the mapping.
     * @return The mapping ID.
     */
    public int getMappingId() {
        return mappingId;
    }

    /**
     * Sets the ID of the mapping.
     * @param mappingId The mapping ID to set.
     */
    public void setMappingId(int mappingId) {
        this.mappingId = mappingId;
    }

    /**
     * Retrieves the personal profile associated with this mapping.
     * @return The personal profile.
     */
    public Personal getPersonal() {
        return personal;
    }

    /**
     * Sets the personal profile associated with this mapping.
     * @param personal The personal profile to set.
     */
    public void setPersonal(Personal personal) {
        this.personal = personal;
    }

    /**
     * Retrieves the team associated with this mapping.
     * @return The team.
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets the team associated with this mapping.
     * @param team The team to set.
     */
    public void setTeam(Team team) {
        this.team = team;
    }
}
