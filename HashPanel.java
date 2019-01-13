import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;

public class HashPanel extends JPanel {
    
    private HashTable h;
    private JPanel controlPanel;
    private HashingPanel hashingPanel = new HashingPanel();
    private DetailsPanel detailsPanel = new DetailsPanel();
    private ButtonGroup hashingType = new ButtonGroup();
    private int eleIndex = 0;

    
    /**
     * Default constructor for HashPanel, creates an instance of HashTable,
     *
     *
     */
    public HashPanel() {
        h = new HashTable();
        add(hashingPanel);
	add(detailsPanel);
    }
    
    public static void main(String [] args){
        JFrame frame = new JFrame("HashTable Visualization");
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
        int dimension = 500;
        int buffer = 40;
        int eleBuffer = 60;
        int lineLength = 100;

        public HashingPanel(){
            setPreferredSize(new Dimension(dimension, dimension));
        }

        
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
            System.out.println("Drawing Table");
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
    }

    
    /*
     * Can just put some details about what is going on e.g. the equation of
     * the current hashing function,
     * what the hashing function is doing, just info to help the user other
     * then the gui.
     **/
    private class DetailsPanel extends JPanel {

        ButtonListener listener = new ButtonListener();
        JButton []buttons = {new JButton("Insert Element"),
                             new JButton("Print table")};
        JRadioButton linearH = new JRadioButton("Linear Probing");
        JRadioButton doubleH = new  JRadioButton("Double Hashing");
        JRadioButton quadraticH =  new JRadioButton("Quadratic Probing");

        
	public DetailsPanel() {
	    setPreferredSize(new Dimension(200, 500));
	}

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            updatePanel(g);
        }
        
        public void updatePanel(Graphics g) {
            System.out.println("Updating");
            g.drawString("Current Hashing Function:", 10, getHeight() - 120);
            g.drawString(h.getHashingEquation(), 10, getHeight() - 90);
            g.drawString("Inserting:", 10, getHeight() - 60);
            g.drawString(Integer.toString(h.elements[eleIndex]),
                         10, getHeight() - 30);

            /*
            for (JButton b: buttons) {
                b.addActionListener(listener);
                add(b);
            }
            */

            linearH.addActionListener(listener);
            hashingType.add(linearH);
            doubleH.addActionListener(listener);
            hashingType.add(doubleH);
            quadraticH.addActionListener(listener);
            hashingType.add(quadraticH);
        }
    }
}
