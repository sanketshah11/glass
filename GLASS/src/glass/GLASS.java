/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package glass;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class GLASS
{

/**
 * @param args the command line arguments
 */
public static void main(String[] args)
{
    //get java jre version
    String version = System.getProperty("java.version");
    String versionString[] = version.split("\\.");
    int versionNumber = Integer.parseInt(versionString[1]);
    System.out.println("versionNumber: " + versionNumber);
    
    //if version less than 1.7 , then project wont load, minimum requirement is 1.7
    if (versionNumber < 7)
    {
        JOptionPane.showMessageDialog(null, "Please Update Java \n Detected java Version: " + version + "\n Recommended Version for best experience: 1.7 \n \n please update to 1.7 or latest", "Java JRE Update Required!", JOptionPane.ERROR_MESSAGE);
    }

    //if java version above 1.7, load the seaglass look and feel library.
    if (versionNumber <= 7)
    {
        try
        {
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            JOptionPane.showMessageDialog(null, "SeaGlass Look And Feel Loading failed: ", "Java JRE Update Required!", JOptionPane.ERROR_MESSAGE);
        }
    }

    //if java version less than 1.7 try to load the default java look and feel , this may or may not work , based on users's local environment
    else
    {
        try
        {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
            UIManager.setLookAndFeel("com.alee.laf.WebLookAndFeel");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex)
        {
            JOptionPane.showMessageDialog(null, "WebLookAndFeel Loading failed: ", "Java JRE Update Required!", JOptionPane.ERROR_MESSAGE);
        }
    }

    GUI gui = new GUI();

    //set width and height of the window frame for GUI based on the detected screen resolution 
    GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
    int width = gd.getDisplayMode().getWidth();
    int height = gd.getDisplayMode().getHeight();
    gui.setPreferredSize(new Dimension(width, height));
    gui.setBounds(0, 0, width, height);
    gui.setVisible(true);
    // gui.resizeComponents();
}

}
