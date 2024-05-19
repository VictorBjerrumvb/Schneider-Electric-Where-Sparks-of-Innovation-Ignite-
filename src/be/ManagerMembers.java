package be;

public class ManagerMembers {
    private int personnelId;
    private int managerId;
    private int teamId;

    public ManagerMembers(int personnelId, int managerId, int teamId) {
        this.personnelId = personnelId;
        this.managerId = managerId;
        this.teamId = teamId;
    }

    public ManagerMembers() {}

    public int getPersonnelId() {
        return personnelId;
    }

    public void setPersonnelId(int personnelId) {
        this.personnelId = personnelId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }
}
