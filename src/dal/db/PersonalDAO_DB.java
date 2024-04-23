package dal.db;

import be.Personal;
import bll.PersonalManager;
import dal.Interface.IPersonal;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class PersonalDAO_DB implements IPersonal {

    private DatabaseConnector databaseConnector;

    public PersonalDAO_DB() throws IOException {
        databaseConnector = new DatabaseConnector();
    }

    @Override
    public List<Personal> getAllPersonal() throws Exception {
        return null;
    }

    @Override
    public Personal createPersonal(Personal personal) throws Exception {
        return null;
    }

    @Override
    public Personal deletePersonal(Personal personal) throws Exception {
        return null;
    }

    @Override
    public Personal updatePersonal(Personal personal) throws Exception {
        return null;
    }

    public Personal validateUser(String personalName, String personalPassword){
        Personal personal = null; // starts the user as null
        /**
         * get the userid from the user that is trying to log in, and is checking for
         * if the password  matches that user.
         */ // "SELECT * FROM FuckEASVBar.dbo.Worker
        String sql = "SELECT * FROM FuckEASVBar.dbo.Worker where PersonalName = ? and PersonalPassword = ?";

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
