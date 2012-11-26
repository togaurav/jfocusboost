/* JFocusBoost is an open-source clone of Focus Booster
 * Copyright (C) 2012 Víctor Orozco
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.shekalug.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.shekalug.model.TimeSettings;
import org.shekalug.view.ButtonsBox;
import org.shekalug.view.JFocusBoost;

public class SettingsController implements Initializable {

    @FXML //  fx:id="anchorPane"
    private AnchorPane anchorPane; // Value injected by FXMLLoader
    @FXML //  fx:id="cancelBtn"
    private Button cancelBtn; // Value injected by FXMLLoader
    @FXML //  fx:id="longBreakLabel"
    private Label longBreakLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="longBreakSlider"
    private Slider longBreakSlider; // Value injected by FXMLLoader
    @FXML //  fx:id="okBtn"
    private Button okBtn; // Value injected by FXMLLoader
    @FXML //  fx:id="pomoLabel"
    private Label pomoLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="pomoSlider"
    private Slider pomoSlider; // Value injected by FXMLLoader
    @FXML //  fx:id="shortBreakLabel"
    private Label shortBreakLabel; // Value injected by FXMLLoader
    @FXML //  fx:id="shortBreakSlider"
    private Slider shortBreakSlider; // Value injected by FXMLLoader
    private long pomodoroDuration;
    private long shortBreakDuration;
    private long longBreakDuration;
    public static Stage containerStage;
    private ChangeListener pomoSliderChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            pomodoroDuration = new_val.longValue() * 60 * 1000;
        }
    };
    private ChangeListener shortBreakSliderChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            shortBreakDuration = new_val.longValue() * 60 * 1000;
        }
    };
    private ChangeListener longBreakSliderChangeListener = new ChangeListener<Number>() {
        @Override
        public void changed(ObservableValue<? extends Number> ov,
                Number old_val, Number new_val) {
            longBreakDuration = new_val.longValue() * 60 * 1000;
        }
    };

    public void okClickHandle(MouseEvent event) {
        TimeSettings.setPomodoroDuration(this.pomodoroDuration);
        TimeSettings.setShortBreakDuration(this.shortBreakDuration);
        TimeSettings.setLongBreakDuration(this.longBreakDuration);
        try {
            TimeSettings.getPrefs().flush();
        } catch (BackingStoreException ex) {
            Logger.getLogger(ButtonsBox.class.getName()).log(Level.SEVERE, null, ex);
        }
        goBack();
    }

    public void cancelClickHandle(MouseEvent event) {
        goBack();
    }

    private void goBack() {
        containerStage.setScene(JFocusBoost.getActualTimerScene());
        containerStage.setX(TimeSettings.getPrevXPosition());
        containerStage.setY(TimeSettings.getPrevYPosition());
    }

    @Override // This method is called by the FXMLLoader when initialization is complete
    public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
        this.pomoLabel.textProperty().bind(pomoSlider.valueProperty().asString());
        this.longBreakLabel.textProperty().bind(longBreakSlider.valueProperty().asString());
        this.shortBreakLabel.textProperty().bind(shortBreakSlider.valueProperty().asString());

        //Actual duration values
        this.pomodoroDuration = TimeSettings.getPomodoroDuration();
        this.shortBreakDuration = TimeSettings.getShortBreakDuration();
        this.longBreakDuration = TimeSettings.getLongBreakDuration();

        //Listener confi
        this.pomoSlider.valueProperty().addListener(this.pomoSliderChangeListener);
        this.shortBreakSlider.valueProperty().addListener(this.shortBreakSliderChangeListener);
        this.longBreakSlider.valueProperty().addListener(this.longBreakSliderChangeListener);
    }
}