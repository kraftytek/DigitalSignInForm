/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.swing.JList;

/**
 *
 * @author kraft
 */
public class searchForm extends javax.swing.JFrame {

    /**
     * Creates new form searchForm
     */
    public searchForm() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        searchResult = new javax.swing.JList<>();
        selButt = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        searchFormButt = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lastNameSearch = new javax.swing.JTextField();
        phoneNumberSearch = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Client");
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        searchResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(searchResult);

        selButt.setText("Select");
        selButt.setFocusPainted(false);
        selButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selButtActionPerformed(evt);
            }
        });

        jButton2.setText("Edit");
        jButton2.setFocusPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        searchFormButt.setText("Search");
        searchFormButt.setFocusPainted(false);
        searchFormButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFormButtActionPerformed(evt);
            }
        });

        jLabel1.setText("Last Name:");

        jLabel2.setText("Phone Number:");

        lastNameSearch.setNextFocusableComponent(searchFormButt);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lastNameSearch)
                            .addComponent(phoneNumberSearch, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                        .addComponent(searchFormButt)
                        .addGap(12, 12, 12)
                        .addComponent(selButt)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(selButt)
                        .addComponent(jButton2)
                        .addComponent(searchFormButt))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(lastNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(phoneNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selButtActionPerformed
        String selClient = searchResult.getSelectedValue();
        String cleanClientID = selClient.substring(11, 15);
        System.out.println(selClient);
        System.out.println(cleanClientID);

        String connectionUrl
                = "jdbc:sqlserver://sql.kraftytek.ca:1433;"
                + "encrypt=false;"
                + "databaseName=NCRO_WorkOrders;"
                + "user=sa;"
                + "password=S!lver88";

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select top 1 client_id, fname, lname, phone, phone2, email, companyName, creation_date
                                  from clients
                                  where client_id = """ + cleanClientID;

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone");
                String cellNumber = searchQ.getString("phone2");
                String emailText = searchQ.getString("email");
                //String companyNameText = searchQ.getString("companyName");
                //String creationDate = searchQ.getString("creation_date");
                String clientID = searchQ.getString("client_id");

                SignInFront.fNameText.setText(firstName);
                SignInFront.lNameText.setText(lastName);
                SignInFront.phoneOneText.setText(phoneNumber);
                SignInFront.cellPhoneText.setText(cellNumber);
                SignInFront.eMailText.setText(emailText);
                SignInFront.clientIDText.setText(clientID);

            }

        } catch (SQLException e) {
        }
        dispose();
    }//GEN-LAST:event_selButtActionPerformed

    private void searchFormButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFormButtActionPerformed
        String txtLName = lastNameSearch.getText();

        String connectionUrl
                = "jdbc:sqlserver://sql.kraftytek.ca:1433;"
                + "encrypt=false;"
                + "databaseName=NCRO_WorkOrders;"
                + "user=sa;"
                + "password=S!lver88";

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select fname, lname, phone, client_id
                                  from clients
                                  where lname like '""" + txtLName + "'";

            Vector<String> resList = new Vector<>();
            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {

                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone");
                int clientID = searchQ.getInt("client_id");
                Collections.addAll(resList, "Client ID: " + clientID + ", Name: " + firstName + ", " + lastName + ", Phone Number: " + phoneNumber);
            }

            searchResult.setListData(resList);
            searchResult.setSelectedIndex(0);

        } catch (SQLException e) {
        }
    }//GEN-LAST:event_searchFormButtActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        lastNameSearch.requestFocus();
    }//GEN-LAST:event_formWindowGainedFocus

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(searchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(searchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(searchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(searchForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new searchForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameSearch;
    private javax.swing.JTextField phoneNumberSearch;
    private javax.swing.JButton searchFormButt;
    private javax.swing.JList<String> searchResult;
    private javax.swing.JButton selButt;
    // End of variables declaration//GEN-END:variables
}
