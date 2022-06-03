/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import static digisigninform.SignInFront.fNameText;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kraft
 */
public class SearchForm extends javax.swing.JFrame {

    /**
     * Creates new form searchForm
     */
    public SearchForm() {
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

        backGroundPanel = new javax.swing.JPanel();
        searchExistingButt = new javax.swing.JButton();
        phoneNumberSearch = new javax.swing.JTextField();
        lastNameSearch = new javax.swing.JTextField();
        searchFormButt = new javax.swing.JButton();
        selButt = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        searchResult = new javax.swing.JList<>();
        selectWorkOrderButt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Client");
        setMinimumSize(new java.awt.Dimension(552, 500));
        setPreferredSize(new java.awt.Dimension(552, 500));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        backGroundPanel.setBackground(new java.awt.Color(255, 255, 255));
        backGroundPanel.setPreferredSize(new java.awt.Dimension(552, 500));

        searchExistingButt.setBackground(new java.awt.Color(255, 255, 255));
        searchExistingButt.setForeground(new java.awt.Color(0, 0, 0));
        searchExistingButt.setText("Search Existing WO");
        searchExistingButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        searchExistingButt.setFocusable(false);
        searchExistingButt.setOpaque(true);
        searchExistingButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchExistingButtActionPerformed(evt);
            }
        });

        phoneNumberSearch.setBackground(new java.awt.Color(255, 255, 255));
        phoneNumberSearch.setForeground(new java.awt.Color(0, 0, 0));
        phoneNumberSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Phone Number", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        phoneNumberSearch.setOpaque(true);

        lastNameSearch.setBackground(new java.awt.Color(255, 255, 255));
        lastNameSearch.setForeground(new java.awt.Color(0, 0, 0));
        lastNameSearch.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Last Name", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        lastNameSearch.setNextFocusableComponent(searchFormButt);
        lastNameSearch.setOpaque(true);

        searchFormButt.setBackground(new java.awt.Color(255, 255, 255));
        searchFormButt.setForeground(new java.awt.Color(0, 0, 0));
        searchFormButt.setText("Search");
        searchFormButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        searchFormButt.setFocusPainted(false);
        searchFormButt.setFocusable(false);
        searchFormButt.setOpaque(true);
        searchFormButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchFormButtActionPerformed(evt);
            }
        });

        selButt.setBackground(new java.awt.Color(255, 255, 255));
        selButt.setForeground(new java.awt.Color(0, 0, 0));
        selButt.setText("Select");
        selButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        selButt.setFocusPainted(false);
        selButt.setFocusable(false);
        selButt.setOpaque(true);
        selButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selButtActionPerformed(evt);
            }
        });

        searchResult.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        searchResult.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jScrollPane1.setViewportView(searchResult);

        selectWorkOrderButt.setBackground(new java.awt.Color(255, 255, 255));
        selectWorkOrderButt.setForeground(new java.awt.Color(0, 0, 0));
        selectWorkOrderButt.setText("Select WO");
        selectWorkOrderButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        selectWorkOrderButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectWorkOrderButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backGroundPanelLayout = new javax.swing.GroupLayout(backGroundPanel);
        backGroundPanel.setLayout(backGroundPanelLayout);
        backGroundPanelLayout.setHorizontalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backGroundPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addComponent(searchFormButt, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selButt, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lastNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addComponent(searchExistingButt, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectWorkOrderButt, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(phoneNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        backGroundPanelLayout.setVerticalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lastNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(phoneNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchExistingButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchFormButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectWorkOrderButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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


    private void selButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selButtActionPerformed
        String selClient = searchResult.getSelectedValue();
        String selWONum = searchResult.getSelectedValue();
        Pattern pattern = Pattern.compile("Client ID: ");
        Matcher matcher = pattern.matcher(selWONum);

        int endWO = 0;
        while (matcher.find()) {
            endWO = matcher.end();
        }

        String cleanClientID = selClient.substring(endWO, selWONum.indexOf(","));

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select top 1 client_id, fname, lname, phone, phone2, email, companyName, creation_date
                                  from clients
                                  where client_id = """ + cleanClientID;

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone").replace("-", "");
                String cellNumber = searchQ.getString("phone2").replace("-", "");
                String emailText = searchQ.getString("email");
                String clientID = searchQ.getString("client_id");
                String compName = searchQ.getString("companyName");

                String phoneFormat;
                String cellFormat;

                if (phoneNumber.length() > 0) {
                    phoneFormat = "(" + phoneNumber.substring(0, 3) + ")-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
                } else {
                    phoneFormat = phoneNumber;
                }

                if (cellNumber.length() > 0) {
                    cellFormat = "(" + cellNumber.substring(0, 3) + ")-" + cellNumber.substring(3, 6) + "-" + cellNumber.substring(6);
                } else {
                    cellFormat = cellNumber;
                }

                SignInFront.fNameText.setText(firstName);
                SignInFront.lNameText.setText(lastName);
                SignInFront.phoneOneText.setText(phoneFormat);
                SignInFront.cellPhoneText.setText(cellFormat);
                SignInFront.eMailText.setText(emailText);
                SignInFront.clientIDText.setText(clientID);
                SignInFront.workDoneText.setText("");
                SignInFront.workToBeDone.setText("");
                SignInFront.passwordText.setText("");
                SignInFront.pinText.setText("");
                SignInFront.companyText.setText(compName);
                SignInFront.checkDesktop.setSelected(false);
                SignInFront.checkLaptop.setSelected(false);
                SignInFront.checkTablet.setSelected(false);
                SignInFront.checkCharger.setSelected(false);

            }

        } catch (SQLException e) {
        }
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {
            String topWorkOrder = """
                                  select max(work_Order_ID) + 1 as word_order_id
                                  from client_service
                                  """;

            ResultSet searchWO = statement.executeQuery(topWorkOrder);

            while (searchWO.next()) {

                String woText = searchWO.getString(1);
                SignInFront.woTextArea.setText(woText);
                fNameText.requestFocus();
            }

        } catch (SQLException e) {
        }
        dispose();
    }//GEN-LAST:event_selButtActionPerformed

    private void searchFormButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFormButtActionPerformed
        String txtLName = lastNameSearch.getText();

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

    private void searchExistingButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchExistingButtActionPerformed
        String selClient = searchResult.getSelectedValue();
        String selWONum = searchResult.getSelectedValue();
        Pattern pattern = Pattern.compile("Client ID: ");
        Matcher matcher = pattern.matcher(selWONum);

        int endWO = 0;
        while (matcher.find()) {
            endWO = matcher.end();
        }

        String cleanClientID = selClient.substring(endWO, selWONum.indexOf(","));
        System.out.println(cleanClientID);

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = "select work_order_id, left(work_to_do, 40) as work_to_do, sign_in_date\n"
                    + "from client_service\n"
                    + "where client_id = " + cleanClientID;

            Vector<String> resList = new Vector<>();
            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {

                String workOrderID = searchQ.getString("work_order_id");
                //String workToDo = searchQ.getString("work_to_do");
                String signInDate = searchQ.getString("sign_in_date").substring(0, 10);
                Collections.addAll(resList, "Sign In Date: " + signInDate + ", Work Order ID: " + workOrderID);
            }

            searchResult.setListData(resList);
            searchResult.setSelectedIndex(0);

        } catch (SQLException e) {
        }
        SignInFront.saveCheck = -1;
    }//GEN-LAST:event_searchExistingButtActionPerformed

    private void selectWorkOrderButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectWorkOrderButtActionPerformed

        String selWONum = searchResult.getSelectedValue();
        Pattern pattern = Pattern.compile("Work Order ID: ");
        Matcher matcher = pattern.matcher(selWONum);

        int endWO = 0;      
        while (matcher.find()) {
            endWO = matcher.end();
        }
        String cleanWOID = selWONum.substring(endWO, selWONum.length());
        System.out.println(selWONum);
        System.out.println(cleanWOID);

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select work_order_id, cs.client_id, work_to_do, work_done, PC_pass, PC_pin, other_equip, Sign_in_date, Tech_name, desktop, laptop, tablet, charger,
                                  c.fname, c.lname, c.companyName, c.phone, c.phone2, c.email
                                  from client_service as cs
                                  inner join clients as c
                                  on cs.client_id = c.client_id
                                  where work_order_id = """ + cleanWOID;

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone").replace("-", "");
                String cellNumber = searchQ.getString("phone2").replace("-", "");
                String emailText = searchQ.getString("email");
                String clientID = searchQ.getString("client_id");
                String compName = searchQ.getString("companyName");
                String workOrderID = searchQ.getString("work_order_id");
                String workToDo = searchQ.getString("work_to_do");
                String workDone = searchQ.getString("work_done");
                String workOrderPass = searchQ.getString("PC_pass");
                String workOrderPin = searchQ.getString("PC_pin");
                String otherEquip = searchQ.getString("other_equip");
                boolean desktopBool = searchQ.getBoolean("desktop");
                boolean laptopBool = searchQ.getBoolean("laptop");
                boolean tabletBool = searchQ.getBoolean("tablet");
                boolean chargerBool = searchQ.getBoolean("charger");

                String phoneFormat;
                String cellFormat;

                if (phoneNumber.length() > 0) {
                    phoneFormat = "(" + phoneNumber.substring(0, 3) + ")-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
                } else {
                    phoneFormat = phoneNumber;
                }

                if (cellNumber.length() > 0) {
                    cellFormat = "(" + cellNumber.substring(0, 3) + ")-" + cellNumber.substring(3, 6) + "-" + cellNumber.substring(6);
                } else {
                    cellFormat = cellNumber;
                }

                SignInFront.fNameText.setText(firstName);
                SignInFront.lNameText.setText(lastName);
                SignInFront.phoneOneText.setText(phoneFormat);
                SignInFront.cellPhoneText.setText(cellFormat);
                SignInFront.eMailText.setText(emailText);
                SignInFront.clientIDText.setText(clientID);
                SignInFront.workDoneText.setText(workDone);
                SignInFront.workToBeDone.setText(workToDo);
                SignInFront.passwordText.setText(workOrderPass);
                SignInFront.pinText.setText(workOrderPin);
                SignInFront.woTextArea.setText(workOrderID);
                SignInFront.equipmentText.setText(otherEquip);
                //SignInFront.techComboBox.setText(techName);                
                SignInFront.companyText.setText(compName);
                SignInFront.checkDesktop.setSelected(desktopBool);
                SignInFront.checkLaptop.setSelected(laptopBool);
                SignInFront.checkTablet.setSelected(tabletBool);
                SignInFront.checkCharger.setSelected(chargerBool);

            }

        } catch (SQLException e) {
        }        
        dispose();

    }//GEN-LAST:event_selectWorkOrderButtActionPerformed

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
            java.util.logging.Logger.getLogger(SearchForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SearchForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SearchForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backGroundPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField lastNameSearch;
    private javax.swing.JTextField phoneNumberSearch;
    private javax.swing.JButton searchExistingButt;
    private javax.swing.JButton searchFormButt;
    private javax.swing.JList<String> searchResult;
    private javax.swing.JButton selButt;
    private javax.swing.JButton selectWorkOrderButt;
    // End of variables declaration//GEN-END:variables
}