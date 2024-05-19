package be;

import javax.persistence.*;
import java.util.Objects;

/**
 * Represents an employee profile with relevant information for rate calculation.
 */
@Entity
@Table(name = "employee_profile")
public class EmployeeProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private double annualSalary;

    @Column(nullable = false)
    private double overheadMultiplier;

    @Column(nullable = false)
    private double fixedAnnualAmount;

    @ManyToOne
    @JoinColumn(name = "geography_id")
    private Geography geography;

    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    @Column(nullable = false)
    private double annualEffectiveWorkingHours;

    @Column(nullable = false)
    private double utilizationPercentage;

    @Column(nullable = false)
    private boolean isOverhead;

    // Constructors

    /**
     * Default constructor.
     */
    public EmployeeProfile() {
    }

    /**
     * Parameterized constructor to initialize an employee profile with provided values.
     *
     * @param annualSalary              The annual salary of the employee.
     * @param overheadMultiplier        The overhead multiplier.
     * @param fixedAnnualAmount         The fixed annual amount.
     * @param geography                 The geography or country of the employee.
     * @param team                      The team the employee belongs to.
     * @param annualEffectiveWorkingHours The annual effective working hours of the employee.
     * @param utilizationPercentage     The utilization percentage of the employee.
     * @param isOverhead                Indicates if the employee is considered an overhead cost.
     */
    public EmployeeProfile(double annualSalary, double overheadMultiplier, double fixedAnnualAmount,
                           Geography geography, Team team, double annualEffectiveWorkingHours,
                           double utilizationPercentage, boolean isOverhead) {
        this.annualSalary = annualSalary;
        this.overheadMultiplier = overheadMultiplier;
        this.fixedAnnualAmount = fixedAnnualAmount;
        this.geography = geography;
        this.team = team;
        this.annualEffectiveWorkingHours = annualEffectiveWorkingHours;
        this.utilizationPercentage = utilizationPercentage;
        this.isOverhead = isOverhead;
    }

    /**
     * Calculates the hourly rate based on the employee's profile.
     *
     * @return The calculated hourly rate.
     */
    public double calculateHourlyRate() {
        double baseRate = (annualSalary + fixedAnnualAmount) / annualEffectiveWorkingHours;
        baseRate *= (1 + overheadMultiplier / 100.0);
        baseRate *= (utilizationPercentage / 100.0);
        return baseRate;
    }

    /**
     * Calculates the day rate based on the employee's profile.
     *
     * @return The calculated day rate.
     */
    public double calculateDayRate() {
        return calculateHourlyRate() * 8; // Assuming 8 hours per day
    }

    /**
     * Compares this employee profile to another object for equality.
     *
     * @param o The object to compare to.
     * @return True if the objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeProfile that = (EmployeeProfile) o;
        return id == that.id &&
                Double.compare(that.annualSalary, annualSalary) == 0 &&
                Double.compare(that.overheadMultiplier, overheadMultiplier) == 0 &&
                Double.compare(that.fixedAnnualAmount, fixedAnnualAmount) == 0 &&
                Double.compare(that.annualEffectiveWorkingHours, annualEffectiveWorkingHours) == 0 &&
                Double.compare(that.utilizationPercentage, utilizationPercentage) == 0 &&
                isOverhead == that.isOverhead &&
                Objects.equals(geography, that.geography) &&
                Objects.equals(team, that.team);
    }

    /**
     * Generates a hash code for this employee profile.
     *
     * @return The hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, annualSalary, overheadMultiplier, fixedAnnualAmount, geography, team, annualEffectiveWorkingHours, utilizationPercentage, isOverhead);
    }

    // ToString

    /**
     * Returns a string representation of this employee profile.
     *
     * @return The string representation.
     */
    @Override
    public String toString() {
        return "EmployeeProfile{" +
                "id=" + id +
                ", annualSalary=" + annualSalary +
                ", overheadMultiplier=" + overheadMultiplier +
                ", fixedAnnualAmount=" + fixedAnnualAmount +
                ", geography=" + geography +
                ", team=" + team +
                ", annualEffectiveWorkingHours=" + annualEffectiveWorkingHours +
                ", utilizationPercentage=" + utilizationPercentage +
                ", isOverhead=" + isOverhead +
                '}';
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAnnualSalary() {
        return annualSalary;
    }

    public void setAnnualSalary(double annualSalary) {
        this.annualSalary = annualSalary;
    }

    public double getOverheadMultiplier() {
        return overheadMultiplier;
    }

    public void setOverheadMultiplier(double overheadMultiplier) {
        this.overheadMultiplier = overheadMultiplier;
    }

    public double getFixedAnnualAmount() {
        return fixedAnnualAmount;
    }

    public void setFixedAnnualAmount(double fixedAnnualAmount) {
        this.fixedAnnualAmount = fixedAnnualAmount;
    }

    public Geography getGeography() {
        return geography;
    }

    public void setGeography(Geography geography) {
        this.geography = geography;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public double getAnnualEffectiveWorkingHours() {
        return annualEffectiveWorkingHours;
    }

    public void setAnnualEffectiveWorkingHours(double annualEffectiveWorkingHours) {
        this.annualEffectiveWorkingHours = annualEffectiveWorkingHours;
    }

    public double getUtilizationPercentage() {
        return utilizationPercentage;
    }

    public void setUtilizationPercentage(double utilizationPercentage) {
        this.utilizationPercentage = utilizationPercentage;
    }

    public boolean isOverhead() {
        return isOverhead;
    }

    public void setOverhead(boolean overhead) {
        isOverhead = overhead;
    }
}
