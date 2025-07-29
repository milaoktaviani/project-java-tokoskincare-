/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;
import Aplikasi.frmPembayaran;
import static Aplikasi.frmPembayaran.txNofak;
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
public class controltransaksi extends koneksi{
    private Statement st;
    private koneksi conn;
    private ResultSet rs;
    public controltransaksi() {
        koneksi koneksi;
        koneksi = new koneksi();
        
        super.setKoneksi();
        
    }
     
    public DefaultTableModel Model = new DefaultTableModel();
    public void tampilpembayaran(String id_produk,String nama_produk,int harga,int qty,int total){
        String[] kolom = {"Id Produk","Nama Produk","Harga","Qty","Total"};
        Model.setColumnIdentifiers(kolom);
        if(!(id_produk.equals(""))){
            Object[] data = new Object[5];
            data[0]= id_produk;
            data[1]= nama_produk;
            data[2]= harga;
            data[3]=qty;
            data[4]=total;
            Model.addRow(data);
        }
    }
    public void simpanPenjualan(String idbayar,String kasir,String idpelanggan,String namapelanggan,int diskon,int total,int bayar,int kembali,String tanggal) throws SQLException{
        String sql = "INSERT INTO tbpembayaran VALUES('"+idbayar+"','"+kasir+"','"+idpelanggan+"','"+namapelanggan+"','"+diskon+"','"+total+"','"+bayar+"','"+kembali+"','"+tanggal+"')";
        st.executeUpdate(sql);
    }
    public void insertBarang(String idbayar,String idproduk,String namaproduk,int harga,int qty,int total) throws SQLException{
        String sql = "INSERT INTO tbdetailtransaksi VALUES('"+idbayar+"','"+idproduk+"','"+namaproduk+"','"+harga+"','"+qty+"','"+total+"')";
        st.executeUpdate(sql);
    }
    public void updateStok(int sisa,String idproduk) throws SQLException{
      
        String sql = "UPDATE tbproduk set stok = '"+sisa+"' WHERE idproduk = '"+idproduk+"'";
        st.executeUpdate(sql);
    }
    
     public void noFak() {
        try {
            Connection conn = koneksi.koneksiDB();
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM tbpembayaran ORDER BY idpembayaran DESC";
            ResultSet result =stat.executeQuery(sql);


            if(result.next()) {
                String nofak = result.getString("idpembayaran").substring(1);
                String AN = "" + (Integer.parseInt(nofak) + 1);
                String nol = "";

                if(AN.length() == 1) {
                    nol = "000";
                } else if(AN.length() == 2) {
                    nol = "00";
                } else if(AN.length() == 3) {
                    nol = "0";
                } else if(AN.length() == 4) {
                    nol = "";
                }

                txNofak.setText("F" + nol + AN);
            } else {
                txNofak.setText("F0001");
            }
        } catch (SQLException ex) {
            Logger.getLogger(frmPembayaran.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        controltransaksi control = new controltransaksi();
        control.noFak();
    }

    
}
