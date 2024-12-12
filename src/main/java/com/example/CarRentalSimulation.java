package com.example;
import jssc.SerialPort;
import jssc.SerialPortException;

public class CarRentalSimulation {// je continuerai ca pour la partie 2 

    private SerialPort serialPort;

    public CarRentalSimulation(String portName) {
        serialPort = new SerialPort(portName);
        try {
            serialPort.openPort();  
            serialPort.setParams(SerialPort.BAUDRATE_9600,
                                 SerialPort.DATABITS_8,
                                 SerialPort.STOPBITS_1,
                                 SerialPort.PARITY_NONE);
            System.out.println("Port série ouvert avec succès !");
        } catch (SerialPortException e) {
            e.printStackTrace();
            System.out.println("Erreur : Impossible d'ouvrir le port série !");
        }
    }

    // Méthode pour envoyer des commandes pour contrôler les LED
    public void sendCommand(char command) {
        try {
            if (serialPort.isOpened()) {
                serialPort.writeByte((byte) command);  // Envoyer la commande série
                System.out.println("Commande envoyée : " + command);
            } else {
                System.out.println("Erreur : le port série n'est pas ouvert !");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    // Fermer le port série
    public void close() {
        try {
            if (serialPort.isOpened()) {
                serialPort.closePort();
                System.out.println("Port série fermé avec succès !");
            } else {
                System.out.println("Erreur : le port série n'est pas ouvert !");
            }
        } catch (SerialPortException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        
        CarRentalSimulation simulation = new CarRentalSimulation("COM5");  // Remplace "COM5" par le port correct

        // Simuler les états des voitures
        simulation.sendCommand('V');  // Allumer la LED Verte (voiture disponible)
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        simulation.sendCommand('R');  // Allumer la LED Rouge (voiture louée)
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        simulation.sendCommand('J');  // Allumer la LED Jaune (voiture en maintenance)
        try { Thread.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }

        simulation.close();  // Fermer la connexion série
    }
}
