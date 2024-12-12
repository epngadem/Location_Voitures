package com.example;

import java.util.Scanner;

public class CarRentalMenu {

    private static CarManager carManager = new CarManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        carManager.createTable();  
        carManager.createReservationTable();
        boolean quit = false;

        while (!quit) {
            afficherMenu();
            int choix = scanner.nextInt();
            scanner.nextLine();  

            switch (choix) {
                case 1 -> listerVoitures();
                case 2 -> ajouterVoiture();
                case 3 -> mettreAJourStatut();
                case 4 -> supprimerVoiture();
                case 5 -> reserverVoiture();
                case 0 -> {
                    System.out.println("Quitter le programme...");
                    quit = true;
                }
                default -> System.out.println("Choix invalide. Veuillez réessayer.");
            }
        }
    }

    private static void afficherMenu() {
        System.out.println("\n*** Menu de Gestion de Location de Voitures ***");
        System.out.println("1. Lister toutes les voitures");
        System.out.println("2. Ajouter une nouvelle voiture");
        System.out.println("3. Mettre à jour le statut de location d'une voiture");
        System.out.println("4. Supprimer une voiture");
        System.out.println("5. Réserver une voiture");
        System.out.println("0. Quitter");
        System.out.print("Choisissez une option : ");
    }

    private static void listerVoitures() {
        System.out.println("\nListe des voitures :");
        carManager.getAllCars();
    }

    private static void ajouterVoiture() {
        System.out.print("Entrez le modèle de la voiture : ");
        String model = scanner.nextLine();
        System.out.print("La voiture est-elle louée ? (oui/non) : ");
        boolean isRented = scanner.nextLine().equalsIgnoreCase("oui");
        carManager.addCar(model, isRented);
    }

    private static void mettreAJourStatut() {
        System.out.print("Entrez l'ID de la voiture à mettre à jour : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("La voiture est-elle louée ? (oui/non) : ");
        boolean isRented = scanner.nextLine().equalsIgnoreCase("oui");
        carManager.updateCarStatus(id, isRented);
    }

    private static void supprimerVoiture() {
        System.out.print("Entrez l'ID de la voiture à supprimer : ");
        int id = scanner.nextInt();
        scanner.nextLine();
        carManager.deleteCar(id);
    }

    private static void reserverVoiture() {
        System.out.print("Entrez votre prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Entrez votre nom : ");
        String nom = scanner.nextLine();
        System.out.print("Entrez votre email : ");
        String email = scanner.nextLine();
        System.out.print("Entrez votre numéro de téléphone : ");
        String telephone = scanner.nextLine();
    
        System.out.print("Entrez le modèle de la voiture que vous souhaitez réserver : ");
        String model = scanner.nextLine();
        int carId = carManager.getCarIdByModel(model);
    
        if (carId != -1) {  
            System.out.println("Oui " + prenom + ", la voiture " + model + " est disponible pour vous ! Réservation en cours...");
            carManager.updateCarStatus(carId, true); 
            carManager.addReservation(carId, prenom, nom, email, telephone);  
        } else {
            System.out.println("Désolé " + prenom + ", la voiture " + model + " n'est pas disponible pour le moment. Nous vous recontacterons dès qu'une voiture de ce modèle sera à portée.");
        }
    }
    

    
}
