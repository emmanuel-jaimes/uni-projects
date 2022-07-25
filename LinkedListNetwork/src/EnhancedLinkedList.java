
import java.util.NoSuchElementException;

/**
 * @param <E> generic implementation of ListNode to be contained within a LinkedList implementation
 */
class ListNode<E> {
    /**
     * generic object reference representing information for creation of ListNode
     */
    E data;
    /**
     * reference of the ListNode to follow corresponding initialization of ListNode
     */
    ListNode<E> nextNode;

    /**
     * @param object reference of element for current ListNode to contain
     */
    ListNode(E object) {
        this(object, null);
    }

    /**
     * @param object reference of element for current ListNode to contain
     * @param node reference of ListNode following current ListNode
     */
    ListNode(E object, ListNode<E> node) {
        data = object;
        nextNode = node;
    }

}

/**
 * @param <E> generic class implementation of a linked list containing ListNodes
 */
    class List<E>
    {
        private ListNode<E> firstNode;
        private ListNode<E> lastNode;
        private final String name;
        private int size;

        /**
         * default constructor initializing name of list and setting size to 0
         */
        public List()
        {
            this("list");
            size = 0;
        }

        /**
         * @param listname reference for name of the list
         *  sets the first and last nodes of the list to be null
         */
        public List(String listname)
        {
            name = listname;
            firstNode = lastNode = null;
        }

        /**
         * @param insertItem reference of element to be inserted into front of array
         */
        public void insertAtFront(E insertItem)
        {
            if (isEmpty())
            {
                firstNode = lastNode = new ListNode<>(insertItem);
            }
            else
            {
                firstNode = new ListNode<E>(insertItem, firstNode);
            }

            this.size++;
        }

        /**
         * @param insertItem reference of element to be inserted into end of array
         */
        public void insertAtBack(E insertItem)
        {
            if (isEmpty())
            {
                firstNode = lastNode = new ListNode<>(insertItem);
            }
            else
            {
                lastNode = lastNode.nextNode = new ListNode<>(insertItem);
            }
            this.size++;
        }

        /**
         * @param insertItem element to be inserted in linked list
         * @param index reference of position for element to be inserted
         */
        public void insertAt(E insertItem, int index)
        {
            if (index == 0)
            {
                insertAtFront(insertItem);
            }
            else if (index == this.size -1)
            {
                insertAtBack(insertItem);
            }
            else
            {
                ListNode<E> current = firstNode;
                int i = 0;
                while (i+1 != index)
                {
                    current = current.nextNode;
                    i++;
                }
                ListNode<E> newNode = new ListNode<E>(insertItem, current.nextNode);
                current.nextNode = newNode;
            }
            this.size++;
        }

        /**
         * @return reference of element to be removed
         * @throws NoSuchElementException if no such element exists
         */
        public E removeFromFront() throws NoSuchElementException
        {
            if (isEmpty())
            {
                throw new NoSuchElementException(name + " is empty");
            }
            E removedItem = firstNode.data;

            if (firstNode == lastNode)
            {
                firstNode = lastNode = null;
            }
            else
            {
               firstNode = firstNode.nextNode;
            }

            this.size--;
            return removedItem;
        }

        /**
         * @return reference of element that is to be removed
         * @throws NoSuchElementException if no such element exists
         */
        public E removeFromBack() throws NoSuchElementException
        {
            if (isEmpty())
            {
                return null;
            }

            E removedItem = lastNode.data;
            if (firstNode == lastNode) //size == 1
            {
                firstNode = lastNode = null;
            }
            else
            {
                ListNode<E> current = firstNode;
                while (current.nextNode != lastNode)
                {
                    current = current.nextNode;
                }
                lastNode = current;
                current.nextNode = null;
            }

            this.size--;
            return removedItem;
        }

        /**
         * @param index int reference representing location for object to be removed
         * @return the element of the removed listNode
         * @throws NoSuchElementException throws exception if no such element exist in the list
         */
        public E removeAt(int index) throws NoSuchElementException
        {
            E removedItem;
            if (isEmpty())
            {
                throw new NoSuchElementException(name + " is empty");
            }
            else
            {
                if (index == 0)
                {
                    removeFromFront();
                    removedItem = firstNode.data;
                }
                else if (index == this.size -1)
                {
                    removeFromBack();
                    removedItem = lastNode.data;
                }
                else
                {
                    int i = 0;
                    ListNode<E> current = firstNode;
                    ListNode<E> prev = null;
                    while (i != index)
                    {
                        prev = current;
                        current = current.nextNode;
                        i++;
                    }
                    removedItem = current.data;

                    prev.nextNode = current.nextNode;
                    current = prev;
                }
            }

            this.size--;
            return removedItem;
        }

        /**
         * @return true if linked list contains no elements, otherwise false
         */
        public boolean isEmpty()
        {
            return firstNode == null;
        }

        /**
         * @return size of the linked list
         */
        public int getSize()
        {
            return this.size;
        }

        /**
         * @return String representing information contained by the LinkedList object
         */
        public String toString()
        {
            if (isEmpty())
            {
                return ("Empty list");
            }

            StringBuilder str = new StringBuilder();
            ListNode<E> current = firstNode;
            while (current != null)
            {
                str.append(" ").append(current.data);
                current = current.nextNode;
            }

            return str.toString();
        }
    }
