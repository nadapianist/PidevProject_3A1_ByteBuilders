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

    private final File videosDirectory = new File("C:/Users/nadal/IdeaProjects/Pidev/src/main/resources/Videos");
    private final ObservableList<String> videoNames = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        loadVideosFromDirectory();
        comboVid.setItems(videoNames);

    }

    private void loadVideosFromDirectory() {
        if (videosDirectory.isDirectory()) {
            File[] videoFiles = videosDirectory.listFiles((dir, name) -> name.toLowerCase().endsWith(".mp4")
                    || name.toLowerCase().endsWith(".avi")
                    || name.toLowerCase().endsWith(".mkv")
                    || name.toLowerCase().endsWith(".mov"));
            if (videoFiles != null) {
                for (File file : videoFiles) {
                    videoNames.add(file.getName());
                }
            }
        }
    }

    @FXML
    void vid(ActionEvent event) {
        // Play the selected video when the ComboBox selection changes
        String selectedVideoName = comboVid.getValue();
        if (selectedVideoName != null) {
            String videoPath = new File(videosDirectory, selectedVideoName).toURI().toString();
            playVideo(videoPath);
        } else {
            // If no video is selected, show an error message or handle it accordingly
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No Video Selected");
            alert.setContentText("Please select a video.");
            alert.showAndWait();
        }
    }

    @FXML
    void PauseVid(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @FXML
    void PlayVid(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    private void playVideo(String videoPath) {
        try {
            Media med = new Media(videoPath);
            mediaPlayer = new MediaPlayer(med);
            media.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();
        } catch (MediaException e) {
            // Handle media exception, such as invalid file format or non-existent file
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Failed to Play Video");
            alert.setContentText("An error occurred while playing the video.");
            alert.showAndWait();
        }
    }

    @FXML
    void ResetVid(ActionEvent event) {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.seek(mediaPlayer.getStartTime());
        }
    }
}

