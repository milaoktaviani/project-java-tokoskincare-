/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;
import Aplikasi.controltransaksi;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.InputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Mila Oktaviani
 */
public class frmPembayaran extends javax.swing.JFrame {
//     controltransaksi ct;
//
//    public String id_produk;
//    public String nama_produk;
//    public static int qty;
//    public static int harga;
//    public static int stok;
//    public int total;

    

//    /**
//     *
//     * @param idproduk
//     * @param namaproduk
//     * @param harga
//     * @param qty
//     * @param total
//     */
   
    
    private String namapengguna = Sessi.getNamaPengguna();
    java.util.Date tanggalsekarang = new java.util.Date();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private String tanggal = format.format(tanggalsekarang);
//    private CARI CARI;
    private pelanggan pelanggan ;
    private cariProduk cariProduk ;
    private int select;
    private int select2;
    private DefaultTableModel model;
    String iddetailorder, idproduk, namaproduk, harga, stok, qty, total,idpembayaran, kasir, idpelanggan, namapelanggan, diskon, bayar, kembali;
    /**
     * Creates new form frmPembayaran
     */
    public frmPembayaran() {
        initComponents();
        txtnamapengguna.setText(namapengguna);
        lbtanggal.setText(tanggal);
         setJam();
        
        model = new DefaultTableModel();
        tbldetailorder.setModel(model);
        model.addColumn("Id Detail Order");
        model.addColumn("Id Pembayaran");
        model.addColumn("Id Produk");
        model.addColumn("Nama Produk");
        model.addColumn("Harga");
        model.addColumn("Qty");
        model.addColumn("Total");
      
//        setJam();
//        this.CARI = new CARI();
        
        
       
//        ct = new controltransaksi();
//        tbldetailorder.setModel(ct.Model);
//        ct.tampilpembayaran("", "", 0, 0, 0);
//        ct.noFak();
//        txidproduk.setText(produk.idProduk);
//        txnamaproduk.setText(produk.namaProduk);
//        txharga.setText(produk.harga);
//        txstok.setText(produk.stok);
        txidPelanggan.setText(plg.idPelanggan);
        txpelanggan.setText(plg.namaPelanggan);
        
        getdata();
        noFak();
    } 
    
    private void getdata() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbdetailorder WHERE idpembayaran NOT IN (SELECT idpembayaran FROM tbpembayaran);";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[7];
                obj[0] = rs.getString("iddetailorder");
                obj[1] = rs.getString("idpembayaran");
                obj[2] = rs.getString("idproduk");
                obj[3] = rs.getString("namaproduk");
                obj[4] = rs.getString("harga");
                obj[5] = rs.getString("qty");
                obj[6] = rs.getString("total");
                
                model.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void noFak() {
        try {
            Connection conn = koneksi.koneksiDB();
            Statement stat = conn.createStatement();
            String sql = "SELECT * FROM tbdetailorder ORDER BY idpembayaran DESC";
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
    

    
//    public final void setJam(){
//         ActionListener taskPerformer = new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                String nol_jam = "", nol_menit = "", nol_detik ="";
//                
//                java.util.Date dateTime = new java.util.Date();
//                int nilai_jam = dateTime.getHours();
//                int nilai_menit = dateTime.getMinutes();
//                int nilai_detik = dateTime.getSeconds();
//                
//                if(nilai_jam <= 9) nol_jam = "0";
//                if(nilai_menit <= 9) nol_menit = "0";
//                if(nilai_detik <= 9) nol_detik = "0";
//                
//                String jam = nol_jam + Integer.toString(nilai_jam);
//                String menit = nol_menit + Integer.toString(nilai_menit);
//                String detik = nol_detik + Integer.toString(nilai_detik);
//                
//                lbjam.setText(jam+":"+menit+":"+detik+"");
//            }
//        };
//        new Timer(1000, taskPerformer).start();
//    }
    public final void setJam(){
         ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                String nol_jam = "", nol_menit = "", nol_detik ="";
                
                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();
                
                if(nilai_jam <= 9) nol_jam = "0";
                if(nilai_menit <= 9) nol_menit = "0";
                if(nilai_detik <= 9) nol_detik = "0";
                
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                
                lbjam.setText(jam+":"+menit+":"+detik+"");
            }
        };
        new Timer(1000, taskPerformer).start();
    }
    
    
    
