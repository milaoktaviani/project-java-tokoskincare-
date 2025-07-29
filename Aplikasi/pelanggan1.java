/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mila Oktaviani
 */
public class pelanggan1 extends koneksi{
    public pelanggan1(){
        super.setKoneksi();
}
    public DefaultTableModel model = new DefaultTableModel();
    
     public void tampil(){
        try {
            Connection conn = koneksi.koneksiDB();
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM tbpelanggan";
            ResultSet rs = stat.executeQuery(sql);
            String[] kolom = {"ID PELANGGAN","NAMA PELANGGAN","ALAMAT","NO.TELEPON"};
            model.setColumnIdentifiers(kolom);
            while(rs.next()){
                Object[] data = new Object[4];
                data[0] = rs.getString("idpelanggan");
                data[1] = rs.getString("namapelanggan");
                data[2] = rs.getString("alamat");
                data[3] = rs.getString("nohp");
                
                model.addRow(data);
            }
        } catch (SQLException ex) {
            Logger.getLogger(pelanggan.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}