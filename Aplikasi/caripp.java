/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
/**
 *
 * @author Mila Oktaviani
 */
public class caripp extends koneksi{
    public caripp(){
        
        super.setKoneksi();
    }
    
    public DefaultTableModel modelProduk = new  DefaultTableModel();
    
     public void tampil(){
        try {
//            String sql = "SELECT * FROM masterproduk WHERE nama_barang LIKE '%" + nama + "%'";
            Connection conn = koneksi.koneksiDB();
            Statement stat = conn.createStatement();
            String sqli= "SELECT * FROM tbproduk" ;
            String[] kolom = {"Id Produk","Nama Produk","Stok","Harga"};
            modelProduk.setColumnIdentifiers(kolom);
            ResultSet result =stat.executeQuery(sqli);
            while(result.next()){
            Object[] data = new Object[4];
            data[0] = result.getString("idproduk");
            data[1] = result.getString("namaproduk");
            data[2] = result.getString("stok");
            data[3] = result.getString("harga");
          
            modelProduk.addRow(data);
            
            
        }
        } catch (SQLException ex) {
            Logger.getLogger(caripp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
