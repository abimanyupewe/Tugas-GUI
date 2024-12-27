/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.tugas_logindanregister;

import static com.mycompany.tugas_logindanregister.Register.nama;
import javax.swing.JOptionPane;

/**
 *
 * @author Lenovo
 */
public class homePage extends javax.swing.JFrame {

    /**
     * Creates new form homePage
     */
    int confirm;

    public homePage() {
        initComponents();
        
        JOptionPane.showMessageDialog(this, "Hallo, Selamat datang" + nama + "diprogram pendataan Logistik.", "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuInput = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        menuProfile = new javax.swing.JMenuItem();
        menuLogout = new javax.swing.JMenuItem();
        menuKeluar = new javax.swing.JMenuItem();

        jLabel1.setText("jLabel1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jMenu1.setText("Menu");

        menuInput.setText("Input");
        menuInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuInputActionPerformed(evt);
            }
        });
        jMenu1.add(menuInput);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Pengaturan");

        menuProfile.setText("Profile");
        menuProfile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuProfileActionPerformed(evt);
            }
        });
        jMenu2.add(menuProfile);

        menuLogout.setText("Logout");
        menuLogout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuLogoutActionPerformed(evt);
            }
        });
        jMenu2.add(menuLogout);

        menuKeluar.setText("Keluar");
        menuKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuKeluarActionPerformed(evt);
            }
        });
        jMenu2.add(menuKeluar);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 567, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 342, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuLogoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuLogoutActionPerformed
        // TODO add your handling code here:
        confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin logout ?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();

            Login Lg = new Login();
            Lg.setVisible(true);
            Lg.setLocationRelativeTo(null);
        }
    }//GEN-LAST:event_menuLogoutActionPerformed

    private void menuKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuKeluarActionPerformed
        // TODO add your handling code here:
        confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin keluar dari program ?",
                "Konfirmasi", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            dispose();
            JOptionPane.showMessageDialog(this, "Anda keluar dari program, Terimakasih.", "Info", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_menuKeluarActionPerformed

    private void menuProfileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuProfileActionPerformed
        // TODO add your handling code here:
        dispose();   

        Profile profil = new Profile();
        profil.setVisible(true);
        profil.setLocationRelativeTo(null);
    }//GEN-LAST:event_menuProfileActionPerformed

    private void menuInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuInputActionPerformed
        // TODO add your handling code here:
        dispose();

        inputData fromInput = new inputData();
        fromInput.setVisible(true);
        fromInput.setLocationRelativeTo(null);
    }//GEN-LAST:event_menuInputActionPerformed

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
            java.util.logging.Logger.getLogger(homePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(homePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(homePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(homePage.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new homePage().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem menuInput;
    private javax.swing.JMenuItem menuKeluar;
    private javax.swing.JMenuItem menuLogout;
    private javax.swing.JMenuItem menuProfile;
    // End of variables declaration//GEN-END:variables
}
