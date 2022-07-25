
/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B04_AnimalZoo
 * Class: AnimalZoo
 */

import java.util.Scanner;
import java .util.*;


/**
 *  AnimalZoo class that handles animal objects and their specified types
 *  for user handling and representation of zookeeper for animal objects
 *
 **/
public class AnimalZoo {
    /**
     * @param args driver for Animal class
     *             initializes animal object and implementation
     *             of polymorphic attributes classes
     */
    public static void main (String[] args)
    {


        /**
         * Zookeeper functions
         */
        //list of animals in the zoo
        ArrayList<Animal> animalzoo = new ArrayList<>();

        /**
            Add animal of each type upon initialization of animalzoo,
            arraylist containing Animal objects representing the zoo
         **/

        //initialize animal objects
        animalzoo.add(new Zebra());
        animalzoo.add(new Lion());
        animalzoo.add(new Birds());
        animalzoo.add(new Snakes());

        //initialize user input as zookeeper
        Scanner zookeeper = new Scanner(System.in);
        //inDecision reads in user inputs decisions
        int inDecision;

        //event loop handling for zookeeper decisions
        do {
            System.out.println("What would you like to do zookeeper?: " +
                        "\n 1. Add a new Animal? "
                        + "\n 2. Feed the Animals"
                        + "\n 3. Display Animals in Zoo "
                        + "\n 0. Retire as ZooKeeper");
            inDecision = zookeeper.nextInt();


            if (inDecision == 1)    //adds new animal
            {
                System.out.println("Which animal would you like to add to the zoo?"
                        + "\n 1. Lion "
                        + "\n 2. Zebra"
                        + "\n 3. Bird"
                        + "\n 4. Snake");
                //deci reads in secondary zookeeper decisions
                int deci = zookeeper.nextInt();


                if (deci == 1)  //lion
                {
                    animalzoo.add(new Lion());
                }
                else if (deci == 2) //zebra
                {
                    animalzoo.add(new Zebra());

                }
                else if (deci == 3) //bird
                {
                    animalzoo.add(new Birds());

                 }
                else if (deci == 4) //snake
                {
                    animalzoo.add(new Snakes());

                 }
            }
            else if (inDecision == 2) // feed the animals in the zoo
            {
                for (Animal i : animalzoo)
                {
                    i.feed();
                }
            }
            else if (inDecision == 3)   //display animals in the zoo
            {
                for (Animal i : animalzoo) {
                    System.out.println(i.toString());
                }
            }
        } while (inDecision != 0) ;


    }
}
