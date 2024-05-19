package dal.db;

import be.CreateTeamMapping;
import be.ManagerMembers;
import be.Personnel;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) class for performing database operations related to Team entities.
 */
public class ManagerMembersDAO_DB {

    // DatabaseConnector instance for establishing database connections
    private final DatabaseConnector databaseConnector;

    /**
     * Constructs a new TeamDAO_DB object and initializes the DatabaseConnector.
     *
     * @throws IOException if an I/O error occurs when initializing the DatabaseConnector.
     */
    public ManagerMembersDAO_DB() throws IOException {
        try {
            databaseConnector = new DatabaseConnector();
        } catch (IOException ex) {
            throw ex;
        }
    }

    /**
     * Retrieves a Team from the database based on its ID.
     *
     * @return the retrieved Team object, or null if no Team is found with the given ID.
     * @throws DataAccessException if an error occurs while fetching the Team from the database.
     */
    public List<ManagerMembers> getManagerMembers() throws DataAccessException {
        List<ManagerMembers> allManagerMembers = new ArrayList<>();
        String sql = "SELECT * FROM SparksExamSchneider.dbo.ManagerMembers";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {

                // Map DB row to Song object
                int managerId = rs.getInt("ManagerId");
                int personnelId = rs.getInt("PersonnelId");
                int teamId = rs.getInt("TeamId");


                ManagerMembers managerMembers = new ManagerMembers (personnelId, managerId, teamId);
                allManagerMembers.add(managerMembers);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Error retrieving all ManagerMEmbers data.", e);
        }
        return allManagerMembers;
    }

    public ManagerMembers deleteManagerMember (ManagerMembers managerMembers) throws Exception {
        String sql = "DELETE FROM SparksExamSchneider.dbo.ManagerMembers WHERE ManagerId = ? AND PersonnelId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,managerMembers.getManagerId());
            stmt.setInt(2,managerMembers.getPersonnelId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Manager Member", ex);
        }
        return managerMembers;
    }
    public ManagerMembers deleteManagerTeam (ManagerMembers managerMembers) throws Exception {
        String sql = "DELETE FROM SparksExamSchneider.dbo.ManagerMembers WHERE ManagerId = ? AND TeamId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql))
        {
            stmt.setInt(1,managerMembers.getManagerId());
            stmt.setInt(2,managerMembers.getTeamId());
            stmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Manager Team", ex);
        }
        return managerMembers;
    }

    public ManagerMembers createManagerMember(ManagerMembers managerMembers) throws DataAccessException {
        String sql = "INSERT INTO SparksExamSchneider.dbo.ManagerMembers (ManagerId, PersonnelId, TeamId) VALUES (?,?,?);";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            // Set parameters for the prepared statement
            stmt.setInt(1, managerMembers.getManagerId());

            if (managerMembers.getPersonnelId() != 0) {
                stmt.setInt(2, managerMembers.getPersonnelId());
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);
            }

            if (managerMembers.getTeamId() != 0) {
                stmt.setInt(3, managerMembers.getTeamId());
            } else {
                stmt.setNull(3, java.sql.Types.INTEGER);
            }

            stmt.executeUpdate();

            return managerMembers;
        } catch (SQLException ex) {
            throw new DataAccessException("Could not add Manager Member/Team | Database Class", ex);
        }
    }


}
