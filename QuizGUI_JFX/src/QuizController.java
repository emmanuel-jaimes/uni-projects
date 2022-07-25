import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.*;

/**
 * QuizController class handles method actions for when components are interacted with in the java fx graohical user interface
 */
public class QuizController implements Initializable {

    /**
     * String objects to hold submitted answers for first three questions
     */
    private String answer1 = null, answer2 = null, answer3 = null;
    /**
     * arraylist to hold submitted answers for question 4
     */
    private final ArrayList<String> answer4 = new ArrayList<>();
    /**
     * arraylist to hold submitted answers for question 5
     */
    private final ArrayList<String> answer5 = new ArrayList<>();
    /**
     * determine whether user has accepted that cheating is bad checkbox
     */
    private boolean hasAgreed = false;

    //  summary stats variables
    /**
     * status of each question initialized to be correct
     */
    private String q1Status = "Correct", q2Status = "Correct", q3Status = "Correct", q4Status = "Correct", q5Status = "Correct";
    /**
     * String objects containing key answers for first 3 questions
     */
    private String key1 = "2", key2 = "10", key3 = "E";
    /**
     * initialize arraylist to hold key items for question 4
     */
    private ArrayList<String> key4 = new ArrayList<>();
    /**
     * initialize arraylist to hold key items for question 5
     */
    private ArrayList<String> key5 = new ArrayList<>();
    /**
     * initial score on quiz
     */
    private int score = 100;

    //question 1
    /**
     * toggle group for single selection of question 1 radio buttons
     */
    final ToggleGroup question1Toggle = new ToggleGroup();
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q1A;
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q1B;
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q1C;
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q1D;
    //question 2
    /**
     * toggle group for single selection of question 2 radion button groups
     */
    final ToggleGroup question2Toggle = new ToggleGroup();
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q2A;
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q2B;
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q2C;
    /**
     * initialize radio button for question 2
     */
    @FXML private RadioButton q2D;
    //question 3
    /**
     * initialize combo box for question 3
     */
    @FXML private ComboBox<String> question3ComboBox = new ComboBox<>();
    //question 4
    /**
     * checkbox for question 4 choice
     */
    @FXML private CheckBox q4beak;
    /**
     * checkbox for question 4 choice
     */
    @FXML private CheckBox q4wings;
    /**
     * checkbox for question 4 choice
     */
    @FXML private CheckBox q4fur;
    /**
     * checkbox for question 4 choice
     */
    @FXML private CheckBox q4arms;
    //question 5
    /**
     * initialize listview for question 5
     */
    @FXML private ListView<String> question5colorsList;
    /**
     * initialize listview for question 5
     */
    @FXML private ListView<String> question5coolList;
    //agreement box
    /**
     * initialize checkbox for no-cheating agreement
     */
    @FXML private CheckBox quizAgreementCheckBox;
    //submit button
    /**
     * initialize button for final submission of quiz
     */
    @FXML private Button submitButton;
    //move buttons
    /**
     * initialize button to move selected list item to the right from left
     */
    @FXML private Button moveRightButton;
    /**
     * initialize button to move selected list item to the left from right
     */
    @FXML private Button moveLeftButton;

    //question handling
    /**
     * store the answer chosen from question 1 radio buttons
     */
    @FXML //question 1
    void selectedRadioButtonQ1()
    {
        if (this.question1Toggle.getSelectedToggle().equals(this.q1A))
        {
            answer1 = q1A.getText();
        }
        if (this.question1Toggle.getSelectedToggle().equals(this.q1B))
        {
            answer1 = q1B.getText();
        }
        if (this.question1Toggle.getSelectedToggle().equals(this.q1C))
        {
            answer1 = q1C.getText();
        }
        if (this.question1Toggle.getSelectedToggle().equals(this.q1D))
        {
            answer1 = q1D.getText();
        }
    }

