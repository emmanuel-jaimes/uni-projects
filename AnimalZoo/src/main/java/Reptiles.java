/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B04_AnimalZoo
 * Class: Reptiles
 */

import java.util.Scanner;

/**
 * Reptiles abstract class that defines specific characteristics
 * for specified classification of animal object later to be
 * implemented by specified types
 *
 **/
public abstract class Reptiles extends Animal
{

    /**
     * @return string object that represents
     * animal classification for reptile class
     */
    @Override
    public String Classification()
    {
        return "Reptile";
    }

    /**
     * @return int object representing leg count for reptiles
     */
    @Override
    public int legs()
    {
        return 4;
    }

}

/**
 *  snake class extends reptiles abstract class and
 *  adds specified characteristics for snake animal objects
 **/
class Snakes extends Reptiles
{
    /**
     * default constructor for snake class and initializes instance variables
     */
    public Snakes()
    {
        super();
        Scanner identifier = new Scanner(System.in);
        System.out.println("Name your Snake: ");
        this.setName(identifier.next());
        System.out.println("How old is your Snake? ");
        this.setAge(identifier.nextInt());
    }

    /**
     * @return int object representing the slither speed of snake class
     */
    public int slitherSpeed()
    {
        //mph
        return 3;
    }

    /**
     * @return String object representing the snakes class color
     */
    public String color()
    {
        return "Red Yellow Black";
    }

    /**
     * @return int object representing the snakes class length
     */
    public int length()
    {
        //feet
        return 7;
    }

    /**
     * @return String object representing the snake class diet
     */
    @Override
    public String diet()
    {
        return "Carnivorous";
    }

    /**
     * @return String object representing Reptiles class type
     */
    @Override
    public String Type()
    {
        return "Snake";
    }

    /**
     * @return String object representing the snake class specified sound
     */
    @Override
    public String sound()
    {
        return "SsssSssSs I'm a snake";
    }


    /**
     * @return printable method for displaying
     * characteristics of specified animal objects type to terminal
     */
    @Override
    public String toString()
    {
        return super.toString()
                + "Snake Length in ft: " + this.length() + "\n"
                + "Color: " + this.color() + "\n"
                + "Slither Speed: " + this.slitherSpeed() + "\n";
    }

}