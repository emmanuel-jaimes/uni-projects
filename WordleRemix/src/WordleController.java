import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import java.io.IOException;
import java.net.*;
import java.time.LocalTime;
import java.util.*;

/**
 * this class handles user interactions with the GUI and communicates with a server
 */
public class WordleController implements Initializable {
    // The text area where user can enter letter
    /** GUI component  */
    @FXML private TextArea r1c1;
    /** GUI component  */
    @FXML private TextArea r1c2;
    /** GUI component  */
    @FXML private TextArea r1c3;
    /** GUI component  */
    @FXML private TextArea r1c4;
    /** GUI component  */
    @FXML private TextArea r1c5;
    /** GUI component  */
    @FXML private TextArea r2c1;
    /** GUI component  */
    @FXML private TextArea r2c2;
    /** GUI component  */
    @FXML private TextArea r2c3;
    /** GUI component  */
    @FXML private TextArea r2c4;
    /** GUI component  */
    @FXML private TextArea r2c5;
    /** GUI component  */
    @FXML private TextArea r3c1;
    /** GUI component  */
    @FXML private TextArea r3c2;
    /** GUI component  */
    @FXML private TextArea r3c3;
    /** GUI component  */
    @FXML private TextArea r3c4;
    /** GUI component  */
    @FXML private TextArea r3c5;
    /** GUI component  */
    @FXML private TextArea r4c1;
    /** GUI component  */
    @FXML private TextArea r4c2;
    /** GUI component  */
    @FXML private TextArea r4c3;
    /** GUI component  */
    @FXML private TextArea r4c4;
    /** GUI component  */
    @FXML private TextArea r4c5;
    /** GUI component  */
    @FXML private TextArea r5c1;
    /** GUI component  */
    @FXML private TextArea r5c2;
    /** GUI component  */
    @FXML private TextArea r5c3;
    /** GUI component  */
    @FXML private TextArea r5c4;
    /** GUI component  */
    @FXML private TextArea r5c5;
    /** GUI component  */
    @FXML private TextArea r6c1;
    /** GUI component  */
    @FXML private TextArea r6c2;
    /** GUI component  */
    @FXML private TextArea r6c3;
    /** GUI component  */
    @FXML private TextArea r6c4;
    /** GUI component  */
    @FXML private TextArea r6c5;
    /** GUI component  */
    @FXML private TextField wordGuess;
    /** GUI component  */
    @FXML private TextField timerTextField;
    /** controls max characters allowed in text areas */
    private final static int MAX_CHAR = 1;
    /** controls max characters allowed in guesses */
    private final static int MAX_GUESS_SIZE = 5;
    /** arraylist of textAreas to call to manipulate */
    private final ArrayList<TextArea> textAreasToEnable = new ArrayList<>();
    /** counter to locate which text Area we are at now */
    private int areaCount = 0;
    /** GUI component for button */
    @FXML private Button submitGuessButton;
    /** GUI component for button */
    @FXML private Button leaderboardButton;
    /** GUI component for button */
    @FXML private Button statsButton;
    /** User's final score of the game */
    private int score;
    /**Socket to connect to server*/
    private DatagramSocket socket;
    /** Timer **/
    private final Timer timer = new Timer();
    /** reference to time representation of hour */
    private int hour;
    /** reference to time representation of minute */
    private int minute;
    /** reference to time representation of second */
    private int second;

    /**
     * initializes components of the gui and sets up controller for connection with the server
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wordGuess.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_GUESS_SIZE ? change : null));
        //limits each text area to one character only
        for (TextArea textArea : Arrays.asList(r1c1 ,r1c2, r1c3, r1c4, r1c5, r2c1, r2c2, r2c3, r2c4, r2c5, r3c1, r3c2, r3c3, r3c4, r3c5, r4c1, r4c2, r4c3, r4c4, r4c5, r5c1, r5c2, r5c3, r5c4, r5c5, r6c1, r6c2, r6c3, r6c4, r6c5)) {
            textArea.setTextFormatter(new TextFormatter<String>(change -> change.getControlNewText().length() <= MAX_CHAR ? change : null));
            textArea.setDisable(true);
            textArea.setStyle("-fx-background-color: rgb(190, 190, 275);");
            textAreasToEnable.add(textArea);
        }

        // set up timer every second
        activateNextColumn();
        setupSocket();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                showTime();
            }
        }, 0, 1000);
    }

    /**
     * enables next column to be editable
     */
    @FXML
    void activateNextColumn() {
        if(areaCount < 29){
            for(int i = 0; i < 5; i++){
                //textAreasToEnable.get(30 - areaCount).setDisable(true);
                textAreasToEnable.get(areaCount).setDisable(false);
                textAreasToEnable.get(areaCount).setEditable(false);
                areaCount++;
            }
        }
    }

