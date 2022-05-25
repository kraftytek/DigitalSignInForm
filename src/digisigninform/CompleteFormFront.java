/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import static digisigninform.PartsUsedFrame.upcCode;
import java.awt.Color;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;

/**
 *
 * @author kraft
 */
public class CompleteFormFront extends javax.swing.JFrame {

    /**
     * Creates new form FormFront
     */
    public CompleteFormFront() {
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane6 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        background = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        receivedText = new javax.swing.JTextField();
        fNameText = new javax.swing.JTextField();
        phoneText = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        workToDoText = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        workPerformedText = new javax.swing.JTextArea();
        jScrollPane4 = new javax.swing.JScrollPane();
        totalText = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        serTag = new javax.swing.JTextArea();
        woText = new javax.swing.JTextField();
        cellText = new javax.swing.JTextField();
        compName = new javax.swing.JTextField();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextArea3 = new javax.swing.JTextArea();
        printCompleteButt = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        closeButt = new javax.swing.JButton();
        addItembutt = new javax.swing.JButton();
        removeButt = new javax.swing.JButton();
        eMailText = new javax.swing.JTextField();
        lNameText = new javax.swing.JTextField();
        partsUsedPane = new javax.swing.JScrollPane();
        partsUsedList = new javax.swing.JTable();

        jTextArea2.setColumns(20);
        jTextArea2.setRows(5);
        jScrollPane6.setViewportView(jTextArea2);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(704, 944));
        setResizable(false);
        setSize(new java.awt.Dimension(704, 944));

        background.setBackground(new java.awt.Color(255, 255, 255));
        background.setForeground(new java.awt.Color(255, 255, 255));
        background.setMinimumSize(new java.awt.Dimension(780, 927));
        background.setPreferredSize(new java.awt.Dimension(780, 927));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Service Work Order");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        receivedText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        receivedText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Completed Date ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        receivedText.setFocusable(false);

        fNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fNameText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "First Name", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        phoneText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        phoneText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Phone", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jScrollPane1.setBorder(null);

        workToDoText.setColumns(20);
        workToDoText.setLineWrap(true);
        workToDoText.setRows(5);
        workToDoText.setWrapStyleWord(true);
        workToDoText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Work to be done", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane1.setViewportView(workToDoText);

        jScrollPane2.setBorder(null);

        workPerformedText.setColumns(20);
        workPerformedText.setLineWrap(true);
        workPerformedText.setRows(5);
        workPerformedText.setWrapStyleWord(true);
        workPerformedText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Work Performed:", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane2.setViewportView(workPerformedText);

        jScrollPane4.setBorder(null);

        totalText.setEditable(false);
        totalText.setBackground(new java.awt.Color(255, 255, 255));
        totalText.setColumns(20);
        totalText.setFont(new java.awt.Font("SansSerif", 0, 14)); // NOI18N
        totalText.setForeground(new java.awt.Color(0, 0, 0));
        totalText.setLineWrap(true);
        totalText.setRows(3);
        totalText.setWrapStyleWord(true);
        totalText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Total Owing", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane4.setViewportView(totalText);

        jScrollPane5.setBorder(null);

        serTag.setColumns(20);
        serTag.setLineWrap(true);
        serTag.setRows(5);
        serTag.setText("\nService fees are payable whether a problem is solved or not.\nNational Computer Resource will not release the above equipment until all parts and/or labour charges have been paid in full.\n\nThank you for your Business!");
        serTag.setToolTipText("");
        serTag.setWrapStyleWord(true);
        serTag.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Agreement", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        jScrollPane5.setViewportView(serTag);