   public void setPelanggan(pelanggan pelanggan) {
        this.pelanggan = pelanggan;
    }
    
    public void selectpelanggan() {
        select2 = pelanggan.select2();
        DefaultTableModel Pelanggan = pelanggan.getTableModel(); 

        String idPelanggan = "" + Pelanggan.getValueAt(select2, 0);
        String namaPelanggan = "" + Pelanggan.getValueAt(select2, 1);
        
        txidPelanggan.setText(idPelanggan);
        txpelanggan.setText(namaPelanggan);
        
        plg.idPelanggan = idPelanggan;
        plg.namaPelanggan = namaPelanggan;
    }
    
    public void setProduk(cariProduk cariProduk) {
        this.cariProduk = cariProduk;
    }
    
    public void selectproduk() {
        select = cariProduk.select();
        DefaultTableModel CariProduk = cariProduk.getTableModel(); 

        String idProduk = "" + CariProduk.getValueAt(select, 0);
        String namaProduk = "" + CariProduk.getValueAt(select, 1);
        String harga = "" + CariProduk.getValueAt(select, 3);
        String stok = "" + CariProduk.getValueAt(select, 4);
        
        txidproduk.setText(idProduk);
        txnamaproduk.setText(namaProduk);
        txharga.setText(harga);
        txstok.setText(stok);

        // Store the selected product details in the static class
        produk.idProduk = idProduk;
        produk.namaProduk = namaProduk;
        produk.harga = harga;
        produk.stok = stok;
    }
    
    public void hitungharga() {
        try {
            int jumlahbarang = Integer.parseInt(txqty.getText());
            int hargabarang = Integer.parseInt(txharga.getText());
            int totalharga = jumlahbarang * hargabarang;

            txtotal.setText(String.valueOf(totalharga)); 
        } catch (NumberFormatException e) {
            txtotal.setText("");
        }       
    }
    
