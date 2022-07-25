import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.time.LocalTime;
import java.util.*;

/**
 * This class is run when the server is started.
 */
public class Server extends JFrame{
    /**Text area to display packets received*/
    private final JTextArea displayArea;
    /**Socket to connect to client*/
    private DatagramSocket socket;

    /** writes date to userHistory.txt file */
    Formatter userHistoryWriter;
    /** writes data to tournamentData.txt file */
    Formatter tournamentDataWriter;
    /** users high score */
    int playerHighScore = 0;
    /** reference to players previous scores */
    String playerScores = ",";
    /** reference to users current streak */
    int playerStreak = 0;
    /** determines whether logged on user has played before */
    boolean hasPlayed = false;
    /** integer to reference how many games a player has played */
    int playerGames = 0;
    /**Users unique IP address*/
    String IPIdentifier = "";
    /** object array that holds info to write to files */
    Object[] writeableTournamentData = new Object[2];
    /**Accessor for words class to obtain words of the day*/
    Words wordOfTheDay = new Words();
    /**List of users that have began the tournament*/
    HashMap<InetAddress, Integer> usersLoggedON = new HashMap<>();
    /**List of users that have completed the tournament*/
    HashMap<InetAddress, Boolean> playedToCompletion = new HashMap<>();
    /**List of users and the question that they have completed*/
    HashMap<InetAddress, Boolean[]> questionON = new HashMap<>();
    /**Scores of the users updated after every round*/
    HashMap<InetAddress, int[]> userScores = new HashMap<>();
    /**The five "words of the day" for the tournament*/
    HashMap<Integer, String> words = new HashMap<>();
    /**Score of the round, subtracted for each guess used*/
    int scoreForRound = 0;
    /**Number of guesses in the current round*/
    int guessNumber = 0;
    /**Determines if the user is on the same round as the previous guess or not*/
    int previousRound = 0;
    /**Number of the round*/
    int round = 0;

    /** Constructor to set up the Server for the Wordle and DatagramSocket */
        public Server()
        {
            super("Server"); //calls superclass constructor (JFrame)

            displayArea = new JTextArea(); // create displayArea
            add(new JScrollPane(displayArea), BorderLayout.CENTER);
            setSize(400, 300); // set size of window
            setVisible(true); // show window
            setUpFileWriters(); //setup the output files

            try // create DatagramSocket for sending and receiving packets
            {
                socket = new DatagramSocket(23747);
            }
            catch (SocketException socketException)
            {
                socketException.printStackTrace();
                System.exit(1);
                closeUserHistoryFile();
                closeTournamentDataFile();
            }

            //add words of the day to their hash map
            words.put(1, wordOfTheDay.getRandomWord());
            words.put(2, wordOfTheDay.getRandomWord());
            words.put(3, wordOfTheDay.getRandomWord());
            words.put(4, wordOfTheDay.getRandomWord());
            words.put(5, wordOfTheDay.getRandomWord());

            displayMessage("Words:" + "\n" +
                    words.get(1) + "\n" +
                    words.get(2) + "\n" +
                    words.get(3) + "\n" +
                    words.get(4) + "\n" +
                    words.get(5) + "\n");
        }


    /**
     * Determines the status of the user using various hashmap information.
     * @param receive DatagramPacket from Client (WordleController)
     * @return String: status of user (new, started (in progress), or completed)
     */
    public String userStatus(DatagramPacket receive){
        String userStatus = "";
        //InetAddress address = receive.getAddress();
        if(playedToCompletion.get(receive.getAddress()) == null){
            if(usersLoggedON.get(receive.getAddress()) != null){
                displayMessage("started ");
                //determine question the user is on
                Boolean[] questionStats = questionON.get(receive.getAddress());
                String print = "";
                for( boolean s: questionStats){
                    if(s){
                        print = "true ";
                    }
                    else{
                        print = "false";
                    }
                    displayMessage(print);
                }
            }
            else{
                displayMessage("new ");
                userStatus = "new";
                //store users info that have logged on
                storePortAndAddress(receive.getAddress(), receive.getPort());
                //add user to the completion map
//                setCompletion(receive.getAddress());
                //add user to question map
                Boolean [] array = {false, false, false, false, false};
                questionON.put(receive.getAddress(), array);
                //adding the user scores to the hashmap of scores
                int[] newScoreArray = {0, 0, 0, 0, 0};
                userScores.put(receive.getAddress(), newScoreArray);
            }
        }
        else{
            userStatus = "complete";
            displayMessage("complete ");
        }
        return userStatus;
    }

