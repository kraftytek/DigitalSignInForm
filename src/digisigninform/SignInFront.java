/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package digisigninform;

import java.awt.Graphics;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.PrinterException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import org.krysalis.barcode4j.ChecksumMode;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import java.awt.Toolkit;
import javax.swing.SwingUtilities;

/**
 *
 * @author kraftytek (Chris Reid)
 */
public class SignInFront extends javax.swing.JFrame {

    /**
     * Creates new form SignInFront
     *
     */
    public SignInFront() {

        initComponents();
    }

    /**
     * *************************************************************************************************************
     * Get the server connection information from the cofig file, should move to
     * src/resources/config.txt
     * **************************************************************************************************************
     */
    public static ArrayList<String> getValues() {
        FileInputStream stream = null;
        String userDir = System.getProperty("user.dir");
        try {
            stream = new FileInputStream(userDir + "/config.txt");
        } catch (FileNotFoundException e) {
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        String strLine;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while ((strLine = reader.readLine()) != null) {
                String lastWord = strLine.substring(strLine.lastIndexOf(" ") + 1);
                lines.add(lastWord);
            }
        } catch (IOException e) {
        }
        try {
            reader.close();
        } catch (IOException e) {
        }
        return lines;
    }

    /**
     * *************************************************************************************************************
     * phone formatter function to clean up the phone number to make it look
     * like (123) 456-7890
     * **************************************************************************************************************
     */
    public static String phoneFormat(String phoneNumber) {

        if (phoneNumber.length() == 10) {

            String areaCode = phoneNumber.substring(0, 3);
            String nextThree = phoneNumber.substring(3, 6);
            String lastDigits = phoneNumber.substring(6, 10);

            String cleanPhone = "(" + areaCode + ")-" + nextThree + "-" + lastDigits;

            return cleanPhone;
        }
        return null;
    }

    /**
     * *************************************************************************************************************
     * create a global table model to be used in functions
     * **************************************************************************************************************
     */
    Vector<String> columnNames = new Vector<>();

    {
        columnNames.addElement("Desc");
        columnNames.addElement("UPC");
    }

    DefaultTableModel model = new DefaultTableModel(columnNames, 0) {

        @Override
        // get the data type of each column to force proper rendering
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
     * *************************************************************************************************************
     * GlobaL variables for the current work order, the current user, and the
     * current status
     * **************************************************************************************************************
     */
    public ArrayList<String> configList = getValues();
    public String connectionUrl = configList.get(0);
    public String workOrderText = "0";
    public static int clearWasDone = -1;
    public static int globalClientID = -1;

    /**
     * *************************************************************************************************************
     * applies the currently selected status to the work order in the DB
     * **************************************************************************************************************
     */
    public void updateStatus() {

        String currentWorkOrder = woTextArea.getText();

        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement checkStatus = connection.createStatement();) {
            String checkQue = "select isnull(max(1),2) as status\n"
                    + "from status_link\n"
                    + "where work_order_id = " + currentWorkOrder;

            ResultSet searchQ = checkStatus.executeQuery(checkQue);
            Boolean exists = true;
            if (searchQ.next()) {
                int dupeCheck = searchQ.getInt("status");

                if (dupeCheck == 2) {
                    exists = false;
                }
            }
            String selectedStatus = (String) statusComboBox.getSelectedItem();

            if (exists == true) {

                String updateStatus = "update status_link set status_id = (select status_id from statuses where status_name like '"
                        + selectedStatus + "')\n"
                        + "where work_Order_ID = " + currentWorkOrder;

                checkStatus.executeUpdate(updateStatus);

            } else {

                String insertStatus = "insert into status_link(work_Order_ID, status_id)\n"
                        + "select " + currentWorkOrder + ", (select status_id from statuses where status_name = '"
                        + selectedStatus + "') as status_id\n";

                checkStatus.executeUpdate(insertStatus);
            }

        } catch (SQLException e) {
        }

    }

