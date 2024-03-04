/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import static digisigninform.SignInFront.getValues;
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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author kraft
 */
public class WorkOrderHistoryFrame extends javax.swing.JFrame {

    /**
     * Creates new form WorkOrderHistroyFrame
     */
    public WorkOrderHistoryFrame() {
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

    Vector<String> columnNames = new Vector<>();

    {
        columnNames.addElement("First Name");
        columnNames.addElement("Last Name");
        columnNames.addElement("Work Order ID");
        columnNames.addElement("Sign In Date");
        columnNames.addElement("Status");
    }

    DefaultTableModel historyModel = new DefaultTableModel(columnNames, 0) {

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

        backgroundPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        reportTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(500, 965));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        backgroundPanel.setBackground(new java.awt.Color(255, 255, 255));
        backgroundPanel.setForeground(new java.awt.Color(0, 0, 0));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(490, 910));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Work Order History");

        reportTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "First Name", "Last Name", "Work Order Number", "Service Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(reportTable);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setForeground(new java.awt.Color(0, 0, 0));
        jButton1.setText("Refresh");
        jButton1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton1.setFocusable(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 490, Short.MAX_VALUE)
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 862, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
public static int openedFrame = -1;
    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {
            String workOrderHistory = "select top 60 c.fname, c.lname, cs.work_Order_ID, CONVERT(Char(16), cs.sign_in_date ,20) as sign_in_date, s.status_name \n"
                    + "from client_service as cs\n"
                    + "inner join clients as c\n"
                    + "on cs.client_id = c.client_id\n"
                    + "left outer join status_link as sl\n"
                    + "on sl.work_Order_ID = cs.work_Order_ID\n"
                    + "left outer join statuses as s\n"
                    + "on sl.status_id = s.status_id\n"
                    + "order by 3 desc;";

            ResultSet searchQ = statement.executeQuery(workOrderHistory);
            for (int i = 0; i < 60; i++) {
                if (searchQ.next()) {
                    String fNameString = searchQ.getString("fname");
                    String lNameString = searchQ.getString("lname");
                    String workOrderString = searchQ.getString("work_Order_ID");
                    String signInString = searchQ.getString("sign_in_date");
                    String workOrderStatus = searchQ.getString("status_name");

                    Object[] rowData = {fNameString, lNameString, workOrderString, signInString, workOrderStatus};
                    historyModel.addRow(rowData);
                }
                reportTable.setModel(historyModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkOrderHistoryFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_formWindowActivated

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

        historyModel.setRowCount(0);
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {
            String workOrderHistory = "select top 60 c.fname, c.lname, cs.work_Order_ID, CONVERT(Char(16), cs.sign_in_date ,20) as sign_in_date, s.status_name \n"
                    + "from client_service as cs\n"
                    + "inner join clients as c\n"
                    + "on cs.client_id = c.client_id\n"
                    + "left outer join status_link as sl\n"
                    + "on sl.work_Order_ID = cs.work_Order_ID\n"
                    + "left outer join statuses as s\n"
                    + "on sl.status_id = s.status_id\n"
                    + "order by 3 desc;";

            ResultSet searchQ = statement.executeQuery(workOrderHistory);
            for (int i = 0; i < 60; i++) {
                if (searchQ.next()) {
                    String fNameString = searchQ.getString("fname");
                    String lNameString = searchQ.getString("lname");
                    String workOrderString = searchQ.getString("work_Order_ID");
                    String signInString = searchQ.getString("sign_in_date");
                    String workOrderStatus = searchQ.getString("status_name");

                    Object[] rowData = {fNameString, lNameString, workOrderString, signInString, workOrderStatus};
                    historyModel.addRow(rowData);
                }
                reportTable.setModel(historyModel);
            }
        } catch (SQLException ex) {
            Logger.getLogger(WorkOrderHistoryFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(WorkOrderHistoryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(WorkOrderHistoryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(WorkOrderHistoryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(WorkOrderHistoryFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new WorkOrderHistoryFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backgroundPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable reportTable;
    // End of variables declaration//GEN-END:variables
}
