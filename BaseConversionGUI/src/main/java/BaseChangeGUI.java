/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B03_BaseChangeGUI
 * Class: BaseChangeGUI
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * BaseChangeGUI implements graphical user interface for
 * providing JFrame windows that handle inputs and outputs
 * respective to users interactions
 */
public class BaseChangeGUI extends JFrame
{

    private final JPanel bcPanel;
    private final JPanel titlePanel;
    private final JPanel outputPanel;
    private final JLabel title = new JLabel("Base Change Converter");
    private final JLabel baseFromLabel = new JLabel("Base from: ");
    private final JLabel toBaseLabel = new JLabel("to Base: ");
    private final JButton swapButton;
    private JTextField inputNumber;
    private final JTextField outputNumber;
    private final JComboBox<String> baseFromComboBox;
    private final JComboBox<String> toBaseComboBox;
    private static final String[] bases = {"2","8","10","16","32"};

    private int baseFrom = 0;
    private int toBase = 0;
    private String originalNumber;

    public void setBaseFrom(String val)
    {
        baseFrom = Integer.parseInt(val);
    }
    public int getBaseFrom()
    {
        return baseFrom;
    }
    public void setToBase(String val)
    {
        toBase = Integer.parseInt(val);
    }
    public int getToBase()
    {
        return toBase;
    }
    public void setOriginalNumber(String val)
    {
        originalNumber = val;
    }
    public String getOriginalNumber()
    {
        return originalNumber;
    }