    /**
     * *************************************************************************************************************
     * gets the selected work orders current status and applies it to the form
     * **************************************************************************************************************
     */
    public void setStatus() {
        String currentWorkOrder = woTextArea.getText();
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement setStatus = connection.createStatement();) {
            // get the current work orders status from the DB
            String getStatus = "select status_name\n"
                    + "from statuses where status_id in (select status_id from status_link where work_order_id = "
                    + currentWorkOrder + " )";

            ResultSet searchQ = setStatus.executeQuery(getStatus);
            // set the front combo box to display status
            while (searchQ.next()) {
                String currentStatus = searchQ.getString("status_name");
                statusComboBox.setSelectedItem(currentStatus);
            }

        } catch (SQLException e) {
        }
    }

    /**
     * *************************************************************************************************************
     * adds or updates the last updated datetime for a given work order
     * **************************************************************************************************************
     */
    public void lastUpdated() {
        String currentWorkOrder = woTextArea.getText();
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement updatedLast = connection.createStatement();) {

            // check table to see if there is a current entry for the workOrder
            String entryExists = "select isnull(max(1),2) as checkExists \n"
                    + "from lastUpdated\n"
                    + "where work_order_id = " + currentWorkOrder;

            // insert new entry
            String addEntry = "insert into lastUpdated (work_order_id, lastUpdated)\n"
                    + "select " + currentWorkOrder + " as work_order_id,\n"
                    + "getdate() as lastUpdated";

            // if there is a current entry apply an update
            String updateEntry = "update lastUpdated\n"
                    + "set lastUpdated = getdate()\n"
                    + "where work_order_id = " + currentWorkOrder;

            ResultSet searchQ = updatedLast.executeQuery(entryExists);

            Boolean doesExist = true;

            if (searchQ.next()) {
                int existCheck = searchQ.getInt("checkExists");
                if (existCheck == 2) {
                    doesExist = false;
                }
            }
            if (doesExist) {
                updatedLast.executeUpdate(updateEntry);
            } else {
                updatedLast.executeUpdate(addEntry);
            }

        } catch (SQLException e) {
        }

    }

    /**
     * *************************************************************************************************************
     * clear all form fields and set global client ID to -1
     * **************************************************************************************************************
     */
    public void clearForm() {
        globalClientID = -1;
        fNameText.setText("");
        lNameText.setText("");
        phoneOneText.setText("");
        cellPhoneText.setText("");
        checkDesktop.setSelected(false);
        checkLaptop.setSelected(false);
        checkTablet.setSelected(false);
        checkCharger.setSelected(false);
        workToBeDone.setText("");
        workDoneText.setText("");
        equipmentText.setText("");
        passwordText.setText("");
        pinText.setText("");
        eMailText.setText("");
        clientIDText.setText("");
        woTextArea.setText("");
        companyText.setText("");
        createdDateText.setText(sdf3.format(new Date()));
        fNameText.requestFocusInWindow();
        clearWasDone = 1;
        statusComboBox.setSelectedIndex(0);
    }

    /**
     * *************************************************************************************************************
     * update provided hardware
     * **************************************************************************************************************
     */
    public void updateHardware() {
        String currentWorkOrder = woTextArea.getText();
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement updateHardware = connection.createStatement();) {

            boolean desktop = checkDesktop.isSelected();
            boolean laptop = checkLaptop.isSelected();
            boolean tablet = checkTablet.isSelected();
            boolean charger = checkCharger.isSelected();

            int desktopBool = (desktop) ? 1 : 0;
            int laptopBool = (laptop) ? 1 : 0;
            int tabletBool = (tablet) ? 1 : 0;
            int chargerBool = (charger) ? 1 : 0;

            String updateQue = "update client_service \n"
                    + "set desktop = " + desktopBool + ",\n"
                    + "laptop = " + laptopBool + ",\n"
                    + "tablet = " + tabletBool + ",\n"
                    + "charger = " + chargerBool + "\n"
                    + "where work_order_id = " + currentWorkOrder;

            updateHardware.executeUpdate(updateQue);

        } catch (SQLException ex) {
            Logger.getLogger(SignInFront.class.getName()).log(Level.SEVERE, null, ex);

        }
    }

    /**
     * *************************************************************************************************************
     * update work order
     * **************************************************************************************************************
     */
    public void updateWorkOrder() {
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement updateWorkOrder = connection.createStatement();) {

            String workToDo = workToBeDone.getText().replace("'", "''");
            String clientPass = passwordText.getText().replace("'", "''");
            String clientPin = pinText.getText();
            String workDone = workDoneText.getText().replace("'", "''");
            String workOrderID = woTextArea.getText();
            String techName = (String) techComboBox.getSelectedItem();

            String addClientScript = "update client_service"
                    + " set work_to_do = '" + workToDo + "', "
                    + "pc_pass = '" + clientPass + "', "
                    + "pc_pin = '" + clientPin + "', "
                    + "work_done = '" + workDone + "', "
                    + "tech_name = '" + techName + "'"
                    + "where work_order_ID = ltrim(rtrim('" + workOrderID + "'))";

            updateWorkOrder.executeUpdate(addClientScript);
            updateStatus();
            lastUpdated();

        } catch (SQLException e) {
        }
    }

    public void addNewClient() {
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement addClientEntry = connection.createStatement();) {

            String firstName = fNameText.getText().replace("'", "''");
            String lastName = lNameText.getText().replace("'", "''");
            String phoneHome = phoneOneText.getText().replace("(", "").replace(")", "").replace(" ", "")
                    .replace("-", "");
            String phoneCell = cellPhoneText.getText().replace("(", "").replace(")", "").replace(" ", "")
                    .replace("-", "");
            String companyString = companyText.getText().replace("'", "''");
            String emailString = eMailText.getText();

            String addClientScript = "insert into clients(fname, lname, companyName, phone, phone2, email, creation_date)"
                    + "select '"
                    + firstName + "' as fname, '"
                    + lastName + "' as lname, '"
                    + companyString + "' as companyName, '"
                    + phoneHome + "' as phone, '"
                    + phoneCell + "' as phone2,'"
                    + emailString + "' as email,"
                    + "getdate() as creation_date";

            addClientEntry.executeUpdate(addClientScript);

        } catch (SQLException ex) {
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        backgroundPanel = new javax.swing.JPanel();
        titleText = new javax.swing.JLabel();
        fNameText = new javax.swing.JTextField();
        lNameText = new javax.swing.JTextField();
        phoneOneText = new javax.swing.JTextField();
        cellPhoneText = new javax.swing.JTextField();
        equipmentField = new javax.swing.JScrollPane();
        equipmentText = new javax.swing.JTextArea();
        workToDoField = new javax.swing.JScrollPane();
        workToBeDone = new javax.swing.JTextArea();
        checkLaptop = new javax.swing.JCheckBox();
        checkDesktop = new javax.swing.JCheckBox();
        checkTablet = new javax.swing.JCheckBox();
        passwordText = new javax.swing.JTextField();
        pinText = new javax.swing.JTextField();
        eMailText = new javax.swing.JTextField();
        checkCharger = new javax.swing.JCheckBox();
        workPerformedArea = new javax.swing.JScrollPane();
        workDoneText = new javax.swing.JTextArea();
        partBox = new javax.swing.JTextField();
        MinChargeText = new javax.swing.JTextField();
        legalPane = new javax.swing.JScrollPane();
        legalText = new javax.swing.JTextArea();
        SigLabel = new javax.swing.JLabel();
        signText = new javax.swing.JTextField();
        clientIDText = new javax.swing.JLabel();
        companyText = new javax.swing.JTextField();
        woTextArea = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        logo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        saveIcon = new javax.swing.JLabel();
        saveNewClientButt = new javax.swing.JButton();
        createdDateText = new javax.swing.JTextField();
        statusComboBox = new javax.swing.JComboBox<>();
        topMenu = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        addNewClientButt = new javax.swing.JMenuItem();
        searchExistingClient = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        searchWorkOrderButt = new javax.swing.JMenuItem();
        updateWorkOrder = new javax.swing.JMenuItem();
        completeWorkOrder = new javax.swing.JMenuItem();
        clearWorkOrder = new javax.swing.JMenuItem();
        woHistory = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        printWorkOrder = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("National Computer Resource Sign In");
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setIconImage(Toolkit.getDefaultToolkit().getImage(SignInFront.class.getResource("/icons/happyIcon3.png")));
        setMaximumSize(new java.awt.Dimension(700, 965));
        setMinimumSize(new java.awt.Dimension(700, 965));
        setPreferredSize(new java.awt.Dimension(710, 965));
        setSize(new java.awt.Dimension(700, 965));

        backgroundPanel.setBackground(new java.awt.Color(255, 255, 255));
        backgroundPanel.setPreferredSize(new java.awt.Dimension(780, 927));

        titleText.setBackground(new java.awt.Color(255, 255, 255));
        titleText.setFont(new java.awt.Font("Rockwell", 0, 20)); // NOI18N
        titleText.setForeground(new java.awt.Color(0, 0, 0));
        titleText.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleText.setText("Service Work Order");

        fNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        fNameText.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "First Name", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        fNameText.setNextFocusableComponent(lNameText);
        fNameText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fNameTextFocusGained(evt);
            }
        });
        fNameText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fNameTextKeyTyped(evt);
            }
        });

        lNameText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        lNameText.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Last Name", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        lNameText.setNextFocusableComponent(phoneOneText);

        phoneOneText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        phoneOneText.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Home Phone", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        phoneOneText.setNextFocusableComponent(cellPhoneText);

        cellPhoneText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        cellPhoneText.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Cell Phone", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N

        equipmentField.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Other Equipment", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        equipmentField.setNextFocusableComponent(passwordText);
        equipmentField.setOpaque(false);

        equipmentText.setColumns(1);
        equipmentText.setRows(1);
        equipmentText.setTabSize(0);
        equipmentText.setNextFocusableComponent(passwordText);
        equipmentField.setViewportView(equipmentText);

        workToDoField.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Work to be Done", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        workToDoField.setOpaque(false);

        workToBeDone.setColumns(20);
        workToBeDone.setLineWrap(true);
        workToBeDone.setRows(5);
        workToBeDone.setWrapStyleWord(true);
        workToBeDone.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                workToBeDoneFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                workToBeDoneFocusLost(evt);
            }
        });
        workToBeDone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                workToBeDoneKeyTyped(evt);
            }
        });
        workToDoField.setViewportView(workToBeDone);

        checkLaptop.setBackground(new java.awt.Color(255, 255, 255));
        checkLaptop.setFont(new java.awt.Font("Rockwell", 0, 12)); // NOI18N
        checkLaptop.setText("Laptop");
        checkLaptop.setFocusPainted(false);
        checkLaptop.setOpaque(true);
        checkLaptop.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkLaptopItemStateChanged(evt);
            }
        });

        checkDesktop.setBackground(new java.awt.Color(255, 255, 255));
        checkDesktop.setFont(new java.awt.Font("Rockwell", 0, 12)); // NOI18N
        checkDesktop.setText("Desktop");
        checkDesktop.setFocusPainted(false);
        checkDesktop.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkDesktopItemStateChanged(evt);
            }
        });

        checkTablet.setBackground(new java.awt.Color(255, 255, 255));
        checkTablet.setFont(new java.awt.Font("Rockwell", 0, 12)); // NOI18N
        checkTablet.setText("Other");
        checkTablet.setFocusPainted(false);
        checkTablet.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkTabletItemStateChanged(evt);
            }
        });

        passwordText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        passwordText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Password", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        passwordText.setNextFocusableComponent(pinText);

        pinText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pinText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Pin", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        pinText.setNextFocusableComponent(workToBeDone);

        techComboBox.setBackground(new java.awt.Color(255, 255, 255));
        techComboBox.setEditable(true);
        techComboBox.setForeground(new java.awt.Color(0, 0, 0));
        techComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Default", "Chris R", "Jarett L", "Paul Q", "James C", "Aaron S" }));
        techComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Tech", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        techComboBox.setFocusable(false);
        techComboBox.setRequestFocusEnabled(true);
        for (int i = 0; i < techComboBox.getComponentCount(); i++)
        {
            if (techComboBox.getComponent(i) instanceof JComponent) {
                ((JComponent) techComboBox.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
            }
        }
        ((JLabel)techComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        eMailText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        eMailText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "E-Mail", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        eMailText.setNextFocusableComponent(equipmentText);

        checkCharger.setBackground(new java.awt.Color(255, 255, 255));
        checkCharger.setFont(new java.awt.Font("Rockwell", 0, 12)); // NOI18N
        checkCharger.setText("Charger");
        checkCharger.setFocusPainted(false);
        checkCharger.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                checkChargerItemStateChanged(evt);
            }
        });

        workPerformedArea.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Work Performed", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        workPerformedArea.setOpaque(false);

        workDoneText.setColumns(20);
        workDoneText.setLineWrap(true);
        workDoneText.setRows(5);
        workDoneText.setWrapStyleWord(true);
        workDoneText.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                workDoneTextFocusLost(evt);
            }
        });
        workDoneText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                workDoneTextKeyTyped(evt);
            }
        });
        workPerformedArea.setViewportView(workDoneText);

        partBox.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Parts Used", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        partBox.setOpaque(true);

        MinChargeText.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        MinChargeText.setText("$50");
        MinChargeText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Sign In Fee", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N

        legalPane.setOpaque(false);

        legalText.setEditable(false);
        legalText.setColumns(20);
        legalText.setFont(new java.awt.Font("Segoe UI", 0, 10)); // NOI18N
        legalText.setLineWrap(true);
        legalText.setRows(5);
        legalText.setText("I agree to release the above equipment to National Computer Resource (\"NCR\") for evaluation and/or repair. I agree that NCR is in no way responsible for the condition of equipment prior to be serviced, or for lost or damaged data that may occur during the evaluation and/or repair. I agree that the above description of the equipment is based solely upon my representations and may be in error. I agree that NCR makes no representations, warranties or guarantees as to the length of time to make an evaluation or repair, as to whether can in fact be made, or as to the current or future impact any evaluation or repairs may have on existing hardware, software or external and peripheral devices that may be attached to the equipment (eg., networks). I agree that service fees are payable whether or not a problem is solved. I agree that National Computer Resource will not release the above equipment until all parts and/or labour charges have been paid in full. I agree that after 90-days all unclaimed equipment becomes property of NCR.");
        legalText.setWrapStyleWord(true);
        legalText.setFocusable(false);
        legalPane.setViewportView(legalText);

        SigLabel.setText("Client SIgnature:");

        signText.setEditable(false);
        signText.setBackground(new java.awt.Color(255, 255, 255));
        signText.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        signText.setForeground(new java.awt.Color(0, 0, 0));
        signText.setText("________________________________________________________________________________________________________________");
        signText.setAutoscrolls(false);
        signText.setBorder(null);
        signText.setFocusable(false);

        clientIDText.setBackground(new java.awt.Color(255, 255, 255));
        clientIDText.setForeground(new java.awt.Color(255, 255, 255));
        clientIDText.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);

        companyText.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        companyText.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Company", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        companyText.setNextFocusableComponent(eMailText);

        woTextArea.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        woTextArea.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        woTextArea.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Work Order ID", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        woTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                woTextAreaKeyPressed(evt);
            }
        });

        jSeparator1.setBackground(new java.awt.Color(0, 0, 0));
        jSeparator1.setForeground(new java.awt.Color(0, 0, 0));

        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/logo-Scaled_inverted.png"))); // NOI18N

        jScrollPane1.setBorder(null);
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setWheelScrollingEnabled(false);

        jTextPane1.setBorder(null);
        jTextPane1.setText("     102-1980 Cooper Rd., Kelowna, B.C., Canada V1Y-8K5\n   Phone: 250-868-9765 / 250-763-2492 | Fax:877-263-8594 \n   www.ncro.ca | service@ncro.ca | facebook.com/ncrodotca");
        jScrollPane1.setViewportView(jTextPane1);

        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png"))); // NOI18N

        saveNewClientButt.setBackground(new java.awt.Color(255, 255, 255));
        saveNewClientButt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/saveClient.png"))); // NOI18N
        saveNewClientButt.setBorder(null);
        saveNewClientButt.setFocusable(false);
        saveNewClientButt.setOpaque(true);
        saveNewClientButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveNewClientButtActionPerformed(evt);
            }
        });

        createdDateText.setBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true), "Created Date", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N

        statusComboBox.setBackground(new java.awt.Color(255, 255, 255));
        statusComboBox.setEditable(true);
        statusComboBox.setForeground(new java.awt.Color(0, 0, 0));
        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "New", "In Progress", "Pending Client", "Pending Ordered Part", "Due", "Over Due", "Complete", "Abandoned" }));
        statusComboBox.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2), "Status", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Rockwell", 0, 12))); // NOI18N
        statusComboBox.setFocusable(false);
        statusComboBox.setRequestFocusEnabled(true);
        for (int i = 0; i < statusComboBox.getComponentCount(); i++)
        {
            if (statusComboBox.getComponent(i) instanceof JComponent) {
                ((JComponent) statusComboBox.getComponent(i)).setBorder(new EmptyBorder(0, 0,0,0));
            }
        }
        ((JLabel)statusComboBox.getRenderer()).setHorizontalAlignment(SwingConstants.CENTER);
        statusComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                statusComboBoxItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout backgroundPanelLayout = new javax.swing.GroupLayout(backgroundPanel);
        backgroundPanel.setLayout(backgroundPanelLayout);
        backgroundPanelLayout.setHorizontalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(clientIDText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SigLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, backgroundPanelLayout.createSequentialGroup()
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(workPerformedArea)
                                    .addComponent(MinChargeText, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(partBox))
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(phoneOneText)
                                            .addComponent(fNameText))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(cellPhoneText)
                                            .addComponent(lNameText)))
                                    .addComponent(companyText)
                                    .addComponent(eMailText)
                                    .addComponent(workToDoField, javax.swing.GroupLayout.PREFERRED_SIZE, 317, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(techComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(statusComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, 368, Short.MAX_VALUE)
                                            .addComponent(equipmentField, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(pinText, javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(passwordText, javax.swing.GroupLayout.Alignment.TRAILING)))
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addComponent(checkLaptop, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checkDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(checkTablet, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(createdDateText)))
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                                        .addComponent(saveNewClientButt, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(titleText, javax.swing.GroupLayout.PREFERRED_SIZE, 261, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(saveIcon))
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 340, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addComponent(woTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(backgroundPanelLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(checkCharger, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(legalPane, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(9, 9, 9)))
                .addGap(13, 13, 13))
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(signText, javax.swing.GroupLayout.PREFERRED_SIZE, 552, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );
        backgroundPanelLayout.setVerticalGroup(
            backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(backgroundPanelLayout.createSequentialGroup()
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(backgroundPanelLayout.createSequentialGroup()
                            .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(saveIcon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(titleText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(backgroundPanelLayout.createSequentialGroup()
                                    .addComponent(saveNewClientButt)
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(logo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(woTextArea, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(checkDesktop, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkTablet, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(checkCharger, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(checkLaptop, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(fNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lNameText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(phoneOneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cellPhoneText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(createdDateText))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(techComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(eMailText)
                    .addComponent(statusComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addComponent(equipmentField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(passwordText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pinText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(workToDoField, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(backgroundPanelLayout.createSequentialGroup()
                        .addComponent(workPerformedArea, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(MinChargeText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(partBox, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(legalPane, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(backgroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(SigLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(signText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(clientIDText, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(142, 142, 142))
        );

        topMenu.setBackground(new java.awt.Color(255, 255, 255));
        topMenu.setBorder(null);
        topMenu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        topMenu.setPreferredSize(new java.awt.Dimension(700, 26));

        jMenu1.setBackground(new java.awt.Color(255, 255, 255));
        jMenu1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu1.setForeground(new java.awt.Color(0, 0, 0));
        jMenu1.setText("<html><p style='text-align:center;'>Client Options</p></html>");
        jMenu1.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jMenu1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu1.setMinimumSize(new java.awt.Dimension(140, 24));
        jMenu1.setPreferredSize(new java.awt.Dimension(172, 26));

        addNewClientButt.setBackground(new java.awt.Color(255, 255, 255));
        addNewClientButt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        addNewClientButt.setForeground(new java.awt.Color(0, 0, 0));
        addNewClientButt.setText("Add New Client");
        addNewClientButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        addNewClientButt.setOpaque(true);
        addNewClientButt.setPreferredSize(new java.awt.Dimension(172, 26));
        addNewClientButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addNewClientButtActionPerformed(evt);
            }
        });
        jMenu1.add(addNewClientButt);

        searchExistingClient.setBackground(new java.awt.Color(255, 255, 255));
        searchExistingClient.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchExistingClient.setForeground(new java.awt.Color(0, 0, 0));
        searchExistingClient.setText("Search Existing Clients");
        searchExistingClient.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchExistingClient.setOpaque(true);
        searchExistingClient.setPreferredSize(new java.awt.Dimension(172, 26));
        searchExistingClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchExistingClientActionPerformed(evt);
            }
        });
        jMenu1.add(searchExistingClient);

        topMenu.add(jMenu1);

        jMenu2.setBackground(new java.awt.Color(255, 255, 255));
        jMenu2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu2.setForeground(new java.awt.Color(0, 0, 0));
        jMenu2.setText("<html><p style='text-align:center;'>Work Order Options</p></html>");
        jMenu2.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jMenu2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu2.setPreferredSize(new java.awt.Dimension(172, 26));

        searchWorkOrderButt.setBackground(new java.awt.Color(255, 255, 255));
        searchWorkOrderButt.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        searchWorkOrderButt.setForeground(new java.awt.Color(0, 0, 0));
        searchWorkOrderButt.setText("Search Work Order");
        searchWorkOrderButt.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        searchWorkOrderButt.setOpaque(true);
        searchWorkOrderButt.setPreferredSize(new java.awt.Dimension(172, 26));
        searchWorkOrderButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchWorkOrderButtActionPerformed(evt);
            }
        });
        jMenu2.add(searchWorkOrderButt);

        updateWorkOrder.setBackground(new java.awt.Color(255, 255, 255));
        updateWorkOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        updateWorkOrder.setForeground(new java.awt.Color(0, 0, 0));
        updateWorkOrder.setText("Update Work Order");
        updateWorkOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        updateWorkOrder.setOpaque(true);
        updateWorkOrder.setPreferredSize(new java.awt.Dimension(172, 26));
        updateWorkOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateWorkOrderActionPerformed(evt);
            }
        });
        jMenu2.add(updateWorkOrder);

        completeWorkOrder.setBackground(new java.awt.Color(255, 255, 255));
        completeWorkOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        completeWorkOrder.setForeground(new java.awt.Color(0, 0, 0));
        completeWorkOrder.setText("Complete Work Order");
        completeWorkOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        completeWorkOrder.setOpaque(true);
        completeWorkOrder.setPreferredSize(new java.awt.Dimension(172, 26));
        completeWorkOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                completeWorkOrderActionPerformed(evt);
            }
        });
        jMenu2.add(completeWorkOrder);

        clearWorkOrder.setBackground(new java.awt.Color(255, 255, 255));
        clearWorkOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        clearWorkOrder.setForeground(new java.awt.Color(0, 0, 0));
        clearWorkOrder.setText("Clear Work Order");
        clearWorkOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        clearWorkOrder.setOpaque(true);
        clearWorkOrder.setPreferredSize(new java.awt.Dimension(172, 26));
        clearWorkOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearWorkOrderActionPerformed(evt);
            }
        });
        jMenu2.add(clearWorkOrder);

        woHistory.setBackground(new java.awt.Color(255, 255, 255));
        woHistory.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        woHistory.setForeground(new java.awt.Color(0, 0, 0));
        woHistory.setText("Work Order History");
        woHistory.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        woHistory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                woHistoryActionPerformed(evt);
            }
        });
        jMenu2.add(woHistory);

        topMenu.add(jMenu2);

        jMenu3.setBackground(new java.awt.Color(255, 255, 255));
        jMenu3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu3.setForeground(new java.awt.Color(0, 0, 0));
        jMenu3.setText("<html><p style='text-align:center;'>Print Options</p></html>");
        jMenu3.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jMenu3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu3.setPreferredSize(new java.awt.Dimension(172, 26));

        printWorkOrder.setBackground(new java.awt.Color(255, 255, 255));
        printWorkOrder.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        printWorkOrder.setForeground(new java.awt.Color(0, 0, 0));
        printWorkOrder.setText("Print Work Order");
        printWorkOrder.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        printWorkOrder.setOpaque(true);
        printWorkOrder.setPreferredSize(new java.awt.Dimension(172, 26));
        printWorkOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printWorkOrderActionPerformed(evt);
            }
        });
        jMenu3.add(printWorkOrder);

        topMenu.add(jMenu3);

        jMenu4.setBackground(new java.awt.Color(255, 255, 255));
        jMenu4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenu4.setForeground(new java.awt.Color(0, 0, 0));
        jMenu4.setText("<html><p style='text-align:center;'>Other Options</p></html>");
        jMenu4.setFont(new java.awt.Font("Rockwell", 0, 14)); // NOI18N
        jMenu4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jMenu4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jMenu4.setPreferredSize(new java.awt.Dimension(172, 26));

        jMenuItem9.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem9.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem9.setText("About App");
        jMenuItem9.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem9.setOpaque(true);
        jMenuItem9.setPreferredSize(new java.awt.Dimension(172, 26));
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem9);

        jMenuItem10.setBackground(new java.awt.Color(255, 255, 255));
        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jMenuItem10.setForeground(new java.awt.Color(0, 0, 0));
        jMenuItem10.setText("About Creator");
        jMenuItem10.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jMenuItem10.setOpaque(true);
        jMenuItem10.setPreferredSize(new java.awt.Dimension(172, 26));
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        topMenu.add(jMenu4);

        setJMenuBar(topMenu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 713, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(backgroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 910, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkLaptopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkLaptopItemStateChanged
        updateHardware();
    }//GEN-LAST:event_checkLaptopItemStateChanged

    private void checkDesktopItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkDesktopItemStateChanged
        updateHardware();
    }//GEN-LAST:event_checkDesktopItemStateChanged

    private void checkTabletItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkTabletItemStateChanged
        updateHardware();
    }//GEN-LAST:event_checkTabletItemStateChanged

    private void checkChargerItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_checkChargerItemStateChanged
        updateHardware();
    }//GEN-LAST:event_checkChargerItemStateChanged

    // Create the print function to call.
    public static class Printer implements Printable {

        final Component comp;

        public Printer(Component comp) {
            this.comp = comp;
        }

        @Override
        public int print(Graphics g, PageFormat format, int page_index)
                throws PrinterException {
            if (page_index > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            // get the bounds of the component
            Dimension dim = comp.getSize();
            double cHeight = dim.getHeight();
            double cWidth = dim.getWidth();

            // get the bounds of the printable area
            double pHeight = format.getImageableHeight();
            double pWidth = format.getImageableWidth();

            double pXStart = format.getImageableX();
            double pYStart = format.getImageableY();

            double xRatio = pWidth / cWidth;
            double yRatio = pHeight / cHeight;

            Graphics2D g2 = (Graphics2D) g;
            g2.translate(pXStart, pYStart);
            g2.scale(xRatio + 50, yRatio + 50);
            comp.paint(g2);

            return Printable.PAGE_EXISTS;
        }
    }

    // open the client search form
    private void searchExistingClientActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchExistingClientActionPerformed
        // search clears form as well.
        SearchForm gui = new SearchForm();
        gui.setVisible(true);
        clearForm();

    }// GEN-LAST:event_searchExistingClientActionPerformed

    private void woTextAreaKeyPressed(java.awt.event.KeyEvent evt) {
        woTextArea.addActionListener(action);
    }

    private static final SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static BufferedImage generateCode39BarcodeImage(String barcodeText) throws Exception {

        final int dpi = 180;
        Code39Bean barcodeGenerator = new Code39Bean();
        barcodeGenerator.setChecksumMode(ChecksumMode.CP_AUTO);
        barcodeGenerator.setDisplayStartStop(true);
        barcodeGenerator.setHeight(10);
        BitmapCanvasProvider canvas = new BitmapCanvasProvider(dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
        barcodeGenerator.generateBarcode(canvas, barcodeText);
        return canvas.getBufferedImage();
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    private void printWorkOrderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_printWorkOrderActionPerformed

        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat preformat = pjob.defaultPage();
        preformat.setOrientation(PageFormat.PORTRAIT);
        Paper paper = new Paper();
        double margin = 2;
        paper.setImageableArea(margin, margin, paper.getWidth() - margin * 2, paper.getHeight()
                - margin * 2);
        PageFormat postformat = pjob.pageDialog(preformat);
        postformat.setPaper(paper);
        // If user does not hit cancel then print.
        if (preformat != postformat) {
            // Set print component
            pjob.setPrintable(new PrintForm.Printer(backgroundPanel), postformat);
            if (pjob.printDialog()) {
                try {
                    pjob.print();

                } catch (PrinterException ex) {
                    Logger.getLogger(CompleteFormFront.class
                            .getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }// GEN-LAST:event_printWorkOrderActionPerformed
    // Global Client ID value to keep data entry consistent

    private void addNewClientButtActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_addNewClientButtActionPerformed
        clearWasDone = -1;
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
        workToBeDone.setText("");
        if (fNameText.getText().length() > 0) {
            addNewClient();

            try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement addWorkOrder = connection.createStatement();) {

                String firstName = fNameText.getText().replace("'", "''");
                String lastName = lNameText.getText().replace("'", "''");
                String phoneHome = phoneOneText.getText().replace("(", "").replace(")", "").replace(" ", "")
                        .replace("-", "");
                String phoneCell = cellPhoneText.getText().replace("(", "").replace(")", "").replace(" ", "")
                        .replace("-", "");

                String getClientID = "select client_id from clients where fname = '"
                        + firstName + "' and lname = '"
                        + lastName + "' and phone = '"
                        + phoneHome + "'";

                ResultSet searchQ = addWorkOrder.executeQuery(getClientID);

                while (searchQ.next()) {
                    String clientIDString = searchQ.getString("client_id");
                    clientIDText.setText(clientIDString);
                    globalClientID = Integer.parseInt(clientIDString);
                }

                NewClientAddedMessage gui = new NewClientAddedMessage();
                gui.setVisible(true);

                String getWorkOrder = "select top 1 work_order_id + 1 as work_order_id from client_service order by 1 desc";

                ResultSet searchT = addWorkOrder.executeQuery(getWorkOrder);
                statusComboBox.setSelectedIndex(0);
                while (searchT.next()) {
                    String workOrderText = searchT.getString("work_order_id");
                    woTextArea.setText(workOrderText);
                    phoneOneText.setText(phoneFormat(phoneHome));
                    cellPhoneText.setText(phoneFormat(phoneCell));
                }

            } catch (SQLException e) {
            }
        } else {
            AddNewClientError gui = new AddNewClientError();
            gui.setVisible(true);
        }

    }// GEN-LAST:event_addNewClientButtActionPerformed

    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem9ActionPerformed
        AboutAppFrame gui = new AboutAppFrame();
        gui.setVisible(true);
    }// GEN-LAST:event_jMenuItem9ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_jMenuItem10ActionPerformed
        AboutCreatorFrame gui = new AboutCreatorFrame();
        gui.setVisible(true);
    }// GEN-LAST:event_jMenuItem10ActionPerformed

    private void workDoneTextFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_workDoneTextFocusLost
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png")));
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));

        updateWorkOrder();
    }// GEN-LAST:event_workDoneTextFocusLost

    private void workToBeDoneFocusLost(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_workToBeDoneFocusLost
        // if the global ID is populated perform update when Work to be done fucus is
        // lost
        if (globalClientID > 0) {
            saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png"))); // NOI18N
            saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
            updateWorkOrder();

        }
    }// GEN-LAST:event_workToBeDoneFocusLost

    private void workToBeDoneKeyTyped(java.awt.event.KeyEvent evt) {
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/FrownIcon.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
        updateWorkOrder();
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
    }

    private void workDoneTextKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_workDoneTextKeyTyped

        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/FrownIcon.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
    }// GEN-LAST:event_workDoneTextKeyTyped

    private void fNameTextKeyTyped(java.awt.event.KeyEvent evt) {// GEN-FIRST:event_fNameTextKeyTyped

        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/FrownIcon.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
    }// GEN-LAST:event_fNameTextKeyTyped

    private void workToBeDoneFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_workToBeDoneFocusGained
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/FrownIcon.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));

        if (clearWasDone < 1) {
            // check if global client ID is set

            if (globalClientID == -1) {
                SaveClientFirstFrame gui = new SaveClientFirstFrame();
                gui.setVisible(true);
                clearWasDone = 1;
            } else if (globalClientID > 0) {
                lastUpdated();
                try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement addWorkOrder = connection.createStatement();) {

                    String clientID = String.valueOf(globalClientID);
                    String workOrderText = woTextArea.getText();

                    String workOrderExist = "select isnull(max(1),2) as checkDupe from client_service where client_id = '"
                            + clientID + "' and work_order_id = '"
                            + workOrderText + "'";

                    ResultSet searchQ = addWorkOrder.executeQuery(workOrderExist);

                    Boolean isDupe = true;
                    if (searchQ.next()) {
                        int dupeCheck = searchQ.getInt("checkDupe");

                        if (dupeCheck == 2) {
                            isDupe = false;
                        }
                    }

                    if (isDupe == false) {

                        String currentClient = String.valueOf(globalClientID);
                        String workToDo = workToBeDone.getText().replace("'", "''");
                        boolean desktop = checkDesktop.isSelected();
                        boolean laptop = checkLaptop.isSelected();
                        boolean tablet = checkTablet.isSelected();
                        boolean charger = checkCharger.isSelected();
                        String clientPass = passwordText.getText().replace("'", "''");
                        String clientPin = pinText.getText();
                        String techName = techComboBox.getSelectedItem().toString();
                        String workDone = workDoneText.getText().replace("'", "''");
                        String otherEquip = equipmentText.getText().replace("'", "''");

                        int desktopBool = (desktop) ? 1 : 0;
                        int laptopBool = (laptop) ? 1 : 0;
                        int tabletBool = (tablet) ? 1 : 0;
                        int chargerBool = (charger) ? 1 : 0;

                        String addClientScript = "insert into client_service(client_id, work_to_do, pc_pass, pc_pin, other_equip, tech_name, desktop, laptop, tablet, charger, work_done)"
                                + "select " + currentClient + " as client_id,"
                                + "'" + workToDo + "' as work_to_do,"
                                + "'" + clientPass + "' as pc_pass,"
                                + "'" + clientPin + "' as pc_pin,"
                                + "'" + otherEquip + "' as other_equip,"
                                + "'" + techName + "' as tech_name,"
                                + desktopBool + " as desktop,"
                                + laptopBool + " as laptop,"
                                + tabletBool + " as tablet,"
                                + chargerBool + " as charger,"
                                + "'" + workDone + "' as work_done";
                        addWorkOrder.executeUpdate(addClientScript);
                        updateStatus();
                    }

                } catch (SQLException ex) {
                    Logger.getLogger(SignInFront.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }// GEN-LAST:event_workToBeDoneFocusGained

    private void fNameTextFocusGained(java.awt.event.FocusEvent evt) {// GEN-FIRST:event_fNameTextFocusGained
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
    }// GEN-LAST:event_fNameTextFocusGained

    private void saveNewClientButtActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_saveNewClientButtActionPerformed
        clearWasDone = -1;
        saveIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/happyIcon3.png"))); // NOI18N
        saveIcon.setPreferredSize(new java.awt.Dimension(30, 30));
        workToBeDone.setText("");
        statusComboBox.setSelectedIndex(0);
        if (fNameText.getText().length() > 0) {
            addNewClient();
            try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement addWorkOrder = connection.createStatement();) {

                String firstName = fNameText.getText().replace("'", "''");
                String lastName = lNameText.getText().replace("'", "''");
                String phoneHome = phoneOneText.getText().replace("(", "").replace(")", "").replace(" ", "")
                        .replace("-", "");
                String phoneCell = cellPhoneText.getText().replace("(", "").replace(")", "").replace(" ", "")
                        .replace("-", "");

                String getClientID = "select client_id from clients where fname = '"
                        + firstName + "' and lname = '"
                        + lastName + "' and phone = '"
                        + phoneHome + "'";

                ResultSet searchQ = addWorkOrder.executeQuery(getClientID);

                while (searchQ.next()) {
                    String clientIDString = searchQ.getString("client_id");
                    clientIDText.setText(clientIDString);
                    globalClientID = Integer.parseInt(clientIDString);
                }
                NewClientAddedMessage gui = new NewClientAddedMessage();
                gui.setVisible(true);

                String getWorkOrder = "select top 1 work_order_id + 1 as work_order_id from client_service order by 1 desc";

                ResultSet searchT = addWorkOrder.executeQuery(getWorkOrder);

                while (searchT.next()) {
                    String workOrderText = searchT.getString("work_order_id");
                    woTextArea.setText(workOrderText);
                    phoneOneText.setText(phoneFormat(phoneHome));
                    cellPhoneText.setText(phoneFormat(phoneCell));
                }

            } catch (SQLException e) {
            }
        } else {
            AddNewClientError gui = new AddNewClientError();
            gui.setVisible(true);
        }
    }// GEN-LAST:event_saveNewClientButtActionPerformed

    private void woHistoryActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_woHistoryActionPerformed
        WorkOrderHistoryFrame gui = new WorkOrderHistoryFrame();
        gui.setVisible(true);
    }// GEN-LAST:event_woHistoryActionPerformed

    private void clearWorkOrderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_clearWorkOrderActionPerformed
        clearForm();
    }// GEN-LAST:event_clearWorkOrderActionPerformed

    private void completeWorkOrderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_completeWorkOrderActionPerformed
        CompleteFormFront gui = new CompleteFormFront();
        gui.setVisible(true);
        setStatus();
        model.setRowCount(0);

        String date = sdf3.format(new Date());
        String fname = fNameText.getText();
        String lname = lNameText.getText();
        String companyName = companyText.getText();
        String phoneText = phoneOneText.getText();
        String cellText = cellPhoneText.getText();
        String emailText = eMailText.getText();

        String workToDo = workToBeDone.getText();
        String workPerformed = workDoneText.getText();
        String workOrderID = woTextArea.getText();

        CompleteFormFront.fNameText.setText(fname);
        CompleteFormFront.lNameText.setText(lname);
        CompleteFormFront.phoneText.setText(phoneText);
        CompleteFormFront.cellText.setText(cellText);
        CompleteFormFront.compName.setText(companyName);
        CompleteFormFront.eMailText.setText(emailText);

        CompleteFormFront.workToDoText.setText(workToDo);
        CompleteFormFront.workPerformedText.setText(workPerformed);
        CompleteFormFront.woText.setText(workOrderID);
        CompleteFormFront.receivedText.setText(date);

        // get currently attached parts
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            workOrderText = woTextArea.getText();
            String getUPCs = """
                    select upc.upc_desc, upc.upc_cost, upc.upc_code
                    from service_link as sl
                    inner join upc_codes as upc
                    on sl.service_fee_id = upc.upc_id
                    where work_Order_ID =
                    """ + workOrderText;

            ResultSet searchQ = statement.executeQuery(getUPCs);
            if (!searchQ.isBeforeFirst()) {

            } else {
                while (searchQ.next()) {
                    String upcDescText = searchQ.getString("upc_desc");
                    String upcCostText = searchQ.getString("upc_cost");
                    String upcCodeText = searchQ.getString("upc_code");
                    String completeText = upcDescText + " -> $" + upcCostText;
                    Image newImage = generateCode39BarcodeImage(upcCodeText);
                    ImageIcon icon = new ImageIcon(newImage);

                    Object[] rowData = {completeText, icon};
                    model.addRow(rowData);
                    CompleteFormFront.partsUsedList.setModel(model);
                    CompleteFormFront.partsUsedList.setRowHeight(
                            ((ImageIcon) CompleteFormFront.partsUsedList.getValueAt(0, 1)).getIconHeight());
                }
            }

            CompleteFormFront.partsUsedList.getColumnModel().getColumn(0).setMaxWidth(130);
            CompleteFormFront.partsUsedList.getColumnModel().getColumn(0).setMinWidth(130);

        } catch (SQLException ex) {
            Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (Exception ex) {
            Logger.getLogger(PartsUsedFrame.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

        if (model.getRowCount() > 0) {
            CompleteFormFront.partsUsedList
                    .setRowHeight(((ImageIcon) CompleteFormFront.partsUsedList.getValueAt(0, 1)).getIconHeight());
        }
        // update cost total
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement2 = connection.createStatement();) {
            workOrderText = woTextArea.getText();
            String getWorkCost = """
                    select upc.upc_cost, upc.upc_code
                    from service_link as sl
                    inner join upc_codes as upc
                    on sl.service_fee_id = upc.upc_id
                    where work_Order_ID ="""
                    + workOrderText;

            ResultSet searchQ = statement2.executeQuery(getWorkCost);
            ArrayList<Double> list = new ArrayList<>();
            while (searchQ.next()) {
                String costText = searchQ.getString("upc_cost");
                Double costDouble = Double.parseDouble(costText.replace("$", ""));

                list.add(costDouble);
            }
            double sum = 0;
            for (int i = 0; i <= list.size() - 1; i++) {
                sum += list.get(i);
            }
            DecimalFormat df = new DecimalFormat("#.##");
            String roundSum = df.format(sum);

            CompleteFormFront.totalText.setText("Total before tax: $" + roundSum);

        } catch (SQLException ex) {
            Logger.getLogger(SignInFront.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }// GEN-LAST:event_completeWorkOrderActionPerformed

    private void updateWorkOrderActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_updateWorkOrderActionPerformed
        updateWorkOrder();
    }

    // this function is depricated as you can simple hit enter after typing the work
    // order ID to search. keep for special users..?
    private void searchWorkOrderButtActionPerformed(java.awt.event.ActionEvent evt) {// GEN-FIRST:event_searchWorkOrderButtActionPerformed
        try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {

            String defaultWO = woTextArea.getText();
            String cleanWO = defaultWO.trim();

            String topWorkOrder = """
                    select work_order_id, cs.client_id, work_to_do, cs.pc_pass, cs.pc_pin, cs.other_equip, tech_name, desktop, laptop, tablet, charger, cs.client_id,
                    c.fname, c.lname, c.phone, c.phone2, c.email, work_done, c.companyName, cs.sign_in_date
                    from client_service as cs
                    inner join clients as c
                    on cs.client_id = c.client_id
                    where work_order_ID = """
                    + cleanWO;

            ResultSet searchQ = statement.executeQuery(topWorkOrder);

            while (searchQ.next()) {
                String woToDoText = searchQ.getString("work_to_do");
                String passText = searchQ.getString("pc_pass");
                String pinText = searchQ.getString("pc_pin");
                Boolean desktop = searchQ.getBoolean("desktop");
                Boolean laptop = searchQ.getBoolean("laptop");
                Boolean tablet = searchQ.getBoolean("tablet");
                Boolean charger = searchQ.getBoolean("charger");
                String firstName = searchQ.getString("fname");
                String lastName = searchQ.getString("lname");
                String phoneNumber = searchQ.getString("phone").replace("(", "").replace(")", "").replace(" ", "")
                        .replace("-", "");
                String cellNumber = searchQ.getString("phone2").replace("(", "").replace(")", "").replace(" ", "")
                        .replace("-", "");
                String emailText = searchQ.getString("email");
                String clientID = searchQ.getString("client_id");
                String workDone = searchQ.getString("work_done");
                String companyString = searchQ.getString("companyName");
                String otherEquip = searchQ.getString("other_equip");
                String techName = searchQ.getString("tech_name");
                String signInDate = searchQ.getString("sign_in_date");

                SignInFront.techComboBox.setSelectedItem(techName);
                SignInFront.clientIDText.setText(clientID);
                SignInFront.workToBeDone.setText(woToDoText);
                SignInFront.passwordText.setText(passText);
                SignInFront.pinText.setText(pinText);
                SignInFront.checkLaptop.setSelected(laptop);
                SignInFront.checkDesktop.setSelected(desktop);
                SignInFront.checkTablet.setSelected(tablet);
                SignInFront.checkCharger.setSelected(charger);
                SignInFront.fNameText.setText(firstName);
                SignInFront.lNameText.setText(lastName);
                SignInFront.phoneOneText.setText(phoneFormat(phoneNumber));
                SignInFront.cellPhoneText.setText(phoneFormat(cellNumber));
                SignInFront.eMailText.setText(emailText);
                SignInFront.clientIDText.setText("Client ID: " + clientID);
                SignInFront.workDoneText.setText(workDone);
                SignInFront.companyText.setText(companyString);
                SignInFront.equipmentText.setText(otherEquip);
                SignInFront.techComboBox.setSelectedItem(techName);
                SignInFront.createdDateText.setText(signInDate.substring(0, (signInDate.length() - 4)));
                setStatus();

            }

        } catch (SQLException e) {
        }
    }// GEN-LAST:event_searchWorkOrderButtActionPerformed

    private void statusComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {// GEN-FIRST:event_statusComboBoxItemStateChanged
        updateStatus();
        lastUpdated();
    }// GEN-LAST:event_statusComboBoxItemStateChanged

    Action action = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {

            try ( Connection connection = DriverManager.getConnection(connectionUrl);  Statement statement = connection.createStatement();) {
                String defaultWO = woTextArea.getText();
                String cleanWO = defaultWO.trim();
                String topWorkOrder = """
                        select work_order_id, cs.client_id, work_to_do, cs.pc_pass, cs.pc_pin, cs.other_equip, tech_name, desktop, laptop, tablet, charger, cs.client_id,
                        c.fname, c.lname, c.phone, c.phone2, c.email, work_done, c.companyName, cs.sign_in_date
                        from client_service as cs
                        inner join clients as c
                        on cs.client_id = c.client_id
                        where work_order_ID = """
                        + cleanWO;

                ResultSet searchQ = statement.executeQuery(topWorkOrder);

                while (searchQ.next()) {
                    String woToDoText = searchQ.getString("work_to_do");
                    String passText = searchQ.getString("pc_pass");
                    String pinText = searchQ.getString("pc_pin");
                    Boolean desktop = searchQ.getBoolean("desktop");
                    Boolean laptop = searchQ.getBoolean("laptop");
                    Boolean tablet = searchQ.getBoolean("tablet");
                    Boolean charger = searchQ.getBoolean("charger");
                    String firstName = searchQ.getString("fname");
                    String lastName = searchQ.getString("lname");
                    String phoneNumber = searchQ.getString("phone").replace("(", "").replace(")", "").replace(" ", "")
                            .replace("-", "");
                    String cellNumber = searchQ.getString("phone2").replace("(", "").replace(")", "").replace(" ", "")
                            .replace("-", "");
                    String emailText = searchQ.getString("email");
                    String otherEquip = searchQ.getString("other_equip");
                    String clientID = searchQ.getString("client_id");
                    String workDone = searchQ.getString("work_done");
                    String companyString = searchQ.getString("companyName");
                    String techName = searchQ.getString("tech_name");
                    String signInDate = searchQ.getString("sign_in_date");

                    SignInFront.clientIDText.setText(clientID);
                    SignInFront.workToBeDone.setText(woToDoText);
                    SignInFront.passwordText.setText(passText);
                    SignInFront.pinText.setText(pinText);
                    SignInFront.checkLaptop.setSelected(laptop);
                    SignInFront.checkDesktop.setSelected(desktop);
                    SignInFront.checkTablet.setSelected(tablet);
                    SignInFront.checkCharger.setSelected(charger);
                    SignInFront.fNameText.setText(firstName);
                    SignInFront.lNameText.setText(lastName);
                    SignInFront.phoneOneText.setText(phoneFormat(phoneNumber));
                    SignInFront.cellPhoneText.setText(phoneFormat(cellNumber));
                    SignInFront.eMailText.setText(emailText);
                    SignInFront.equipmentText.setText(otherEquip);
                    SignInFront.clientIDText.setText("Client ID: " + clientID);
                    SignInFront.workDoneText.setText(workDone);
                    SignInFront.companyText.setText(companyString);
                    globalClientID = Integer.parseInt(clientID);
                    SignInFront.techComboBox.setSelectedItem(techName);
                    SignInFront.createdDateText.setText(signInDate.substring(0, (signInDate.length() - 4)));
                    setStatus();
                    lastUpdated();
                }

            } catch (SQLException t) {
            }
        }
    };

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SignInFront().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField MinChargeText;
    private javax.swing.JLabel SigLabel;
    private javax.swing.JMenuItem addNewClientButt;
    private javax.swing.JPanel backgroundPanel;
    public static javax.swing.JTextField cellPhoneText;
    public static javax.swing.JCheckBox checkCharger;
    public static javax.swing.JCheckBox checkDesktop;
    public static javax.swing.JCheckBox checkLaptop;
    public static javax.swing.JCheckBox checkTablet;
    private javax.swing.JMenuItem clearWorkOrder;
    public static javax.swing.JLabel clientIDText;
    public static javax.swing.JTextField companyText;
    private javax.swing.JMenuItem completeWorkOrder;
    public static javax.swing.JTextField createdDateText;
    public static javax.swing.JTextField eMailText;
    private javax.swing.JScrollPane equipmentField;
    public static javax.swing.JTextArea equipmentText;
    public static javax.swing.JTextField fNameText;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextPane jTextPane1;
    public static javax.swing.JTextField lNameText;
    private javax.swing.JScrollPane legalPane;
    private javax.swing.JTextArea legalText;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField partBox;
    public static javax.swing.JTextField passwordText;
    public static javax.swing.JTextField phoneOneText;
    public static javax.swing.JTextField pinText;
    private javax.swing.JMenuItem printWorkOrder;
    private javax.swing.JLabel saveIcon;
    private javax.swing.JButton saveNewClientButt;
    private javax.swing.JMenuItem searchExistingClient;
    private javax.swing.JMenuItem searchWorkOrderButt;
    private javax.swing.JTextField signText;
    private javax.swing.JComboBox<String> statusComboBox;
    public static final javax.swing.JComboBox<String> techComboBox = new javax.swing.JComboBox<>();
    private javax.swing.JLabel titleText;
    private javax.swing.JMenuBar topMenu;
    private javax.swing.JMenuItem updateWorkOrder;
    private javax.swing.JMenuItem woHistory;
    public static javax.swing.JTextField woTextArea;
    public static javax.swing.JTextArea workDoneText;
    private javax.swing.JScrollPane workPerformedArea;
    public static javax.swing.JTextArea workToBeDone;
    private javax.swing.JScrollPane workToDoField;
    // End of variables declaration//GEN-END:variables
}
