package sample.database;

import java.util.List;

import sample.models.Club;
import sample.models.Player;
import sample.models.User;

public interface DAO {

    /*
    Users
     */

    //Insert User
    void insertUser(User user);

    //Get Certain User
    User getUser(String username);

    //Get All Users
    List<User> getAllUsers();


    void confirmUser(String username, List<Player> selectedPlayers);

    /*
    Clubs
     */

    //get All Clubs
    List<Club> getAllClubs();


    /*
    Players
     */

    //get Certain Player
    Player getPlayer(String id);

    //get All Players
    List<Player> getAllPlayers();

    //get Players of Certain Position
    List<Player> getPlayersByPosition(String position);

    //get Players of Certain Club
    List<Player> getPlayersByClub(String clubName);



}
