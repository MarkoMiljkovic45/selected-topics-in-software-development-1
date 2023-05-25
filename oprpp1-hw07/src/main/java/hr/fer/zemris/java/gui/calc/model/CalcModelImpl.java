package hr.fer.zemris.java.gui.calc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleBinaryOperator;

/**
 * An implementation of the CalcModel interface
 */
public class CalcModelImpl implements CalcModel {

    /**
     * Flag that tell weather the currently displayed expression is editable
     * <p>
     * An example of editing is adding a digit, adding a decimal dot, change the number sign
     */
    private boolean isEditable;
    /**
     * True if the currently displayed number is negative
     */
    private boolean isNegative;
    /**
     * String containing the inputted digits
     */
    private String digits;
    /**
     * Numeric representation of digits
     */
    private double value;
    /**
     * The displayed value
     */
    private String frozenValue;
    /**
     * Operand to be used in pendingOperation
     */
    private double activeOperand;
    /**
     * True if activeOperand is set, false otherwise
     */
    private boolean isActiveOperandSet;
    /**
     * Operation that will be executed next
     */
    private DoubleBinaryOperator pendingOperation;
    /**
     * Listeners subscribed to this model
     */
    private final List<CalcValueListener> listeners;

    /**
     * The most generic constructor
     */
    public CalcModelImpl(boolean isEditable, boolean isNegative, String digits, double value, String frozenValue, double activeOperand, boolean isActiveOperandSet, DoubleBinaryOperator pendingOperation, List<CalcValueListener> listeners) {
        this.isEditable = isEditable;
        this.isNegative = isNegative;
        this.digits = digits;
        this.value = value;
        this.frozenValue = frozenValue;
        this.activeOperand = activeOperand;
        this.isActiveOperandSet = isActiveOperandSet;
        this.pendingOperation = pendingOperation;
        this.listeners = listeners;
    }

    /**
     * The default constructor
     */
    public CalcModelImpl() {
        this(true, false, "", 0, null, 0, false, null, new ArrayList<>());
    }

    @Override
    public void addCalcValueListener(CalcValueListener l) {
        if (l == null)
            throw new NullPointerException("Listener can't be null");

        listeners.add(l);
    }

    @Override
    public void removeCalcValueListener(CalcValueListener l) {
        if (l == null)
            throw new NullPointerException("Listener can't be null");

        listeners.remove(l);
    }

    @Override
    public double getValue() {
        return value;
    }

    @Override
    public void setValue(double value) {
        this.value = value;
        frozenValue = Double.toString(value);
        isEditable = false;
        listeners.forEach(listener -> listener.valueChanged(this));
    }

    @Override
    public boolean isEditable() {
        return isEditable;
    }

    @Override
    public void clear() {
        digits = "";
        value = 0;
        isEditable = true;
        frozenValue = null;
        listeners.forEach(listener -> listener.valueChanged(this));
    }

    @Override
    public void clearAll() {
        clear();
        clearActiveOperand();
        pendingOperation = null;
    }

    @Override
    public void swapSign() throws CalculatorInputException {
        if (!isEditable)
            throw new CalculatorInputException("The current value is not editable.");

        isNegative = !isNegative;
        value = value * -1;
        listeners.forEach(listener -> listener.valueChanged(this));
    }

    @Override
    public void insertDecimalPoint() throws CalculatorInputException {
        if (!isEditable)
            throw new CalculatorInputException("The current value is not editable.");

        if (digits.equals(""))
            throw new CalculatorInputException("No value set.");

        if (digits.contains("."))
            throw new CalculatorInputException("The expression already contains a decimal point.");

        digits = digits + ".";
    }

    @Override
    public void insertDigit(int digit) throws CalculatorInputException, IllegalArgumentException {
        if (digit < 0 || digit > 9)
            throw new IllegalArgumentException("Invalid digit: " + digit);

        if (!isEditable)
            throw new CalculatorInputException("The current value is not editable.");

        String newDigits;
        double newValue;

        try {
            if (digits.equals("0"))
                newDigits = Integer.toString(digit);
            else
                newDigits = digits + digit;

            newValue = Double.parseDouble(newDigits);
        } catch (Exception e) {
            throw new CalculatorInputException("The digit " + digit + " can't be added to current value.");
        }

        if (!newDigits.equals("00"))                //Silently ignoring adding 0 to 0
            digits = newDigits;

        if (newValue == Double.POSITIVE_INFINITY || newValue == Double.NEGATIVE_INFINITY)
            throw new CalculatorInputException("Number too large.");

        if (isNegative)
            value = newValue * -1;
        else
            value = newValue;
        listeners.forEach(listener -> listener.valueChanged(this));
    }

    @Override
    public boolean isActiveOperandSet() {
        return isActiveOperandSet;
    }

    @Override
    public double getActiveOperand() throws IllegalStateException {
        if (!isActiveOperandSet())
            throw new IllegalStateException("Active operand is not set.");

        return activeOperand;
    }

    @Override
    public void setActiveOperand(double activeOperand) {
        this.activeOperand = activeOperand;
        isActiveOperandSet = true;
    }

    @Override
    public void clearActiveOperand() {
        isActiveOperandSet = false;
    }

    @Override
    public DoubleBinaryOperator getPendingBinaryOperation() {
        return pendingOperation;
    }

    @Override
    public void setPendingBinaryOperation(DoubleBinaryOperator op) {
        pendingOperation = op;
    }

    @Override
    public String toString() {
        if (frozenValue != null)
            return frozenValue;
        else
            return getDigits();
    }

    private String getDigits() {
        String s;

        if (digits.equals(""))
            s = "0";
        else
            s = digits;

        if (isNegative)
            s = "-" + s;

        return s;
    }
}
