package tn.esprit.Controllers;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

public class Video implements Initializable {

    @FXML
    private MediaView mv;

    private MediaPlayer mediaPlayer;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Load media
        String mediaPath = "file:///D:/Marketing Videos/final version chatTNS.mp4";
        Media media = new Media(mediaPath);

        // Create media player
        mediaPlayer = new MediaPlayer(media);

        // Bind media player to media view
        mv.setMediaPlayer(mediaPlayer);

        // Resize media view with the parent stack pane
        StackPane.setAlignment(mv, javafx.geometry.Pos.CENTER);
        mv.fitWidthProperty().bind(mv.getScene().widthProperty());
        mv.fitHeightProperty().bind(mv.getScene().heightProperty());

        // Auto play media
        mediaPlayer.setAutoPlay(true);
    }
}

