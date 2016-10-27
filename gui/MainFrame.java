package gui;
import javax.swing.JInternalFrame;
import javax.swing.JDesktopPane;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JFrame;
import javax.swing.KeyStroke;

import java.awt.event.*;
import java.awt.*;

import logic.BacaFile;
import logic.Trie;

public class MainFrame extends JFrame implements ActionListener {
    JDesktopPane desktop;

    public MainFrame() {
        super("Main Frame");

        //Make the big window be indented 50 pixels from each edge
        //of the screen.
        int inset = 50;
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(inset, inset,
                  screenSize.width  - inset*2,
                  screenSize.height - inset*2);

        //Set up the GUI.
        desktop = new JDesktopPane(); //a specialized layered pane
        createFrame(); //create first "window"
        setContentPane(desktop);
        setJMenuBar(createMenuBar());

        //Make dragging a little faster but perhaps uglier.
        desktop.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);
    }

    protected JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        //Set up the lone menu.
        JMenu menu = new JMenu("Program");
        menu.setMnemonic(KeyEvent.VK_P);
        menuBar.add(menu);

        //Set up the lone menu.
        JMenu menuData = new JMenu("Data");
        menuData.setMnemonic(KeyEvent.VK_D);
        menuBar.add(menuData);

        //Set up the first menu item.
        JMenuItem menuItem = new JMenuItem("New");
        menuItem.setMnemonic(KeyEvent.VK_N);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_N, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("new");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the second menu item.
        menuItem = new JMenuItem("Quit");
        menuItem.setMnemonic(KeyEvent.VK_Q);
        menuItem.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_Q, ActionEvent.ALT_MASK));
        menuItem.setActionCommand("quit");
        menuItem.addActionListener(this);
        menu.add(menuItem);

        //Set up the third menu item.
        JMenuItem menuItemWord = new JMenuItem("Load Word");
        menuItemWord.setMnemonic(KeyEvent.VK_W);
        menuItemWord.setAccelerator(KeyStroke.getKeyStroke(
                KeyEvent.VK_W, ActionEvent.ALT_MASK));
        menuItemWord.setActionCommand("word");
        menuItemWord.addActionListener(this);
        menuData.add(menuItemWord);
        return menuBar;
    }

    //React to menu selections.
    public void actionPerformed(ActionEvent e) {
        if ("new".equals(e.getActionCommand())) { //new
            createFrame();
        } else { //quit
            quit();
        }
    }

    //Create a new internal frame.
    protected void createFrame() {
        System.out.println("InternalFrameCreated");

        Trie trie = new Trie();

        BacaFile bacaFile = new BacaFile("Kamus.txt");
        trie = bacaFile.bacaFile(trie);

        System.out.println("Mencari sebuah kata :");
        trie.searchWord("Z>ARIS");
        trie.searchWord("Z>ARISS");

        FrameCoba frame = new FrameCoba(trie);
        frame.setVisible(true); //necessary as of 1.3
        Board[][] arrBoard = frame.getArrBoard();
        arrBoard[7][7].setValue("A");
        arrBoard[8][7].setValue("B");
        arrBoard[9][7].setValue("L");
        arrBoard[10][7].setValue("E");

        Board[] arrRack = frame.getRackBoard();
        arrRack[0].setValue("C");
        arrRack[1].setValue("A");
        arrRack[2].setValue("R");
        arrRack[3].setValue("E");
        desktop.add(frame);
        try {
            frame.setSelected(true);
        } catch (java.beans.PropertyVetoException e) {}
    }

    //Quit the application.
    protected void quit() {
        System.exit(0);
    }

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public static void createAndShowGUI() {
        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        //Create and set up the window.
        MainFrame frame = new MainFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Display the window.
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
