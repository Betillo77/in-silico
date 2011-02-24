/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package blackjack;

import javax.swing.JFrame;

/**
 *
 * @author seb
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        /*java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                JFrame f = new JFrame();
                f.add(new frmBJ());
                f.pack();
                f.setVisible(true);
                //new frmBJ().setVisible(true);
            }
        });*/
        JFrame f = new JFrame();
        f.add(new frmBJ());
        f.pack();
        f.setVisible(true);
    }

}
