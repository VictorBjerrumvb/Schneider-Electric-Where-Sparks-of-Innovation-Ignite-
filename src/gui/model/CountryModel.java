package gui.model;

import be.Geography;
import be.Team;
import bll.CountryManager;
import bll.TeamManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Model class for managing Team data in the GUI.
 */
public class CountryModel {
    private ObservableList<Geography> allGeography;
    private CountryManager countryManager;

    /**
     * Constructs a new TeamModel instance.
     */
    public CountryModel() {
        try {
            countryManager = new CountryManager();
            allGeography = FXCollections.observableArrayList();
            // Fetch all Team data from the manager and add it to the observable list
            allGeography.addAll(countryManager.getAllGeography());
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    /**
     * Retrieves the observable list of all Personnel data.
     *
     * @return The observable list of Personnel data.
     */
    public ObservableList<Geography> getAllGeography() {
        return allGeography;
    }

    /**
     * Deletes the specified Personnel data from both the manager and the observable list.
     *
     * @param geography The Personnel data to delete.
     */
    public void deleteGeography(Geography geography) {
        try {
            countryManager.deleteGeography(geography);
            allGeography.remove(geography);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Creates new Personnel data using the provided information and adds it to the manager and the observable list.
     *
     * @param geography The Personnel data to create.
     */
    public void createGeography(Geography geography) {
        try {
            // Create the Team data through the manager
            Geography g = countryManager.createGeography(geography);
            // Add the created Team data to the observable list
            allGeography.add(g);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateGeography(Geography geography) {
        try {
            countryManager.updateGeography(geography);
            allGeography.clear();
            allGeography.addAll(countryManager.getAllGeography());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void updateGeographyName(Geography geography) {
        try {
            countryManager.updateGeographyName(geography);
            allGeography.clear();
            allGeography.addAll(countryManager.getAllGeography());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