    /**
     *  as characters are typed into the textfield, they are placed into the boxes
     */
    @FXML
    void putInBox(KeyEvent event) throws IOException {
        String temp = event.getText();
        // doesn't allow user to input more than 5 letters
        if (wordGuess.getText().length() <= 5) {
            if (event.getCode() == KeyCode.ENTER) {
                submit();
            }

            // if one letter is deleted
            if (event.getCode() == KeyCode.BACK_SPACE) {
                // find the last text area
                for (int i = 0; i < textAreasToEnable.size(); i++) {
                    if (textAreasToEnable.get(i).getText().equals("")) {
                        if ((i - 1) < areaCount - 5) {
                            return;
                        }
                        else {
                            textAreasToEnable.get(i - 1).clear();
                            return;
                        }
                    }
                }
                if (!textAreasToEnable.get(29).getText().equals("")) {
                    textAreasToEnable.get(29).clear();
                    return;
                }
            }
            for (int i = 0; i < textAreasToEnable.size(); i ++) {
                // find the first text area that is available
                if(!textAreasToEnable.get(i).isDisabled()){
                    if (textAreasToEnable.get(i).getText().equals("")) {
                        textAreasToEnable.get(i).setText(temp);
                        return;
                    }
                }
            }
        }
    }

    /**
     * calls to display rules of the game once button is pressed
     */
    @FXML
    void showRule() {
        displayRule();
    }

    /**
     * displays rules for the WORDLE
     */
    public void displayRule() {
        // show rules
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("HOW TO PLAY");
        message.setHeaderText("Guess the WORDLE in six tries.");
        message.setContentText("Each guess must be a valid five-letter word" +
                "\n\nYou can guess 5 words in the tournament, and the tournament is once per day." +
                "\n\nAfter each guess, the color of the tiles will change to show how close your guess was to the word." +
                "\n\nIf the letter turns green, that means the letter is in the word and in the correct spot." +
                "\n\nIf the letter turns pink, that means the letter is in the word but in the wrong spot." +
                "\n\nIf the letter turns grey, that means the letter is not in the word in any spot.");
        message.show();
    }

    /**
     * displays final scores for the user
     * @param guess how many guess the user took
     * @param score the score for the user
     */
    public void displayFinalScore(int guess, int score){
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("CONGRATULATIONS");
        message.setHeaderText("You have completed the wordle tournament of the day!!");
        message.setContentText("You completed the fifth wordle in " + guess + " guesses." +
                "\n\nYour final score for the tournament is...." + score +
                "\n\nCome back again tomorrow to play again. If you would like to view today's leaderboard, click the Leaderboard button");
        message.show();
    }

