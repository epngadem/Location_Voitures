package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection connect() {
        Connection conn = null;

        try {
            // 1.Charger le pilote JDBC de PostgreSQL
            Class.forName("org.postgresql.Driver");  

            // 2.Établir la connexion avec la base de données
            String url = "jdbc:postgresql://localhost:5432/voitures"; 
            String user = "kom"; 
            String password = "kom";  

            conn = DriverManager.getConnection(url, user, password);  

            System.out.println("Connexion réussie à la base de données voitures !");
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Pilote PostgreSQL introuvable !");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Erreur de connexion à la base de données !");
            e.printStackTrace();
        }

        return conn; 
    }
}

