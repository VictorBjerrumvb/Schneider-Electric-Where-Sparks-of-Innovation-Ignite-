package gui.model;

import be.Personnel;
import bll.PersonnelManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class for managing Personnel data in the GUI.
 */
public class PersonnelModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonnelModel.class);
    private ObservableList<Personnel> allPersonnel;
    private PersonnelManager personnelManager;
    private Map<Integer, Personnel> personnelMap = new HashMap<>();

    /**
     * Constructs a new PersonnelModel instance.
     */
    public PersonnelModel() {
        try {
            personnelManager = new PersonnelManager();
            allPersonnel = FXCollections.observableArrayList();
            allPersonnel.addAll(personnelManager.getAllPersonnel());
            preprocessPersonnel(allPersonnel);
        } catch (Exception e) {
            LOGGER.error("Failed to load Personnel data: {}", e.getMessage());
        }
    }

    /**
     * Retrieves the observable list of all Personnel data.
     *
     * @return The observable list of Personnel data.
     */
    public ObservableList<Personnel> getAllPersonnel() {
        return allPersonnel;
    }

    /**
     * Retrieves a Personnel object by its ID.
     *
     * @param id The ID of the Personnel to retrieve.
     * @return The Personnel object if found, otherwise null.
     */
    public Personnel getPersonnelById(int id) {
        return personnelMap.get(id);
    }

    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param personnel The Personnel data to delete.
     */
    public void deletePersonnel(Personnel personnel) {
        try {
            personnelManager.deletePersonnel(personnel);
            allPersonnel.remove(personnel);
        } catch (Exception e) {
            LOGGER.error("Failed to delete Personnel: {}", e.getMessage());
        }
    }

    /**
     * Creates new Personnel data using the provided information and adds it to the manager and the observable list.
     *
     * @param personnel The Personnel data to create.
     */
    public void createPersonnel(Personnel personnel) {
        try {
            Personnel p = personnelManager.createPersonnel(personnel);
            if (p != null) {
                allPersonnel.add(p);
                personnelMap.put(p.getId(), p);
            } else {
                LOGGER.error("Failed to add created Personnel to the observable list.");
            }
        } catch (Exception e) {
            LOGGER.error("Failed to create Personnel: {}", e.getMessage());
        }
    }

    /**
     * Updates the specified Personnel data using the provided information.
     *
     * @param personnel The Personnel data to update.
     */
    public void updatePersonnel(Personnel personnel) {
        try {
            personnelManager.updatePersonnel(personnel);
            allPersonnel.clear();
            allPersonnel.addAll(personnelManager.getAllPersonnel());
        } catch (Exception e) {
            LOGGER.error("Failed to update Personnel: {}", e.getMessage());
        }
    }

    private void preprocessPersonnel(List<Personnel> allPersonnel) {
        for (Personnel p : allPersonnel) {
            personnelMap.put(p.getId(), p);
        }
    }
}
