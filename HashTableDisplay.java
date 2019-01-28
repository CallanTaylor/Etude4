import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * HashTableDisplay is the highest level component of the GUI, it creates a new
 * JFrame and an instance of itself and adds one of each of its child classes,
 * UserPanel and DisplayPanel.
 */
public class HashTableDisplay extends JPanel {

    private HTable h;
    private UserPanel userPanel = new UserPanel();
    private DisplayPanel displayPanel = new DisplayPanel();
    public static int globalHeight;
    private static int globalWidth;

    private static int currentTableSize = 7;
    private static int maxTableSize =  17;
    private int currentActiveKey;
    private int positionOfActiveKey;
    private ArrayList<Integer> intermediateResults;

    /*
     * Current action is used for animations, 0 = no animation needed. 1 = animate
     * insertion. 2 = animate deletion. 3 = animate search.
     */
    private int currentAction;

    /**
     * The default constructor for HashTableDisplay, creates an instance of
     * HashTable and adds an instance of UserPanel and DisplayPanel.
     */
    public HashTableDisplay() {
        h = new HTable(currentTableSize);
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        add(userPanel);
        add(displayPanel);
    }

    /**
     * The entry point of the program, creates a JFrame and adds an instance of
     * HashTableDisplay as the ContentPane/
     * 
     * @param args - no command line arguments are used.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Hash Table Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HashTableDisplay());
        frame.setMinimumSize(new Dimension(900, 700));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * By calling paintComponent in class HashTableDisplay we can access the height
     * and width of the highest level component using the getHeight and getWidth
     * methods.
     * 
     * @param g - the graphics component.
     */
    public void paintComponent(Graphics g) {
        globalHeight = getHeight();
        globalWidth = getWidth();
        super.paintComponent(g);
        setBackground(Color.WHITE);
    }

    /**
     * Inner Class ButtonListener implements ActionListener to handle action events
     * such as insertions, deletions, searches plus resizing of the hashtable and
     * changing the hashing function.
     */
    private class ButtonListener implements ActionListener {

        /**
         * The method called when any component with an instance of ButtonListener
         * attatched to it is triggered. ActionPerformed finds the cause of the
         * actionevent and performs the relevent actions.
         */
        public void actionPerformed(ActionEvent e) {
            JButton buttonPressed;
            JRadioButton radioButtonPressed;
            int newKey = 0;
            String hashesTo = "";
            if (e.getSource() instanceof JButton) {
                buttonPressed = (JButton) e.getSource();

                if (buttonPressed.getText().equals("New Table")) {
                    currentTableSize = userPanel.getTableSize();
                    h = new HTable(currentTableSize);
                    if (currentTableSize != 0) {
                        userPanel.linearProbingRB.doClick();
                        displayPanel.repaint();
                    }
                }

                if (buttonPressed.getText().equals("Insert")) {
                    newKey = userPanel.getNewKey();
                    if (newKey != 0) {
                        intermediateResults = h.insert(newKey);
                        currentActiveKey = newKey;
                        currentAction = 1;
                        displayPanel.repaint();
                    }
                }

                if (buttonPressed.getText().equals("Delete")) {
                    newKey = userPanel.getDeleteKey();
                    if (newKey != 0) {
                        positionOfActiveKey = h.search(newKey);
                        h.delete(newKey);
                        currentActiveKey = newKey;
                        currentAction = 2;
                        displayPanel.repaint();
                    }
                }

                if (buttonPressed.getText().equals("Search")) {
                    currentActiveKey = userPanel.getSearchKey();
                    if (currentActiveKey != 0) {
                        positionOfActiveKey = h.search(currentActiveKey);
                        currentAction = 3;
                        displayPanel.repaint();
                        ;
                    }
                }
            }

            if (e.getSource() instanceof JRadioButton) {
                radioButtonPressed = (JRadioButton) e.getSource();
                if (radioButtonPressed.getText().equals("Linear Probing")) {
                    h.setHashingType(0);
                } else if (radioButtonPressed.getText().equals("Double Hashing")) {
                    h.setHashingType(1);
                }
            }
            repaint();
        }
    }

    /**
     * UserPanel extends JPanel to create a component for displaying infomation
     * about the HashTable to the user, and getting user input such as new keys for
     * inserting, deleting, and searching.
     */
    private class UserPanel extends JPanel {

        private JTextField tableSizeTF;
        private JTextField insertNumberTF;
        private JTextField deleteNumberTF;
        private JTextField searchNumberTF;
        private JRadioButton linearProbingRB;
        private JLabel intro;
        private JLabel hashing;
        private JLabel displayActions;
        private int panelWidth = 235;

        ButtonListener listener = new ButtonListener();

