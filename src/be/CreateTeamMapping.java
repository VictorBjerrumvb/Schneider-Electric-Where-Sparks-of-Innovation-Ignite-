package be;

public class CreateTeamMapping {
    private int personnelId;
    private int teamId;
    private int mappingId;

    public CreateTeamMapping(int personnelId, int teamId, int mappingId) {
        this.personnelId = personnelId;
        this.teamId = teamId;
        this.mappingId = mappingId;
    }

    public CreateTeamMapping() {}

    public int getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getMappingId() {
        return mappingId;
    }

    public void setMappingId(int mappingId) {
        this.mappingId = mappingId;
    }
}
