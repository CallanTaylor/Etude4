import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class HashPanel extends JPanel {
    
    private HashTable h;
    private JPanel controlPanel;
    private HashingPanel hashingPanel = new HashingPanel();
    private DetailsPanel detailsPanel = new DetailsPanel();
    //private Random rand = new Random();
    private JButton []buttons = {new JButton("Insert Element"),
                                 new JButton("Print table")};
    private JRadioButton []radioButtons = {new JRadioButton("Linear Probing"),
                                           new JRadioButton("Double Hashing"),
                                           new JRadioButton("Quadratic Probing")};
    private int eleIndex = 0;

    
    public HashPanel() {
        h = new HashTable();
        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(200, 500));
        controlPanel.setBackground(Color.blue);
        ButtonListener listner = new ButtonListener();
        for(JButton b: buttons) {
            b.addActionListener(listner);
            controlPanel.add(b);
        }
        
        ButtonGroup hashingGroup = new ButtonGroup();
        for (JRadioButton rb: radioButtons) {
            hashingGroup.add(rb);
            rb.addActionListener(listner);
            controlPanel.add(rb);
            if (rb.getText() == "Linear Probing") {
                rb.setSelected(true);
            }
        }
        
        add(hashingPanel);
	add(detailsPanel);
        add(controlPanel);
    }
    
    public static void main(String [] args){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HashPanel());
        frame.pack();
        frame.setVisible(true);
    }
    
    private class ButtonListener implements ActionListener {
    
        public void actionPerformed (ActionEvent e) {
            JButton button;
            JRadioButton rbutton;
            if (e.getSource() instanceof JButton) {
                button = (JButton) e.getSource();
                if (button.getText().equals("Insert Element")) {
                    if (h.numKeys != h.size){
                        h.insert(h.elements[eleIndex++]);        
                    } else {
                        System.out.println("Finished");
                    }     
                } else if (button.getText().equals("Print table")) {
                    h.print();
                }
            } else {
                rbutton = (JRadioButton) e.getSource();
                h.clear();
                eleIndex = 0;
                if (rbutton.getText().equals("Linear Probing")) {
                    h.linearProbing();
                } else if (rbutton.getText().equals("Double Hashing")) {
                    h.doubleHashing();
                }else if (rbutton.getText().equals("Quadratic Probing")) {
                    h.quadraticProbing();
                }
            } 
            repaint();
        }
    }

    
    private class HashingPanel extends JPanel {
        private int xDimension = 500;
        private int yDimension = 300; 
        int dimension = 500;
        int buffer = 40;
        int eleBuffer = 60;
        int lineLength = 100;

        
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            drawTable(g);
            insert(g);
        }

        public void insert(Graphics g) {
            for (int i = 0; i < h.size; i++) {
                if (h.keys[i] != 0) {
                    g.drawString(Integer.toString(h.keys[i]),
                                 dimension/2, eleBuffer+(i*buffer));
                }
            }
        }

        public void drawTable(Graphics g) {
            g.drawLine(dimension/2-lineLength, buffer,
                       dimension/2+lineLength, buffer);
            
            for(int i = 0; i < h.size; i++) {
                buffer += 40;
                g.drawLine(dimension/2-lineLength, buffer,
                           dimension/2+lineLength, buffer);
                           
            }
            buffer = 40;
            g.drawLine(dimension/2-lineLength, buffer, dimension/2-lineLength,
                       buffer + (40*h.size));
            g.drawLine(dimension/2+lineLength, buffer, dimension/2+lineLength,
                       buffer + (40*h.size));
        }

        
        public HashingPanel(){
            setPreferredSize(new Dimension(dimension, dimension));
        }
    }

    /*
     * Can just put some details about what is going on e.g. the equation of
     * the current hashing function,
     * what the hashing function is doing, just info to help the user other
     * then the gui.
     **/
    private class DetailsPanel extends JPanel {

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            updatePanel(g);
        }

        public void updatePanel(Graphics g) {
            g.drawString("Current Hashing Function:", 10, 30);
            g.drawString(h.getHashingEquation(), 10, 60);
            g.drawString("Inserting:", 10, 90);
            g.drawString(Integer.toString(h.elements[eleIndex]), 10, 120);
        }
        
	public DetailsPanel() {
	    setPreferredSize(new Dimension(200, 500));
	    setBackground(Color.green);
	}
    }
}