        /**
         * Default contructor for UserPanel, creates instances of UI component such as
         * JButtons, JRadioButtons, and JTextFields sets default values, names the
         * buttons and adds adds actions listeners where appropriate. Components are
         * then added to a JPanel called ButtonPanel to organise them into a grid
         * layout.
         */
        public UserPanel() {
            setBackground(Color.WHITE);
            JButton insertB = new JButton("Insert");
            JButton deleteB = new JButton("Delete");
            JButton searchB = new JButton("Search");
            deleteB.addActionListener(listener);
            deleteNumberTF = new JTextField(5);
            searchNumberTF = new JTextField(5);
            insertNumberTF = new JTextField(5);
            intro = new JLabel("<html> Hash Tables are simply a way of <br/> "
                    + " storing items such as integers so that <br/>" + " you dont have to spend alot of time<br/>"
                    + " looking for something. They do this by<br/>" + " finding a position for each item based <br/>"
                    + " on the value of the item itself.<html>");
            hashing = new JLabel("<html> Whenever you need to insert, delete, <br> "
            + " or search for, an item, the item <br> "
            + " (in this case an integer) is modified <br> "
            + " by some function called the hashing <br> "
            + " function which gives us a position in <br>"
            + " the table. If there is allready an item <br>"
            + " at that position we either increment the <br>"
            + " position by 1 (Linear Hashing) or add the <br>"
            + " value of a second hashing function. <br>"
            + " (double Hashing) <br>");
            displayActions = new JLabel();
            insertB.addActionListener(listener);
            searchB.addActionListener(listener);
            tableSizeTF = new JTextField(5);
            JButton createTableButton = new JButton("New Table");
            createTableButton.addActionListener(listener);
            JPanel buttonPanel = new JPanel();
            linearProbingRB = new JRadioButton("Linear Probing");
            linearProbingRB.doClick();
            linearProbingRB.addActionListener(listener);
            JRadioButton doubleHashingRB = new JRadioButton("Double Hashing");
            doubleHashingRB.addActionListener(listener);
            ButtonGroup hashingType = new ButtonGroup();
            hashingType.add(linearProbingRB);
            hashingType.add(doubleHashingRB);
            buttonPanel.setLayout(new GridLayout(0, 2, 5, 10));
            buttonPanel.setBackground(Color.WHITE);
            buttonPanel.add(createTableButton);
            buttonPanel.add(tableSizeTF);
            buttonPanel.add(insertB);
            buttonPanel.add(insertNumberTF);
            buttonPanel.add(deleteB);
            buttonPanel.add(deleteNumberTF);
            buttonPanel.add(searchB);
            buttonPanel.add(searchNumberTF);
            buttonPanel.add(linearProbingRB);
            buttonPanel.add(doubleHashingRB);
            setLayout(new GridLayout(0, 1, 2, 20));
            add(buttonPanel);
            intro.setForeground(Color.BLACK);
            hashing.setForeground(Color.BLACK);
            add(intro);
            add(hashing);
            add(displayActions);
        }

        /**
         * Accessor method getTableSize returns the string in the tableSize text field
         * and converts it to an integer to be used for resizing the hashtable.
         * 
         * @return size - the value in the table size text field.
         */
        public int getTableSize() {
            int size = 0;
            try {
                size = Integer.parseInt(tableSizeTF.getText());
                tableSizeTF.setText("");
            } catch (NumberFormatException e) {
                tableSizeTF.setText("");
            }
            if (size == 0) {
                System.err.println("Error: table size must be > 0");
            }
            return size;
        }

        /**
         * Accessor method for getting the new value to insert.
         * 
         * @return newKey - the new value to insert.
         */
        public int getNewKey() {
            int newKey = 0;
            try {
                newKey = Integer.parseInt(insertNumberTF.getText());
            } catch (NumberFormatException e) {
            }
            insertNumberTF.setText("");
            return newKey;
        }

        /**
         * Accessor method for the delete number text field.
         * 
         * @return deleteKey - the value to be deleted.
         */
        public int getDeleteKey() {
            int deleteKey = 0;
            try {
                deleteKey = Integer.parseInt(deleteNumberTF.getText());
            } catch (NumberFormatException e) {
            }
            deleteNumberTF.setText("");
            return deleteKey;
        }

        /**
         * Accessor method for the search key text field.
         * 
         * @return searchKey - the key to be searched for.
         */
        public int getSearchKey() {
            int searchKey = 0;
            try {
                searchKey = Integer.parseInt(searchNumberTF.getText());
            } catch (NumberFormatException e) {
            }
            searchNumberTF.setText("");
            return searchKey;
        }

        /**
         * PaintComponent sets the absolute location of the component and allows the
         * size to change dynamically by accessing the parents static datafields height
         * & width
         * 
         * @param g - the Graphics compaonent.
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            super.setLocation(10, 0);
            super.setSize(panelWidth, globalHeight);
        }
    }

    /**
     * DisplayPanel extends JPanel to create a component for displaying the
     * HashTable, it will animate and display the hashtable plus all insertions,
     * deletions, and searches.
     */
    private class DisplayPanel extends JPanel {

        private int tableLeftEdge;
        private int tableTopEdge;
        private int tableWidth;
        private int tableHeight;

        private int insertTFHeight = 53;
        private int deleteTFHeight = 85;
        private int searchTFHeight = 122;

        private int minimumCellHeight = 30;
        private int minimumCellWidth = 60;

        private int newKey;

