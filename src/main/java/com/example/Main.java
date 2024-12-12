package com.example;

import com.fazecast.jSerialComm.SerialPort;

public class Main {

    public static void main(String[] args) {
        CarManager carManager = new CarManager();

        // Configuration du port série sur COM17
        SerialPort sp = SerialPort.getCommPort("COM17");
        sp.setComPortParameters(9600, 8, 1, 0); // Baudrate 9600, 8 bits de données, 1 bit de stop, pas de parité
        sp.setComPortTimeouts(SerialPort.TIMEOUT_WRITE_BLOCKING, 0, 0);

        if (!sp.openPort()) {
            System.out.println("Erreur : Impossible d'ouvrir le port COM17.");
            return;
        }
        System.out.println("Port série COM17 ouvert avec succès.");

        // Ajout de voitures pour tester
        carManager.addCar("Tesla Model S", false);
        carManager.addCar("BMW i8", true);
        carManager.addCar("Audi A4", false);

        // Contrôle des LED pour chaque voiture
        for (Car car : carManager.getAllCars()) {
            char commande;
            if (car.isRented()) {
                commande = 'R'; // Rouge : voiture louée
            } else if (car.isInProgress()) {
                commande = 'Y'; // Jaune : en cours d'utilisation
            } else {
                commande = 'G'; // Vert : disponible
            }

            // Envoyer la commande au microcontrôleur
            try {
                sp.getOutputStream().write(commande);
                sp.getOutputStream().flush();
                System.out.println("Commande envoyée pour la voiture " + car.getModel() + " : " + commande);
            } catch (Exception e) {
                System.out.println("Erreur lors de l'envoi de la commande pour " + car.getModel());
                e.printStackTrace();
            }
        }

        // Fermeture du port série
        sp.closePort();
        System.out.println("Port série COM17 fermé.");
    }
}
