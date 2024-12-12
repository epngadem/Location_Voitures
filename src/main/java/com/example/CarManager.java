package com.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarManager {

    private Connection conn;

    public CarManager() {
        DatabaseConnection dbConn = new DatabaseConnection();
        this.conn = dbConn.connect();  // Établir la connexion à la base de données
    }

    // 1. Création de la table Car
    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Car (" +
                                "id SERIAL PRIMARY KEY, " +
                                "model VARCHAR(100), " +
                                "isRented BOOLEAN DEFAULT FALSE)";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table Car créée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Car !");
            e.printStackTrace();
        }
    }

    // 2. Création de la table Reservation
    public void createReservationTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS Reservation (" +
                                "reservation_id SERIAL PRIMARY KEY, " +
                                "car_id INT REFERENCES Car(id), " +
                                "prenom VARCHAR(50), " +
                                "nom VARCHAR(50), " +
                                "email VARCHAR(100), " +
                                "telephone VARCHAR(15))";
        try (Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Table Reservation créée avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table Reservation !");
            e.printStackTrace();
        }
    }

    // 3. Ajouter une réservation
    public void addReservation(int carId, String prenom, String nom, String email, String telephone) {
        String insertSQL = "INSERT INTO Reservation (car_id, prenom, nom, email, telephone) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setInt(1, carId);
            pstmt.setString(2, prenom);
            pstmt.setString(3, nom);
            pstmt.setString(4, email);
            pstmt.setString(5, telephone);
            pstmt.executeUpdate();
            System.out.println("Réservation ajoutée pour " + prenom + " " + nom);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 4. Ajouter une voiture
    public void addCar(String model, boolean isRented) {
        if (carExists(model)) {
            System.out.println("La voiture " + model + " existe déjà dans la base de données !");
            return;
        }
        String insertSQL = "INSERT INTO Car (model, isRented) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            pstmt.setString(1, model);
            pstmt.setBoolean(2, isRented);
            pstmt.executeUpdate();
            System.out.println("Voiture ajoutée : " + model);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 5. Vérifier si une voiture existe
    public boolean carExists(String model) {
        String checkCarSQL = "SELECT COUNT(*) FROM Car WHERE model = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(checkCarSQL)) {
            pstmt.setString(1, model);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // 6. Obtenir toutes les voitures
    public List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        String selectSQL = "SELECT * FROM Car";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            while (rs.next()) {
                cars.add(new Car(
                    rs.getInt("id"),
                    rs.getString("model"),
                    rs.getBoolean("isRented")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cars;
    }

    // 7. Récupérer une voiture par son ID
    public Car getCarById(int id) {
        String query = "SELECT * FROM Car WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Car(
                    rs.getInt("id"),
                    rs.getString("model"),
                    rs.getBoolean("isRented")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 8. Mise à jour du statut de location
    public void updateCarStatus(int id, boolean isRented) {
        String updateSQL = "UPDATE Car SET isRented = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
            pstmt.setBoolean(1, isRented);
            pstmt.setInt(2, id);
            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Statut de location mis à jour pour la voiture avec ID: " + id);
            } else {
                System.out.println("Aucune voiture trouvée avec ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 9. Supprimer une voiture
    public void deleteCar(int id) {
        String deleteSQL = "DELETE FROM Car WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            pstmt.setInt(1, id);
            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Voiture supprimée avec succès, ID: " + id);
            } else {
                System.out.println("Aucune voiture trouvée avec ID: " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 10. Obtenir l'ID d'une voiture par modèle
    public int getCarIdByModel(String model) {
        String selectSQL = "SELECT id FROM Car WHERE model = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            pstmt.setString(1, model);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