    /**
     * store the answer chosen from question 2 radio buttons
     */
    @FXML //question 2
    void selectedRadioButtonQ2()
    {
        if (this.question2Toggle.getSelectedToggle().equals(this.q2A))
        {
            answer2 = q2A.getText();
        }
        if (this.question2Toggle.getSelectedToggle().equals(this.q2B))
        {
            answer2 = q2B.getText();
        }
        if (this.question2Toggle.getSelectedToggle().equals(this.q2C))
        {
            answer2 = q2C.getText();
        }
        if (this.question2Toggle.getSelectedToggle().equals(this.q2D))
        {
            answer2 = q2D.getText();
        }
    }

    /**
     * store the chosen answer from combo box component
     */
    @FXML   //question 3
    void ComboBoxSelection()
    {
        answer3 = question3ComboBox.getValue();
    }

    /**
     * update checkbox answers chosen to array list and sort
     */
    @FXML
    void setQ4wings()
    {
        if (!answer4.contains(q4wings.getText()))
            answer4.add(q4wings.getText());
        else
            answer4.remove(q4wings.getText());

        Collections.sort(answer4);
    }

    /**
     * update checkbox answers chosen to array list and sort
     */
    @FXML
    void setQ4beak()
    {
        if (!answer4.contains(q4beak.getText()))
            answer4.add(q4beak.getText());
        else
            answer4.remove(q4beak.getText());

        Collections.sort(answer4);
    }

    /**
     * update checkbox answers chosen to array list and sort
     */
    @FXML
    void setQ4fur()
    {
        if (!answer4.contains(q4fur.getText()))
            answer4.add(q4fur.getText());
        else
            answer4.remove(q4fur.getText());

        Collections.sort(answer4);
    }

    /**
     * update checkbox answers chosen to array list and sort
     */
    @FXML
    void setQ4arms()
    {
        if (!answer4.contains(q4arms.getText()))
            answer4.add(q4arms.getText());
        else
            answer4.remove(q4arms.getText());

        Collections.sort(answer4);
    }

    /**
     * move selected items from list on the left to the list on the right when the button is pressed
     */
    @FXML   //question 5
    void moveRight()
    {
        ObservableList<String> listSelected1 = question5colorsList.getSelectionModel().getSelectedItems();

        for (String item : listSelected1)
        {
            if (!question5coolList.getItems().contains(item))
            {
                question5coolList.getItems().add(item);
                answer5.add(item);
            }
        }
        Collections.sort(answer5);
    }

    /**
     * move items from the list on the right back to the list on the left when the button is pressed
     */
    @FXML   //question 5
    void moveLeft()
    {
        String item = question5coolList.getSelectionModel().getSelectedItem();
        question5coolList.getItems().remove(item);
        answer5.remove(item);
        Collections.sort(answer5);
    }