    public boolean updatestok() {
        String jumlahStr = txqty.getText();
        int stok = Integer.parseInt(txstok.getText());

        if (jumlahStr.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Jumlah Harus Diisi!");
            return false;
        }

        try {
            int jumlah = Integer.parseInt(jumlahStr);

            if (jumlah > stok) {
                JOptionPane.showMessageDialog(null, "Stok Tidak cukup!");
                return false;
            } else {
                Connection conn = koneksi.koneksiDB();
                String sql = "UPDATE tbproduk SET stok = stok - ? WHERE idproduk = ?";
                PreparedStatement p = conn.prepareStatement(sql);
                p.setInt(1, jumlah);
                p.setString(2, idproduk);
                p.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
            return false;
        } catch (NumberFormatException e) {

            return false;
        }
    }
    
    public void tambahstok() {
            try{
                Connection conn = koneksi.koneksiDB();
                String sql = "UPDATE tbproduk SET stok = stok + ? WHERE idproduk = ?";
                PreparedStatement p = (PreparedStatement)conn.prepareStatement(sql);
                int jumlahInt = Integer.parseInt(qty);
                p.setInt(1, jumlahInt);
                p.setString(2, idproduk);
                p.executeUpdate();
            }catch(SQLException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            }catch (NumberFormatException e) {
                
        } 
    }
    
    public void hitungdetail() {
        try {
             Connection conn = koneksi.koneksiDB();
             Statement stat = conn.createStatement();
             String sql = "SELECT SUM(harga * qty) AS total FROM tbdetailorder WHERE idpembayaran NOT IN (SELECT idpembayaran FROM tbpembayaran)";
             ResultSet rs = stat.executeQuery(sql);
             
             if (rs.next()) {
                  total = rs.getString("total");
                  txtotal2.setText(String.valueOf(total)); 
                  txGrandTotal.setText(String.valueOf(total));
                  tampilTTL.setText(String.valueOf(total));
             }
             
        } catch (SQLException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void selectdata() {
        int i = tbldetailorder.getSelectedRow();
        if (i == -1) {
            //tidak ada baris yang di pilih
            return;
        }
        txNofak.setText(""+model.getValueAt(i, 1));
        txidproduk.setText(""+model.getValueAt(i, 2));
        txnamaproduk.setText(""+model.getValueAt(i, 3));
        txqty.setText(""+model.getValueAt(i, 5));
    }
    
    public void loaddata() {
        idpembayaran = txNofak.getText();
        idproduk = txidproduk.getText();
        namaproduk = txnamaproduk.getText();
        harga = txharga.getText();
        qty = txqty.getText();
        total = txtotal.getText();
    }
    
    public void loaddatatransaksi() {
        idpembayaran = txNofak.getText();
        kasir = txtnamapengguna.getText();
        idpelanggan = txidPelanggan.getText();
        namapelanggan = txpelanggan.getText();
        diskon = txDiskon.getText();
        total = txGrandTotal.getText();
        bayar = txBayar.getText();
        kembali = txKembali.getText();
        tanggal = lbtanggal.getText();
    }
    
    public void simpandata() {
        loaddata();
        if (!updatestok()) {
            txidproduk.setText("");
            txnamaproduk.setText("");
            txqty.setText("");
            txtotal.setText("");
            return;           
        }
        try {
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "INSERT INTO tbdetailorder (idpembayaran, idproduk, namaproduk, harga, qty, total) VALUES (?, ?, ?, ?, ?, ?)";          
            PreparedStatement p = conn.prepareStatement(sql);
                p.setString(1, idpembayaran);
                p.setString(2, idproduk);
                p.setString(3, namaproduk);
                p.setString(4, harga);
                p.setString(5, qty);
                p.setString(6, total);
                p.executeUpdate();
            getdata();
            hitungdetail();
            noFak();
            
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, x.getMessage());
        }
    }

    public void simpandatatransaksi() {
        loaddatatransaksi();
        try {
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "INSERT INTO tbpembayaran (idpembayaran, kasir, idpelanggan, namapelanggan, diskon, total, bayar, kembali, tanggal) " +
                         "SELECT idpembayaran, ?, ?, ?, ?, ?, ?, ?, ? FROM tbdetailorder WHERE idpembayaran NOT IN (SELECT idpembayaran FROM tbpembayaran)";
            PreparedStatement p = conn.prepareStatement(sql);
                p.setString(1, kasir);
                p.setString(2, idpelanggan);
                p.setString(3, namapelanggan);
                p.setString(4, diskon);
                p.setString(5, total);
                p.setString(6, bayar);
                p.setString(7, kembali);
                p.setString(8, tanggal);
                p.executeUpdate();
                
                getdata();
                txidPelanggan.setText("");
                txpelanggan.setText("");
                txtotal2.setText("");
                txDiskon.setText("");
                txGrandTotal.setText("");
                txBayar.setText("");
                txKembali.setText("");
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
        } catch (SQLException x) {
            JOptionPane.showMessageDialog(null, x.getMessage());
        }
    }
    
    public void cetak() {
    try {
        // Adjust the path accordinncare/src/lg to your setup
        String file = "/Aplikasi/struk1.jasper";

        // Verify the file path
        InputStream Report;
        Report = getClass().getResourceAsStream(file);
        if (Report == null) {
            JOptionPane.showMessageDialog(null, "Report file not found: " + file);
            return;
        }

        Connection conn = koneksi.koneksiDB();
        if (conn == null) {
            JOptionPane.showMessageDialog(null, "Database connection error");
            return;
        }

        // Set parameters
        HashMap<String, Object> parameter = new HashMap<>();
        parameter.put("tanggal", lbtanggal.getText());
        parameter.put("kasir", txtnamapengguna.getText());
        parameter.put("jam1", lbjam.getText());
        parameter.put("subtotal1", txtotal2.getText());
        parameter.put("diskon", txDiskon.getText());
        parameter.put("grandtotal1", txGrandTotal.getText());
        parameter.put("bayar", txBayar.getText());
        parameter.put("kembali", txKembali.getText());

        
        // Generate report
        JasperPrint print = JasperFillManager.fillReport(Report, parameter, conn);
        JasperViewer.viewReport(print, false);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
    }
}

    public void cetakstruk () {
        try {
    HashMap parameters = new HashMap();
    String a = lbjam.getText();
    parameters.put("jam", a);
    
    String namafile = "src/laporan/report1.jasper";
    File Report = new File (namafile);
    JasperReport jreprt;
    jreprt = (JasperReport) JRLoader.loadObject (Report);
    JasperPrint jprint =JasperFillManager.fillReport (jreprt, parameters, koneksi.koneksiDB());
    
    JasperViewer.viewReport (jprint, false);
    
    } catch (Exception e) {
    JOptionPane.showMessageDialog(this, "Laporan Gagal"+ e);
}
    }
    
    public void cetakStrukk() {
    try {
        File namafile= new File("src/laporan/report1.jasper");
        JasperReport jrpt= (JasperReport) JRLoader.loadObject(namafile);
        HashMap <String, Object> parameters = new HashMap<>();
        String a= lbjam.getText();
        parameters.put("jam", a);
        JasperPrint jpPrint= JasperFillManager.fillReport(jrpt, parameters, koneksi.koneksiDB());
        JasperViewer.viewReport(jpPrint, false);
    } catch (JRException e) {
        JOptionPane.showMessageDialog(this, "Laporan Gagal: " + e.getMessage());
        e.printStackTrace(); // Tambahkan ini untuk melihat error yang lebih detail 
}
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtotal = new javax.swing.JTextField();
        txharga = new javax.swing.JTextField();
        txstok = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        kGradientPanel1 = new keeptoo.KGradientPanel();
        tampilTTL = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbldetailorder = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        txpelanggan = new javax.swing.JTextField();
        txNofak = new javax.swing.JTextField();
        txtotal2 = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        txGrandTotal = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        txDiskon = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        txBayar = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txKembali = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        btnCetak = new javax.swing.JButton();
        jLabel18 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        btnReset = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txidPelanggan = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        btnCariPlg = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        txidproduk = new javax.swing.JTextField();
        txnamaproduk = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        btnnew = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txqty = new javax.swing.JTextField();
        btnsimpan = new javax.swing.JButton();
        lbjam = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lbtanggal = new javax.swing.JLabel();
        txtnamapengguna = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        txtotal.setEditable(false);

        txharga.setEditable(false);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bawahmarble1.jpg"))); // NOI18N
        getContentPane().add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 770, 1240, 130));

        kGradientPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 255, 255)), "TOTAL", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 16), new java.awt.Color(255, 255, 255))); // NOI18N
        kGradientPanel1.setkBorderRadius(0);
        kGradientPanel1.setkEndColor(new java.awt.Color(0, 51, 51));
        kGradientPanel1.setkStartColor(new java.awt.Color(102, 102, 102));

        tampilTTL.setFont(new java.awt.Font("Segoe UI", 1, 50)); // NOI18N
        tampilTTL.setForeground(new java.awt.Color(255, 255, 255));
        tampilTTL.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tampilTTL.setText("0");

        javax.swing.GroupLayout kGradientPanel1Layout = new javax.swing.GroupLayout(kGradientPanel1);
        kGradientPanel1.setLayout(kGradientPanel1Layout);
        kGradientPanel1Layout.setHorizontalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tampilTTL, javax.swing.GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
                .addContainerGap())
        );
        kGradientPanel1Layout.setVerticalGroup(
            kGradientPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, kGradientPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(tampilTTL, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        getContentPane().add(kGradientPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 50, 390, 110));

        tbldetailorder.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tbldetailorder.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbldetailorderMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbldetailorder);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 180, 960, 150));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "PEMBAYARAN", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 16), new java.awt.Color(0, 0, 102))); // NOI18N
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txpelanggan.setEditable(false);
        jPanel1.add(txpelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 189, 181, 32));

        txNofak.setEditable(false);
        txNofak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txNofakActionPerformed(evt);
            }
        });
        jPanel1.add(txNofak, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 43, 181, 32));

        txtotal2.setEditable(false);
        jPanel1.add(txtotal2, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 43, 181, 32));

        jLabel12.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(0, 0, 102));
        jLabel12.setText("No. Faktur");
        jPanel1.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 22, -1, -1));

        jLabel13.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 0, 102));
        jLabel13.setText("Nama Pelanggan");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 159, -1, -1));

        jLabel14.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 0, 102));
        jLabel14.setText("Sub Total");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 22, -1, -1));

        txGrandTotal.setEditable(false);
        jPanel1.add(txGrandTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 189, 181, 32));

        jLabel15.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 0, 102));
        jLabel15.setText("Diskon (%)");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 88, -1, -1));

        txDiskon.setEditable(false);
        txDiskon.setText("0");
        txDiskon.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txDiskonCaretUpdate(evt);
            }
        });
        jPanel1.add(txDiskon, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 118, 181, 32));

        jLabel16.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(0, 0, 102));
        jLabel16.setText("Grand Total");
        jPanel1.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(319, 159, -1, -1));

        txBayar.setEditable(false);
        txBayar.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txBayarCaretUpdate(evt);
            }
        });
        jPanel1.add(txBayar, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 43, 199, 32));

        jLabel9.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(0, 0, 102));
        jLabel9.setText("Bayar");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 22, -1, -1));

        txKembali.setEnabled(false);
        jPanel1.add(txKembali, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 118, 199, 32));

        jLabel17.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(0, 0, 102));
        jLabel17.setText("Kembali");
        jPanel1.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(526, 88, -1, -1));

        btnCetak.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-print-50.png"))); // NOI18N
        btnCetak.setEnabled(false);
        btnCetak.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCetakActionPerformed(evt);
            }
        });
        jPanel1.add(btnCetak, new org.netbeans.lib.awtextra.AbsoluteConstraints(915, 52, 80, 60));

        jLabel18.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(0, 0, 102));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("CETAK NOTA");
        jPanel1.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(894, 22, 117, -1));

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-save-50.png"))); // NOI18N
        btnSave.setEnabled(false);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(772, 52, 80, 60));

        jLabel19.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(0, 0, 102));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("SIMPAN");
        jPanel1.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 22, 117, -1));

        btnReset.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-reset-50.png"))); // NOI18N
        btnReset.setEnabled(false);
        btnReset.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnResetActionPerformed(evt);
            }
        });
        jPanel1.add(btnReset, new org.netbeans.lib.awtextra.AbsoluteConstraints(1059, 52, 80, 60));

        jLabel20.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(0, 0, 102));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("RESET");
        jPanel1.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(1040, 20, 115, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-cancel-30.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 160, 90, 50));

        txidPelanggan.setEditable(false);
        jPanel1.add(txidPelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 118, 181, 32));

        jLabel21.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(0, 0, 102));
        jLabel21.setText("ID Pelanggan");
        jPanel1.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(21, 88, -1, -1));

        btnCariPlg.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btnCariPlg.setForeground(new java.awt.Color(0, 0, 102));
        btnCariPlg.setText("CARI");
        btnCariPlg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariPlgActionPerformed(evt);
            }
        });
        jPanel1.add(btnCariPlg, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 120, 70, 30));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 530, 1200, 230));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ITEM PRODUK", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Century Gothic", 1, 16), new java.awt.Color(0, 0, 102))); // NOI18N

        txidproduk.setEditable(false);

        txnamaproduk.setEditable(false);

        btnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-search-35.png"))); // NOI18N
        btnCari.setEnabled(false);
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        btnnew.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btnnew.setForeground(new java.awt.Color(0, 0, 102));
        btnnew.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-25.png"))); // NOI18N
        btnnew.setText("NEW");
        btnnew.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnnewActionPerformed(evt);
            }
        });

        btnhapus.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btnhapus.setForeground(new java.awt.Color(0, 0, 102));
        btnhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-25.png"))); // NOI18N
        btnhapus.setText("HAPUS");
        btnhapus.setEnabled(false);
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });

        btncancel.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btncancel.setForeground(new java.awt.Color(0, 0, 102));
        btncancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-cancel-25.png"))); // NOI18N
        btncancel.setText("CANCEL");
        btncancel.setEnabled(false);
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("ID PRODUK");

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("NAMA PRODUK");

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("QTY");

        txqty.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txqtyKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txqtyKeyTyped(evt);
            }
        });

        btnsimpan.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        btnsimpan.setForeground(new java.awt.Color(0, 0, 102));
        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-save-25.png"))); // NOI18N
        btnsimpan.setText("SIMPAN");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txidproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10)
                    .addComponent(txnamaproduk, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(txqty, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnnew)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnsimpan)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(btnhapus)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncancel)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)))
                .addGap(11, 11, 11)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnnew, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnhapus, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btncancel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnsimpan, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnCari, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txnamaproduk)
                            .addComponent(txqty, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(txidproduk)))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 1190, 140));

        lbjam.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbjam.setForeground(new java.awt.Color(0, 0, 102));
        lbjam.setText("jam");
        getContentPane().add(lbjam, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 160, 40));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Kasir:");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 140, -1, -1));

        lbtanggal.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        lbtanggal.setForeground(new java.awt.Color(0, 0, 102));
        lbtanggal.setText("tanggal");
        getContentPane().add(lbtanggal, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 130, 160, 40));

        txtnamapengguna.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        txtnamapengguna.setForeground(new java.awt.Color(0, 0, 102));
        txtnamapengguna.setText("pengguna");
        getContentPane().add(txtnamapengguna, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 140, -1, -1));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("Tanggal:");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 140, -1, -1));

        jLabel3.setFont(new java.awt.Font("Castellar", 1, 28)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Ethereal Skincare");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 380, 40));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/atasmarble1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1240, 170));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/close-up-white-marble-texture-background.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1240, 720));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
//      CARI frm = new CARI (null, true,"frmPembayaran");
//        frm.setTitle("PILIH PRODUK");
//        frm.setVisible(true);
        new cariProduk().setVisible(true);
        this.setVisible(false);
//        selectproduk();
    }//GEN-LAST:event_btnCariActionPerformed

    private void btnnewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnnewActionPerformed
        // TODO add your handling code here:
        btnCari.setEnabled(true);
        btnnew.setEnabled(false);
        btncancel.setEnabled(true);

    }//GEN-LAST:event_btnnewActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
