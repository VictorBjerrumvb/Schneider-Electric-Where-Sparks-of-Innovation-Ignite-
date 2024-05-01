package be;

import javax.persistence.*;

/**
 * Represents a mapping between a Personnel profile and a team.
 */
@Entity
@Table(name = "Personnel_team_mapping")
public class PersonnelTeamMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int mappingId;

    @ManyToOne
    @JoinColumn(name = "Personnel_id")
    private Personnel personnel;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Constructors

    /**
     * Constructs a Personnel-team mapping with default values.
     */
    public PersonnelTeamMapping() {
    }

    /**
     * Constructs a Personnel-team mapping with the specified Personnel profile and team.
     * @param personnel The Personnel profile to map.
     * @param team The team to map to.
     */
    public PersonnelTeamMapping(Personnel personnel, Team team) {
        this.personnel = personnel;
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
     * Retrieves the Personnel profile associated with this mapping.
     * @return The Personnel profile.
     */
    public Personnel getPersonnel() {
        return personnel;
    }

    /**
     * Sets the Personnel profile associated with this mapping.
     * @param personnel The Personnel profile to set.
     */
    public void setPersonnel(Personnel personnel) {
        this.personnel = personnel;
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
