import java.awt.*;
import java.awt.font.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.*;
import java.net.*;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;


public class ServerFrame {

    public static void main(String args[]) {
        try {
        	
            final JTextField thePort=new JTextField(); 
            Font the_font = new Font("SansSerif", Font.BOLD, 20);
            thePort.setFont(the_font);
            JFrame frame = new JFrame("Chat Server Frame");
            JButton button = new JButton("Request Port");
            button.addActionListener((ev)->{
                                         int portnumber=Integer.parseInt(thePort.getText().trim());
                                         try {
                                        	 SSLServerSocketFactory sslserversocketfactory =
                                                     (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
                                             SSLServerSocket sslserversocket =
                                                     (SSLServerSocket) sslserversocketfactory.createServerSocket(portnumber);
//                                             SSLServerSocket server = new ServerSocket(portnumber); 
                                             
                                             ChatThread chat = new ChatThread(sslserversocket); //one per port
                                             Thread t = new Thread(chat); 
                                             t.start(); 
                                             thePort.setText("Port "+thePort.getText().trim()+ " is open and listening.");
                                             thePort.repaint();
                                         } catch (Exception e) {
                                             thePort.setText("Port "+thePort.getText().trim()+ " is not available.");
                                             thePort.repaint();
                                         }
                                     });
            Container contentPane = frame.getContentPane();
            contentPane.add(thePort, BorderLayout.CENTER);
            contentPane.add(button, BorderLayout.SOUTH);
            frame.setSize(500, 200);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ;
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
