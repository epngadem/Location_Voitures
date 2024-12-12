package com.example;

public class Car {
    private int id;           // ID unique de la voiture
    private String model;     // Modèle de la voiture
    private boolean isRented; // Statut de location de la voiture
    private boolean inProgress; // Indique si la voiture est en cours d'utilisation

    // Constructeur
    public Car(int id, String model, boolean isRented) {
        this.id = id;
        this.model = model;
        this.isRented = isRented;
        this.inProgress = false; // Initialisé à false par défaut
    }

    // Getters et setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public boolean isRented() {
        return isRented;
    }

    public void setRented(boolean rented) {
        isRented = rented;
    }

    public boolean isInProgress() {
        return inProgress;
    }

    public void setInProgress(boolean inProgress) {
        this.inProgress = inProgress;
    }

    // Méthode pour afficher les informations de la voiture
    public void displayInfo() {
        String status = isRented ? "Louée" : (inProgress ? "En cours d'utilisation" : "Disponible");
        System.out.println("ID: " + id + ", Modèle: " + model + ", Statut: " + status);
    }
}
