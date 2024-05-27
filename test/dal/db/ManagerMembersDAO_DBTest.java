package dal.db;

import be.ManagerMembers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ManagerMembersDAO_DBTest {

    private ManagerMembersDAO_DB managerMembersDAO;

    @BeforeEach
    void setUp() {
        try {
            managerMembersDAO = new ManagerMembersDAO_DB();
        } catch (IOException e) {
            fail("Failed to initialize ManagerMembersDAO_DB");
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getManagerMembers() {
        try {
            List<ManagerMembers> allManagerMembers = managerMembersDAO.getManagerMembers();
            assertNotNull(allManagerMembers);
            assertFalse(allManagerMembers.isEmpty());
        } catch (DataAccessException e) {
            fail("Failed to retrieve all manager members data: " + e.getMessage());
        }
    }

    @Test
    void deleteManagerMember() {
        try {
            List<ManagerMembers> allManagerMembers = managerMembersDAO.getManagerMembers();
            if (!allManagerMembers.isEmpty()) {
                ManagerMembers managerMemberToDelete = allManagerMembers.get(0);
                managerMembersDAO.deleteManagerMember(managerMemberToDelete);
                allManagerMembers = managerMembersDAO.getManagerMembers();
                assertFalse(allManagerMembers.contains(managerMemberToDelete));
            } else {
                fail("No manager member record found for deletion");
            }
        } catch (Exception e) {
            fail("Failed to delete manager member: " + e.getMessage());
        }
    }
}
