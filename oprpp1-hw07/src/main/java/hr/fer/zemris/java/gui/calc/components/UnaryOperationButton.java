package hr.fer.zemris.java.gui.calc.components;

import hr.fer.zemris.java.gui.calc.model.CalcModel;

import java.util.function.DoubleUnaryOperator;

public class UnaryOperationButton extends CalculatorButton implements InvertibleButton {

    private final DoubleUnaryOperator operation;
    private final String operationName;
    private final DoubleUnaryOperator inverseOperation;
    private final String inverseOperationName;
    private DoubleUnaryOperator activeOperation;
    private boolean isInverted;

    private UnaryOperationButton(CalcModel model, DoubleUnaryOperator operation, String operationName, DoubleUnaryOperator inverseOperation, String inverseOperationName, boolean isInverted) {
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

            double result = activeOperation.applyAsDouble(model.getValue());
            model.setValue(result);
            model.clearActiveOperand();
            model.setPendingBinaryOperation(null);
        });
    }

    public UnaryOperationButton(CalcModel model, DoubleUnaryOperator operation, String operationName, DoubleUnaryOperator inverseOperation, String inverseOperationName) {
        this(model, operation, operationName, inverseOperation, inverseOperationName, false);
    }

    public UnaryOperationButton(CalcModel model, DoubleUnaryOperator operation, String operationName) {
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
