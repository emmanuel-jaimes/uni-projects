/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B04_AnimalZoo
 * Class: Animal
 */


/**
 * Animal class that defines methods of Animals later to be implemented
 */
public abstract class Animal {

    private boolean fed = false;
    private String name = "";
    private int age;

    /**
     * @param Name String element representing
     *             given name for animal object
     */
    public void setName(String Name)
    {
        name = Name;
    }

    /**
     * @return String object representing
     *          given animal objects name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param Age int element representing
     *             given animals age
     */
    public void setAge(int Age)
    {
        age = Age;
    }

    /**
     * @return int object representing animals age
     */
    public int getAge()
    {
        return age;
    }

    /**
     * @return String object representing animals diet
     *  to be implemented in AnimalTypeClass
     */
    public abstract String diet();

    /**
     * @return String element representing animals classification
     * to be implemented in AnimalType class
     */
    public abstract String Classification();

    /**
     * @return String object representing animal classification type
     * to be implemented in AnimalType Class
     */
    public abstract String Type();

    /**
     * @return String object representing animal objects sound
     * to be implement in AnimalTypeClass
     */
    public abstract String sound();

    /**
     * @return int object representing amount of legs of animal
     * to be implemented in AnimalType Class
     */
    public abstract int legs();

    /**
     * method for displaying animals actions after being fed
     */
    public void feed() {
        fed = true;
        System.out.println("Mmmmm, thank you for feeding me " + this.sound());
    }

    /**
     * @return fed boolean object representing whether animal object
     * has been fed or not
     */
    public boolean isFed() {
        return fed;
    }

    /**
     * @return String representing common characteristics of animal objects
     */
    public String toString()
    {
        return "\n" + "Animal Classification: " + this.Classification() + "\n"
                + "Name: " + this.getName() + "\n"
                + "Type: " + this.Type() + "\n"
                + "Diet: " + this.diet() + "\n"
                + "Age: " + this.getAge() + "\n"
                + "Sound: " + this.sound() + "\n"
                + "Leg Count: " + this.legs() + "\n"
                + "Has eaten? " + this.isFed() + "\n";
    }

}
