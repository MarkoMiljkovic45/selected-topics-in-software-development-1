package hr.fer.zemris.java.hw06.shell.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class UtilTest {

    @Test
    public void testUtilParser1() {
        assertArrayEquals(
                new String[] {"home/t.txt"},
                Util.parse("home/t.txt").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser2() {
        assertArrayEquals(
                new String[] {"home/t.txt", "."},
                Util.parse("home/t.txt .").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser3() {
        assertArrayEquals(
                new String[] {"home/t.txt"},
                Util.parse("\"home/t.txt\"").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser4() {
        assertArrayEquals(
                new String[] {"home/t.txt", "."},
                Util.parse("\"home/t.txt\" .").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser5() {
        assertArrayEquals(
                new String[] {"C:\\home\\t n t.txt"},
                Util.parse("\"C:\\home\\t n t.txt\"").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser6() {
        assertArrayEquals(
                new String[] {"home\"t\" n t.txt"},
                Util.parse("\"home\\\"t\\\" n t.txt\"").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser7() {
        assertArrayEquals(
                new String[] {"arg", "home\"t\" n t.txt"},
                Util.parse("arg \"home\\\"t\\\" n t.txt\"").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser8() {
        assertArrayEquals(
                new String[] {"home\"t\" n t.txt", "arg"},
                Util.parse("\"home\\\"t\\\" n t.txt\" arg").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser9() {
        assertArrayEquals(
                new String[] {"C:\\home\\deb\\\\bla bla.txt"},
                Util.parse("\"C:\\home\\deb\\\\\\bla bla.txt\"").toArray(new String[0])
        );
    }

    @Test
    public void testUtilParser10() {
        assertArrayEquals(
                new String[] {"C:\\home\\deb\\\\bla bla.txt", "."},
                Util.parse("\"C:\\home\\deb\\\\\\bla bla.txt\" .").toArray(new String[0])
        );
    }
}
