
import java.util.Scanner;

/**
 * Driver class containing main method for Recursively Searching for item in linked list
 */
public class RecursivelySearchListTest {

    /**
     * @param args initializes driver method for running and testing recursive search of item in linked list
     */
    public static void main(String[] args)
    {
        List<Integer> testList = new List<>();
        testList.insertAtFront(34);
        testList.insertAtFront(87);
        testList.insertAtFront(12);
        testList.insertAtFront(45);
        testList.insertAtFront(4);
        testList.insertAtFront(56);

        System.out.println("Linked List contains: \n\t\t");
        testList.print();

        System.out.println("Enter integer to search for: ");
        Scanner input = new Scanner(System.in);
        int item = input.nextInt();

        testList.getRecursively(testList.getHead(), item);
        System.out.println("Fin");
    }
}