        woText.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        woText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        woText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), " Work Order ID ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        cellText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cellText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Cell", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        compName.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        compName.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Company", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jScrollPane7.setBorder(null);
        jScrollPane7.setOpaque(false);

        jTextArea3.setColumns(14);
        jTextArea3.setRows(4);
        jTextArea3.setText("       102-1980 Cooper Rd., Kelowna, B.C., Canada V1Y-8K5\n     Phone: 250-868-9765 / 250-763-2492 | Fax:877-263-8594\n     www.ncro.ca | service@ncro.ca | facebook.com/ncrodotca");
        jTextArea3.setBorder(null);
        jScrollPane7.setViewportView(jTextArea3);

        printCompleteButt.setBackground(new java.awt.Color(255, 255, 255));
        printCompleteButt.setForeground(new java.awt.Color(0, 0, 0));
        printCompleteButt.setText("Print");
        printCompleteButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        printCompleteButt.setContentAreaFilled(false);
        printCompleteButt.setFocusable(false);
        printCompleteButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printCompleteButtActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setForeground(new java.awt.Color(0, 0, 0));
        jButton2.setText("Email");
        jButton2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        jButton2.setContentAreaFilled(false);
        jButton2.setFocusable(false);

        closeButt.setBackground(new java.awt.Color(255, 255, 255));
        closeButt.setForeground(new java.awt.Color(0, 0, 0));
        closeButt.setText("Close");
        closeButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        closeButt.setContentAreaFilled(false);
        closeButt.setFocusable(false);
        closeButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtActionPerformed(evt);
            }
        });

        addItembutt.setBackground(new java.awt.Color(255, 255, 255));
        addItembutt.setForeground(new java.awt.Color(0, 0, 0));
        addItembutt.setText("Add Item");
        addItembutt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        addItembutt.setContentAreaFilled(false);
        addItembutt.setFocusPainted(false);
        addItembutt.setFocusable(false);
        addItembutt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addItembuttActionPerformed(evt);
            }
        });

        removeButt.setBackground(new java.awt.Color(255, 255, 255));
        removeButt.setForeground(new java.awt.Color(0, 0, 0));
        removeButt.setText("Clear Items");
        removeButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        removeButt.setContentAreaFilled(false);
        removeButt.setFocusPainted(false);
        removeButt.setFocusable(false);
        removeButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                removeButtActionPerformed(evt);
            }
        });

        eMailText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eMailText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "E-Mail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        lNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lNameText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Last Name ", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        partsUsedPane.setBackground(new java.awt.Color(255, 255, 255));
        partsUsedPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Parts Used"));
        partsUsedPane.setForeground(new java.awt.Color(0, 0, 0));
        partsUsedPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        partsUsedList.setBackground(new java.awt.Color(255, 255, 255));
        partsUsedList.setForeground(new java.awt.Color(0, 0, 0));
        partsUsedList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Desc", "UPC"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        partsUsedList.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        partsUsedList.setAutoscrolls(false);
        partsUsedList.setColumnSelectionAllowed(true);
        partsUsedList.setShowGrid(false);
        partsUsedPane.setViewportView(partsUsedList);
        JTableHeader header = partsUsedList.getTableHeader();
        header.setBackground(Color.white);
        header.setForeground(Color.black);

        javax.swing.GroupLayout backgroundLayout = new javax.swing.GroupLayout(background);
        background.setLayout(backgroundLayout);
        backgroundLayout.setHorizontalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(phoneText, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fNameText, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lNameText, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(cellText, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eMailText)
                            .addComponent(compName)))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(partsUsedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addComponent(receivedText, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane7)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(woText, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane5)
                            .addComponent(jScrollPane2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundLayout.createSequentialGroup()
                                .addComponent(closeButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(printCompleteButt, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(addItembutt, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(removeButt, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );
        backgroundLayout.setVerticalGroup(
            backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(woText)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(backgroundLayout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(receivedText, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fNameText)
                            .addComponent(compName))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(eMailText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(lNameText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cellText)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 307, Short.MAX_VALUE)
                    .addComponent(partsUsedPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(removeButt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(addItembutt, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(321, 321, 321))
                    .addGroup(backgroundLayout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(backgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(printCompleteButt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(closeButt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(49, 49, 49))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.PREFERRED_SIZE, 704, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtActionPerformed

    private void addItembuttActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addItembuttActionPerformed
        PartsUsedFrame gui = new PartsUsedFrame();
        gui.setVisible(true);
    }//GEN-LAST:event_addItembuttActionPerformed


    private void printCompleteButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printCompleteButtActionPerformed
        //JFrame yourComponent = new JFrame();
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat preformat = pjob.defaultPage();
        preformat.setOrientation(PageFormat.PORTRAIT);
        Paper paper = new Paper();
        double margin = 2; // half inch
        paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight()
                - margin * 2);
        PageFormat postformat = pjob.pageDialog(preformat);
        postformat.setPaper(paper);
        //If user does not hit cancel then print.
        if (preformat != postformat) {
            //Set print component
            pjob.setPrintable(new PrintForm.Printer(background), postformat);
            if (pjob.printDialog()) {
                try {
                    pjob.print();
                } catch (PrinterException ex) {
                    Logger.getLogger(CompleteFormFront.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }//GEN-LAST:event_printCompleteButtActionPerformed


    private void removeButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_removeButtActionPerformed

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String workOrderTxt = CompleteFormFront.woText.getText();
            //delete the service items attached to the specified work order
            String deleteQue = "delete\n"
                    + "from service_link\n"
                    + "where work_order_id like " + workOrderTxt;
            statement.executeUpdate(deleteQue);

        } catch (SQLException e) {
        }

        //temp workaround to clear list
       JTable backTable = new JTable();
        DefaultTableModel model = (DefaultTableModel)(backTable.getModel());
        CompleteFormFront.partsUsedList.setModel(model);
        CompleteFormFront.totalText.setText("");
        PartsUsedFrame.upcList.removeAllElements();
        PartsUsedFrame.totalCost = 0.0;
        PartsUsedFrame.doubles.clear();

        /*
        double totalCost = 0.0;

        //get the current selected upc item
        int currentSel = partsUsedList.getSelectedIndex();
        //make a copy of the existing Jlist as listModel
        DefaultComboBoxModel listModel = (DefaultComboBoxModel) partsUsedList.getModel();

        List<Double> doubles = new ArrayList<>(10);
        doubles.addAll(PartsUsedFrame.doubles);
        //remove the element from the copy and from the doubles array
        listModel.removeElementAt(currentSel);
        doubles.remove(currentSel);

        DecimalFormat f = new DecimalFormat("##.00");
        String upcCost = PartsUsedFrame.upcCostText.getText().replace("$", "");
        double upcDouble = Double.parseDouble(upcCost);
        doubles.add(upcDouble);

        String upcText = PartsUsedFrame.upcDescText.getText();
        Collections.addAll(PartsUsedFrame.upcTextList, upcText);
        DefaultComboBoxModel model = new DefaultComboBoxModel(PartsUsedFrame.upcList);

        //set the list with the removed item
        partsUsedList.setModel(model);

        for (Double i : doubles) {
            PartsUsedFrame.totalCost += i;
        }
        double taxAmt = (totalCost * 1.12) - totalCost;
        double totalAmt = totalCost + taxAmt;

        CompleteFormFront.totalText.setText("Initial Cost: $" + f.format(totalCost) + "\n"
                + "Taxes: $" + f.format(taxAmt) + "\n"
                + "After Taxes: $" + f.format(totalAmt));

        }
         */
    }//GEN-LAST:event_removeButtActionPerformed

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
            java.util.logging.Logger.getLogger(CompleteFormFront.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CompleteFormFront.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CompleteFormFront.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CompleteFormFront.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CompleteFormFront().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addItembutt;
    private javax.swing.JPanel background;
    public static javax.swing.JTextField cellText;
    private javax.swing.JButton closeButt;
    public static javax.swing.JTextField compName;
    public static javax.swing.JTextField eMailText;
    public static javax.swing.JTextField fNameText;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JTextArea jTextArea3;
    public static javax.swing.JTextField lNameText;
    public static javax.swing.JTable partsUsedList;
    private javax.swing.JScrollPane partsUsedPane;
    public static javax.swing.JTextField phoneText;
    private javax.swing.JButton printCompleteButt;
    public static javax.swing.JTextField receivedText;
    private javax.swing.JButton removeButt;
    private javax.swing.JTextArea serTag;
    public static javax.swing.JTextArea totalText;
    public static javax.swing.JTextField woText;
    public static javax.swing.JTextArea workPerformedText;
    public static javax.swing.JTextArea workToDoText;
    // End of variables declaration//GEN-END:variables
}