//        int row = tbldetailorder.getSelectedRow();
//        ct.Model.removeRow(row);
//        btnhapus.setEnabled(false);
//        btncancel.setEnabled(false);
//        btnnew.setEnabled(true);
//        btnCari.setEnabled(false);
////        tampilTTL.setText("0");
////        txtotal2.setText("");
////        txGrandTotal.setText("");
//        int subTotal = 0;
//          for(int n =0;n<tbldetailorder.getRowCount();n++){
//              int hargajual = (int) tbldetailorder.getValueAt(n, 2);
//                int  qty1 = (int) tbldetailorder.getValueAt(n, 3);
//               int  total1 = hargajual*qty;
//                 int total2 = (int) tbldetailorder.getValueAt(n, 4);
//                  
//                 subTotal += total2;
//                
//            }
//            tampilTTL.setText(Integer.toString(subTotal));
//            txtotal2.setText(Integer.toString(subTotal));
//            txDiskon.setText("0");       // TODO add your handling code here:
            loaddata();
            int pesan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini? " + idpembayaran, "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
            if (pesan == JOptionPane.OK_OPTION) {
                try {
                    Connection conn = koneksi.koneksiDB();
                    String sql = "DELETE FROM tbdetailorder WHERE idpembayaran = ?";
                    try (PreparedStatement p = conn.prepareStatement(sql)) {
                        p.setString(1, idpembayaran);
                        p.executeUpdate();
                    }
                    tambahstok();
                    getdata();
                    txNofak.setText("");
                    txidproduk.setText("");
                    txnamaproduk.setText("");
                    txqty.setText("");
                    JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
                }
            }
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        btncancel.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnnew.setEnabled(true);
        btnCari.setEnabled(false);
        btnhapus.setEnabled(false);
        txqty.setEditable(false);
        txqty.setText("");
        tbldetailorder.clearSelection();        // TODO add your handling code here:
       
    }//GEN-LAST:event_btncancelActionPerformed

    private void txNofakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txNofakActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txNofakActionPerformed

    private void txDiskonCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txDiskonCaretUpdate
        // TODO add your handling code here:
        if(txDiskon.getText().length()>0){
            int subtotal = Integer.parseInt(txtotal2.getText());
            int diskon = Integer.parseInt(txDiskon.getText());
            int ndiskon = subtotal*diskon/100;
            int total = subtotal - ndiskon;
            txGrandTotal.setText(Integer.toString(total));

        }
    }//GEN-LAST:event_txDiskonCaretUpdate

    private void txBayarCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txBayarCaretUpdate
        // TODO add your handling code here:
        if(txBayar.getText().length()>0){
            int bayar = Integer.parseInt(txBayar.getText());
            int total = bayar-Integer.parseInt(txGrandTotal.getText());
            txKembali.setText(Integer.toString(total));

            btnSave.setEnabled(true);
            btnReset.setEnabled(true);
            btnCetak.setEnabled(true);

        }
    }//GEN-LAST:event_txBayarCaretUpdate

    private void btnCetakActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCetakActionPerformed
       cetak();