    /**
     * Reset the words when the local time is at midnight
     */
    public void resetDay() {
        LocalTime localTime = LocalTime.now();
        int hour = localTime.getHour();
        int minute = localTime.getMinute();
        int second = localTime.getSecond();
        if (hour == 0 && minute == 0 && second == 0) {
            words.put(1, wordOfTheDay.getRandomWord());
            words.put(2, wordOfTheDay.getRandomWord());
            words.put(3, wordOfTheDay.getRandomWord());
            words.put(4, wordOfTheDay.getRandomWord());
            words.put(5, wordOfTheDay.getRandomWord());
            round = 0;
        }
    }

    /**
     * This method waits for packets to arrive, display data and echo packet to client
     */
    public void waitForPackets()
        {
            while (true)
            {
                try // receive packet, display contents, return copy to client
                {
                    resetDay();
                    byte[] data = new byte[100]; // set up packet
                    DatagramPacket receivePacket =
                            new DatagramPacket(data, data.length);

                    socket.receive(receivePacket); // wait to receive packet

                    // display information from received packet
                    displayMessage("\nPacket received:" +
                            "\nFrom host: " + receivePacket.getAddress() +
                            "\nHost port: " + receivePacket.getPort() +
                            "\nLength: " + receivePacket.getLength() +
                            "\nContaining:\n\t" + new String(receivePacket.getData(),
                            0, receivePacket.getLength()));

                    //if user has not finished the game (started or new)
                    String dataToSend = "";
                    String dataReceived = new String(receivePacket.getData(), 0, receivePacket.getLength());
                    if(dataReceived.equals("leaderboard")){
                        dataToSend = getTournamentData();
                    }
                    else if(dataReceived.equals("statistics")){
                        dataToSend = userHistory();
                    }
                    else if(playedToCompletion.get(receivePacket.getAddress()) == null){
                        String userStatus = userStatus(receivePacket);
                        //determine round
                        Boolean[] fromQON = questionON.get(receivePacket.getAddress());
                        for(int i = 4; i >= 0; i--){
                            if(!fromQON[i]){
                                round = i+1;
                            }
                        }
                        //flag if it's a new round or not
                        if(round != previousRound){
                            guessNumber = 0;
                            previousRound = round;
                            scoreForRound = 0;
                        }
                        //receive guess from client
                        guessNumber++; //add to the number of guesses for the round
                        String guessFromClient = new String(receivePacket.getData(), 0 , receivePacket.getLength());
                        //handles guess and store guess data into string to send back
                        displayMessage(words.get(round) + " ROUND " + round);
                        dataToSend = guessHandler(guessFromClient, words.get(round));
                        if(dataToSend.equals("ttttt")){
                            fromQON[round-1] = true;
                            questionON.put(receivePacket.getAddress(), fromQON); //overwrite previous hash value
                            playerStreak++;
                            if(round == 5){
                                scoreForRound = 10 * (7 - guessNumber);
                                int[] arrayOfScores = userScores.get(receivePacket.getAddress());
                                arrayOfScores[round -1] = scoreForRound;
                                userScores.put(receivePacket.getAddress(), arrayOfScores);
                                int finalScore = 0;
                                int [] finalScores = userScores.get(receivePacket.getAddress());
                                for(int s = 0; s < 5; s++){
                                    finalScore += finalScores[s];
                                }
                                dataToSend = "e" + guessNumber + finalScore;
                                playedToCompletion.put(receivePacket.getAddress(), true);
                                //todo end of game is here
                                playerGames++;
                                String finalScoreString = Integer.toString(finalScore);
                                String previousScores = playerScores;
                                String intermediate = previousScores.concat(finalScoreString);
                                playerScores = intermediate.concat(",");
                                //playerScores += finalScore + ",";//append score to history of scores
//                                String playersScores = playerScores.toString();
                                writeableTournamentData[1] = finalScore; //to store final score in tournament data
                                updateHighScore(finalScore);
                                Object[] userHistoryUpdate = {receivePacket.getAddress(), playerGames, playerStreak, playerHighScore, playerScores};
                                String toWrite = convertToWriteable(writeableTournamentData); //toString
                                writeToTournamentData(toWrite); //write to file
                                writeToUserHistory(userHistoryUpdate);
                            }
                            else{
                                scoreForRound = 10 * (7 - guessNumber); //2 pts for correct letters, scaled by number of guesses left + 1
                                int[] arrayOfScores = userScores.get(receivePacket.getAddress());
                                arrayOfScores[round -1] = scoreForRound;
                                userScores.put(receivePacket.getAddress(), arrayOfScores);
                                dataToSend = guessNumber + String.valueOf(scoreForRound);
                            }
                        }
                        else if(guessNumber == 6){
                            playerStreak = 0;
                            char [] answers = dataToSend.toCharArray(); //put string to character array
                            int score = 0;
                            for(char c:answers){
                                if(c == 't'){
                                    score += 2;
                                }
                                else if(c == 'i'){
                                    score += 1;
                                }
                            }
                            scoreForRound = score * (7 - guessNumber);
                            int[] arrayOfScores = userScores.get(receivePacket.getAddress());
                            arrayOfScores[round -1] = scoreForRound;
                            userScores.put(receivePacket.getAddress(), arrayOfScores);
                            dataToSend = guessNumber + String.valueOf(scoreForRound);
                        }
                    }
                    else{
                        //send flag back to client that they've already played
                        dataToSend = "ddddd";
                    }

                    displayMessage("\n" + dataToSend);
                    //convert into send packet
                    data = dataToSend.getBytes();
                    receivePacket.setData(data);
                    receivePacket.setLength(data.length);
                    sendPacketToClient(receivePacket);
                }
                catch (IOException ioException)
                {
                    displayMessage(ioException + "\n");
                    ioException.printStackTrace();
                }
            }
        }