    /**
     * BaseChangeGUI
     *  Constructs panels and elements that implement user friendly
     *  experience to convert inputs to corresponding outputs
     */
    public BaseChangeGUI()
    {
        //initialize panels
        bcPanel = new JPanel();
        titlePanel = new JPanel();
        outputPanel = new JPanel();

        //initialize text fields
        swapButton = new JButton("Swap Bases"); //swap button
        bcPanel.setLayout(new GridLayout(1, 1));    //panel for base conversion area
        inputNumber = new JTextField("Enter Number Here");
        outputNumber = new JTextField("Result", inputNumber.getText().length());
        outputNumber.setEditable(false);

        //comboBox for Base Conversions
        baseFromComboBox = new JComboBox<>(bases);      //base from
        baseFromComboBox.setMaximumRowCount(5);
        baseFromComboBox.setSelectedItem(baseFromComboBox.getItemAt(2));

        toBaseComboBox = new JComboBox<>(bases);        //to base
        toBaseComboBox.setMaximumRowCount(5);
        toBaseComboBox.setSelectedItem(toBaseComboBox.getItemAt(0));

        //PANELS
        titlePanel.add(title);  //Title
        bcPanel.add(inputNumber); //BaseChange Area
        bcPanel.add(baseFromLabel);
        bcPanel.add(baseFromComboBox);
        bcPanel.add(swapButton);
        bcPanel.add(toBaseLabel);
        bcPanel.add(toBaseComboBox);
        outputPanel.add(outputNumber); //Output Result area

        //add to frame
        this.add(titlePanel, BorderLayout.NORTH);
        this.add(bcPanel, BorderLayout.CENTER);
        this.add(outputPanel,BorderLayout.SOUTH);

        //event handler for text fields
        KeyListener keyListener = new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (inputNumber.getText().isEmpty())
                    outputNumber.setText(null);
                else {
                    String insert = inputNumber.getText();
                    updateConvert(insert, String.valueOf(getBaseFrom()), String.valueOf(getToBase()));
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        };

        inputNumber.addKeyListener(keyListener);

        //combo box item listeners
        this.setBaseFrom(bases[baseFromComboBox.getSelectedIndex()]);
        this.setToBase(bases[toBaseComboBox.getSelectedIndex()]);

        BoxComboHandler boxComboHandler = new BoxComboHandler();
        baseFromComboBox.addItemListener(boxComboHandler);
        toBaseComboBox.addItemListener(boxComboHandler);

        //swap button handling and behaviors
        swapButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swapAction();
            }
        });
    }

    /**
     * no return type
     * swap method used to swap combo box bases and
     * update output
     */
    public void swapAction()
    {
        inputNumber.setText(outputNumber.getText());
        Object temp = baseFromComboBox.getSelectedItem();
        baseFromComboBox.setSelectedItem(toBaseComboBox.getSelectedItem());
        toBaseComboBox.setSelectedItem(temp);
        updateConvert(inputNumber.getText(), String.valueOf(this.getBaseFrom()), String.valueOf(this.getToBase()));
    }

    /**
     *
     * @param n String element representing input number from user
     * @param ogBase String element representing original base of the number given
     * @param tBase String element representing the target base of the number given
     */
    public void updateConvert(String n, String ogBase, String tBase)
    {
        this.setOriginalNumber(n);
        this.setBaseFrom(ogBase);
        this.setToBase(tBase);

        BaseChange outerBC = new BaseChange(this.getOriginalNumber(), this.getBaseFrom(), this.getToBase());
        outputNumber.setColumns(outerBC.convert().length());
        outputNumber.setText(outerBC.convert());
    }

    /**
     * Class that handles events for ComboBox listeners
     */
    private class BoxComboHandler implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getSource() == baseFromComboBox)
            {
                setBaseFrom(bases[baseFromComboBox.getSelectedIndex()]);
                updateConvert(getOriginalNumber(), String.valueOf(getBaseFrom()), String.valueOf(getToBase()));
            }
            else if (e.getSource() == toBaseComboBox) {
                setToBase(bases[toBaseComboBox.getSelectedIndex()]);
                updateConvert(getOriginalNumber(), String.valueOf(getBaseFrom()), String.valueOf(getToBase()));
            }
        }
    }

    /**
     *  BaseChange class that constructs BaseChange object
     *  to convert input number to given target base from
     *  original base
     */
    public class BaseChange {

        //*********************************************
        private int originalBase;
        private int desiredBase;
        private String n;

        public void setOriginalBase(int originalBase) {
            this.originalBase = originalBase;
        }
        public int getOriginalBase()
        {
            return originalBase;
        }

        public void setDesiredBase(int desiredBase) {
            this.desiredBase = desiredBase;
        }
        public int getDesiredBase()
        {
            return desiredBase;
        }
        public void setN(String in)
        {
            n = in;
        }
        public String getN()
        {
            return n;
        }

        /**
         * @param input String element representing number to be converted
         * @param ogBase int element representing original numbers
         * @param targetBase int element representing target base
         */
        public BaseChange(String input, int ogBase, int targetBase)
        {
            this.setOriginalBase(ogBase);
            this.setDesiredBase(targetBase);
            this.setN(input);
        }

        /**
         * @return String object
         * method for converting input number from original base to target base
         */
        public String convert()
        {
            int decimalConversion = 0;
            String set = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

            // no base change
            if (this.getOriginalBase() == this.getDesiredBase()) {
                return n;
            }


            //invalid base conversion range
            if ((this.getOriginalBase() > 32 || this.getOriginalBase() < 2) || (this.getDesiredBase() > 32 || this.getDesiredBase() < 2))
            {
                String error = "Invalid bases to convert: \n \t Please try different bases.";
                System.out.println(error);
                return error;
            }

            //**VALIDATION****************************************************************
            //makes sure input numbers correspond to the given base
            //validate
            for (int e = 0; e < this.getN().length(); e++)
            {
                if (set.indexOf(this.getN().charAt(e)) +1 > this.getOriginalBase())
                {
                    String error = "Invalid Number. Change base or number";
                    System.err.println(error);
                    return error;
                }
            }

            //**CONVERSION*****************************************************************
            //conversion is not necessary
            //System.out.println("\nInitial decimal: " + decimalConversion);
            if (this.getOriginalBase() == 10)
            {
                decimalConversion = Integer.parseInt(n);
            }

            /*
             * Convert string from original base to decimal base
             *  if not already decimal
             */
            else if (this.getOriginalBase() != 10)
            {
                for (int i = 0; i < this.getN().length(); i++) {
                    int x = i;
                    char c = this.getN().charAt(this.getN().length() - i - 1);
                    decimalConversion += set.indexOf(c) * Math.pow(this.getOriginalBase(), x);
                }
            }
            /* Returns the decimal conversion of originalBase */
            if (this.getDesiredBase() == 10)
                return String.valueOf(decimalConversion);

            /*
             * Convert from decimal base to desired base
             */
            StringBuilder desiredNumber = new StringBuilder();

            int i = decimalConversion;
            while (i != 0)
            {
                desiredNumber.insert(0, set.charAt(i%this.getDesiredBase()));
                i = i/this.getDesiredBase();
            }

            return String.valueOf(desiredNumber);
        }
    }


}
