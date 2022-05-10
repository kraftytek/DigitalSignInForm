/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import static digisigninform.SignInFront.clientIDText;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.Collections;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.DefaultComboBoxModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import net.sourceforge.barbecue.Barcode;
import net.sourceforge.barbecue.BarcodeFactory;
import net.sourceforge.barbecue.BarcodeImageHandler;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.impl.upcean.EAN13Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;

/**
 *
 * @author kraft
 */
public class PartsUsedFrame extends javax.swing.JFrame {

    /**
     * Creates new form PartsUsedFrame
     */
    public PartsUsedFrame() {
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
        jLabel1 = new javax.swing.JLabel();
        upcCode = new javax.swing.JTextField();
        upcDesc = new javax.swing.JTextField();
        barCode = new javax.swing.JLabel();
        makeBarButt = new javax.swing.JButton();
        saveBarcodeButt = new javax.swing.JButton();
        upcCostText = new javax.swing.JTextField();
        upcDescText = new javax.swing.JLabel();
        upcCombo = new javax.swing.JComboBox<>();
        selectButt = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowActivated(java.awt.event.WindowEvent evt) {
                formWindowActivated(evt);
            }
        });

        backGroundPanel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("UPC Generator");

        upcCode.setBackground(new java.awt.Color(255, 255, 255));
        upcCode.setForeground(new java.awt.Color(0, 0, 0));
        upcCode.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "UPC Code", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        upcCode.setNextFocusableComponent(upcCostText);
        upcCode.setOpaque(true);

        upcDesc.setBackground(new java.awt.Color(255, 255, 255));
        upcDesc.setForeground(new java.awt.Color(0, 0, 0));
        upcDesc.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "UPC Description"));
        upcDesc.setOpaque(true);

        barCode.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        makeBarButt.setBackground(new java.awt.Color(255, 255, 255));
        makeBarButt.setForeground(new java.awt.Color(0, 0, 0));
        makeBarButt.setText("Generate Barcode");
        makeBarButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        makeBarButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                makeBarButtActionPerformed(evt);
            }
        });

        saveBarcodeButt.setBackground(new java.awt.Color(255, 255, 255));
        saveBarcodeButt.setForeground(new java.awt.Color(0, 0, 0));
        saveBarcodeButt.setText("Save Barcode");
        saveBarcodeButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        saveBarcodeButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBarcodeButtActionPerformed(evt);
            }
        });

        upcCostText.setBackground(new java.awt.Color(255, 255, 255));
        upcCostText.setForeground(new java.awt.Color(0, 0, 0));
        upcCostText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "UPC Cost", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION));
        upcCostText.setNextFocusableComponent(upcDescText);
        upcCostText.setOpaque(true);

        upcDescText.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        upcDescText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        upcCombo.setBackground(new java.awt.Color(255, 255, 255));
        upcCombo.setForeground(new java.awt.Color(0, 0, 0));
        upcCombo.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        upcCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upcComboActionPerformed(evt);
            }
        });

        selectButt.setBackground(new java.awt.Color(255, 255, 255));
        selectButt.setForeground(new java.awt.Color(0, 0, 0));
        selectButt.setText("Select Barcode");
        selectButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));
        selectButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectButtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout backGroundPanelLayout = new javax.swing.GroupLayout(backGroundPanel);
        backGroundPanel.setLayout(backGroundPanelLayout);
        backGroundPanelLayout.setHorizontalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(upcCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(makeBarButt, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(selectButt, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveBarcodeButt, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE))
                    .addGroup(backGroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(upcCode, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(upcCostText)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(upcDesc, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addGap(115, 115, 115)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(upcDescText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barCode, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backGroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        backGroundPanelLayout.setVerticalGroup(
            backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backGroundPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(10, 10, 10)
                .addComponent(upcCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(upcCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upcDesc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(upcCostText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(upcDescText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(barCode, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(backGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(makeBarButt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(saveBarcodeButt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectButt, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(backGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static BufferedImage generateCode39BarcodeImage(String barcodeText) throws Exception {
        Code39Bean barcodeGenerator = new Code39Bean();
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(90, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    String connectionUrl
            = "jdbc:sqlserver://sql.kraftytek.ca:1433;"
            + "encrypt=false;"
            + "databaseName=NCRO_WorkOrders;"
            + "user=sa;"
            + "password=S!lver88";

    private void makeBarButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_makeBarButtActionPerformed

        String upcNum = upcCode.getText();

        try {
            Image newImage = generateCode39BarcodeImage(upcNum);
            String upcText = upcDesc.getText() + "->$" + upcCostText.getText();
            ImageIcon icon = new ImageIcon(newImage);            
            barCode.setIcon(icon);
            upcDescText.setText(upcText);
        } catch (Exception ex) {
            Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_makeBarButtActionPerformed

    private void saveBarcodeButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBarcodeButtActionPerformed

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String upcDescText = upcDesc.getText();
            String upcCodeText = upcCode.getText();
            String upcPriceText = upcCostText.getText();

            String addUpcScript = "insert into upc_codes(upc_desc, upc_code, upc_cost)\n"
                    + "values('"
                    + upcDescText + "','"
                    + upcCodeText + "','"
                    + upcPriceText + "')";

            statement.executeUpdate(addUpcScript);

        } catch (SQLException e) {
        }
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String populateList = "select upc_desc, upc_code, upc_cost from upc_codes";
            Vector<String> upcList = new Vector<>();
            ResultSet searchQ = statement.executeQuery(populateList);

            while (searchQ.next()) {
                String upcDescText = searchQ.getString("upc_desc");
                String upcCodeText = searchQ.getString("upc_code");
                String upcCostText = searchQ.getString("upc_cost");

                Collections.addAll(upcList, "UPC Code: " + upcCodeText + ", Description: " + upcDescText + ", Cost: $" + upcCostText);
            }
            System.out.println(upcList);

            DefaultComboBoxModel model = new DefaultComboBoxModel(upcList);
            upcCombo.setModel(model);
            upcCombo.setSelectedIndex(0);

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_saveBarcodeButtActionPerformed

    private void formWindowActivated(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowActivated
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String populateList = "select upc_desc, upc_code, upc_cost from upc_codes";
            Vector<String> upcList = new Vector<>();
            ResultSet searchQ = statement.executeQuery(populateList);

            while (searchQ.next()) {
                String upcDescText = searchQ.getString("upc_desc");
                String upcCodeText = searchQ.getString("upc_code");
                String upcCostText = searchQ.getString("upc_cost");

                Collections.addAll(upcList, "UPC Code: " + upcCodeText + ", Description: " + upcDescText + ", Cost: $" + upcCostText);
            }
            System.out.println(upcList);

            DefaultComboBoxModel model = new DefaultComboBoxModel(upcList);
            upcCombo.setModel(model);

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_formWindowActivated

    private void upcComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upcComboActionPerformed
        String selectedItem = (String) upcCombo.getSelectedItem();
        Pattern pattern = Pattern.compile("UPC Code: ");
        Matcher matcher = pattern.matcher(selectedItem);

        int endWO = 0;
        while (matcher.find()) {
            endWO = matcher.end();
        }
        String cleanUpcCode = selectedItem.substring(endWO, selectedItem.indexOf(","));

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String getUpcScript = "select upc_desc, upc_code, upc_cost from upc_codes where upc_code like '"
                    + cleanUpcCode + "'";

            ResultSet searchQ = statement.executeQuery(getUpcScript);
            while (searchQ.next()) {
                String upcDescText = searchQ.getString("upc_desc");
                String upcCodeText = searchQ.getString("upc_code");
                String upcCost = searchQ.getString("upc_cost").replace("$", "");

                upcCode.setText(upcCodeText);
                upcDesc.setText(upcDescText);
                upcCostText.setText("$" + upcCost);
            }

            String upcNum = upcCode.getText();

            try {
                Image newImage = generateCode39BarcodeImage(upcNum);
                ImageIcon icon = new ImageIcon(newImage);
                barCode.setIcon(icon);
                upcDescText.setText(upcDesc.getText() + "->" + upcCostText.getText());

            } catch (Exception ex) {
                Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_upcComboActionPerformed
    public Vector<Icon> upcList = new Vector<>();
    public Vector<String> upcTextList = new Vector<>();

    public double totalCost;
    
    private void selectButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectButtActionPerformed
        DecimalFormat f = new DecimalFormat("##.00");
        Icon selectedUpcIcon = barCode.getIcon();
        String upcText = upcDescText.getText();
        String upcCost = upcCostText.getText().replace("$","");
        double upcDouble = Double.parseDouble(upcCost);
        Collections.addAll(upcList, selectedUpcIcon);
        Collections.addAll(upcTextList, upcText);
        DefaultComboBoxModel model = new DefaultComboBoxModel(upcList);       
        CompleteFormFront.partsUsedList.setModel(model);
        totalCost = (totalCost + upcDouble);
        double totalRound = Math.round(totalCost * 100.0) / 100.0;
        //String totalText = String.valueOf(totalRound);
        double totalTax = totalCost * 1.12;
        double totalTaxRound = Math.round(totalTax * 100.0) / 100.0;
        double taxAmount = totalTaxRound - totalRound;
        double taxAmountRound = Math.round(taxAmount * 100.0) / 100.0;
        //String taxText = String.valueOf(taxAmountRound);
        CompleteFormFront.totalText.setText("Initial Cost: $" + f.format(totalRound) + "\n" 
                + "Taxes: $" + f.format(taxAmountRound) + "\n"
                + "After Taxes: $" + f.format(totalTaxRound));

    }//GEN-LAST:event_selectButtActionPerformed

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
            java.util.logging.Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PartsUsedFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel backGroundPanel;
    private javax.swing.JLabel barCode;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton makeBarButt;
    private javax.swing.JButton saveBarcodeButt;
    private javax.swing.JButton selectButt;
    private javax.swing.JTextField upcCode;
    private javax.swing.JComboBox<String> upcCombo;
    private javax.swing.JTextField upcCostText;
    private javax.swing.JTextField upcDesc;
    private javax.swing.JLabel upcDescText;
    // End of variables declaration//GEN-END:variables
}