        /**
         * Paint component is the function that is called when the display panels
         * repaint() method is called, it uses the parent classes data fields to find
         * the appropriate actions to animate and calls the respective functions.
         */
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            super.setSize(globalWidth - userPanel.panelWidth, globalHeight);
            super.setLocation(userPanel.panelWidth, 0);
            tableLeftEdge = getWidth() / 3;
            tableTopEdge = 70;
            tableHeight = globalHeight - 140;
            tableWidth = (globalWidth - userPanel.panelWidth) / 3;
            renderTable(g);
            enterValues(g);
            if (currentAction == 1) {
                animateInsertion(g);
            } else if (currentAction != 0 && positionOfActiveKey != -1) {
                animateAction(g);
            }
            currentAction = 0;
            currentActiveKey = 0;
        }

        /**
         * Render table is responsible for drawing and labelling the hashtable.
         * 
         * @param g - the graphics component.
         */
        public void renderTable(Graphics g) {
            int i;

            for (i = 0; i < currentTableSize; i++) {
                g.drawString(Integer.toString(i), tableLeftEdge - 15, tableTopEdge + (i * minimumCellHeight) + 20);
                g.drawRect(tableLeftEdge, tableTopEdge + (i * minimumCellHeight), tableWidth, minimumCellHeight);
            }
        }

        /**
         * enterValues is responsible for displaying the current values being stored in
         * the table. It does this by using HTable's getKeyValue method which returns
         * the values at each position unless they have not been set.
         * 
         * @param g - the graphics component.
         */
        public void enterValues(Graphics g) {
            int i, currentKey = 0;

            for (i = 0; i < currentTableSize; i++) {
                currentKey = h.getKeyValue(i);
                if (currentKey != 0) {
                    g.drawString(Integer.toString(currentKey), tableLeftEdge + (tableWidth / 2),
                            (i * minimumCellHeight) + tableTopEdge + 20);
                }
            }
        }

        /**
         * Responible for displaying to the user actions such as insertions, deletions,
         * searches, and collisions. It access' the mostRecentKey data field and draws
         * an arrow in the GUI using ta static datafield current action to find if we
         * want to animate an insertion, deletion or a search.
         *
         * @param g - the graphics component.
         */
        public void animateAction(Graphics g) {
            int cellHeight = tableTopEdge + 15 + (positionOfActiveKey * minimumCellHeight);
            int lineBegining = 0;
            if (currentAction == 1) {
                lineBegining = insertTFHeight;
            } else if (currentAction == 2) {
                lineBegining = deleteTFHeight;
            } else if (currentAction == 3) {
                lineBegining = searchTFHeight;
            }
            g.drawLine(0, lineBegining, 60, lineBegining);
            g.drawLine(60, lineBegining, 60, cellHeight);
            g.drawLine(60, cellHeight, tableLeftEdge - 20, cellHeight);
            g.drawLine(tableLeftEdge - 30, cellHeight - 10, tableLeftEdge - 20, cellHeight);
            g.drawLine(tableLeftEdge - 30, cellHeight + 10, tableLeftEdge - 20, cellHeight);
        }

        /**
         * animate insertions animates the insertionm of new values into the hash table,
         * this includes any collisions that may occur as a result of a new key being
         * inserted.
         * 
         * @param g - the graphics component.
         */
        public void animateInsertion(Graphics g) {

            int cellHeight;
            int lineBegining = insertTFHeight;

            for (int i = 0; i < intermediateResults.size(); i++) {
                positionOfActiveKey = intermediateResults.get(i);
                cellHeight = tableTopEdge + 15 + (positionOfActiveKey * minimumCellHeight);
                if (i == (intermediateResults.size() - 1)) {
                    g.setColor(Color.BLACK);
                    if (i == 0) {
                        g.drawString(Integer.toString(currentActiveKey) + " % " + Integer.toString(h.getSize()) + " = ",
                                65, cellHeight - 5);
                    } else {
                        if (h.usesLinearProbing()) {
                            g.drawString("+ 1 % " + h.getSize() + " = ", 65, cellHeight - 5);
                        } else {
                            g.drawString("+ (" + Integer.toString(currentActiveKey) + " X 3) + 1 % "
                                    + Integer.toString(h.getSize()) + " = ", 65, cellHeight - 5);
                        }
                    }
                    g.setColor(Color.BLACK);
                    animateAction(g);
                } else {
                    g.setColor(Color.RED);
                    if (i == 0) {
                        g.drawString(Integer.toString(currentActiveKey) + " % " + Integer.toString(h.getSize()) + " = ",
                                65, cellHeight - 5);
                    } else {
                        if (h.usesLinearProbing()) {
                            g.drawString("+ 1 % " + h.getSize() + " = ", 65, cellHeight - 5);
                        } else {
                            g.drawString("+ (" + Integer.toString(currentActiveKey) + " X 3) + 1 % "
                                    + Integer.toString(h.getSize()) + " = ", 65, cellHeight - 5);
                        }
                    }
                    animateAction(g);
                }
            }
            positionOfActiveKey = -1;
            intermediateResults.clear();
        }
    }
}