//           simpandatatransaksi();
    }//GEN-LAST:event_btnCetakActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
         simpandatatransaksi();
         
//       String idbayar = txNofak.getText();
//        String kasir = txtnamapengguna.getText();
//        String idpelanggan = txidPelanggan.getText();
//        String namapelanggan = txpelanggan.getText();
//       int total = Integer.parseInt(txGrandTotal.getText());
//       int diskon = Integer.parseInt(txDiskon.getText());
//       int bayar = Integer.parseInt(txBayar.getText());
//       int kembali = Integer.parseInt(txKembali.getText());
//      
//
//       String tanggal = jLabel4.getText();
//       if(bayar<total){
//           JOptionPane.showMessageDialog(rootPane, "PEMBAYARAN KURANG");
//       }else{
//            for(int n = 0;n<tbldetailorder.getRowCount();n++){
//            try {
//                String idproduk = (String) tbldetailorder.getValueAt(n, 0);
//                String namaproduk = (String) tbldetailorder.getValueAt(n, 1);
//                int harga = (int) tbldetailorder.getValueAt(n, 2);
//                int qty = (int) tbldetailorder.getValueAt(n, 3);
//                int totall = (int) tbldetailorder.getValueAt(n, 4);
//                koneksi.setKoneksi();
//                String sqli = "SELECT stok from tbproduk where idproduk = '"+idproduk+"'";
//                Statement st = koneksi.koneksiDB().createStatement();
//                ResultSet rs = st.executeQuery(sqli);
//                while(rs.next()){
//                    int stok = rs.getInt("stok");
//                    int sisa = stok - qty;
//                    ct.updateStok(sisa, idproduk);
//                    
//                }
//                try {
//                    ct.insertBarang(idbayar, idproduk, namaproduk, harga, qty, totall);
//                } catch (SQLException ex) {
//                    Logger.getLogger(frmPembayaran.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            } catch (SQLException ex) {
//                Logger.getLogger(frmPembayaran.class.getName()).log(Level.SEVERE, null, ex);
//            }
//       }
//       
//        
//        try {
//            ct.simpanPenjualan(idbayar,kasir,idpelanggan, namapelanggan, diskon,total,bayar,kembali,tanggal);
//            JOptionPane.showMessageDialog(rootPane, "DATA BERHASIL DI SIMPAN");
//         
//               
//
//          
////            ct.noFak();
//        } catch (SQLException ex) {
//            Logger.getLogger(frmPembayaran.class.getName()).log(Level.SEVERE, null, ex);
//        } 
//      
//        
//         btnSave.setEnabled(false);
//         btnCetak.setEnabled(true);
//         btnCariPlg.setEnabled(false);
//         btnnew.setEnabled(false);
//       }
       // TODO add your handling code here:
       
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnResetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnResetActionPerformed
//        ct.Model.removeRow(0);
        tampilTTL.setText("0");
        tbldetailorder.removeAll();
