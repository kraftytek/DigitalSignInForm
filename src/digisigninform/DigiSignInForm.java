/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package digisigninform;

import javax.swing.JFrame;

/**
 *
 * @author kraft
 */
public class DigiSignInForm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Open main form
        SignInFront front = new SignInFront();        
        front.setVisible(true);
        //Open the work order history at the same time and put it next to the form
        WorkOrderHistoryFrame gui = new WorkOrderHistoryFrame();              
        gui.setLocation(front.getX() + front.getWidth(), front.getY());
        gui.setVisible(true);  

    }

}
