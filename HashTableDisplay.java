import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class HashTableDisplay extends JPanel {

    private UserPanel userPanel = new UserPanel();
    private DisplayPanel displayPanel = new DisplayPanel();
    private Color displayBackground = new Color(218, 227, 242);

    public HashTableDisplay() {
        add(userPanel);
        add(displayPanel);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Hash Table Display");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new HashTableDisplay());
        frame.setSize(400, 400);
        frame.pack();
        frame.setVisible(true);
    }

    private class UserPanel extends JPanel {

        public UserPanel() {
            JTextField insertNumberTF = new JTextField(10);
            JButton insertB = new JButton("Insert");
            add(insertNumberTF);
            add(insertB);
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
        }
    }


    private class DisplayPanel extends JPanel {

        int xDimension = 300;
        int yDimension = 300;
        int tableHieght = 20;
        int tableLength = 100;

        public DisplayPanel() {
            setPreferredSize(new Dimension(xDimension, yDimension));
        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(displayBackground);
            renderTable(g);
        }

        public void renderTable(Graphics g) {
            
            g.drawLine(xDimension/2 - tableLength, tableHieght,
                       xDimension/2 - tableLength, tableHieght);
        }
    }
}
