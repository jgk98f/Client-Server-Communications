import java.io.PrintWriter;
import java.net.*;
import java.util.Scanner;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class ChatThread implements Runnable {

    private SSLSocket socket;
    private SSLServerSocket s_socket;
    private boolean isClosing = false;
    
    public boolean getisClosing()
    {
    	return isClosing;
    }
    
    public void setisClosing(boolean closing)
    {
    	isClosing = closing;
    }

    public ChatThread(SSLServerSocket ss)
    {
        s_socket = ss;
    }

    @Override
    public void run() 
    {
        try {
            socket = (SSLSocket) s_socket.accept();
            System.out.println("\n listening on port "+socket.getLocalPort()+
                               "\n sending on port "+socket.getPort()+"\n\n");
            Scanner in = new Scanner(socket.getInputStream()); //From Client
            PrintWriter out = new PrintWriter(socket.getOutputStream()); //To Client
            while (true) {//While ServerFrame is running
            	if(getisClosing())
            		(new Thread(new ChatThread(s_socket))).start();
                if (in.hasNext()) {
                	if(in.hasNextBoolean() == true)
                	{
                		setisClosing(true);
                	}
                    String input = in.nextLine(); 
                    System.out.println("Client Said: " + input); 
                    out.println("The Server Heard you Say: " + input); 
                    out.flush();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