//        ct.noFak();
        txpelanggan.setText("UMUM");
        txDiskon.setText("0");
        txtotal2.setText("");
        txGrandTotal.setText("");
        txBayar.setText("");
        txKembali.setText("");
        txBayar.setEditable(false);
        txDiskon.setEditable(false);
//        ct.Model.setRowCount(0);
        btnCetak.setEnabled(false);
        btnReset.setEnabled(false);
        btnCariPlg.setEnabled(false);
        btnSave.setEnabled(false);
        btnCetak.setEnabled(false);
        btnnew.setEnabled(true);   // TODO add your handling code here:
     

    }//GEN-LAST:event_btnResetActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int pesan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin keluar? ", "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            new frmUtama().setVisible(true);
            this.setVisible(false);
        } else {
            this.setVisible(true);
        }
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tbldetailorderMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbldetailorderMouseClicked
        selectdata();
        btnhapus.setEnabled(true);
        btnsimpan.setEnabled(false);
        btncancel.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_tbldetailorderMouseClicked

    private void txqtyKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txqtyKeyTyped
//             if(txqty.getText().length()==5){
//            evt.consume();
//        }        // TODO add your handling code here:
    }//GEN-LAST:event_txqtyKeyTyped

    private void btnCariPlgActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariPlgActionPerformed
        // TODO add your handling code here:
        new pelanggan().setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnCariPlgActionPerformed

    private void txqtyKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txqtyKeyReleased
        // TODO add your handling code here:
        hitungharga();
    }//GEN-LAST:event_txqtyKeyReleased

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        // TODO add your handling code here:
        simpandata();
        
        btnnew.setEnabled(true);
        btnCari.setEnabled(false);
        btnsimpan.setEnabled(false);
        btncancel.setEnabled(false);
        txDiskon.setEditable(true);
        txBayar.setEditable(true);
        btnCariPlg.setEnabled(true);
        txqty.setEditable(false);
        txqty.setText("");
        txnamaproduk.setText("");
        txidproduk.setText("");
        txBayar.requestFocus();
        
       
