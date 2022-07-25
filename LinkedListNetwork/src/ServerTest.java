// Class that tests the Server.
import javax.swing.JFrame;

/**
 * ServerTest class containing driver method for Server class
 */
public class ServerTest
{

    /**
     * driver method to setup and run the server class
     * @param args
     */
    public static void main(String[] args)
    {
        Server application = new Server(); // create server
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.waitForPackets(); // run server application
    }
}