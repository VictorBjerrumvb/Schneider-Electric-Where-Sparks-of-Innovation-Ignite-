package bll;

import be.Personal;
import dal.db.PersonalDAO_DB;

import java.io.IOException;
import java.util.List;

public class PersonalManager {
    private PersonalDAO_DB personalDAO_db;

    public PersonalManager() {
        try {
            personalDAO_db = new PersonalDAO_DB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Personal> getAllPersonal() throws Exception {
        return personalDAO_db.getAllPersonal();
    }
    public Personal createPersonal (Personal newPersonal) throws Exception {
        return personalDAO_db.createPersonal(newPersonal);
    }
    public Personal deletePersonal(Personal worker) throws Exception {
        personalDAO_db.deletePersonal(worker);
        return worker;
    }

    public Personal updatePersonal(Personal selectedPersonal) throws Exception {
        return personalDAO_db.updatePersonal(selectedPersonal);
    }


    public Personal validatePersonal(String userName, String password) throws Exception {
        return personalDAO_db.validateUser(userName, password);
    }
}
