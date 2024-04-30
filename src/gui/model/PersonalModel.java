package gui.model;

import be.Personal;
import bll.PersonalManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Model class for managing personal data in the GUI.
 */
public class PersonalModel {
    private static final Logger LOGGER = LoggerFactory.getLogger(PersonalModel.class);
    private ObservableList<Personal> allPersonal;
    private PersonalManager personalManager;

    /**
     * Constructs a new PersonalModel instance.
     */
    public PersonalModel() {
        try {
            personalManager = new PersonalManager();
            allPersonal = FXCollections.observableArrayList();
            // Fetch all personal data from the manager and add it to the observable list
            allPersonal.addAll(personalManager.getAllPersonal());
        } catch (Exception e) {
            // Log and handle any exceptions that occur during initialization
            LOGGER.error("Failed to load personal data: {}", e.getMessage());
            // Handle exception appropriately, perhaps notify the user
        }
    }

    /**
     * Retrieves the observable list of all personal data.
     *
     * @return The observable list of personal data.
     */
    public ObservableList<Personal> getAllPersonal() {
        return allPersonal;
    }

    /**
     * Deletes the specified personal data from both the manager and the observable list.
     *
     * @param personal The personal data to delete.
     */
    public void deletePersonal(Personal personal) {
        try {
            personalManager.deletePersonal(personal);
            allPersonal.remove(personal);
        } catch (Exception e) {
            // Log and handle any exceptions that occur during deletion
            LOGGER.error("Failed to delete personal: {}", e.getMessage());
            // Notify the user of the failure
        }
    }

    /**
     * Creates new personal data using the provided information and adds it to the manager and the observable list.
     *
     * @param personal The personal data to create.
     */
    public void createPersonal(Personal personal) {
        try {
            // Create the personal data through the manager
            Personal p = personalManager.createPersonal(personal);
            // Add the created personal data to the observable list
            allPersonal.add(p);
        } catch (Exception e) {
            // Log and handle any exceptions that occur during creation
            LOGGER.error("Failed to create personal: {}", e.getMessage());
            // Notify the user of the failure
        }
    }

    /**
     * Updates the specified personal data using the provided information.
     *
     * @param personal The personal data to update.
     */
    public void updatePersonal(Personal personal) {
        try {
            // Update the personal data through the manager
            personalManager.updatePersonal(personal);
        } catch (Exception e) {
            // Log and handle any exceptions that occur during update
            LOGGER.error("Failed to update personal: {}", e.getMessage());
            // Notify the user of the failure
        }
    }
}
