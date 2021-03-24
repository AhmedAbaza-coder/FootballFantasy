package sample.models;

import com.sun.javafx.binding.StringFormatter;

import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import sample.database.AppDatabase;

public class User {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String gender;
    private String squadName;
    private float money;
    private boolean isNewUser;
    private List<Player> selectedPlayers;

    //Static
    private static List<User> sCurrentUsers = AppDatabase.getInstance().getAllUsers();
    private static User loggedInUser;

    //TODO (1) - UPDATE RANK, POINTS, AND MONEY LOGICS
    private int rank = 1;
    private int points = 5;


    public User() { }

    public User(String firstName, String lastName, String username, String password, String gender, String squadName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.squadName = squadName;
    }

    public User(String firstName, String lastName, String username, String password, String gender, String squadName,
                List<Player> selectedPlayers) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.squadName = squadName;
        this.selectedPlayers = selectedPlayers;
    }

    public User(User user) {
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.username = user.username;
        this.password = user.password;
        this.gender = user.gender;
        this.squadName = user.squadName;
        this.selectedPlayers = user.selectedPlayers;
    }

    /*
        Methods
     */

    //Static
    public static boolean validateUserLogin(String email, String password) {
        boolean isExist = false;
        for (User user : sCurrentUsers) {
            if (user.getUsername().equalsIgnoreCase(email)) {
                if (user.getPassword().equals(password)) {
                    loggedInUser = user;
                    isExist = true;
                    break;
                }
            }
        }
        return isExist;
    }

    public static boolean validateUserRegistration(String email, String password) {
        boolean validEmail = isValidEmail(email);
        boolean uniqueEmail = isEmailUnique(email);
        boolean validPassword = isValidPassword(password);
        return validEmail && validPassword && uniqueEmail;
    }

    public static boolean isEmailUnique(String email) {
        boolean isExist = false;
        for (User user : sCurrentUsers) {
            if (email.equalsIgnoreCase(user.getUsername())) {
                isExist = true;
                break;
            }
        }
        return !isExist;
    }

    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&-]+)@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();

    }

    private static boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    /*
        Getters & Setters
     */

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSquadName() {
        return squadName;
    }

    public void setSquadName(String squadName) {
        this.squadName = squadName;
    }

    public List<Player> getSelectedPlayers() {
        if(selectedPlayers.get(0).getClub() == null)
            selectedPlayers.forEach(player -> player.setClubObject());

        System.out.println(selectedPlayers);
        return selectedPlayers;
    }

    public void setSelectedPlayers(List<Player> selectedPlayers) {
        this.selectedPlayers = selectedPlayers;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public boolean isNewUser() {
        return squadName.equals("null");
    }

    public float getMoney() {
        return money;
    }

    public void setMoney(float money) {
        this.money = money;
    }

    public void cutPrice(float price) {
        this.money -= price;
    }
    //Static

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public static List<User> getCurrentUsers() {
        return sCurrentUsers;
    }

    public static void setCurrentUsers(List<User> currentUsers) {
        sCurrentUsers = currentUsers;
    }

    public static void logOutUser() {
        loggedInUser = null;
    }
    /*

     */

    public static final Comparator<User> BY_RANK = Comparator.comparingInt(User::getRank);

    @Override
    public String toString() {
        return String.format("[Name: %s %s - Username: %s - Password %s - SquadName: %s - Gender %s]",
                firstName, lastName, username, password, squadName, gender).toString();
    }
}
