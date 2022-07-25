/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: A01_BaseChange
 * Class: BaseChangeDriver
 */

import java.util.Scanner;

/**
 * BaseChangeDriver class handles BaseChange objects
 * and initializes them for users use
 */
public class BaseChangeDriver {
    /**
     * @param args driver for BaseChange class
     *             initializers BaseChange objects
     *             and allows user input
     */
    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        String input;
        int base, targetBase;
        System.out.println("Enter number");
        input = in.nextLine();

        System.out.println("Enter the given numbers base");
        base = in.nextInt();

        System.out.println("Enter the target base for the given number");
        targetBase = in.nextInt();

        BaseChange target = new BaseChange(input, base, targetBase);
        System.out.print("Number " + input + " from base " + base + " to base " + targetBase + " is ");
        System.out.println(target.convert());
    }
}