//        id_produk = txidproduk.getText();
//        nama_produk = txnamaproduk.getText();
//        qty = Integer.parseInt(txqty.getText());
//        total = harga*qty;
//        int total1 = 0;
//        int subTotal = 0;
//        int hargajual;
//        int qty1;
//        int total2 = 0;
//
//        if(txqty.getText().isEmpty()){
//            JOptionPane.showMessageDialog(this, "ISI QTY TERLEBIH DAHULU");
//        }else if(qty>=stok){
//            JOptionPane.showMessageDialog(this, "STOK TIDAK MENCUKUPI");
//        }else{
//            ct.tampilpembayaran(id_produk, nama_produk, harga, qty, total);
//            txidproduk.setText("");
//            txnamaproduk.setText("");
//            txqty.setText("");
//            for(int n =0;n<tbldetailorder.getRowCount();n++){
//                hargajual = (int) tbldetailorder.getValueAt(n, 2);
//                qty1 = (int) tbldetailorder.getValueAt(n, 3);
//                total1 = hargajual*qty;
//                total2 = (int) tbldetailorder.getValueAt(n, 4);
//
//                subTotal += total2;
//
//            }
//            tampilTTL.setText(Integer.toString(subTotal));
//            txtotal2.setText(Integer.toString(subTotal));
//            txDiskon.setText("0");
//
//        }
        
