package gui.model;

import be.Personnel;
import bll.PersonnelManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class for managing Personnel data in the GUI.
 */
public class PersonnelModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonnelModel.class);
    private ObservableList<Personnel> allPersonnel;
    private PersonnelManager personnelManager;

    /**
     * Constructs a new PersonnelModel instance.
     */
    public PersonnelModel() {
        try {
            personnelManager = new PersonnelManager();
            allPersonnel = FXCollections.observableArrayList();
            // Fetch all Personnel data from the manager and add it to the observable list
            allPersonnel.addAll(personnelManager.getAllPersonnel());
        } catch (Exception e) {
            // Log and handle any exceptions that occur during initialization
            LOGGER.error("Failed to load Personnel data: {}", e.getMessage());
            // Handle exception appropriately, perhaps notify the user
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
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param personnel The Personnel data to delete.
     */
    public void deletePersonnel(Personnel personnel) {
        try {
            personnelManager.deletePersonnel(personnel);
            allPersonnel.remove(personnel);
        } catch (Exception e) {
            // Log and handle any exceptions that occur during deletion
            LOGGER.error("Failed to delete Personnel: {}", e.getMessage());
            // Notify the user of the failure
        }
    }

    /**
     * Creates new Personnel data using the provided information and adds it to the manager and the observable list.
     *
     * @param personnel The Personnel data to create.
     */
    public void createPersonnel(Personnel personnel) {
        try {
            // Create the Personnel data through the manager
            Personnel p = personnelManager.createPersonnel(personnel);
            // Add the created Personnel data to the observable list
            if (p != null) {
                allPersonnel.add(p);
            } else {
                // Log and handle the case where creation succeeds but adding to the list fails
                LOGGER.error("Failed to add created Personnel to the observable list.");
                // Notify the user of the failure
            }
        } catch (Exception e) {
            // Log and handle any exceptions that occur during creation
            LOGGER.error("Failed to create Personnel: {}", e.getMessage());
            // Notify the user of the failure
        }
    }

    /**
     * Updates the specified Personnel data using the provided information.
     *
     * @param personnel The Personnel data to update.
     */
    public void updatePersonnel(Personnel personnel) {
        try {
            // Update the Personnel data through the manager
            personnelManager.updatePersonnel(personnel);
            // Personnel data is updated in the manager, but we assume it reflects in the list
        } catch (Exception e) {
            // Log and handle any exceptions that occur during update
            LOGGER.error("Failed to update Personnel: {}", e.getMessage());
            // Notify the user of the failure
        }
    }
}
