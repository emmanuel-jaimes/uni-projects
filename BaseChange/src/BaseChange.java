/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: A01_BaseChange
 * Class: BaseChange
 */

import java.lang.Math;

/**
 *  BaseChange class that constructs BaseChange object
 *  to convert input number to given target base from
 *  original base
 */
public class BaseChange {

    //*********************************************
    //instance variables
    private int originalBase;   //original base given for number given
    private int desiredBase;    //target base given for nuber given
    private String n;   //input number given

    //getters and setters for instance variables
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


        //**VALIDATION****************************************************************
        //invalid base conversion range
        if ((this.getOriginalBase() > 32 || this.getOriginalBase() < 2) || (this.getDesiredBase() > 32 || this.getDesiredBase() < 2))
        {
            String error = "Invalid bases to convert: \n \t Please try different bases.";
            System.out.println(error);
            return error;
        }

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

        //***CONVERSION*****************************************************************

        //conversion is not necessary
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
                char c = this.getN().charAt(this.getN().length() - i -1);
                decimalConversion += set.indexOf(c) * Math.pow(this.getOriginalBase(), x);
            }
        }
        /* Returns the decimal conversion of originalBase */
        if (this.getDesiredBase() == 10)
            return String.valueOf(decimalConversion);


        //Convert from decimal base to desired base
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
