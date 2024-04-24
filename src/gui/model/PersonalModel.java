package gui.model;

import be.Personal;
import bll.PersonalManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonalModel {
    private ObservableList<Personal> allPersonal;
    private PersonalManager personalManager;

    public PersonalModel() throws Exception {
        personalManager = new PersonalManager();
        allPersonal = FXCollections.observableArrayList();
        allPersonal.addAll(personalManager.getAllPersonal());
    }
    public ObservableList<Personal> getAllPersonal() {
        return allPersonal;
    }

    public void deletePersonal(Personal personal) throws Exception {
        Personal p = personalManager.deletePersonal(personal);
        allPersonal.remove(p);
    }

    public void createPersonal(Personal personal) throws Exception{
        Personal p = personalManager.createPersonal(personal);
        allPersonal.add(p);
    }

    public void updatePersonal(Personal personal) throws Exception {
        Personal selectedPersonal = new Personal();
        selectedPersonal.setId(personal.getId());
        selectedPersonal.setUsername(personal.getUsername());
        selectedPersonal.setRole(personal.getRole());
        selectedPersonal.setPassword(personal.getPassword());
        selectedPersonal.setRoleId(personal.getRoleId());
        selectedPersonal.setSalary(personal.getSalary());
        selectedPersonal.setPicture(personal.getPicture());

        personalManager.updatePersonal(selectedPersonal);
    }
}
