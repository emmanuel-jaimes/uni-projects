/**
 * Name: Emmanuel Jaimes
 * Section: SWD 3330
 * Project: B03_BaseChangeGUI
 * Class: BaseChangeGUITest
 */

import javax.swing.JFrame;

/**
 * BaseChangeGUITest class handles BaseChangeGUI objects
 * and initializes them for users to use.
 */
public class BaseChangeGUITest extends JFrame
{
    /**
     * @param args driver for the BaseChangeGUI class
     *             initializes GUI for BaseChangeGUI
     */
    public static void main (String[] args)
    {
        BaseChangeGUI panel = new BaseChangeGUI();
        panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setSize(1000, 650);
        panel.setVisible(true);
    }
}
