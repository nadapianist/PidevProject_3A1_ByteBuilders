package tn.esprit.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Slider;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.io.File;
public class Video {

    @FXML
    private ComboBox<String> comboVid;

    @FXML
    private MediaView media;

    private MediaPlayer mediaPlayer;

    private final File videosDirectory = new File("C:/Users/nadal/IdeaProjects/Pidev/src/main/resources/Marketing  Videos");
    private final ObservableList<String> videoNames = FXCollections.observableArrayList();

    @FXML
    public void initialize() {


    }

    private void loadVideosFromDirectory() {

    }

    @FXML
    void vid(ActionEvent event) {

    }

    @FXML
    void PauseVid(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    void PlayVid(ActionEvent event) {

    }

    private void playVideo(String videoPath) {

    }

    @FXML
    void ResetVid(ActionEvent event) {

    }
}

