/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digisigninform;

/**
 *
 * @author kraft
 */
import java.awt.*;
import java.awt.dnd.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileSystemView;
import java.io.*;

public class FileDropList extends JPanel implements DropTargetListener {

    private DefaultListModel listModel = new DefaultListModel();
    private DropTarget dropTarget;
    private JScrollPane jScrollPane1;
    private JList list;

    /**
     * Create the panel.
     */
    public FileDropList() {
        //setLayout(null);        
        list = new JList();
        dropTarget = new DropTarget(list, this);
        list.setModel(listModel);
        list.setDragEnabled(true);
        FileListCellRenderer renderer = new FileListCellRenderer();
        list.setCellRenderer(renderer);
        //list.setTransferHandler(new FileTransferHandler());
        jScrollPane1 = new JScrollPane(list);

        //jScrollPane1.setBounds(10, 150, 635, 330);
        add(jScrollPane1);

    }

    public static void main(String[] args) throws Exception {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                JPanel pan = new FileDropList();
                pan.setBorder(new LineBorder(Color.BLACK));
                JOptionPane.showMessageDialog(null, pan);
            }
        };
        SwingUtilities.invokeLater(r);
    }

    @Override
    public void dragEnter(DropTargetDragEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void dragOver(DropTargetDragEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void dropActionChanged(DropTargetDragEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void dragExit(DropTargetEvent dte) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void drop(DropTargetDropEvent dtde) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}

/**
 * A FileListCellRenderer for a File.
 */
class FileListCellRenderer extends DefaultListCellRenderer {

    private static final long serialVersionUID = -7799441088157759804L;
    private FileSystemView fileSystemView;
    private JLabel label;
    private Color textSelectionColor = Color.BLACK;
    private Color backgroundSelectionColor = Color.CYAN;
    private Color textNonSelectionColor = Color.BLACK;
    private Color backgroundNonSelectionColor = Color.WHITE;

    FileListCellRenderer() {
        label = new JLabel();
        label.setOpaque(true);
        fileSystemView = FileSystemView.getFileSystemView();
    }

    @Override
    public Component getListCellRendererComponent(
            JList list,
            Object value,
            int index,
            boolean selected,
            boolean expanded) {

        File file = (File) value;
        label.setIcon(fileSystemView.getSystemIcon(file));
        label.setText(fileSystemView.getSystemDisplayName(file));
        label.setToolTipText(file.getPath());

        if (selected) {
            label.setBackground(backgroundSelectionColor);
            label.setForeground(textSelectionColor);
        } else {
            label.setBackground(backgroundNonSelectionColor);
            label.setForeground(textNonSelectionColor);
        }

        return label;

    }
}
