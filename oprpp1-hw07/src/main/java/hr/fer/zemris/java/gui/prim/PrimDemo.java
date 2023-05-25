package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

public class PrimDemo extends JFrame {

    public PrimDemo() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Dvije liste s istim modelom");
        setBounds(100, 100, 500, 500);
        initGUI();
    }

    public void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        PrimListModel model = new PrimListModel();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        JPanel central = new JPanel(new GridLayout(1, 0));
        central.add(new JScrollPane(list1));
        central.add(new JScrollPane(list2));

        cp.add(central, BorderLayout.CENTER);

        JButton nextButton = new JButton("sljedeći");
        nextButton.addActionListener(e -> model.next());
        cp.add(nextButton, BorderLayout.PAGE_END);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PrimDemo().setVisible(true));
    }
}
