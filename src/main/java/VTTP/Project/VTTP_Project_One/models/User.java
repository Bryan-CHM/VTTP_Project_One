package VTTP.Project.VTTP_Project_One.models;

public class User {
    private String username;
    private String password;
    private Boolean privated;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Boolean getPrivated() {
        return privated;
    }
    public void setPrivated(Boolean privated) {
        this.privated = privated;
    }
}