//        try {
//            String idProduk = txidproduk.getText();
//            String namaProduk = txnamaproduk.getText();
//            int qty = Integer.parseInt(txqty.getText());
//            int harga = Integer.parseInt(txharga.getText()); // Assuming you have txHarga for the product price
//            int stok = Integer.parseInt(txstok.getText()); // Assuming you have txStok for the product stock
//            int total = harga * qty;
//
//            if (txqty.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(this, "ISI QTY TERLEBIH DAHULU");
//            } else if (qty > stok) {
//                JOptionPane.showMessageDialog(this, "STOK TIDAK MENCUKUPI");
//            } else {
//                ct.tampilpembayaran(idProduk, namaProduk, harga, qty, total);
//
//                // Clear input fields after adding the item
//                txidproduk.setText("");
//                txnamaproduk.setText("");
//                txqty.setText("");
//
//                int subTotal = 0;
//
//                for (int n = 0; n < tbldetailorder.getRowCount(); n++) {
//                    int hargajual = (int) tbldetailorder.getValueAt(n, 2);
//                    int qty1 = (int) tbldetailorder.getValueAt(n, 3);
//                    int total2 = (int) tbldetailorder.getValueAt(n, 4);
//
//                    subTotal += total2;
//                }
//
//                tampilTTL.setText(Integer.toString(subTotal));
//                txtotal2.setText(Integer.toString(subTotal));
//                txDiskon.setText("0");
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(this, "Silakan masukkan nilai yang valid.");
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(this, "Terjadi kesalahan: " + e.getMessage());
//        }
        
    }//GEN-LAST:event_btnsimpanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPembayaran.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPembayaran().setVisible(true);
            }
        });
    }
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnCariPlg;
    private javax.swing.JButton btnCetak;
    private javax.swing.JButton btnReset;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnnew;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private keeptoo.KGradientPanel kGradientPanel1;
    private javax.swing.JLabel lbjam;
    private javax.swing.JLabel lbtanggal;
    private javax.swing.JLabel tampilTTL;
    private javax.swing.JTable tbldetailorder;
    private javax.swing.JTextField txBayar;
    public static javax.swing.JTextField txDiskon;
    public static javax.swing.JTextField txGrandTotal;
    private javax.swing.JTextField txKembali;
    public static javax.swing.JTextField txNofak;
    public static javax.swing.JTextField txharga;
    public static javax.swing.JTextField txidPelanggan;
    public static javax.swing.JTextField txidproduk;
    public static javax.swing.JTextField txnamaproduk;
    public static javax.swing.JTextField txpelanggan;
    public javax.swing.JTextField txqty;
    private javax.swing.JTextField txstok;
    private javax.swing.JLabel txtnamapengguna;
    public static javax.swing.JTextField txtotal;
    public static javax.swing.JTextField txtotal2;
    // End of variables declaration//GEN-END:variables
}
