/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package digisigninform;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;

/**
 *
 * @author kraft
 */
public class PrintForm {

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
            g2.scale(xRatio, yRatio);
            comp.printAll(g2);

            return Printable.PAGE_EXISTS;
        }
    }
}
