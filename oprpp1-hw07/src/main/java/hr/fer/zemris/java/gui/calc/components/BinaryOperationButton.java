package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import java.util.function.DoubleBinaryOperator;

public class BinaryOperationButton extends CalculatorButton implements InvertibleButton {

    private final DoubleBinaryOperator operation;
    private final String operationName;
    private final DoubleBinaryOperator inverseOperation;
    private final String inverseOperationName;
    private DoubleBinaryOperator activeOperation;
    private boolean isInverted;

    private BinaryOperationButton(CalcModel model, DoubleBinaryOperator operation, String operationName, DoubleBinaryOperator inverseOperation, String inverseOperationName, boolean isInverted) {
        super(operationName);
        this.operation = operation;
        this.operationName = operationName;
        this.inverseOperation = inverseOperation;
        this.inverseOperationName = inverseOperationName;
        this.isInverted = isInverted;

        setActiveOperation();

        addActionListener(e -> {
            if (model.getPendingBinaryOperation() != null) {
                double result = model.getPendingBinaryOperation().applyAsDouble(model.getActiveOperand(), model.getValue());
                model.setValue(result);
                model.clearActiveOperand();
            }

            model.setActiveOperand(model.getValue());
            model.setPendingBinaryOperation(activeOperation);
            model.clear();
        });
    }

    public BinaryOperationButton(CalcModel model, DoubleBinaryOperator operation, String operationName, DoubleBinaryOperator inverseOperation, String inverseOperationName) {
        this(model, operation, operationName, inverseOperation, inverseOperationName, false);
    }

    public BinaryOperationButton(CalcModel model, DoubleBinaryOperator operation, String operationName) {
        this(model, operation, operationName, null, null, false);
    }

    @Override
    public void invert() {
        isInverted = !isInverted;

        setActiveOperation();

        if (isInverted && inverseOperation != null)
            setText(inverseOperationName);
        else
            setText(operationName);
    }

    public void setActiveOperation() {
        if (isInverted && inverseOperation != null)
            activeOperation = inverseOperation;
        else
            activeOperation = operation;
    }
}
