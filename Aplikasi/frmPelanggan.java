/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Aplikasi;
import com.toedter.calendar.JDateChooser;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mila Oktaviani
 */
public class frmPelanggan extends javax.swing.JFrame {
private DefaultTableModel model;
String idpelanggan, namapelanggan, jk, tempatlahir, tanggallahir, alamat, nohp;
    /**
     * Creates new form frmPelanggan
     */
    public frmPelanggan() {
        initComponents();
        this.setLocationRelativeTo(null);
        
        model = new DefaultTableModel();
        tblpelanggan.setModel(model);
        model.addColumn("Id Pelanggan");
        model.addColumn("Nama Pelanggan");
        model.addColumn("Alamat");
        model.addColumn("No Telpon");
        
        GetData();
        nonaktif();
    }

     public void GetData(){
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Statement st = (Statement)koneksi.koneksiDB().createStatement();
            String sql ="select * from tbpelanggan";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()){
                Object[] obj = new Object[4];
                obj[0] = rs.getString("idpelanggan");
                obj[1] = rs.getString("namapelanggan");
                obj[2] = rs.getString("alamat");
                obj[3] = rs.getString("nohp");
                
                model.addRow(obj);
            }
    }catch (SQLException error){
        JOptionPane.showMessageDialog(null, error.getMessage());  
        }
    }
     
     public void nonaktif() {
        txtidpelanggan.setEnabled(false);
        txtnamapelanggan.setEnabled(false);
        txtalamat.setEnabled(false);
        txtnotelp.setEnabled(false);
        btnsimpan.setEnabled(false);
        btnedit.setEnabled(false);
        btnhapus.setEnabled(false);
    }
    
    public void aktif() {
        txtnamapelanggan.setEnabled(true);
        txtnamapelanggan.requestFocus();
        txtalamat.setEnabled(true);
        txtnotelp.setEnabled(true);
        btnsimpan.setEnabled(true);
    }
    
    public void idotomatis(){
        try{
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql="select * from tbpelanggan order by idpelanggan desc";
            ResultSet rs=st.executeQuery(sql);
            if(rs.next()){
                String id=rs.getString("idpelanggan").substring(3);
                String AN=""+(Integer.parseInt(id)+1);
                String nol = "";
                 
                if(AN.length()==1)
                   {nol="000";}
                else if(AN.length()==2)
                   {nol="00";}
                else if(AN.length()==3)
                   {nol="";}
                   txtidpelanggan.setText("PL"+nol+AN);
            }else {
                   txtidpelanggan.setText("PL0001");
            }
        }catch (SQLException error) {
             JOptionPane.showMessageDialog(null, error.getMessage());
        }
    }
    
    public void loaddata(){
        idpelanggan=txtidpelanggan.getText();
        namapelanggan=txtnamapelanggan.getText();
        alamat=txtalamat.getText();
        nohp=txtnotelp.getText();
    }
    
    public void simpandata(){
        loaddata();
        try {
            Connection conn = koneksi.koneksiDB();
            Statement stat = conn.createStatement();
            String sql = "INSERT INTO tbpelanggan (idpelanggan, namapelanggan, alamat, nohp) VALUES "
                   + "('" + idpelanggan + "','" + namapelanggan + "','" + alamat + "','" + nohp + "')";
                PreparedStatement p = (PreparedStatement)koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                nonaktif();
                kosongkan();
                JOptionPane.showMessageDialog(null, "Data Berhasil Disimpan"); 
                
                idotomatis();
        }catch (SQLException ex) {
                    Logger.getLogger(frmPelanggan.class.getName()).log(Level.SEVERE, null, ex);
                }
    }
    
    public void selectdata(){
        int i = tblpelanggan.getSelectedRow();
        if (i== -1){
            return;
        }
        txtidpelanggan.setText(""+model.getValueAt(i, 0));
        txtnamapelanggan.setText(""+model.getValueAt(i, 1));
        txtalamat.setText(""+model.getValueAt(i, 2));
        txtnotelp.setText(""+model.getValueAt(i, 3));
        btnhapus.setEnabled(true);
        btnedit.setEnabled(true);
    }
     
    public void ubahdata(){
        loaddata();
        try{
            Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
            String sql = "Update tbpelanggan set namapelanggan='"+namapelanggan+"',"+"alamat='"+alamat+"',"+"nohp='"+nohp+"' WHERE idpelanggan='"+idpelanggan+"'";
            PreparedStatement p=(PreparedStatement) koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                selectdata();
                 
                JOptionPane.showMessageDialog(null, "Data Berhasil Dirubah");
        }catch(SQLException err){
            JOptionPane.showMessageDialog(null, err.getMessage());
        }
    }
    
    public void hapusdata(){
        loaddata();
        int pesan=JOptionPane.showConfirmDialog(null, "anda yakin ingin menghapus data pelanggan "+idpelanggan+"?","konfirmasi", JOptionPane.OK_CANCEL_OPTION);
        if(pesan == JOptionPane.OK_OPTION){
            try{
                Connection conn = koneksi.koneksiDB();
            Statement st = conn.createStatement();
                String sql="DELETE from tbpelanggan Where idpelanggan='"+idpelanggan+"'";
                PreparedStatement p=(PreparedStatement) koneksi.koneksiDB().prepareStatement(sql);
                p.executeUpdate();
                GetData();
                 
                JOptionPane.showMessageDialog(null, "Data Berhasil Dihapus");
            } catch(SQLException err) {
                JOptionPane.showConfirmDialog(null, err.getMessage());
            }
        }
    }
     
    public void kosongkan(){
        txtnamapelanggan.setText("");
        txtalamat.setText("");
        txtnotelp.setText("");
    }
    
    public void cari() {
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();
        try {
            Connection con = koneksi.koneksiDB();
            Statement st = con.createStatement();
            String sql =  "select * from tbpelanggan where namapelanggan like '%" + txtcari.getText() + "%'";
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                Object[] obj = new Object[4];
                obj[0] = rs.getString(1);
                obj[1] = rs.getString(2);
                obj[2] = rs.getString(3);
                obj[3] = rs.getString(4);
               
                model.addRow(obj);
            }
        }catch (SQLException error) {
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

        txtcari = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btnsimpan = new javax.swing.JButton();
        btnedit = new javax.swing.JButton();
        btnhapus = new javax.swing.JButton();
        btncancel = new javax.swing.JButton();
        btntambah = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblpelanggan = new javax.swing.JTable();
        txtnotelp = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtalamat = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtnamapelanggan = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtidpelanggan = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtcari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtcariKeyTyped(evt);
            }
        });
        getContentPane().add(txtcari, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 160, 210, 30));

        jLabel11.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(0, 0, 102));
        jLabel11.setText("Cari Pelanggan");
        getContentPane().add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 160, -1, 30));

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
        getContentPane().add(btnsimpan, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 520, 120, 50));

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
        getContentPane().add(btnedit, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 520, 120, 50));

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
        getContentPane().add(btnhapus, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 520, 120, 50));

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
        getContentPane().add(btncancel, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 520, 120, 50));

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
        getContentPane().add(btntambah, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 380, -1, 50));

        tblpelanggan.setFont(new java.awt.Font("Century Gothic", 0, 16)); // NOI18N
        tblpelanggan.setModel(new javax.swing.table.DefaultTableModel(
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
        tblpelanggan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblpelangganMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblpelanggan);

        getContentPane().add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 210, 560, 270));
        getContentPane().add(txtnotelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 310, 250, 30));

        jLabel8.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 0, 102));
        jLabel8.setText("No Telpon");
        getContentPane().add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 310, -1, -1));
        getContentPane().add(txtalamat, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 260, 250, 30));

        jLabel7.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(0, 0, 102));
        jLabel7.setText("Alamat");
        getContentPane().add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 260, -1, -1));
        getContentPane().add(txtnamapelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 210, 250, 30));

        jLabel5.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 0, 102));
        jLabel5.setText("Nama ");
        getContentPane().add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 210, -1, -1));
        getContentPane().add(txtidpelanggan, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 160, 250, 30));

        jLabel4.setFont(new java.awt.Font("Century Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 102));
        jLabel4.setText("ID Pelanggan");
        getContentPane().add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 160, -1, -1));

        jLabel3.setFont(new java.awt.Font("Castellar", 1, 36)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 102));
        jLabel3.setText("Data Pelanggan");
        getContentPane().add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 480, 50));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/atasmarble1.jpg"))); // NOI18N
        getContentPane().add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1090, 120));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/close-up-white-marble-texture-background.jpg"))); // NOI18N
        getContentPane().add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 1090, 480));

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Image/bawahmarble1.jpg"))); // NOI18N
        getContentPane().add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 600, 1090, 120));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtcariKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtcariKeyTyped
        cari();        // TODO add your handling code here:
    }//GEN-LAST:event_txtcariKeyTyped

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        aktif();
        idotomatis();  
        btnhapus.setEnabled(false);
        btnedit.setEnabled(false);
        btntambah.setEnabled(false);
        kosongkan();        // TODO add your handling code here:
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnsimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsimpanActionPerformed
        simpandata(); 
        nonaktif();
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnsimpanActionPerformed

    private void btneditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditActionPerformed
        ubahdata();
        nonaktif();
        kosongkan();
        btnedit.setEnabled(false);
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btneditActionPerformed

    private void btnhapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnhapusActionPerformed
        hapusdata();
        kosongkan();
        nonaktif();
        btntambah.setEnabled(true);        // TODO add your handling code here:
    }//GEN-LAST:event_btnhapusActionPerformed

    private void btncancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncancelActionPerformed
        new frmUtama().setVisible(true);
        this.setVisible(false);         // TODO add your handling code here:
    }//GEN-LAST:event_btncancelActionPerformed

    private void tblpelangganMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblpelangganMouseClicked
        selectdata();
        aktif();
        btnsimpan.setEnabled(false);
        btntambah.setEnabled(false);        // TODO add your handling code here:
    }//GEN-LAST:event_tblpelangganMouseClicked

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
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmPelanggan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmPelanggan().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncancel;
    private javax.swing.JButton btnedit;
    private javax.swing.JButton btnhapus;
    private javax.swing.JButton btnsimpan;
    private javax.swing.JButton btntambah;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblpelanggan;
    private javax.swing.JTextField txtalamat;
    private javax.swing.JTextField txtcari;
    private javax.swing.JTextField txtidpelanggan;
    private javax.swing.JTextField txtnamapelanggan;
    private javax.swing.JTextField txtnotelp;
    // End of variables declaration//GEN-END:variables
}
