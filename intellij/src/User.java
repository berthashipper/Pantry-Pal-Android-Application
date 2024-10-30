public class User {
    private String username;
    private Pantry pantry;

    public User(String username) {
        this.username = username;
        this.pantry = new Pantry();
    }

    // Getters and Setters for username
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    // Getters for Pantry
    public Pantry getPantry() {
        return pantry;
    }

}

