/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B04_AnimalZoo
 * Class: Ave
 */

import java.util.Scanner;

/**
 *  Ave abstract class that defines specific characteristics
 *  for specified classification of animal objects later to
 *  be implemented by specified types
 **/
public abstract class Ave extends Animal{

    /**
     * @return String that represents
     * animal classification for ave object
     */
    @Override
    public String Classification()
    {
        return "Ave";
    }

    /**
     * @return int object representing number of
     * legs for ave animal objects
     */
    @Override
    public int legs()
    {
        return 2;
    }

}

/**
 * Birds class extends Ave class and
 * adds specified characteristics for Bird animal objects
 *
 **/
class Birds extends Ave
{
    /**
     * Default constructor for Birds class
     */
    public Birds()
    {
        super();
        Scanner identifier = new Scanner(System.in);
        System.out.println("Name your Bird: ");
        this.setName(identifier.next());
        System.out.println("How old is your Bird? ");
        this.setAge(identifier.nextInt());
    }

    /**
     * @return int element representing
     * specific characteristic of Birds class for wingspan
     */
    public int wingspan()
    {
        //feet
        return 3;
    }

    /**
     * @return String object representing
     * the characteristic of Birds objects beak
     */
    public String beak()
    {
        return "Pointy";
    }

    /**
     * @return String object representing
     * the animal objects classification type
     */
    @Override
    public String Type()
    {
        return "Bird";
    }

    /**
     * @return String object representing diet of Birds class
     */
    @Override
    public String diet()
    {
        return "Insectivorous";
    }

    /**
     * @return String object representing the Birds class sound
     */
    @Override
    public String sound()
    {
        return "CacAaaawwwwww I'm a bird" ;
    }

    /**
     * @return printable method for displaying
     * characteristics of specified animal objects type to terminal
     */
    @Override
    public String toString()
    {
        return super.toString()
                + "Wingspan Size in feet " + this.wingspan() + "\n"
                + "Beak Characteristics: " + this.beak() + "\n"
                + "Food Diet: " + this.diet() + "\n";
    }

}