package sample.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import sample.models.Club;
import sample.models.Player;
import sample.models.User;

public class AppDatabase implements DAO {

    public static final String CONNECTION_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    public static final String CONNECTION_CONST = "hr";
    public static final String CLASS_NAME = "oracle.jdbc.driver.OracleDriver";

    private static AppDatabase sInstance;
    private Connection connection;
    private ResultSet set;


    private AppDatabase() {
        try {
            Class.forName(CLASS_NAME);
            connection = DriverManager.getConnection(CONNECTION_URL, CONNECTION_CONST, CONNECTION_CONST);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static synchronized AppDatabase getInstance() {
        if (sInstance == null) sInstance = new AppDatabase();

        return sInstance;
    }

    public Connection getConnection() {
        return connection;
    }


    public void closeConnection() {
        try {
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
        DAO
     */

    @Override
    public void insertUser(User user) {
        String sql = "INSERT INTO USERS values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, user.getSquadName());
            statement.setString(2, user.getFirstName());
            statement.setString(3, user.getLastName());
            statement.setString(4, user.getUsername());
            statement.setString(5, user.getPassword());
            statement.setString(6, user.getGender());
            statement.setFloat(7, 100.0f);
            set = statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public User getUser(String username) {
        User user = new User();
        String sql = "SELECT * FROM USERS WHERE EMAIL = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            set = statement.executeQuery();
            while (set.next()) {
                user.setSquadName(set.getString("SQUADNAME"));
                user.setFirstName(set.getString("FNAME"));
                user.setLastName(set.getString("LNAME"));
                user.setUsername(set.getString("EMAIL"));
                user.setPassword(set.getString("USERPASSWORD"));
                user.setGender(set.getString("GENDER"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return user;
    }

    /*
     List<String> strings = new ArrayList<>();
        try {
            String sql = "select * from yahood";
            sSet = sStatement.executeQuery(sql);
            while (sSet.next()) {
                strings.add(sSet.getInt(1) + "  " + sSet.getString(2));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strings;
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM USERS";

        try {
            Statement statement = connection.createStatement();
            set = statement.executeQuery(sql);
            while (set != null && set.next()) {
                User user = new User();
                user.setSquadName(set.getString("SQUADNAME"));
                user.setFirstName(set.getString("FNAME"));
                user.setLastName(set.getString("LNAME"));
                user.setUsername(set.getString("EMAIL"));
                user.setPassword(set.getString("USERPASSWORD"));
                user.setGender(set.getString("GENDER"));
                user.setMoney(set.getFloat("MONEY"));
                if(!user.isNewUser()) user.setSelectedPlayers(getUserPlayers(user.getUsername()));
                users.add(user);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return users;
    }


    @Override
    public List<Club> getAllClubs() {
        List<Club> clubs = new ArrayList<>();
        String sql = "SELECT * FROM CLUB";
        try {
            Statement statement = connection.createStatement();
            set = statement.executeQuery(sql);
            while (set.next()) {
                Club club = new Club(set.getString(2), set.getString(1), set.getString(3));
                clubs.add(club);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return clubs;
    }

    @Override
    public Player getPlayer(String id) {
        Player player = null;
        String sql = "SELECT * FROM PLAYERS WHERE playerpicid = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, id);
            set = statement.executeQuery();
            while (set.next()) {
                boolean selection = set.getString(16) == "TRUE" ? true : false;
                player = new Player(set.getString(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getInt(6), set.getString(7),
                        set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11),
                        set.getInt(12), set.getFloat(13), selection, set.getString(14));


            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return player;
    }

    @Override
    public List<Player> getAllPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM PLAYERS";
        try {
            Statement statement = connection.prepareStatement(sql);
            set = statement.executeQuery(sql);
            while (set.next()) {
                boolean selection = set.getString(16) == "TRUE" ? true : false;
                Player player = new Player(set.getString(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getInt(6), set.getString(7),
                        set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11),
                        set.getInt(12), set.getFloat(13), selection, set.getString(14));

                players.add(player);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

    public List<Player> getAllSelectedPlayers() {
        List<Player> players = new ArrayList<>();
        String sql = "select players.* from selectedplayers, players where selectedplayers.playername = players.PLAYERPICID";
        try {
            Statement statement = connection.prepareStatement(sql);
            set = statement.executeQuery(sql);
            while (set.next()) {
                boolean selection = set.getString(16) == "TRUE" ? true : false;
                Player player = new Player(set.getString(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getInt(6), set.getString(7),
                        set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11),
                        set.getInt(12), set.getFloat(13), selection, set.getString(14));

                players.add(player);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

    public List<Player> getUserPlayers(String username) {
        List<Player> players = new ArrayList<>();
        String sql = "select players.* from selectedplayers, players where selectedplayers.playername = players.PLAYERPICID AND selectedplayers.username = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                boolean selection = set.getString(16) == "TRUE" ? true : false;
                Player player = new Player(set.getString(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getInt(6), set.getString(7),
                        set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11),
                        set.getInt(12), set.getFloat(13), selection, set.getString(14));

                players.add(player);
            }
            set.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Player> getPlayersByPosition(String position) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM PLAYERS WHERE position = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, position);
            set = statement.executeQuery();
            while (set.next()) {
                boolean selection = set.getString(16) == "TRUE" ? true : false;
                Player player = new Player(set.getString(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getInt(6), set.getString(7),
                        set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11),
                        set.getInt(12), set.getFloat(13), selection, set.getString(14));

                players.add(player);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

    @Override
    public List<Player> getPlayersByClub(String clubName) {
        List<Player> players = new ArrayList<>();
        String sql = "SELECT * FROM PLAYERS WHERE CLUBNAME = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, clubName);
            set = statement.executeQuery();
            while (set.next()) {
                boolean selection = set.getString(16) == "TRUE" ? true : false;
                Player player = new Player(set.getString(1), set.getString(2), set.getString(3),
                        set.getString(4), set.getString(5), set.getInt(6), set.getString(7),
                        set.getInt(8), set.getInt(9), set.getInt(10), set.getInt(11),
                        set.getInt(12), set.getFloat(13), selection, set.getString(14));

//                player.setClubObject(set.getString(14));
                players.add(player);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return players;
    }

    @Override
    public void confirmUser(String username, List<Player> selectedPlayers) {

        try {
            String sqlDelete = "DELETE FROM selectedPlayers WHERE username = ?";
            PreparedStatement deleteStatement = connection.prepareStatement(sqlDelete);
            deleteStatement.setString(1, username);
            ResultSet deleteSet = deleteStatement.executeQuery();

            for (Player player : selectedPlayers) {
                String sql = "INSERT INTO SelectedPlayers values (?,?)";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, username);
                statement.setString(2, player.getPictureId());
                ResultSet set = statement.executeQuery();
            }
        }catch (SQLException throwables) {
                throwables.printStackTrace();
            }


    }

    public void confirmSquadName(String username, String squadName, float money) {

        String sql = "UPDATE users SET squadname = ?, money = ? WHERE email = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, squadName);
            statement.setFloat(2, money);
            statement.setString(3, username);
            set = statement.executeQuery();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}

