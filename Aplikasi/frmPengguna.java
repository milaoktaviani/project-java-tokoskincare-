/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mila Oktaviani
 */
public class frmPengguna extends javax.swing.JFrame {
    private DefaultTableModel model;
    private DefaultTableModel model2;
    String idpengguna, idpegawai, namapengguna, username, password, jabatan;
    /**
     * Creates new form frmPengguna
     */
    public frmPengguna() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        model = new DefaultTableModel();
        tblpengguna.setModel(model);
        model.addColumn("Id Pengguna");
        model.addColumn("Id Pegawai");
        model.addColumn("Nama Pengguna");
        model.addColumn("Username");
        model.addColumn("Password");
        model.addColumn("Jabatan");
        
        model2 = new DefaultTableModel();
        tblpegawai.setModel(model2);
        model2.addColumn("Id Pegawai");
        model2.addColumn("Nama Pegawai");
        model2.addColumn("Jabatan");
        
        GetData();
        getDataPegawai();
        nonaktif();
        idotomatis();
        loaddata();
        selectdata();
    }
    
    public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbpengguna";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[6];
                obj[0] = rs.getString("idpengguna");
                obj[1] = rs.getString("idpegawai");
                obj[2] = rs.getString("namapengguna");
                obj[3] = rs.getString("username");
                obj[4] = rs.getString("password");
                obj[5] = rs.getString("jabatan");
                
                model.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    private void getDataPegawai() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            Statement st = (Statement)koneksi.koneksiDB().createStatement();
            String sql ="select * from tbpegawai";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[3];
                obj[0] = rs.getString("idpegawai");
                obj[1] = rs.getString("namapegawai");
                obj[2] = rs.getString("jabatan");
                
                model2.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
     public void nonaktif() {
        txtidpengguna.setEnabled(false);
        txtidpegawai.setEnabled(false);
        txtnamapengguna.setEnabled(false);
        txtusername.setEnabled(false);
        txtpassword.setEnabled(false);
        cbjabatan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
        btnsimpan.setEnabled(false);
        btncancel.setEnabled(true);
        tblpegawai.setEnabled(false);
        txtcari.setEnabled(false);
    }
    
    public void aktif() {
        txtidpengguna.setEnabled(false);
        txtidpegawai.setEnabled(false);
        txtnamapengguna.setEnabled(false);
        txtusername.setEnabled(true);
        txtpassword.setEnabled(true);
        cbjabatan.setEnabled(true);
        btnedit.setEnabled(true);
        btnhapus.setEnabled(true);
        btnsimpan.setEnabled(true);
        btncancel.setEnabled(true);
        txtcari.setEnabled(true);
    }
    
     public void idotomatis() {
        try {
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM tbpengguna order by idpengguna desc";
            ResultSet rs = st.executeQuery(sql);
            
            if(rs.next()) {
                String id = rs.getString("idpengguna");
                if (id.length() >= 4) {
                    String AN = "" + (Integer.parseInt(id.substring(3))+1);
                    String nol = "";
                    
                    if (AN.length()==1)
                        {nol="000";}
                    else if (AN.length()==2)
                        {nol="00";}
                    else if (AN.length() ==3) 
                        {nol="";}
                    txtidpengguna.setText("PG"+nol+AN);
                }else {
                txtidpengguna.setText("PG0001");
            }                    
            }
        }catch (Exception error){
            JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
     
      public void loaddata() {
        idpengguna = txtidpengguna.getText();
        idpegawai = txtidpegawai.getText();
        namapengguna = txtnamapengguna.getText();
        username = txtusername.getText();
        password = txtpassword.getText();
        jabatan = (String) cbjabatan.getSelectedItem();
    }
    
    public void simpandata() {
        loaddata();
        try {
           Connection conn = koneksi.koneksiDB();
           Statement stat = conn.createStatement();
           String sql="INSERT INTO tbpengguna(idpengguna,idpegawai,namapengguna,username,password,jabatan)"+"values"+"('"+idpengguna+"',"
                       + "'"+idpegawai+"','"+namapengguna+"','"+username+"','"+password+"','"+jabatan+"')";
           PreparedStatement p = (PreparedStatement)koneksi.koneksiDB().prepareStatement(sql);
           p.executeUpdate();
           GetData();
           nonaktif();
           kosongkan();
           JOptionPane.showMessageDialog(null, "Data berhasil disimpan!");
           
        } catch (SQLException x){
            JOptionPane.showMessageDialog(null, x.getMessage());
        }
    }
    
    public void selectdata(){
        int i = tblpengguna.getSelectedRow();
        if (i== -1){
            return;
        }
        txtidpengguna.setText(""+model.getValueAt(i, 0));
        txtidpegawai.setText(""+model.getValueAt(i, 1));
        txtnamapengguna.setText(""+model.getValueAt(i, 2));
        txtusername.setText(""+model.getValueAt(i, 3));
        txtpassword.setText(""+model.getValueAt(i, 4));
        cbjabatan.setSelectedItem(""+model.getValueAt(i, 5));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
    }
    
    public void selectdataPegawai() {
        int i = tblpegawai.getSelectedRow();
        if (i == -1) {
            return;
        }
        txtidpegawai.setText(""+model2.getValueAt(i, 0));
        txtnamapengguna.setText(""+model2.getValueAt(i, 1));
    }
    
    public void ubahdata(){
        loaddata();
        try{
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "Update tbpengguna set idpegawai='"+idpegawai+"',"+"namapengguna='"+namapengguna+"',"+"username='"+username+"',"+"password='"+password+"',"+"jabatan='"+jabatan+"' WHERE idpengguna='"+idpengguna+"'";
            PreparedStatement p=(PreparedStatement) koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                selectdata();
                 
                JOptionPane.showMessageDialog(null, "Data Berhasil Dirubah");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }

    
    public void kosongkan(){
        txtidpengguna.setText("");
        txtidpegawai.setText("");
        txtnamapengguna.setText("");
        txtusername.setText("");
        txtpassword.setText("");
        cbjabatan.setSelectedItem("");
    }
    
     private void cari() {
        model2.getDataVector().removeAllElements();
        model2.fireTableDataChanged();
        try {
            Statement st = koneksi.koneksiDB().createStatement();
            String sql = "SELECT * FROM tbpegawai where namapegawai like '%" + txtcari.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[3];
                obj[0] = rs.getString("idpegawai");
                obj[1] = rs.getString("namapegawai");
                obj[2] = rs.getString("jenispekerjaan");
                
                model2.addRow(obj);
            }
        }catch(SQLException error) {
            JOptionPane.showMessageDialog(null, error.getMessage());
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

        cbjabatan = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        txtidpegawai = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        btntambah = new javax.swing.JButton();
        txtcari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblpengguna = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpegawai = new javax.swing.JTable();
        txtpassword = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtnamapengguna = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtidpengguna = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cbjabatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "Admin", "Kasir" })
        );
        getContentPane().add(cbjabatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 250, 30));

        jLabel6.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 0, 102));
        jLabel6.setText("ID Pegawai");
        getContentPane().add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));
        getContentPane().add(txtidpegawai, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 210, 250, 30));

        jLabel10.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 0, 102));
        jLabel10.setText("Jabatan");
        getContentPane().add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 410, -1, -1));

        btnsimpan.setBackground(new java.awt.Color(242, 227, 230));
        btnsimpan.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnsimpan.setForeground(new java.awt.Color(0, 0, 102));
        btnsimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-save-30.png"))); // NOI18N
        btnsimpan.setText("Simpan");
        btnsimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsimpanActionPerformed(evt);
            }
        });
        getContentPane().add(btnsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 610, 120, 50));

        btnedit.setBackground(new java.awt.Color(242, 227, 230));
        btnedit.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnedit.setForeground(new java.awt.Color(0, 0, 102));
        btnedit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-edit-30.png"))); // NOI18N
        btnedit.setText("Edit");
        btnedit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditActionPerformed(evt);
            }
        });
        getContentPane().add(btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 610, 120, 50));

        btnhapus.setBackground(new java.awt.Color(242, 227, 230));
        btnhapus.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btnhapus.setForeground(new java.awt.Color(0, 0, 102));
        btnhapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-delete-trash-30.png"))); // NOI18N
        btnhapus.setText("Hapus");
        btnhapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnhapusActionPerformed(evt);
            }
        });
        getContentPane().add(btnhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 610, 120, 50));

        btncancel.setBackground(new java.awt.Color(242, 227, 230));
        btncancel.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btncancel.setForeground(new java.awt.Color(0, 0, 102));
        btncancel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/cancel.png"))); // NOI18N
        btncancel.setText("Cancel");
        btncancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncancelActionPerformed(evt);
            }
        });
        getContentPane().add(btncancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 610, 120, 50));

        btntambah.setBackground(new java.awt.Color(242, 227, 230));
        btntambah.setFont(new java.awt.Font("Century Gothic", 1, 14)); // NOI18N
        btntambah.setForeground(new java.awt.Color(0, 0, 102));
        btntambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Icon/icons8-add-25.png"))); // NOI18N
        btntambah.setText("Tambah");
        btntambah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btntambahActionPerformed(evt);
            }
        });
        getContentPane().add(btntambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 510, 120, 50));
        getContentPane().add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 150, 180, -1));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("Cari Pegawai:");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 150, -1, -1));

        tblpengguna.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpengguna.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpenggunaMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblpengguna);

        getContentPane().add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 410, 560, 170));

        tblpegawai.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        tblpegawai.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpegawai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpegawaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpegawai);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 180, 560, 170));
        getContentPane().add(txtpassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 360, 250, 30));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("Password");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 360, -1, -1));
        getContentPane().add(txtusername, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 310, 250, 30));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Username");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));
        getContentPane().add(txtnamapengguna, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 260, 250, 30));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Nama Pengguna");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));
        getContentPane().add(txtidpengguna, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 160, 250, 30));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("ID Pengguna");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Castellar", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Data Pengguna");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 410, -1));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/atasmarble1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 120));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/close-up-white-marble-texture-background.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1090, 480));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bawahmarble1.jpg"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1090, 120));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();  
        btnhapus.setEnabled(false);
        btnedit.setEnabled(false);
        btntambah.setEnabled(false);
        selectdata();
        tblpegawai.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        simpandata(); 
        nonaktif();
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        ubahdata();
        nonaktif();
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void tblpenggunaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpenggunaMouseClicked
        selectdata();
        aktif();
        btntambah.setEnabled(false);
        btnsimpan.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_tblpenggunaMouseClicked

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        nonaktif();
        btntambah.setEnabled(true);
        loaddata();
        int pesan = JOptionPane.showConfirmDialog(null, "Apakah Anda yakin ingin menghapus data ini? " + idpengguna, "Konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if (pesan == JOptionPane.OK_OPTION) {
            try {
                Connection conn = koneksi.koneksiDB();
                String sql = "DELETE FROM tbpengguna WHERE idpengguna = ?";
                PreparedStatement p = conn.prepareStatement(sql);
                p.setString(1, idpengguna);
                p.executeUpdate();
                GetData();

                JOptionPane.showMessageDialog(null, "Data berhasil dihapus!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
        kosongkan();        // TODO add your handling code here:
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        new frmUtama().setVisible(true);
        this.setVisible(false);        // TODO add your handling code here:
    }//GEN-LAST:event_btncancelActionPerformed

    private void tblpegawaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpegawaiMouseClicked
        selectdataPegawai();        // TODO add your handling code here:
    }//GEN-LAST:event_tblpegawaiMouseClicked

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
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPengguna.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPengguna().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JComboBox<String> cbjabatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblpegawai;
    private javax.swing.JTable tblpengguna;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtidpegawai;
    private javax.swing.JTextField txtidpengguna;
    private javax.swing.JTextField txtnamapengguna;
    private javax.swing.JTextField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
