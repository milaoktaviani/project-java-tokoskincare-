/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

/**
 *
 * @author Mila Oktaviani
 */
public class Sessi {
     private static String idpengguna;
    private static String namapengguna;
    private static String jabatan;
    private static String username;
    private static String password;
    
    public static String getIdPengguna() {
        return idpengguna;
    }
    
    public static void setIdPengguna(String idpengguna) {
        Sessi.idpengguna = idpengguna;
    }
    
    public static String getNamaPengguna() {
        return namapengguna;
    }
    
    public static void setNamaPengguna(String namapengguna) {
        Sessi.namapengguna = namapengguna;
    }
    
    public static String getJabatan() {
        return jabatan;
    }
    
    public static void setLevel(String jabatan) {
        Sessi.jabatan = jabatan;
    }
    
    public static String getUsername() {
        return username;
    }
    
    public static void setUsername(String username) {
        Sessi.username = username;
    }
    
    public static String getPassword() {
        return password;
    }
    
    public static void setPassword(String password) {
        Sessi.password = password;
    }
    
    public static void hapus() {
        idpengguna="";
        namapengguna="";
        username="";
        password="";
        jabatan="";
    }
}


