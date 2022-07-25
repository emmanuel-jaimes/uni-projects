
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Client class that communicates commands to manipulate LinkedList that Server class controls
 */
public class Client extends JFrame
{
    /**
     * initialize socket to open communication between Server/Client
     */
    private DatagramSocket socket;
    /**
     * initialize components to prompt Client user to send commands to Server
     */
    private final JLabel displayLabel, insertLabel, removeLabel, exitLabel;
    /**
     * initialize component to contain components that create Client graphical user interface
     */
    private final JPanel clientPanel;
    /**
     *  initialize component to enter commands and communication to Server from Client
     */
    private final JTextField enterField;
    /**
     * initialize component to display communication between Server/Client
     */
    private final JTextArea displayArea;

    /**
     * Client constructor that initializes components and enables communication between Server and Client
     */
    public Client()
    {
        super("Client");
        clientPanel = new JPanel();
        clientPanel.setLayout(new BoxLayout(clientPanel, BoxLayout.Y_AXIS));
        displayLabel = new JLabel("[1]. [Display] Linked List ");
        insertLabel = new JLabel("[2]. [Insert] Element to Linked List");
        removeLabel = new JLabel("[3]. [Remove] from Linked List");
        exitLabel = new JLabel("[4]. [Exit] Server");
        clientPanel.add(displayLabel);
        clientPanel.add(insertLabel);
        clientPanel.add(removeLabel);
        clientPanel.add(exitLabel);
        enterField = new JTextField("Enter Here...");

        enterField.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent event) {
                        try // create and send packet
                        {
                            //get message from text field
                            String message = event.getActionCommand();
                            displayArea.append("Sending packet containing: " +
                                    message + "\n");

                            byte[] data = message.getBytes();

                            //create sendPacket
                            DatagramPacket sendPacket = new DatagramPacket(data,
                                        data.length, InetAddress.getLocalHost(), 23596);

                            socket.send(sendPacket);
                            displayArea.append("Packet sent \n");
                            displayArea.setCaretPosition(
                                    displayArea.getText().length());

                            enterField.setText("");
                        }
                        catch (IOException ioException)
                        {
                            displayMessage(ioException + "\n");
                            ioException.printStackTrace();
                        }
                    }
                }
        );

        add(clientPanel, BorderLayout.NORTH);
        add(enterField, BorderLayout.AFTER_LAST_LINE);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(400, 300);
        setVisible(true);

        try
        {
            socket = new DatagramSocket();
        }
        catch (SocketException socketException)
        {
            socketException.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * method awaiting communication and commands from Server class
     */
    public void waitForPackets()
    {
        while(true)
        {
            try //receive packet and display contents
            {
                byte[] data = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(
                        data, data.length);

                socket.receive(receivePacket);

                displayMessage("\nEnter [command] followed by [Object] and desired [index location]\n Each seperated by a space");


                displayMessage("\nPacket received:" +
                        "\nFrom host: " + receivePacket.getAddress() +
                        "\nLength: " + receivePacket.getLength() +
                        "\nContaining:\n\t" + new String(receivePacket.getData(),
                        0, receivePacket.getLength()));

                String received = new String(receivePacket.getData(),
                        0, receivePacket.getLength());

                if (received.equals("Server Closed and Exited. . ."))
                {
                    socket.close();
                }
            }
            catch (IOException exception)
            {
                displayMessage(exception + "\n");
                exception.printStackTrace();
            }
        }
    }

    /**
     * @param messageToDisplay reference of string to represent in Client display window
     */
    private void displayMessage(final String messageToDisplay)
    {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        displayArea.append(messageToDisplay);
                    }
                }
        );
    }
}
