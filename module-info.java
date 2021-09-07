module csci205.hw {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires org.junit.jupiter.api;
    opens gui.game.GUI;
    opens gui.solverGUI.GUI;
    opens gui;
}