    /**
     * displays leaderboard summary once respective button is pressed
     */
    @FXML
    void showLeaderboard() throws IOException {
        String output = "leaderboard";
        byte[] data = output.getBytes();
        //create packet to send
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 23747);
        socket.send(sendPacket); //send packet to server
        String displayOne = packetForButton();
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("LEADERBOARD");
        message.setHeaderText("SCORES OF THE DAY");
        message.setContentText(displayOne);
        //todo display real leaderboard
        message.show();
    }

    /**
     * displays statistics information
     * @param event upon button press
     */
    @FXML
    void showStats(ActionEvent event) throws IOException {
        String output = "statistics";
        byte[] data = output.getBytes();
        //create packet to send
        DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 23747);
        socket.send(sendPacket); //send packet to server
        String displayTwo = packetForButton();
        Alert stats = new Alert(Alert.AlertType.INFORMATION);
        stats.setTitle("STATISTICS");
        stats.setHeaderText("Statistical Information");
        stats.setContentText(displayTwo);
        //todo pull user stats from the file
        stats.show();
    }

    public String packetForButton() throws IOException {
        // receive packet and display appropriate contents
        byte[] data = new byte[100]; // set up packet
        DatagramPacket receivePacket = new DatagramPacket(
                data, data.length);

        socket.receive(receivePacket); // wait for packet

        // display packet contents

        String fromServer = new String(receivePacket.getData(), 0, receivePacket.getLength());
        return fromServer;
    }

    /**
     * Set time left till midnight
     */
    public void setTimer() {
        LocalTime time = LocalTime.now();
        hour = 23 - time.getHour();
        minute = 59 - time.getMinute();
        second = 60 - time.getSecond();
    }

    /**
     * Set up the timer textfield that shows how many time left till midnight
     */
    public void showTime() {
        setTimer();
        String time = "";
        if (hour < 10) {
            time = "0" + hour + ":" + minute + ":" + second;
            if (minute < 10) {
                time = "0" + hour + ":0" + minute + ":" + second;
                if (second < 10) {

                }
            }
            if (second < 10) {
                time = "0" + hour + ":" + minute + ":0" + second;
            }
        }
        else if (minute < 10) {
            time = hour + ":0" + minute + ":" + second;
            if (second < 10) {
                time = hour + ":0" + minute + ":0" + second;
            }
        }
        else if (second < 10) {
            time = hour + ":" + minute + ":0" + second;
        }
        else {
            time = hour + ":" + minute + ":" + second;
        }
        timerTextField.setText("Next: " + time);
    }

    /**
     * Tells the user the word they input is not in the word list
     */
    public void invalidWordMessage() {
        Alert invalid = new Alert(Alert.AlertType.ERROR);
        invalid.setTitle("ERROR");
        invalid.setHeaderText("Invalid Word");
        invalid.setContentText("This word is not in the word list");
        invalid.show();
        // reset text boxes
        for (int i = 0; i < textAreasToEnable.size(); i ++) {
            if (textAreasToEnable.get(i).isDisabled()) {
                for (int j = i-5; j < i; j++) {
                    textAreasToEnable.get(j).setText("");
                }
            }
        }
        if (!textAreasToEnable.get(25).isDisabled()) {
            for (int f = 25; f < textAreasToEnable.size(); f++) {
                textAreasToEnable.get(f).setText("");
            }
        }
    }

    /**
     * sets the message that will be displayed at the end of the round
     */
    public void endingMessage(int guess, int score){
        String finalMessage = switch (guess) {
            case 1 -> "You have to have cheated...";
            case 2 -> "Nice work!";
            case 3 -> "You're one smart cookie.";
            case 4 -> "Cutting it close...";
            case 5 -> "Phew!";
            case 6 -> "For a second I was worried...";
            default -> "Good job!";
        };
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("WORDLE COMPLETE");
        message.setHeaderText(finalMessage);
        message.setContentText("Your score for the round is... " + score);
        //todo send packet to server requesting score back
        //todo find IP address for the day in the server and send back as a packet
        message.show();
    }

    /**
     * creates a socket to allow communication with the server
     */
    public void setupSocket(){
        try // create DatagramSocket for sending and receiving packets
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
     * method to be called that displays alert informing user that limit has been reached for session
     */
    public void endOfGameErrorMessage(){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("SORRY");
        message.setHeaderText("You can only play Wordle once a day....");
        message.setContentText("Come back tomorrow for a new word and even more fun!");
        message.show();
        wordGuess.setDisable(true);
    }

    /**
     *  processes a submission once the submit button is pressed
     */
    @FXML
    void submitGuess() throws IOException {
        submit();
    }

    /**
     *  method to be called that displays alert that tells the user the word is not long enough
     */
    public void notEnoughChars(){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("ERROR");
        message.setHeaderText("Your word was not long enough....");
        message.setContentText("Make sure your guess fills all the boxes!");
        message.show();
    }

    /**
     * processes the submitted guess into a packet and communicates it with the server
     */
    public void submit() throws IOException {
        boolean flagForEntry = false;
        Words wordList = new Words();

        //check for stuff in each box (area count - 5
        for(int i = 1; i < 6; i++){
            if(textAreasToEnable.get(areaCount - i).getText().equals("")){
                flagForEntry = true;
            }
        }
        if(flagForEntry) {
            notEnoughChars();
            return;
        }
        else{
            // get the word input
            String wordInput = "";
            for (int index = 0; index < textAreasToEnable.size(); index ++) {
                if (!textAreasToEnable.get(25).getText().equals("")) {
                    for (int k = 25; k < 30; k ++) {
                        wordInput += textAreasToEnable.get(k).getText();
                    }
                    break;
                }
                if (textAreasToEnable.get(index).getText().equals("")) {
                    for (int j = index-5; j < index; j ++) {
                        wordInput += textAreasToEnable.get(j).getText();
                    }
                    break;
                }
            }
            // check for input validation
            if (!wordList.checkValidWord(wordInput)) {
                invalidWordMessage();
                wordGuess.clear();
                return;
            }
            StringBuffer guess = new StringBuffer();
            for(int i = 5; i > 0; i--){
                guess.append(textAreasToEnable.get(areaCount - i).getText());
            }
            byte[] data = guess.toString().getBytes();
            //create packet to send
            DatagramPacket sendPacket = new DatagramPacket(data, data.length, InetAddress.getLocalHost(), 23747);
            socket.send(sendPacket); //send packet to server
        }
        waitForPackets();
        wordGuess.clear();
        wordGuess.setEditable(true);
        activateNextColumn();
    }

    /**
     * This method waits for packets to arrive from Server, display packet contents
     */
    public void waitForPackets() throws IOException
    {
        //while (true)
        {
            // receive packet and display appropriate contents
            byte[] data = new byte[100]; // set up packet
            DatagramPacket receivePacket = new DatagramPacket(
                    data, data.length);

            socket.receive(receivePacket); // wait for packet

            // display packet contents

            String fromServer = new String(receivePacket.getData(), 0, receivePacket.getLength());
            int scoreToPrint = 0;
            int guessToPrint = 0;
            if(fromServer.length() < 5){
                //check for end of game flag
                if(fromServer.length() < 4){
                    //extract number of guesses
                    guessToPrint = Integer.parseInt(fromServer.substring(0,1));
                    //end of round this is a score, print out in an alert
                    scoreToPrint = Integer.parseInt(fromServer.substring(1));
                }
                //todo: look here at why flag doesnt pull
                int count = 0;
                for (int i = 0; i < textAreasToEnable.size(); i ++) {
                    if (!textAreasToEnable.get(i).getText().equals("")) {
                        count ++;
                    }
                }
                endingMessage(guessToPrint, scoreToPrint);      // display the ending message
                wordGuess.setEditable(false);
                wordGuess.setDisable(true);
                reset();
            }
            if (fromServer.charAt(0) == 'e'){
                //extract number of guesses
                guessToPrint = Integer.parseInt(fromServer.substring(1,2));
                //end of the tournament and the score (ex. e29)
                scoreToPrint = Integer.parseInt(fromServer.substring(2));
                //display the score in ending alert that says the tournament is over
                displayFinalScore(guessToPrint, scoreToPrint);
                wordGuess.setDisable(true);
                wordGuess.setEditable(false);
            }
            else if(fromServer.equals("ddddd")){
                endOfGameErrorMessage();
                return;
            }
            // check if the user get the word right, reset the page and display ending message
            if(fromServer.length() == 5){
                char [] answers = new char[5];
                for(int i = 0; i < 5; i++){
                    //put T, F, I into answers array
                    answers[i] = fromServer.charAt(i);
                }

                for(int i = 5; i > 0; i --){
                    //change border colors of text fields in gui
                    if(answers[5-i] == 't'){
                        //set border to green
                        textAreasToEnable.get(areaCount - i).setStyle("-fx-background-color: rgb(0, 204, 0);");
                    }
                    else if(answers[5-i] == 'i'){
                        //set border to yellow
                        textAreasToEnable.get(areaCount - i).setStyle("-fx-background-color: rgb(255, 153, 204);");
                    }
                    else{
                        //set border to grey
                        textAreasToEnable.get(areaCount - i).setStyle("-fx-background-color: rgb(64, 64, 64);");
                    }
                }
            }
        }
    }

    /**
     * resets variables of the running game to be able to play another round
     */
    public void reset(){
//        endingMessage(guessNumber);
//        guessNumber = 0;
        wordGuess.setDisable(false);
        wordGuess.setEditable(true);
        for(TextArea s:textAreasToEnable){
            s.clear();
            s.setDisable(true);
            s.setStyle("-fx-background-color: rgb(190, 190, 275);");
        }
        areaCount = 0;
        //activateNextColumn();
    }

}

