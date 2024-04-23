package be;

public class Personal {
    private String username;
    private String password;
    private int id;
    private int roleId;
    private String role;
    private double salary;
    private String picture;


    public Personal(int id, String username, String password, int roleId, String role, double salary, String picture) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.roleId = roleId;
        this.role = role;
        this.salary = salary;
        this.picture = picture;
    }
    public Personal () {

    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
