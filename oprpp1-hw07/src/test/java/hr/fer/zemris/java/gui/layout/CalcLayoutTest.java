package hr.fer.zemris.java.gui.layout;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.CalcLayoutException;
import hr.fer.zemris.java.gui.layouts.RCPosition;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.*;
import java.awt.*;

public class CalcLayoutTest {

    @Test
    public void invalidPositionOutOfGrid() {
        JPanel p = new JPanel(new CalcLayout(3));

        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(0, 7)));

        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(6, 7)));

        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(2, 0)));

        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(2, 8)));
    }

    @Test
    public void invalidPositionBecauseInsideSpecialBlock() {
        JPanel p = new JPanel(new CalcLayout(3));

        assertThrows(CalcLayoutException.class, () -> p.add(new JLabel("x"), new RCPosition(1, 2)));
    }

    @Test
    public void multipleComponentsOnSamePosition() {
        JPanel p = new JPanel(new CalcLayout(3));

        assertThrows(CalcLayoutException.class, () -> {
            p.add(new JLabel("x"), new RCPosition(3, 2));
            p.add(new JLabel("x"), new RCPosition(3, 2));
        });
    }

    @Test
    public void testLayoutRCPosition() {
        assertDoesNotThrow(() -> {
            JPanel p = new JPanel(new CalcLayout(3));
            p.add(new JLabel("x"), new RCPosition(1,1));
            p.add(new JLabel("y"), new RCPosition(2,3));
            p.add(new JLabel("z"), new RCPosition(2,7));
            p.add(new JLabel("w"), new RCPosition(4,2));
            p.add(new JLabel("a"), new RCPosition(4,5));
            p.add(new JLabel("b"), new RCPosition(4,7));
        });
    }

    @Test
    public void testLayoutStringPosition() {
        assertDoesNotThrow(() -> {
            JPanel p = new JPanel(new CalcLayout(3));
            p.add(new JLabel("x"), "1,1");
            p.add(new JLabel("y"), "2,3");
            p.add(new JLabel("z"), "2,7");
            p.add(new JLabel("w"), "4,2");
            p.add(new JLabel("a"), "4,5");
            p.add(new JLabel("b"), "4,7");
        });
    }

    @Test
    public void testPreferredSize1() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(10,30));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(20,15));
        p.add(l1, new RCPosition(2,2));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }

    @Test
    public void testPreferredSize2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel(""); l1.setPreferredSize(new Dimension(108,15));
        JLabel l2 = new JLabel(""); l2.setPreferredSize(new Dimension(16,30));
        p.add(l1, new RCPosition(1,1));
        p.add(l2, new RCPosition(3,3));
        Dimension dim = p.getPreferredSize();

        assertEquals(152, dim.width);
        assertEquals(158, dim.height);
    }
}
