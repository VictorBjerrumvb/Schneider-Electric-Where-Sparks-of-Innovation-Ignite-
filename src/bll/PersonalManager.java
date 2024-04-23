package bll;

import be.Personal;
import dal.db.PersonalDAO_DB;

import java.io.IOException;

public class PersonalManager {
    private PersonalDAO_DB personalDAO_db;

    public PersonalManager() {
        try {
            personalDAO_db = new PersonalDAO_DB();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public Personal validatePersonal(String userName, String password) throws Exception {
        return personalDAO_db.validateUser(userName, password);
    }
}
