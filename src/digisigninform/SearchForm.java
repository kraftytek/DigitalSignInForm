/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import static digisigninform.SignInFront.fNameText;
import java.awt.Color;
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
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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

    Vector<String> columnNames = new Vector<>();

    {
        columnNames.clear();
        columnNames.addElement("Client ID");
        columnNames.addElement("First Name");
        columnNames.addElement("Last Name");
        columnNames.addElement("Company");
        columnNames.addElement("Home Phone");
        columnNames.addElement("Cell Phone");
    }

    DefaultTableModel model = new DefaultTableModel(columnNames, 0) {

        @Override

        public Class<?> getColumnClass(int column) {
            if (getRowCount() > 0) {
                Object value = getValueAt(0, column);
                if (value != null) {
                    return getValueAt(0, column).getClass();
                }
            }
            return super.getColumnClass(column);
        }
    };

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backGroundPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        resultTable = new javax.swing.JTable();
        searchExistingButt = new javax.swing.JButton();
        phoneNumberSearch = new javax.swing.JTextField();
        lastNameSearch = new javax.swing.JTextField();
        searchFormButt = new javax.swing.JButton();
        selButt = new javax.swing.JButton();
        selectWorkOrderButt = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        mergeClientButt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Search Form");
        setMinimumSize(new java.awt.Dimension(720, 600));
        setPreferredSize(new java.awt.Dimension(720, 620));
        setResizable(false);
        addWindowFocusListener(new java.awt.event.WindowFocusListener() {
            public void windowGainedFocus(java.awt.event.WindowEvent evt) {
                formWindowGainedFocus(evt);
            }
            public void windowLostFocus(java.awt.event.WindowEvent evt) {
            }
        });

        backGroundPanel.setBackground(new java.awt.Color(255, 255, 255));
        backGroundPanel.setMinimumSize(new java.awt.Dimension(720, 600));
        backGroundPanel.setPreferredSize(new java.awt.Dimension(720, 620));

        jScrollPane2.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane2.setBorder(null);
        jScrollPane2.setForeground(new java.awt.Color(0, 0, 0));
        jScrollPane2.setViewportView(null);

        resultTable.setBackground(new java.awt.Color(255, 255, 255));
        resultTable.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 3));
        resultTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Client ID", "First Name", "Last Name", "Company", "Home Phone", "Cell Phone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        resultTable.setRowHeight(25);
        resultTable.setShowGrid(true);
        JTableHeader header = resultTable.getTableHeader();
        header.setBackground(Color.white);
        header.setForeground(Color.black);
        jScrollPane2.setViewportView(resultTable);

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

        selectWorkOrderButt.setBackground(new java.awt.Color(255, 255, 255));
        selectWorkOrderButt.setForeground(new java.awt.Color(0, 0, 0));
        selectWorkOrderButt.setText("Select WO");
        selectWorkOrderButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        selectWorkOrderButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectWorkOrderButtActionPerformed(evt);
            }
        });

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Edit Client");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        mergeClientButt.setBackground(new java.awt.Color(255, 255, 255));
        mergeClientButt.setForeground(new java.awt.Color(0, 0, 0));
        mergeClientButt.setText("Merge Client");
        mergeClientButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        mergeClientButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mergeClientButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backGroundPanelLayout = new javax.swing.GroupLayout(backGroundPanel);
        backGroundPanel.setLayout(backGroundPanelLayout);
        backGroundPanelLayout.setHorizontalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addComponent(lastNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(phoneNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(backGroundPanelLayout.createSequentialGroup()
                                .addComponent(searchFormButt, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(selButt, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backGroundPanelLayout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(mergeClientButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(searchExistingButt, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectWorkOrderButt, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        backGroundPanelLayout.setVerticalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchExistingButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(searchFormButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectWorkOrderButt, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                    .addComponent(mergeClientButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 704, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
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

        int selVal = resultTable.getSelectedRow();
        Object clientIDobj = resultTable.getValueAt(selVal, 0);
        int clientID = Integer.parseInt(clientIDobj.toString());
        String clientString = String.valueOf(clientID);

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select top 1 client_id, fname, lname, phone, phone2, email, companyName, creation_date
                                  from clients
                                  where client_id = """ + clientID;

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone").replace("-", "").replace(" ", "");
                String cellNumber = searchQ.getString("phone2").replace("-", "").replace(" ", "");
                String emailText = searchQ.getString("email");
                String compName = searchQ.getString("companyName");

                SignInFront.globalClientID = clientID;
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
                SignInFront.clientIDText.setText(clientString);
                SignInFront.workDoneText.setText("");
                SignInFront.workToBeDone.setText("");
                SignInFront.passwordText.setText("");
                SignInFront.pinText.setText("");
                SignInFront.companyText.setText(compName);
                SignInFront.checkDesktop.setSelected(false);
                SignInFront.checkLaptop.setSelected(false);
                SignInFront.checkTablet.setSelected(false);
                SignInFront.checkCharger.setSelected(false);
                SignInFront.globalClientID = clientID;
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
        SignInFront.clearWasDone = -1;
        dispose();

    }//GEN-LAST:event_selButtActionPerformed

    private void searchFormButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchFormButtActionPerformed
        DefaultTableModel modelClear = (DefaultTableModel) resultTable.getModel();
        modelClear.setRowCount(0);
        resultTable.setModel(modelClear);
        String txtLName = lastNameSearch.getText();

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select fname, lname, phone, phone2, companyName, client_id
                                  from clients
                                  where lname like '""" + txtLName + "'" + " and flags is null";
            System.out.println(searchInvQue);

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {

                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String companyName = searchQ.getString("companyName");
                String phoneNumber = searchQ.getString("phone");
                String cellNumber = searchQ.getString("phone2");
                String clientText = searchQ.getString("client_id");
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

                Object[] rowData = {clientText, firstName, lastName, companyName, phoneFormat, cellFormat};
                model.addRow(rowData);

            }
            resultTable.setModel(model);
            resultTable.getColumnModel().getColumn(0).setMaxWidth(130);
            resultTable.getColumnModel().getColumn(0).setMinWidth(130);

        } catch (SQLException e) {
        }
    }//GEN-LAST:event_searchFormButtActionPerformed

    private void formWindowGainedFocus(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowGainedFocus
        lastNameSearch.requestFocus();
    }//GEN-LAST:event_formWindowGainedFocus
    public int searchClient = -1;
    private void searchExistingButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchExistingButtActionPerformed

        columnNames = new Vector<>();
        {
            columnNames.clear();
            columnNames.addElement("Work Order ID");
            columnNames.addElement("Service Date");
        }
        model = new DefaultTableModel(columnNames, 0) {
            @Override

            public Class<?> getColumnClass(int column) {
                if (getRowCount() > 0) {
                    Object value = getValueAt(0, column);
                    if (value != null) {
                        return getValueAt(0, column).getClass();
                    }
                }
                return super.getColumnClass(column);
            }
        };

        int selVal = resultTable.getSelectedRow();
        Object clientIDobj = resultTable.getValueAt(selVal, 0);
        int clientID = Integer.parseInt(clientIDobj.toString());
        searchClient = clientID;

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = "select work_order_id, left(work_to_do, 40) as work_to_do, sign_in_date\n"
                    + "from client_service\n"
                    + "where client_id = " + searchClient                    
                    + "order by 1 desc";
            System.out.println(searchInvQue);
            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {

                String workOrderID = searchQ.getString("work_order_id");

                String signInDate = searchQ.getString("sign_in_date").substring(0, 10);

                Object[] rowData = {workOrderID, signInDate};
                model.addRow(rowData);
            }

            resultTable.setModel(model);

            //resultTable.getColumnModel().getColumn(0).setMaxWidth(130);
            //resultTable.getColumnModel().getColumn(0).setMinWidth(130);
        } catch (SQLException e) {
        }

    }//GEN-LAST:event_searchExistingButtActionPerformed

    private void selectWorkOrderButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectWorkOrderButtActionPerformed

        int clientID = searchClient;
        String clientString = String.valueOf(clientID);
        int selVal = resultTable.getSelectedRow();
        Object workOrderObj = resultTable.getValueAt(selVal, 0);
        int workOrderInt = Integer.parseInt(workOrderObj.toString());

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select work_order_id, cs.client_id, work_to_do, work_done, PC_pass, PC_pin, other_equip, Sign_in_date, Tech_name, desktop, laptop, tablet, charger,
                                  c.fname, c.lname, c.companyName, c.phone, c.phone2, c.email
                                  from client_service as cs
                                  inner join clients as c
                                  on cs.client_id = c.client_id
                                  where work_order_id = """ + workOrderInt;

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone").replace("-", "").replace(" ", "");
                String cellNumber = searchQ.getString("phone2").replace("-", "").replace(" ", "");
                String emailText = searchQ.getString("email");
                String compName = searchQ.getString("companyName");
                String workOrderID = searchQ.getString("work_order_id");
                String workToDo = searchQ.getString("work_to_do");
                String workDone = searchQ.getString("work_done");
                String workOrderPass = searchQ.getString("PC_pass");
                String workOrderPin = searchQ.getString("PC_pin");
                String otherEquip = searchQ.getString("other_equip");
                String techName = searchQ.getString("tech_name");
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
                SignInFront.clientIDText.setText(clientString);
                SignInFront.workDoneText.setText(workDone);
                SignInFront.workToBeDone.setText(workToDo);
                SignInFront.passwordText.setText(workOrderPass);
                SignInFront.pinText.setText(workOrderPin);
                SignInFront.woTextArea.setText(workOrderID);
                SignInFront.equipmentText.setText(otherEquip);
                SignInFront.companyText.setText(compName);
                SignInFront.checkDesktop.setSelected(desktopBool);
                SignInFront.checkLaptop.setSelected(laptopBool);
                SignInFront.checkTablet.setSelected(tabletBool);
                SignInFront.checkCharger.setSelected(chargerBool);
                SignInFront.techComboBox.setSelectedItem(techName);
                SignInFront.globalClientID = clientID;

            }

        } catch (SQLException e) {
        }
        dispose();
        searchClient = -1;
    }//GEN-LAST:event_selectWorkOrderButtActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EditClientForm gui = new EditClientForm();
        gui.setVisible(true);

        int selVal = resultTable.getSelectedRow();
        Object clientIDobj = resultTable.getValueAt(selVal, 0);
        int clientID = Integer.parseInt(clientIDobj.toString());
        String clientString = String.valueOf(clientID);

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String searchInvQue = """
                                  select top 1 client_id, fname, lname, phone, phone2, email, companyName, creation_date
                                  from clients
                                  where client_id = """ + clientID;

            ResultSet searchQ = statement.executeQuery(searchInvQue);

            while (searchQ.next()) {
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone").replace("-", "").replace(" ", "");
                String cellNumber = searchQ.getString("phone2").replace("-", "").replace(" ", "");
                String compName = searchQ.getString("companyName");

                SignInFront.globalClientID = clientID;
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

                EditClientForm.fNameField.setText(firstName);
                EditClientForm.lNameField.setText(lastName);
                EditClientForm.homePhoneField.setText(phoneFormat);
                EditClientForm.cellPhoneField.setText(cellFormat);
                EditClientForm.companyName.setText(compName);
                EditClientForm.clientIDField.setText(clientString);

            }

        } catch (SQLException e) {
        }


    }//GEN-LAST:event_jButton1ActionPerformed

    private void mergeClientButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mergeClientButtActionPerformed
        MergeClientFrame gui = new MergeClientFrame();
        gui.setVisible(true);

        
        
        Vector<String> columnNames = new Vector<>();

    {
        columnNames.clear();
        columnNames.addElement("Client ID");
        columnNames.addElement("First Name");
        columnNames.addElement("Last Name");        
        columnNames.addElement("Home Phone");
        columnNames.addElement("Cell Phone");
    }

    DefaultTableModel mergeModel = new DefaultTableModel(columnNames, 0);       
        
        int selVal = resultTable.getSelectedRow();
        Object clientIDobj = resultTable.getValueAt(selVal, 0);
        int clientID = Integer.parseInt(clientIDobj.toString());
        String clientString = String.valueOf(clientID);

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String matchClient = "declare @clientID int;\n"
                    + "set @clientID = " + clientString + """                     
                     select c.client_id, c.fname, c.lname, c.phone, c.phone2, c.flags,
                     row_number() over (
                     partition by
                     c.fname,
                     c.lname,
                     c.phone,
                     c.phone2,
                     c.flags
                     order by 
                     c.fname,
                     c.lname,
                     c.phone,
                     c.phone2,
                     c.flags
                     ) as row_num
                     from clients as c
                     inner join 
                     (select fname, lname, phone, phone2, client_id, flags
                     from clients
                     where client_id = @clientID                 
                     )as x
                     on c.fname = x.fname
                     and c.lname = x.lname
                     and c.phone = x.phone
                     and c.phone2 = x.phone2
                     and c.flags is null""";
            
            System.out.println(matchClient);

            ResultSet searchQ = statement.executeQuery(matchClient);

            while (searchQ.next()) {

                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");               
                String phoneNumber = searchQ.getString("phone");
                String cellNumber = searchQ.getString("phone2");
                String clientText = searchQ.getString("client_id");
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

                Object[] rowData = {clientText, firstName, lastName, phoneFormat, cellFormat};
                mergeModel.addRow(rowData);

            }
            MergeClientFrame.mergeFromTable.setModel(mergeModel);
            MergeClientFrame.mergeFromTable.getColumnModel().getColumn(0).setMaxWidth(130);
            MergeClientFrame.mergeFromTable.getColumnModel().getColumn(0).setMinWidth(130);
            
            MergeClientFrame.mergeIntoTable.setModel(mergeModel);
            MergeClientFrame.mergeIntoTable.getColumnModel().getColumn(0).setMaxWidth(130);
            MergeClientFrame.mergeIntoTable.getColumnModel().getColumn(0).setMinWidth(130);

        
    }
    catch (SQLException ex

    
        ) {
            Logger.getLogger(SearchForm.class.getName()).log(Level.SEVERE, null, ex);
    }

    }//GEN-LAST:event_mergeClientButtActionPerformed

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
    private javax.swing.JButton jButton1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField lastNameSearch;
    private javax.swing.JButton mergeClientButt;
    private javax.swing.JTextField phoneNumberSearch;
    private javax.swing.JTable resultTable;
    private javax.swing.JButton searchExistingButt;
    private javax.swing.JButton searchFormButt;
    private javax.swing.JButton selButt;
    private javax.swing.JButton selectWorkOrderButt;
    // End of variables declaration//GEN-END:variables
}
