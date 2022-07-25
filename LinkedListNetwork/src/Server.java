
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.awt.BorderLayout;
import java.util.Arrays;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

/**
 *  Server class that manipulates a Linked List based on commands communicated from Client Server
 */
public class Server extends JFrame
{
    /**
     *  initialize text area to display communication/interaction between client and server
     */
    private final JTextArea displayArea; // displays packets received
    /**
     *  initialize Socket to allow communication between server and client
     */
    private DatagramSocket socket; // socket to connect to client
    /**
     * initialize linked list for interaction with client server
     */
    private final List list = new List();

    /**
     *  constructor for Server initializes components to display connection for the server and communication to the client
     */
    //set up GUI and DatagramSocket
    public Server()
    {

        super("Server");

        displayArea = new JTextArea(); // create displayArea
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(400, 300); // set size of window
        setVisible(true); // show window
        displayArea.setEditable(false);

        try // create DatagramSocket for sending and receiving packets
        {
            socket = new DatagramSocket(23596);
        }
        catch (SocketException socketException)
        {
            socketException.printStackTrace();
            System.exit(1);
        }
    }

    /**
     *  method to allow server to await connection from client and process commands inputted from client
     */
    // wait for packets to arrive, display data and echo packet to client
    public void waitForPackets()
    {
        while (true)
        {
            try // receive packet, display contents, return copy to client
            {
                String process ;
                byte[] data = new byte[100]; // set up packet
                DatagramPacket receivePacket = new DatagramPacket(data, data.length);

                //get input from received packet
                String received;
                String[] receiveInformation;

                int defaultIndex = -1;

                //loopPrompt
                do
                {
                    //have prompt loop for client
                    displayMessage("\nWaiting to Receive: \n");
                    socket.receive(receivePacket);
                    received = new String(receivePacket.getData(), 0 , receivePacket.getLength());
                    displayMessage("\n message received: " + received);
                    receiveInformation = received.split(" ");
                    displayMessage("\n received information: " + Arrays.toString(receiveInformation));
                    process = receiveInformation[0];
                    displayMessage("\n process: " + process);

                    if (process.equals("1") || process.equals("display"))
                    {
                        //display list
                        //create packets to send to client
                        displayList(receivePacket);
                    }
                    else if (process.equals("2") || process.equals("insert"))

                    {
                        //insert to linked list
                        //create packet to send to client window
                        if (receiveInformation.length > 2)
                            addToList(receiveInformation[1], Integer.parseInt(receiveInformation[2]));
                        else
                            addToList(receiveInformation[1], defaultIndex);

                        displayList(receivePacket);
                    }
                    else if (process.equals("3") || process.equals("remove"))
                    {
                        //remove from linked list
                        //create packet to send to client window
                        if (list.isEmpty())
                        {
                            byte[] err;
                            String unknownPrompt = "List is empty";
                            err = unknownPrompt.getBytes();
                            DatagramPacket errPacket = new DatagramPacket(err, err.length);
                            //send to client
                            sendPacketToClient(errPacket);
                        }
                        else
                        {
                            if (receiveInformation.length > 2) // for specified index location
                                removeFromList(Integer.parseInt(receiveInformation[1]));
                            else //unspecified index location -> remove from back
                                removeFromList(list.getSize() - 1);

                            displayList(receivePacket);
                        }
                    }
                    else //unknown commands and prompts
                    {
                        //error prompt
                        byte[] err;
                        String unknownPrompt = "Unknown Command: Please try again.";
                        err = unknownPrompt.getBytes();
                        DatagramPacket errPacket = new DatagramPacket(err, err.length);
                        //send to client
                        sendPacketToClient(errPacket);
                        displayMessage("\n in unknown case");
                    }
                }
                while ( !process.equals("exit") );


                    displayMessage("\n exited loop");
                    String closeSocket = "Server Closed and Exited. . .";
                    byte[] closeData;
                    closeData = closeSocket.getBytes();

                    DatagramPacket closeServer = new DatagramPacket(closeData, closeData.length);
                    displayMessage("\nServer Closed and Exited. . .");

                    sendPacketToClient(closeServer);
                    socket.close();
            }
            catch (IOException ioException)
            {
                displayMessage(ioException + "\n");
                ioException.printStackTrace();
            }
        }
    }

    /**
     * @param receivePacket DatagramPacket reference of packet to be communicated with Client
     * @throws IOException throws exception for input output exceptions
     */
    // echo packet to client
    private void sendPacketToClient(DatagramPacket receivePacket)
            throws IOException
    {
        displayMessage("\n\nEcho data to client...");

        // create packet to send
        DatagramPacket sendPacket = new DatagramPacket(
                receivePacket.getData(), receivePacket.getLength(),
                receivePacket.getAddress(), receivePacket.getPort());

        socket.send(sendPacket); // send packet to client
        displayMessage("Packet sent\n");
    }

    /**
     * @param messageToDisplay String object reference to be shown on display frame
     */
    // manipulates displayArea in the event-dispatch thread
    private void displayMessage(final String messageToDisplay)
    {
        SwingUtilities.invokeLater(
                new Runnable()
                {
                    public void run() // updates displayArea
                    {
                        displayArea.append(messageToDisplay); // display message
                    }
                }
        );
    }

    /**
     * @param obj Object item to be inserted into the EnhancedLinkList
     * @param index int reference location in EnhancedLinkedList for input Object to be inserted
     * @throws IOException throws exception for input output exceptions
     */
    private void addToList(Object obj, int index) throws IOException
    {
        //initialize DatagramPacket for communication with client
        if (list.isEmpty())
            list.insertAtFront(obj);
        else if (index == 0)
            list.insertAtFront(obj);
        else if (index == list.getSize()-1)
            list.insertAtBack(obj);
        else if (index == -1)
            list.insertAtBack(obj);
        else
            list.insertAt(obj, index);

        //displayed to server window
        displayMessage("\n Completed list insertion" +
                            "\n Object Inserted: " + obj +
                            "\n Index Inserted: " + index);
    }

    /**
     * @param index int representation of index for item at index to be removed
     * @throws IOException throws exception for input output exceptions
     */
    private void removeFromList(int index) throws IOException
    {
        Object removed;
        if (index == 0)
            removed = list.removeFromFront();
        else if (index >= list.getSize()-1)
            removed = list.removeFromBack();
        else
            removed = list.removeAt(index);

        //display message to server window
        displayMessage("\n Complete list removal"
                            + "\n Object Removed: " + removed);
    }

    /**
     * @param receivePacket DatagramPacket that will communicate the updated state of the list to the Client
     * @throws IOException throws exception for input output exceptions
     */
    private void displayList(DatagramPacket receivePacket) throws IOException
    {
        String listdata = "Updated List: " + list.toString();
        receivePacket.setData(listdata.getBytes(), 0, listdata.length());
        sendPacketToClient(receivePacket);
    }
}