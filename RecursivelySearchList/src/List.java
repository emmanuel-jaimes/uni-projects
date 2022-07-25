// Fig. 21.3: List.java
// ListNode and List class declarations.

/**
 *  class to represent one node in a list
 */
class ListNode<T> {
    // package access members; List can access these directly
    /**
     * data for this node
     */
    T data;
    /**
     * reference to next node in the list
     */
    ListNode<T> nextNode;

    /**
     * @param object constructor creates a ListNode that refers to object
     */
    ListNode(T object) {
        this(object, null);
    }

    /**
     * constructor creates ListNode that refers to the specified
     * object and to the next ListNode
     */
    ListNode(T object, ListNode<T> node) {
        data = object;
        nextNode = node;
    }

    /**
     * @return reference to data in node
     */
    T getData() {
        return data;
    }

    /**
     * @return String representation of the information contained within the ListNode
     */
    public String toString()
    {
        return String.valueOf(this.getData());
    }

} // end class ListNode<T>

/**
 * @param <T> generic implementation of the LinkedList
 */
public class List<T>
{
    /**
     * reference for the first node in the linked list
     */
    private ListNode<T> firstNode;
    /**
     * reference for the last node in the linked list
     */
    private ListNode<T> lastNode;
    /**
     * reference of name given to the linked list
     */
    private String name;

    /**
     * Constructor for linked list, initializes empty list with default name
     */
    public List() {
        this("list");
    }

    /**
     * @param listName reference to the name of the linked list
     */
    public List(String listName) {
        name = listName;
        firstNode = lastNode = null;
    }

    /**
     * @return reference to the first node in the linked list
     */
    public ListNode<T> getHead()
    {
        return this.firstNode;
    }


    /**
     * @param head reference of first node to be checked in the linked list
     * @param item reference of target item to be searched for
     * @return string displaying information whether the target object was found
     */
    public String getRecursively(ListNode head, Object item)
    {
        if (head == null)
        {
            System.out.println("Object was not found :( ");
            return null;
        }
        if (head.getData() == item)
        {
            System.out.println(item + " found (: ");
            return head.getData().toString();
        }

        System.out.println("Checked: " + head);
        return getRecursively(head.nextNode, item);
    }

    /**
     * @param insertItem reference of item to be inserted at the front of the linked list
     */
    // insert item at front of List
    public void insertAtFront(T insertItem) {
        if (isEmpty()) // firstNode and lastNode refer to same object
            firstNode = lastNode = new ListNode<T>(insertItem);
        else // firstNode refers to new node
            firstNode = new ListNode<T>(insertItem, firstNode);
    }

    /**
     * @param insertItem reference for item to be inserted at the end of the linked list
     */
    // insert item at end of List
    public void insertAtBack(T insertItem) {
        if (isEmpty()) // firstNode and lastNode refer to same object
            firstNode = lastNode = new ListNode<T>(insertItem);
        else // lastNode's nextNode refers to new node
            lastNode = lastNode.nextNode = new ListNode<T>(insertItem);
    }

    /**
     * @return reference of element to be removed from front
     * @throws EmptyListException throws exception if LinkedList is called to be empty
     */
    public T removeFromFront() throws EmptyListException {
        if (isEmpty()) // throw exception if List is empty
            throw new EmptyListException(name);

        T removedItem = firstNode.data; // retrieve data being removed

        // update references firstNode and lastNode
        if (firstNode == lastNode)
            firstNode = lastNode = null;
        else
            firstNode = firstNode.nextNode;

        return removedItem; // return removed node data
    } // end method removeFromFront

    /**
     * @return reference of element that is to be removed from back
     * @throws EmptyListException throws exception in case LinkedList is empty
     */
    public T removeFromBack() throws EmptyListException {
        if (isEmpty()) // throw exception if List is empty
            throw new EmptyListException(name);

        T removedItem = lastNode.data; // retrieve data being removed

        // update references firstNode and lastNode
        if (firstNode == lastNode)
            firstNode = lastNode = null;
        else // locate new last node
        {
            ListNode<T> current = firstNode;

            // loop while current node does not refer to lastNode
            while (current.nextNode != lastNode)
                current = current.nextNode;

            lastNode = current; // current is new lastNode
            current.nextNode = null;
        }

        return removedItem; // return removed node data
    }

    /**
     * @return true whether linked list is empty, otherwise false
     */
    public boolean isEmpty() {
        return firstNode == null; // return true if list is empty
    }

    /**
     * output list contents
     */
    public void print() {
        if (isEmpty()) {
            System.out.printf("Empty %s%n", name);
            return;
        }

        System.out.printf("The %s is: ", name);
        ListNode<T> current = firstNode;

        // while not at end of list, output current node's data
        while (current != null) {
            System.out.printf("%s ", current.data);
            current = current.nextNode;
        }

        System.out.println();
    }

} // end class List<T>

/**************************************************************************
 * (C) Copyright 1992-2014 by Deitel & Associates, Inc. and               *
 * Pearson Education, Inc. All Rights Reserved.                           *
 *                                                                        *
 * DISCLAIMER: The authors and publisher of this book have used their     *
 * best efforts in preparing the book. These efforts include the          *
 * development, research, and testing of the theories and programs        *
 * to determine their effectiveness. The authors and publisher make       *
 * no warranty of any kind, expressed or implied, with regard to these    *
 * programs or to the documentation contained in these books. The authors *
 * and publisher shall not be liable in any event for incidental or       *
 * consequential damages in connection with, or arising out of, the       *
 * furnishing, performance, or use of these programs.                     *
 *************************************************************************/
