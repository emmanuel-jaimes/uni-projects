/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B04_AnimalZoo
 * Class: Mammal
 */

import java.util.Scanner;


/**
 * Mammal abstract class that defines methods and characteristics
 * to be implemented by later specified animal object types
 **/
public abstract class Mammal extends Animal{

    /**
     * @return String object representing classification of Mammal animal objects
     */
    @Override
    public String Classification()
    {
        return "Mammal";
    }

    /**
     * @return int object representing common characteristics of mammal objects
     */
    @Override
    public int legs()
    {
        return 4;
    }

}

/**
 * Lion class extends Mammal class nad
 * adds specified characteristics for Lion animal objects
 **/
class Lion extends Mammal
{
    /**
     * default constructor for Lion class initializing instance variables
     */
    public Lion()
    {
        super();
        Scanner identifier = new Scanner(System.in);
        System.out.println("Name your Lion: ");
        this.setName(identifier.next());
        System.out.println("How old is your Lion? ");
        this.setAge(identifier.nextInt());
    }

    /**
     * @return String object representing the Lion class specified mane size
     */
    public String maneSize()
    {
        return "Large";
    }

    /**
     * @return String object representing the Lions class specified color
     */
    public String color()
    {
        return "Golden Brown";
    }


    /**
     * @return String object representing the Lions class specified diet
     */
    @Override
    public String diet()
    {
        return "Carnivorous";
    }

    /**
     * @return String object representing the Mammal class specified type of animal
     */
    @Override
    public String Type()
    {
        return "Lion";
    }

    /**
     * @return String object representing the Lion class sound
     */
    @Override
    public String sound()
    {
        return "Rooar I'm a lion";
    }

    /**
     * @return String printable method for displaying
     * characteristics of specified animal objects type to terminal
     */
    @Override
    public String toString()
    {
        return super.toString()
                + "Mane Size: " + this.maneSize() + "\n"
                + "Color: " + this.color() + "\n";
    }
}


/**
 * Zebra class extends mammal class and
 * adds specified characteristics for Mammal animal objects
 *
 **/
class Zebra extends Mammal
{
    /**
     * default zebra constructor initializing instance variables
     */
    public Zebra()
    {
        super();
        Scanner identifier = new Scanner(System.in);
        System.out.println("Name your Zebra: ");
        this.setName(identifier.next());
        System.out.println("How old is your Zebra? ");
        this.setAge(identifier.nextInt());
    }

    /**
     * @return boolean object representing
     * whether the zebra class has nightvision
     */
    public boolean nightVision()
    {
        return true;
    }

    /**
     * @return int object representing sprint speed of zebra class
     */
    public int sprintSpeed()
    {
        //mph
        return 40;
    }

    /**
     * @return String object representing
     * the print pattern of Zebra animal class
     */
    public String print()
    {
        return "Striped";
    }

    /**
     * @return String object representing diet of Zebra class
     */
    @Override
    public String diet()
    {
        return "Herbivorous";
    }

    /**
     * @return String object representing
     * animal objects classification type
     */
    @Override
    public String Type()
    {
        return "Zebra";
    }

    /**
     * @return String object representing Zebras
     * specific sound
     */
    @Override
    public String sound()
    {
        return "Aaaaaaaaa I'm a Zebra";
    }


    /**
     * @return String object method for displaying characteristics
     * of specified animal objects type to terminal
     */
    @Override
    public String toString()
    {
        return super.toString()
                + "Body Print " + this.print() + "\n"
                + "Sprint Speed [mph]: " + this.sprintSpeed() + "\n"
                + "Has Night Vision? " + this.nightVision() + "\n";
    }
}