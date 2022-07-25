// Class that tests the Client.
import javax.swing.JFrame;

/**
 * ClientTest class containing driver method for Client class
 */
public class ClientTest
{
    /**
     * driver method to setup and run the Client class
     * @param args
     */
    public static void main(String[] args)
    {
        Client application = new Client(); // create client
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.waitForPackets(); // run client application
    }
}  // end class ClientTest

//21.26