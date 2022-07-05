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

        SignInFront front = new SignInFront();        
        front.setVisible(true);

        
        WorkOrderHistoryFrame gui = new WorkOrderHistoryFrame();              
        gui.setLocation(front.getX() + front.getWidth(), front.getY());
        gui.setVisible(true);  

    }

}
