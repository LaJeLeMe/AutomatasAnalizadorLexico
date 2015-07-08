package gui;

import com.sun.awt.AWTUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;
import javax.swing.JDialog;

/**
 * Hecho con <3 Por:
 * @author GarciHard
 */

public class DamaIDESplashScreen extends JDialog {
    
    private JFrame frameSplashScreen;
    private JLabel lblImagen;
    private ActionListener controlarTimer;
    private JProgressBar progreso;
    private Timer tiempo;
    private DamaIDE damaIDE;
    
    public DamaIDESplashScreen() {
        controlarTimer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {

                if (progreso.getValue() < 100) { // <100 se incrementa
                    progreso.setValue(progreso.getValue() + 10);
                    damaIDE = new DamaIDE();
                } else { // al alcanzar el 100 se detiene el timer
                    tiempo.stop();
                    damaIDE.setVisible(true);
                    frameSplashScreen.dispose();
                }
            }
        };
        tiempo = new Timer(350, controlarTimer);
        initFrame();
    }
    
    private void initFrame() {
        frameSplashScreen = new JFrame();
        frameSplashScreen.setUndecorated(true);
        frameSplashScreen.setLayout(null);
        frameSplashScreen.setSize(600, 600);
        frameSplashScreen.setLocationRelativeTo(null);
        AWTUtilities.setWindowOpaque(frameSplashScreen, false);
        
        lblImagen = new JLabel();
        lblImagen.setIcon(new ImageIcon(this.getClass().getResource("/images/DamaIDE.png")));
        lblImagen.setSize(400,400);
        lblImagen.setLocation(100,100);
        
        progreso = new JProgressBar();
        progreso.setSize(400, 5);
        progreso.setLocation(100, 480);
        
        frameSplashScreen.add(progreso);
        frameSplashScreen.add(lblImagen);
        
        tiempo.start();
        frameSplashScreen.setVisible(true);
    }
}