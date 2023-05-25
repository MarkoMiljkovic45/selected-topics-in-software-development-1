package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BarChartDemo extends JFrame {

    private final String title;
    private final BarChart model;

    public BarChartDemo(String title, BarChart model) throws HeadlessException {
        super(title);
        this.title = title;
        this.model = model;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 500, 500);
        initGUI();
    }

    private void initGUI() {
        Container cp = getContentPane();

        cp.setLayout(new BorderLayout());
        cp.add(new JLabel(title, JLabel.CENTER), BorderLayout.PAGE_START);
        cp.add(new BarChartComponent(model), BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        try {
            Path data = Path.of(args[0]);
            BarChart model = parseInput(data);

            SwingUtilities.invokeLater(() -> new BarChartDemo(args[0], model).setVisible(true));

        } catch (IndexOutOfBoundsException iob) {
            System.out.println("No data file provided.");
        } catch (RuntimeException re) {
            System.out.println(re.getMessage());
        } catch (Exception e) {
            System.out.println("An error occurred.");
        }
    }

    private static BarChart parseInput(Path data) {
        try {
            List<String> lines = Files.readAllLines(data);

            String xDescription = lines.get(0);
            String yDescription = lines.get(1);

            List<XYValue> values = new ArrayList<>();
            for (String value: lines.get(2).split(" ")) {
                int x = Integer.parseInt(value.split(",")[0]);
                int y = Integer.parseInt(value.split(",")[1]);

                values.add(new XYValue(x, y));
            }

            int yMin = Integer.parseInt(lines.get(3));
            int yMax = Integer.parseInt(lines.get(4));
            int yGap = Integer.parseInt(lines.get(5));

            return new BarChart(values, xDescription, yDescription, yMin, yMax, yGap);

        } catch (IndexOutOfBoundsException iob) {
            throw new RuntimeException("Provided data file has wrong format.");
        } catch (IOException io) {
            throw new RuntimeException("Error while reading the data file.");
        }
    }
}
