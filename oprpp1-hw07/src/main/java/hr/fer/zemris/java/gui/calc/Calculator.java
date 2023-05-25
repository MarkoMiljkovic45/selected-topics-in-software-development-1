package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.components.*;
import hr.fer.zemris.java.gui.calc.model.CalcModelImpl;
import hr.fer.zemris.java.gui.calc.model.CalculatorInputException;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.Stack;

/**
 * A simple calculator application
 */
public class Calculator extends JFrame {

    private final CalcModelImpl model;
    private final Stack<Double> stack;

    public Calculator() {
        this.model = new CalcModelImpl();
        this.stack = new Stack<>();

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Java Calculator v1.0");
        initGUI();
        pack();
    }


    private void initGUI() {
        getContentPane().setLayout(new CalcLayout(5));

        initDisplayLabel();
        initDigits();
        initUnaryOperations();
        initBinaryOperations();
        initActionButtons();
        initInvertCheckBox();
    }

    private void initDisplayLabel() {
        Container cp = getContentPane();

        JLabel displayLabel = new JLabel(model.toString());
        displayLabel.setBackground(Color.YELLOW);
        displayLabel.setOpaque(true);
        displayLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        displayLabel.setFont(displayLabel.getFont().deriveFont(30f));
        displayLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        model.addCalcValueListener(m -> displayLabel.setText(m.toString()));

        cp.add(displayLabel, new RCPosition(1, 1));
    }

    private void initInvertCheckBox() {
        Container cp = getContentPane();

        JCheckBox invCheckBox = new JCheckBox("Inv");

        invCheckBox.addItemListener(e -> {
            for (Component component: cp.getComponents()) {
                if (component instanceof InvertibleButton)
                    ((InvertibleButton) component).invert();
            }
        });

        cp.add(invCheckBox, new RCPosition(5, 7));
    }

    private void initDigits() {
        Container cp = getContentPane();

        CalculatorButton zeroButton = new CalculatorButton("0");
        zeroButton.setFont(zeroButton.getFont().deriveFont(30f));
        zeroButton.addActionListener(e -> {
            try {
                model.insertDigit(0);
            } catch (CalculatorInputException exception) {
                showDialog(exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cp.add(zeroButton, new RCPosition(5, 3));

        for (int i = 0; i < 9; i++) {
            int rowOffset = i / 3;
            int colOffset = i % 3;

            int digit = i + 1;

            CalculatorButton digitButton = new CalculatorButton(Integer.toString(digit));
            digitButton.setFont(digitButton.getFont().deriveFont(30f));
            digitButton.addActionListener(e -> {
                try {
                    model.insertDigit(digit);
                } catch (CalculatorInputException exception) {
                    showDialog(exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            cp.add(digitButton, new RCPosition(4 - rowOffset, 3 + colOffset));
        }
    }

    private void initUnaryOperations() {
        Container cp = getContentPane();

        cp.add(new UnaryOperationButton(model, n -> 1 / n, "1/x"), new RCPosition(2, 1));
        cp.add(new UnaryOperationButton(model, Math::log10, "log", x -> Math.pow(10, x), "10^x"), new RCPosition(3, 1));
        cp.add(new UnaryOperationButton(model, Math::log, "ln", Math::exp, "e^x"), new RCPosition(4, 1));

        cp.add(new UnaryOperationButton(model, Math::sin, "sin", Math::asin, "arcsin"), new RCPosition(2, 2));
        cp.add(new UnaryOperationButton(model, Math::cos, "cos", Math::acos, "arccos"), new RCPosition(3, 2));
        cp.add(new UnaryOperationButton(model, Math::tan, "tan", Math::atan, "arctan"), new RCPosition(4, 2));
        cp.add(new UnaryOperationButton(model, x -> 1 / Math.tan(x), "ctg", x -> 1 / Math.atan(x), "arcctg"), new RCPosition(5, 2));
    }

    private void initBinaryOperations() {
        Container cp = getContentPane();

        cp.add(new BinaryOperationButton(model, Math::pow, "x^n", (x,n) -> Math.pow(x, 1 / n), "x^(1/n)"), new RCPosition(5, 1));
        cp.add(new BinaryOperationButton(model, (a, b) -> a / b, "/"), new RCPosition(2, 6));
        cp.add(new BinaryOperationButton(model, (a, b) -> a * b, "*"), new RCPosition(3, 6));
        cp.add(new BinaryOperationButton(model, (a, b) -> a - b, "-"), new RCPosition(4, 6));
        cp.add(new BinaryOperationButton(model, Double::sum, "+"), new RCPosition(5, 6));
    }

    public void initActionButtons() {
        Container cp = getContentPane();

        CalculatorButton swapSignsButton = new CalculatorButton("+/-");
        swapSignsButton.addActionListener(e -> {
            try {
                model.swapSign();
            } catch (CalculatorInputException exception) {
                showDialog(exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        cp.add(swapSignsButton, new RCPosition(5, 4));

        CalculatorButton insertDecimalDotButton = new CalculatorButton(".");
        insertDecimalDotButton.addActionListener(e -> {
            try {
                model.insertDecimalPoint();
            } catch (CalculatorInputException exception) {
                showDialog(exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        cp.add(insertDecimalDotButton, new RCPosition(5, 5));

        CalculatorButton clearButton = new CalculatorButton("clr");
        clearButton.addActionListener(e -> model.clear());
        cp.add(clearButton, new RCPosition(1, 7));

        CalculatorButton clearAllButton = new CalculatorButton("reset");
        clearAllButton.addActionListener(e -> model.clearAll());
        cp.add(clearAllButton, new RCPosition(2, 7));

        CalculatorButton stackPushButton = new CalculatorButton("push");
        stackPushButton.addActionListener(e -> stack.push(model.getValue()));
        cp.add(stackPushButton, new RCPosition(3, 7));

        CalculatorButton stackPopButton = new CalculatorButton("pop");
        stackPopButton.addActionListener(e -> {
            if (stack.empty())
                showDialog("Stog je prazan!", "Upozorenje o praznom stogu", JOptionPane.WARNING_MESSAGE);
            else
                model.setValue(stack.pop());
        });
        cp.add(stackPopButton, new RCPosition(4, 7));

        CalculatorButton evaluateButton = new CalculatorButton("=");
        evaluateButton.addActionListener(e -> {
            if(model.getPendingBinaryOperation() != null) {
                double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
                model.setValue(result);
                model.clearActiveOperand();
                model.setPendingBinaryOperation(null);
            }
        });
        cp.add(evaluateButton, new RCPosition(1, 6));
    }

    private void showDialog(String message, String title, int type) {
        JOptionPane.showMessageDialog(this, message, title, type);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Calculator().setVisible(true));
    }

}
