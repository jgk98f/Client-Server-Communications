import java.awt.*;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.awt.font.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.io.*;
import java.net.*;


public class ClientFrame {
    static SSLSocket this_socket=null;
    
    static private boolean isClosing = false;
    
    static public boolean getisClosing()
    {
    	return isClosing;
    }
    
    static public void setisClosing(boolean closing)
    {
    	isClosing = closing;
    }
    
    public static void main(String args[]) {
        try {
          
            final JTextField theMessage=new JTextField();
            Font the_font = new Font("SansSerif", Font.BOLD, 20);
            theMessage.setFont(the_font);
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            final int HTTPport=5001;
            InetAddress ip = InetAddress.getByName("localhost");
            try {
            	SSLSocketFactory sslsocketfactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            	this_socket = (SSLSocket) sslsocketfactory.createSocket(ip, HTTPport);
//                this_socket = new Socket(ip, HTTPport);

            } catch (IOException e1) {
                System.out.println("Port 5001 not Available");
                e1.printStackTrace();
                return;
            }
            final  PrintWriter    out =new PrintWriter(this_socket.getOutputStream(), true);
            final BufferedReader  in = new BufferedReader(new InputStreamReader(this_socket.getInputStream()));
            System.out.println("The Inet address is "+ip+
                               "\n listening on port "+this_socket.getLocalPort()+
                               "\n sending on port "+this_socket.getPort()+"\n\n");
            JFrame frame = new JFrame("Chat Frame");
            JButton button = new JButton("Ask Chat Server");
            button.addActionListener((ev)->{
                                         out.println(theMessage.getText());
                                         String the_response="";
                                         try {
                                             while ((the_response=in.readLine())==null) {
                                                 Thread.sleep(500);
                                             }
                                             theMessage.setText(the_response);
                                             theMessage.repaint();
                                         } catch (Exception e) {
                                             e.printStackTrace();
                                         }
                                     });

            Container contentPane = frame.getContentPane();
            contentPane.add(theMessage, BorderLayout.CENTER);
            contentPane.add(button, BorderLayout.SOUTH);
            frame.setSize(500, 200);
            frame.setVisible(true);
            //frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); ;
            frame.addWindowListener(new WindowAdapter() {
                                  public void windowClosing(WindowEvent e) {
                                      try{
                                      setisClosing(true);
                                      out.println(getisClosing());
                                      this_socket.close();
                                      System.out.println("Client Closing");
                                      System.exit(0);
                                      }catch (IOException ioe){ 
                                          System.out.println("Socket did not close properly.");
                                      }
 
                                  }
                              });          
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
 

