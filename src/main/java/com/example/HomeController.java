package com.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class HomeController {

    public void handleCarManagement(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/car_management.fxml")); // Chemin du fichier FXML
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Gestion des Voitures"); // Définit le titre de la fenêtre
            stage.show(); // Affiche la scène mise à jour
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'écran de gestion des voitures.");
            e.printStackTrace();
        }
    }

    public void handleReservations(ActionEvent event) {
        try {
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/reservation.fxml")); // Chemin du fichier FXML
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Réservations"); // Définit le titre de la fenêtre
            stage.show(); // Affiche la scène mise à jour
        } catch (Exception e) {
            System.err.println("Erreur lors du chargement de l'écran des réservations.");
            e.printStackTrace();
        }
    }

    public void handleQuit(ActionEvent event) {
        System.out.println("Quitter le programme...");
        System.exit(0); // Ferme l'application
    }
}