    /**
     * output quiz summary whether conditions have been met
     */
    @FXML
    void submitPressed()
    {
        if (agreement())
        {
            //grade answers
            this.setScore(100);
            //question 1
            if (answer1 == null)
            {
                answer1 = "N/A";
                q1Status = "Unanswered";
                deductPoints(20);
            }
            else if (!answer1.equals(key1))
            {
                q1Status = "Incorrect";
                deductPoints(20);
            }
            //question 2
            if (answer2 == null)
            {
                answer2 = "N/A";
                q2Status = "Unanswered";
                deductPoints(20);
            }
            else if (!answer2.equals(key2))
            {
                q2Status = "Incorrect";
                deductPoints(20);
            }
            //question 3
            if (answer3 == null)
            {
                answer3 = "N/A";
                q3Status = "Unanswered";
                deductPoints(20);
            }
            else if (!answer3.equals(key3))
            {
                q3Status = "Incorrect";
                deductPoints(20);
            }
            //question 4
            if (answer4.isEmpty())
            {
                q4Status = "Unanswered";
                answer4.add("N/A");
                deductPoints(20);
            }
            else if (!answer4.equals(key4))
            {
                q4Status = "Incorrect";
                deductPoints(20);
            }
            //question 5
            if (answer5.isEmpty())
            {
                q5Status = "Unanswered";
                deductPoints(20);
            }
            else if (!answer5.equals(key5))
            {
                q5Status = "Incorrect";
                deductPoints(20);
            }

            //show quiz summary pane
            Alert summary = new Alert(Alert.AlertType.INFORMATION);
            summary.setHeight(800);
            summary.setWidth(300);
            summary.setTitle("\nQuiz");
            //question1
            summary.setHeaderText("\n\nQuiz Graded");
            summary.setContentText( "\nQuestion 1" +
                                    "\nStatus: \t" + q1Status +
                                    "\nSubmitted Answer: \t" + answer1 +
                                    "\nCorrect Answer: \t" + key1 +
            //question2
                                    "\n\nQuestion 2" +
                                    "\nStatus: \t" + q2Status +
                                    "\nSubmitted Answer: \t" + answer2 +
                                    "\nCorrect Answer: \t" + key2 +
            //question3
                                    "\n\nQuestion 3" +
                                    "\nStatus: \t" + q3Status +
                                    "\nSubmitted Answer: \t" + answer3 +
                                    "\nCorrect Answer: \t" + key3 +
            //question4
                                    "\n\nQuestion 4" +
                                    "\nStatus: \t" + q4Status +
                                    "\nSubmitted Answer: \t" + answer4.toString() +
                                    "\nCorrect Answer: \t" + key4.toString() +
            //question5
                                    "\n\nQuestion 5" +
                                    "\nStatus: \t" + q5Status +
                                    "\nSubmitted Answer: \t" + answer5.toString() +
                                    "\nCorrect Answer: \t" + key5.toString() +
            //score
                                    "\n\nScore: \n" + this.getScore());

            summary.show();
        }
        else
        {
            Alert agreementMissing = new Alert(Alert.AlertType.WARNING, "You have not acknowledged that cheating is bad");
            agreementMissing.showAndWait();
        }
    }


    /**
     * set agreement status based on whether check box has been selected
     */
    @FXML
    void agreementChecked()
    {
        //allow submit button to display stats
        this.setAgreement(quizAgreementCheckBox.isSelected());
    }

    /**
     * @param n reference to int amount to deduct from this score
     */
    public void deductPoints(int n)
    {
        this.setScore(getScore() - n);
    }

    /**
     * @param n reference for new score grade on quiz
     */
    public void setScore(int n)
    {
        this.score = n;
    }

    /**
     * @return reference for score on quiz
     */
    public int getScore()
    {
        return score;
    }

    /**
     * @return whether the agreement has been accepted
     */
    public boolean agreement() {return hasAgreed;}

    /**
     * @param agreed sets whether agreement has been accepted
     */
    public void setAgreement(boolean agreed) {hasAgreed = agreed;}

    /**
     * initialize JFX components for running
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //question 1 radio buttons
        q1A.setToggleGroup(question1Toggle);
        q1B.setToggleGroup(question1Toggle);
        q1C.setToggleGroup(question1Toggle);
        q1D.setToggleGroup(question1Toggle);

        //question 2 radio buttons
        q2A.setToggleGroup(question2Toggle);
        q2B.setToggleGroup(question2Toggle);
        q2C.setToggleGroup(question2Toggle);
        q2D.setToggleGroup(question2Toggle);

        //question 3 combo box
        question3ComboBox.setItems(FXCollections.observableArrayList(" ","D", "T", "E", "F"));

        //question 4 check boxes
        key4.add("beak");
        key4.add("wings");


        //question 5 list view area
        question5colorsList.getItems().addAll("Blue", "Green", "Orange", "Purple" , "Yellow", "Red");
        question5colorsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        key5.add("Blue");
        key5.add("Green");
        key5.add("Purple");

        //submit & swap buttons
        submitButton = new Button();
        moveLeftButton = new Button();
        moveRightButton = new Button();

    }
}
