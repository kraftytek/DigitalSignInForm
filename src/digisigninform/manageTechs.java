/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import static digisigninform.SignInFront.checkDesktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;

/**
 *
 * @author kraft
 */
public class manageTechs extends javax.swing.JFrame {

    /**
     * Creates new form manageTechs
     */
    public manageTechs() {
        initComponents();
    }

    public static ArrayList<String> getValues() {
        FileInputStream stream = null;
        String userDir = System.getProperty("user.dir");
        try {
            stream = new FileInputStream(userDir + "/config.txt");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<String>();
        try {
            while ((strLine = reader.readLine()) != null) {
                String lastWord = strLine.substring(strLine.lastIndexOf(" ") + 1);
                lines.add(lastWord);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    public ArrayList<String> configList = getValues();
    public String connectionUrl = configList.get(0);

    public void loadFields() {
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String populateList = "select tech_fname, left(tech_lname, 1) as lastInitial, active\n"
                    + "from assigned_techs\n";
            Vector<String> techList = new Vector<>();
            ResultSet searchQ = statement.executeQuery(populateList);

            while (searchQ.next()) {
                String techFname = searchQ.getString("tech_fname");
                String lastInitial = searchQ.getString("lastInitial");
                
                Boolean isActive = searchQ.getBoolean("active");              
                
                if(isActive == true){
                    activeCheck.setSelected(isActive);
                }else {
                    activeCheck.setSelected(false);
                }

                Collections.addAll(techList, techFname + ", " + lastInitial);
            }

            DefaultComboBoxModel model = new DefaultComboBoxModel(techList);
            techComboBox1.setModel(model);

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void changeActive() {
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String fullName = techComboBox1.getSelectedItem().toString();
            System.out.println(fullName);
            String[] nameList = fullName.split(", ", 0);
            String fnameString = nameList[0];
            String lnameString = nameList[1];
            boolean activeBox = activeCheck.isSelected();
            int desktopBool = (activeBox) ? 1 : 0;

            System.out.println(fnameString + "," + lnameString);

            String updateActiveQue = "declare @fname as varchar(max)\n"
                    + "declare @lname as varchar(max)\n"
                    + "\n"
                    + "set @fname = '" + fnameString + "'\n"
                    + "set @lname = '" + lnameString + "%'\n"
                    + "\n"
                    + "update assigned_techs set active = " + desktopBool + "\n"
                    + "from assigned_techs\n"
                    + "where tech_fname like @fname\n"
                    + "and tech_lname like @lname";

            statement.executeUpdate(updateActiveQue);
            loadFields();

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
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

        jPanel1 = new javax.swing.JPanel();
        fnameText = new javax.swing.JTextField();
        lnameText = new javax.swing.JTextField();
        addButt = new javax.swing.JButton();
        techComboBox1 = new javax.swing.JComboBox<>();
        activeCheck = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        fnameText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "First Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        lnameText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "Last Name", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.TOP));

        addButt.setBackground(new java.awt.Color(255, 255, 255));
        addButt.setForeground(new java.awt.Color(0, 0, 0));
        addButt.setText("Add");
        addButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addButt.setFocusPainted(false);
        addButt.setFocusable(false);
        addButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtActionPerformed(evt);
            }
        });

        techComboBox1.setBackground(new java.awt.Color(255, 255, 255));
        techComboBox1.setForeground(new java.awt.Color(0, 0, 0));
        techComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        activeCheck.setBackground(new java.awt.Color(255, 255, 255));
        activeCheck.setForeground(new java.awt.Color(0, 0, 0));
        activeCheck.setText("Active");
        activeCheck.setFocusPainted(false);
        activeCheck.setFocusable(false);
        activeCheck.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                activeCheckActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(techComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(addButt, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lnameText, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(activeCheck))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lnameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addButt, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(activeCheck, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(techComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        loadFields();

    }//GEN-LAST:event_formWindowActivated

    private void addButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtActionPerformed
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String fnameString = fnameText.getText();
            String lnameString = lnameText.getText();

            String addTechQue = "declare @fname as varchar(max)\n"
                    + "declare @lname as varchar(max)\n"
                    + "set @fname = '" + fnameString + "'\n"
                    + "set @lname = '" + lnameString + "'\n"
                    + "insert into assigned_techs(tech_fname, tech_lname, active)\n"
                    + "values(@fname, @lname, 1)";

            statement.executeUpdate(addTechQue);
            loadFields();

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_addButtActionPerformed

    private void activeCheckActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activeCheckActionPerformed
        changeActive();
        loadFields();
    }//GEN-LAST:event_activeCheckActionPerformed

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
            java.util.logging.Logger.getLogger(manageTechs.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(manageTechs.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(manageTechs.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(manageTechs.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new manageTechs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JCheckBox activeCheck;
    private javax.swing.JButton addButt;
    public static javax.swing.JTextField fnameText;
    private javax.swing.JPanel jPanel1;
    public static javax.swing.JTextField lnameText;
    public static javax.swing.JComboBox<String> techComboBox1;
    // End of variables declaration//GEN-END:variables
}
