package sample.models;

import sample.database.AppDatabase;

import java.util.Comparator;
import java.util.List;

public class Player {

    //static
    public static final String P_FW = "FW", P_MID = "MID", P_DEF = "DEF", P_GK = "GK";

    //fields
    private String pictureId;
    private final String firstName, lastName, nationality, position;
    private int playerNumber, appearance, cleanSheet, goals, assists, points;
    private float price;
    private String pld;
    private String clubName;
    private Club club;
    private boolean isSelected;

    public static List<Player> getRawPlayers() {
        rawPlayers = AppDatabase.getInstance().getAllPlayers();
        selectedPlayers = AppDatabase.getInstance().getAllSelectedPlayers();

        rawPlayers.forEach(player -> {
            player.setClubObject();
        });
        selectedPlayers.forEach(player -> {
            player.setClubObject();
        });
        rawPlayers.removeAll(selectedPlayers);

        return rawPlayers;
    }

    public static List<Player> rawPlayers;
    public static List<Player> selectedPlayers;

    public Player(String firstName, String lastName, String nationality, String position) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.position = position;
    }

    public Player(String pictureId, String firstName, String lastName, String nationality, String position,
                  int playerNumber, String pld, int appearance, int cleanSheet, int goals, int assists, int points, float price,
                  boolean isSelected, String clubName) {
        this.pictureId = pictureId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.nationality = nationality;
        this.position = position;
        this.playerNumber = playerNumber;
        this.appearance = appearance;
        this.cleanSheet = cleanSheet;
        this.goals = goals;
        this.assists = assists;
        this.points = points;
        this.price = price;
        this.pld = pld;
        this.isSelected = isSelected;
        this.clubName = clubName;
    }


    @Override
    public String toString() {
        return String.format("[Name: %s %s - Club: %s - Position: %s - Number: %d - Nationality: %s\n PLD: %s - Appearance: %d - CleanSheets: %d - Goals: %d - Assists: %d - Points: %d\n Price: %.2f - PicID: %s]",
                getFirstName(), getLastName(), getClubName(), getPosition(), getPlayerNumber(), getNationality(), getPld(), getAppearance(), getCleanSheet(), getGoals(), getAssists(), getPoints(), getPrice(), getPictureId());
    }


    public void setClubObject() {
        List<Club> list = AppDatabase.getInstance().getAllClubs();
        for (Club Currentclub : list) {
            String clubObjectName = Currentclub.getClubName();
            if (clubName.equals(clubObjectName)) {
                club = Currentclub;
                break;
            }
        }
    }

    /*
        Compare
     */
    public static final Comparator<Player> BY_CLUB = Comparator.comparing(Player::getClubName);
    public static final Comparator<Player> BY_POSITION = Comparator.comparing(Player::getPosition);
    public static final Comparator<Player> BY_PRICE = Comparator.comparingDouble(Player::getPrice);
    public static final Comparator<Player> BY_NAME = Comparator.comparing(Player::getFirstName);


    /*
        Getters & Setters
     */

    public String getPictureId() {
        return pictureId;
    }

    public void setPictureId(String pictureId) {
        this.pictureId = pictureId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {return firstName + " " + lastName; }

    public String getSearchableName() {return firstName + lastName; }

    public String getNationality() {
        return nationality;
    }

    public String getPosition() {
        return position;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public int getAppearance() {
        return appearance;
    }

    public void setAppearance(int appearance) {
        this.appearance = appearance;
    }

    public int getCleanSheet() {
        return cleanSheet;
    }

    public void setCleanSheet(int cleanSheet) {
        this.cleanSheet = cleanSheet;
    }

    public int getGoals() {
        return goals;
    }

    public void setGoals(int goals) {
        this.goals = goals;
    }

    public int getAssists() {
        return assists;
    }

    public void setAssists(int assists) {
        this.assists = assists;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }


    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getPld() {
        return pld;
    }

    public void setPld(String pld) {
        this.pld = pld;
    }

    public Club getClub() {
        return club;
    }


    //

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Player){
            Player p = (Player) obj;
            if (getFullName().equals(p.getFullName()) && getClubName().equals(p.getClubName()) && getPosition().equals(p.getPosition()))
                return true;
        }
        return false;
    }
}
