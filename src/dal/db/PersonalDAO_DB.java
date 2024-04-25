package dal.db;

import be.Personal;
import bll.PersonalManager;
import dal.Interface.IPersonal;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonalDAO_DB implements IPersonal {

    private DatabaseConnector databaseConnector;

    public PersonalDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Personal> getAllPersonal() throws Exception {

        ArrayList<Personal> allPersonal = new ArrayList<>();

        try (Connection conn = databaseConnector.getConnection();
             Statement stmt = conn.createStatement())
        {
            String sql = "SELECT * FROM SparksExamSchneider.dbo.Personal";
            ResultSet rs = stmt.executeQuery(sql);

            // Loop through rows from the database result set
            while (rs.next()) {

                // Map DB row to Song object
                int id = rs.getInt("PersonalId");
                int roleId = rs.getInt("PersonalRoleId");
                String role = rs.getString("PersonalRole");
                double salary = rs.getDouble("PersonalSalary");
                String picture = rs.getString("PersonalPicture");
                String personalName = rs.getString("PersonalName");
                String personalPassword = rs.getString("PersonalPassword");


                Personal personal = new Personal(id, personalName, personalPassword, roleId, role, salary, picture);
                allPersonal.add(personal);
            }
            return allPersonal;
        }
        catch (SQLException ex){
            ex.printStackTrace();
            throw new Exception("Could not get Personal from database", ex);
        }
    }

    @Override
    public Personal createPersonal(Personal personal) throws Exception {
        String sql = "INSERT INTO SparksExamSchneider.dbo.Personal (PersonalName, PersonalPassword, PersonalRole, PersonalRoleId, PersonalSalary) VALUES (?,?,?,?,?);";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS))
        {
            //bind our parameters
            stmt.setString(1,personal.getUsername());
            stmt.setString(2,personal.getPassword());
            stmt.setString(3,personal.getRole());
            stmt.setInt(4,personal.getRoleId());
            stmt.setDouble(5, personal.getSalary());

            // Run the specified SQL Statement
            stmt.executeUpdate();

            // Get the generated ID from the DB
            ResultSet rs = stmt.getGeneratedKeys();
            int id = 0;

            if (rs.next()) {
                id = rs.getInt(1);
            }

            // Create song object and send up the layers
            Personal createdPersonal = new Personal(id, personal.getUsername(), personal.getPassword(), personal.getRole(), personal.getRoleId(), personal.getSalary());

            return createdPersonal;
        }
        catch (SQLException ex) {
            ex.printStackTrace();
            throw new Exception("Could not add Personal", ex);
        }
    }

    @Override
    public Personal deletePersonal(Personal personal) throws Exception {

        String deleteSql="DELETE FROM SparksExamSchneider.dbo.Personal WHERE PersonalId = ?;";
        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement deleteStmt = conn.prepareStatement(deleteSql))
        {
            deleteStmt.setInt(1,personal.getId());
            deleteStmt.executeUpdate();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
            throw new Exception("Could not delete Personal", ex);
        }
        return personal;
    }

    @Override
    public Personal updatePersonal(Personal personal) throws Exception {
        String sql = "UPDATE SparksExamSchneider.dbo.Personal " +
                "SET PersonalName = ?, PersonalPassword = ?, PersonalRole = ?, PersonalRoleId = ?, PersonalPicture = ?, PersonalSalary = ? "+ // Removed trailing comma
                "WHERE PersonalId = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,personal.getUsername());
            stmt.setString(2,personal.getPassword());
            stmt.setString(3,personal.getRole());
            stmt.setInt(4,personal.getRoleId());
            stmt.setString(5, personal.getPicture());
            stmt.setDouble(6, personal.getSalary());
            stmt.setInt(7, personal.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw  new Exception("Could not Update Personal");
        }
        return personal;
    }

    public Personal validateUser(String personalName, String personalPassword){
        Personal personal = null; // starts the user as null
        /**
         * get the userid from the user that is trying to log in, and is checking for
         * if the password  matches that user.
         */ // "SELECT * FROM FuckEASVBar.dbo.Worker
        String sql = "SELECT * FROM SparksExamSchneider.dbo.Personal where PersonalName = ? and PersonalPassword = ?";

        try (Connection conn = databaseConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){

            stmt.setString(1, personalName);
            stmt.setString(2, personalPassword);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()){
                int id = rs.getInt("PersonalId");
                int roleId = rs.getInt("PersonalRoleId");
                String role = rs.getString("PersonalRole");
                double salary = rs.getDouble("PersonalSalary");
                String picture = rs.getString("PersonalPicture");
                personal = new Personal(id, personalName, personalPassword, roleId, role, salary, picture);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return personal;
    }
}
