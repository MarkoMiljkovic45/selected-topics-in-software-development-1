package hr.fer.zemris.java.gui.calc.components;

import javax.swing.*;
import java.awt.*;

public class CalculatorButton extends JButton {

    public CalculatorButton() {
        this("");
    }

    public CalculatorButton(String text) {
        super(text);

        setPreferredSize(new Dimension(100, 70));
    }
}