        /**
         * Creates new packet and echoes the packet to the client.
         * @param receivePacket Datagram packet to echo
         * @throws IOException Thrown upon IO error
         */
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
            String output = new String(sendPacket.getData(), 0, sendPacket.getLength());
            displayMessage(output);
        }

        /**
         * This method manipulates displayArea in the event-dispatch thread
         * @param messageToDisplay String to display on server GUI
         */
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
     * Adds users into the usersLoggedON hashmap (keeps track of users that have started the game).
     * @param address reference to InetAddress of users that have logged on
     * @param port reference to port user is connecting to from
     */
    private void storePortAndAddress(InetAddress address, int port)
    {
        usersLoggedON.put(address, port);
        writeableTournamentData[0] = address; //store address in file
        getUserHistory();
    }

    /**
     * Handles all guess operations. Determines if letters are correct, incorrect, or in word.
     * @param guess string reference to the guess that the client provides
     * @param word word "of the day" for the round
     * @return String containing information based on its guess to send back to client
     */
    public String guessHandler(String guess, String word)
    {
            //convert Word of the day to vector
            char[] wodChars = word.toCharArray();
            Vector<Character> wodVector = new Vector<>();
            displayMessage("\n");
            for (int i = 0; i < wodChars.length; i++)
            {
                wodVector.add(wodChars[i]);
                displayMessage(Character.toString(wodChars[i]));
            }
            //array to contain information based on guess
            String[] guessedWordData = new String[5];
            //iterate through each letter in the guess
            for (int i = 0; i < guess.length(); i++)
            {
                //if the word of the day contains that letter
                if (wodVector.contains(guess.charAt(i)))
                {
                    //determine where it is located
                    if ((word.charAt(i)) == guess.charAt(i))
                    {
                        //guessed in correct spot
                        guessedWordData[i] = "t";
                    }
                    else
                    {
                        //letter is in word but not correct spot
                        guessedWordData[i] = "i";
                    }
                }
                else //letter is not in the word
                {
                    guessedWordData[i] = "f";
                }
            }
            //guessedWordData now populated with hints for the guess
            String updatedString = "";
            for (int i = 0; i < guessedWordData.length; i++)
            {
                updatedString += guessedWordData[i];
            }
            return updatedString;
    }

    /**
     * Initialize the output files using Formatter
     */
    public void setUpFileWriters()
    {
        try {
            userHistoryWriter = new Formatter("userHistory.txt");
            tournamentDataWriter = new Formatter("tournamentData.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the output files for storing user history
     */
    public void closeUserHistoryFile()
    {
        userHistoryWriter.close();

    }

    /**
     * Close the output file for storing tournament data
     */
    public void closeTournamentDataFile(){
        tournamentDataWriter.close();
    }

    /**
     * Obtaining the history for the user based of the IP address identifier
     */
    public void getUserHistory()
    {
        Scanner info = new Scanner("userHistory.txt");
        String[] scanned = info.nextLine().split(" ");
        //iterates through the file
        while (info.hasNextLine())
        {
            //if address is equal to current users address
            //means that the player has played before
            if (scanned[0] != writeableTournamentData[0]) {
                //get amount of games user has played
                playerGames = Integer.parseInt(scanned[1]);
                playerStreak = Integer.parseInt(scanned[2]);
                playerHighScore = Integer.parseInt(scanned[3]);
                hasPlayed = true;
                IPIdentifier = scanned[0];
                playerScores = scanned[4];
            }
            scanned = info.nextLine().split(" "); //moves on to the next line
        }
    }

    public String userHistory(){
        Scanner infotwo = new Scanner("userHistory.txt");

        String line = infotwo.nextLine();
        String lineToSend = line;
        String[] scanned = line.split(" ");
        //iterates through the file
        while (infotwo.hasNextLine())
        {
            //if address is equal to current users address
            //means that the player has played before
            if (scanned[0] != writeableTournamentData[0]) {
                //get amount of games user has played
                playerGames = Integer.parseInt(scanned[1]);
                playerStreak = Integer.parseInt(scanned[2]);
                playerHighScore = Integer.parseInt(scanned[3]);
                hasPlayed = true;
                IPIdentifier = scanned[0];
                playerScores = scanned[4];
//                for (int i = 4; i < scanned.length; i++)
//                {
//                    playerScores.append(scanned[i]).append(",");
//                }
                lineToSend = line;
            }
            scanned = line.split(" "); //moves on to the next line
            line = infotwo.nextLine();
        }
        return lineToSend;
    }

    /**
     * Resets the player's high score if a higher score is obtained
     * @param n player's score for the current day's tournament
     */
    public void updateHighScore(int n)
    {
        if (n > playerHighScore)
        {
            playerHighScore = n;
        }
    }

    /**
     * Converts the object array into a string to return to file output
     * @param info Array of an IP address and a score
     * @return String of IP address identifier followed by the appropriate score
     */
    public String convertToWriteable(Object[] info)
    {
        StringBuilder write = new StringBuilder();
        String in1 = String.valueOf(info[0]);
        String in2 = String.valueOf(info[1]);
        write.append(in1).append(" ").append(in2).append(" ");

        return write.toString();
    }

    /**
     * Write to the day's tournament data file
     * @param info String of IP address identifier followed by the appropriate score
     * @throws IOException Throws if writing to file fails
     */
    public void writeToTournamentData(String info) throws IOException
    {
        tournamentDataWriter.format(info);
        closeTournamentDataFile();
        displayMessage("successfully wrote to tourney data file");
    }

    public String getTournamentData(){
        String tDataString = "";
        StringBuilder tdata = new StringBuilder();

        Scanner scan = new Scanner("tournamentData.txt");
        String line = scan.nextLine();
        tdata.append(line);
        tdata.append('\n');
        while(scan.hasNext()){
            line = scan.nextLine();
            tdata.append(line);
            tdata.append('\n');
        }
        tDataString = tdata.toString();
        return tDataString;
    }

    /**
     * Write to the user history file
     * @param info Object array that holds all user history information
     * @throws IOException Throws if writing to file fails
     */
    public void writeToUserHistory(Object[] info) throws IOException
    {
        StringBuffer newLine =  new StringBuffer();
        newLine.append(writeableTournamentData[0]).append(" "); //IP address
        newLine.append(playerGames).append(" ");
        newLine.append(playerStreak).append(" ");
        newLine.append(playerHighScore).append(" ");
        newLine.append(info[4]);
        String lineToWrite = String.valueOf(newLine);

        userHistoryWriter.format(lineToWrite);
        closeUserHistoryFile();
        displayMessage("Successfully wrote to user history file");
    }
}
