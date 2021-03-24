package sample.customCells;

import com.jfoenix.controls.JFXButton;

import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import sample.models.Player;

public class CurrentPlayersCell extends ListCell<Player> {

    private Text PlayerName, PLayerTeam, PlayerPosition, PlayerPrice;
    private HBox PlayerCard, AddPlayer, HboxPosAndTeam;
    private VBox PlayerForm;
    private JFXButton PlayerInformBtn;
    private ImageView TShirtPlayer;
    private Line SeparateLine;

    public CurrentPlayersCell() {
        super();

    }

}
