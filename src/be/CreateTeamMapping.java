package be;

import java.util.Objects;

/**
 * Represents a mapping request to create a team mapping for personnel.
 */
public class CreateTeamMapping {
    private int personnelId;
    private int teamId;
    private int mappingId;

    /**
     * Constructs a CreateTeamMapping object with the specified personnel ID, team ID, and mapping ID.
     * @param personnelId The ID of the personnel.
     * @param teamId The ID of the team.
     * @param mappingId The ID of the mapping.
     */
    public CreateTeamMapping(int personnelId, int teamId, int mappingId) {
        this.personnelId = personnelId;
        this.teamId = teamId;
        this.mappingId = mappingId;
    }

    /**
     * Default constructor.
     */
    public CreateTeamMapping() {}

    /**
     * Retrieves the personnel ID.
     * @return The personnel ID.
     */
    public int getPersonnelId() {
        return personnelId;
    }

    /**
     * Sets the personnel ID.
     * @param personnelId The personnel ID to set.
     */
    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }

    /**
     * Retrieves the team ID.
     * @return The team ID.
     */
    public int getTeamId() {
        return teamId;
    }

    /**
     * Sets the team ID.
     * @param teamId The team ID to set.
     */
    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    /**
     * Retrieves the mapping ID.
     * @return The mapping ID.
     */
    public int getMappingId() {
        return mappingId;
    }

    /**
     * Sets the mapping ID.
     * @param mappingId The mapping ID to set.
     */
    public void setMappingId(int mappingId) {
        this.mappingId = mappingId;
    }

    /**
     * Compares this CreateTeamMapping object to another object for equality.
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreateTeamMapping that = (CreateTeamMapping) o;
        return personnelId == that.personnelId &&
                teamId == that.teamId &&
                mappingId == that.mappingId;
    }

    /**
     * Generates a hash code for this CreateTeamMapping object.
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(personnelId, teamId, mappingId);
    }

    /**
     * Returns a string representation of this CreateTeamMapping object.
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "CreateTeamMapping{" +
                "personnelId=" + personnelId +
                ", teamId=" + teamId +
                ", mappingId=" + mappingId +
                '}';
    }
}
