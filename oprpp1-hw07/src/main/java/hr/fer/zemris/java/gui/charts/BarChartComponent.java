package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.util.Comparator;

public class BarChartComponent extends JComponent {

    private final BarChart model;
    private final Point xDescriptionLocation;
    private final Point yDescriptionLocation;
    private final Rectangle chartArea;
    private final int numOfYValues;
    private final int numOfXValues;
    private int yBase;
    private int yGap;
    private int xBase;
    private int xGap;

    private final static int GAP = 10;

    public BarChartComponent(BarChart model) {
        this.model = model;
        this.model.values().sort(Comparator.comparingInt(XYValue::x));

        xDescriptionLocation = new Point();
        yDescriptionLocation = new Point();
        chartArea = new Rectangle();

        int yRange = model.yMax() - model.yMin();
        int yResolution = model.yResolution();

        if (yRange % yResolution != 0) {
            numOfYValues = yRange / yResolution + 1;
        } else {
            numOfYValues = yRange / yResolution;
        }

        numOfXValues = model.values().size();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        calculateDescriptionPositions(g2);
        drawDescriptions(g2);

        calculateChartArea(g2);
        drawGrid(g2);

        drawArrows(g2);

        drawXYTicks(g2);

        drawValues(g2);
    }

    private void calculateDescriptionPositions(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        int fontAscent = fm.getAscent();
        int fontDescend = fm.getDescent();

        int xDescriptionLength = fm.stringWidth(model.xDescription());
        int yDescriptionLength = fm.stringWidth(model.yDescription());

        int height = getHeight();
        int width  = getWidth();

        xDescriptionLocation.x = width / 2 - xDescriptionLength / 2;
        xDescriptionLocation.y = height - fontDescend;

        yDescriptionLocation.x = -1 * (height / 2 + yDescriptionLength / 2);
        yDescriptionLocation.y = fontAscent;
    }

    private void drawDescriptions(Graphics2D g2) {
        g2.rotate(-Math.PI / 2);
        g2.drawString(model.yDescription(), yDescriptionLocation.x, yDescriptionLocation.y);

        g2.rotate(Math.PI / 2);
        g2.drawString(model.xDescription(), xDescriptionLocation.x, xDescriptionLocation.y);
    }

    private void calculateChartArea(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        int fontHeight = fm.getHeight();
        int yMaxWidth  = fm.stringWidth(Integer.toString(model.yMax()));

        int x = fontHeight + GAP + yMaxWidth + GAP;
        int y = 2 * GAP;

        int width  = getWidth()  - x - 2 * GAP;
        int height = getHeight() - y - 2 * fontHeight - 2 * GAP;

        chartArea.setBounds(x, y, width, height);
    }

    private void drawGrid(Graphics2D g2) {
        //Y axis
        yGap = (chartArea.height - 2 * GAP) / numOfYValues;
        yBase = chartArea.y + chartArea.height - GAP;

        for (int i = 0; i <= numOfYValues; i++) {
            int y = yBase - i * yGap;
            g2.drawLine(chartArea.x, y, chartArea.x + chartArea.width, y);
        }

        //X axis
        xGap  = (chartArea.width - 2 * GAP) / numOfXValues;
        xBase = chartArea.x + GAP;

        for (int i = 0; i <= numOfXValues; i++) {
            int x = xBase + i * xGap;
            g2.drawLine(x, chartArea.y, x, chartArea.y + chartArea.height);
        }
    }

    private void drawArrows(Graphics2D g2) {
        //Y axis
        Point yLineEnd = new Point(xBase, chartArea.y);
        Polygon yArrow = new Polygon();
        yArrow.addPoint(yLineEnd.x - GAP / 2, yLineEnd.y);
        yArrow.addPoint(yLineEnd.x + GAP / 2, yLineEnd.y);
        yArrow.addPoint(yLineEnd.x, yLineEnd.y - GAP);

        g2.fillPolygon(yArrow);

        //X axis
        Point xLineEnd = new Point(chartArea.x + chartArea.width, yBase);
        Polygon xArrow = new Polygon();
        xArrow.addPoint(xLineEnd.x, xLineEnd.y + GAP / 2);
        xArrow.addPoint(xLineEnd.x, xLineEnd.y - GAP / 2);
        xArrow.addPoint(xLineEnd.x + GAP, xLineEnd.y);

        g2.fillPolygon(xArrow);
    }

    private void drawXYTicks(Graphics2D g2) {
        FontMetrics fm = g2.getFontMetrics();
        int fontAscent = fm.getAscent();
        int fontDescent = fm.getDescent();
        int yOffset;

        if (fontAscent > fontDescent) {
            yOffset = (fontAscent - fontDescent) / 2;
        } else {
            yOffset = (fontDescent - fontAscent) / -2;
        }

        //Y-axis ticks
        for (int i = 0; i <= numOfYValues; i++) {
            String yValue = Integer.toString(model.yMin() + i * model.yResolution());
            int x = chartArea.x - GAP - fm.stringWidth(yValue);
            int y = yBase - i * yGap + yOffset;

            g2.drawString(yValue, x, y);
        }

        //X-axis ticks
        for (int i = 0; i < numOfXValues; i++) {
            String xValue = Integer.toString(model.values().get(i).x());
            int x = xBase + i * xGap + xGap / 2 - fm.stringWidth(xValue) / 2;
            int y = chartArea.y + chartArea.height + GAP + fontAscent;

            g2.drawString(xValue, x, y);
        }
    }

    private void drawValues(Graphics2D g2) {
        Color preColor = g2.getColor();
        g2.setColor(Color.RED);

        for (int i = 0; i < numOfXValues; i++) {
            int yValue = model.values().get(i).y();

            int x = xBase + i * xGap;
            int y = yBase - (yValue / model.yResolution()) * yGap;

            int yRemainder = yValue % model.yResolution();
            if (yRemainder != 0) {
                y -= (int) (1.0 * yRemainder / model.yResolution() * yGap);
            }

            g2.fill3DRect(x, y, xGap, yBase - y, true);
        }

        g2.setColor(preColor);
    }
}
