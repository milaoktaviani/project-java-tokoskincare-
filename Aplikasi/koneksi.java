/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class koneksi {
    private static Connection koneksi = null;

    public static Connection koneksiDB() {
        if (koneksi == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
                koneksi = DriverManager.getConnection("jdbc:mysql://localhost:3306/skincare", "root", "");
                System.out.println("KONEKSI BERHASIL");
            } catch (ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Driver tidak ditemukan: " + e.getMessage());
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Koneksi database gagal: " + e.getMessage());
            }
        }
        return koneksi;
    }

    public static void setKoneksi() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            koneksi = DriverManager.getConnection("jdbc:mysql://localhost:3306/skincare", "root", "");
            Statement st = koneksi.createStatement();
            System.out.println("KONEKSI BERHASIL");
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Driver tidak ditemukan: " + e.getMessage());
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Koneksi database gagal: " + e.getMessage());
        }
    }
}


    
    
