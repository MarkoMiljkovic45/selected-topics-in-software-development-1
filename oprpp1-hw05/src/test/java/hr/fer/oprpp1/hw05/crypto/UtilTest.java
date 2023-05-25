package hr.fer.oprpp1.hw05.crypto;

import org.junit.jupiter.api.Test;

import static hr.fer.oprpp1.hw05.crypto.Util.byteToHex;
import static hr.fer.oprpp1.hw05.crypto.Util.hexToByte;
import static org.junit.jupiter.api.Assertions.*;

public class UtilTest {

    /*** hexToByte tests ***/

    @Test
    public void testHexToByte() {
        assertArrayEquals(new byte[] {1, -82, 34}, hexToByte("01aE22"));
    }

    @Test
    public void testEmptyStringHexToByte() {
        assertArrayEquals(new byte[] {}, hexToByte(""));
    }

    @Test
    public void testOddSizedHexToByte() {
        assertThrows(IllegalArgumentException.class, () -> hexToByte("01aE221"));
    }

    @Test
    public void testInvalidCharacterHexToByte() {
        assertThrows(IllegalArgumentException.class, () -> hexToByte("01aE22g"));
    }

    /*** byteToHex tests  ***/

    @Test
    public void testByteToHex() {
        assertEquals("01ae22", byteToHex(new byte[] {1, -82, 34}));
    }

    @Test
    public void testEmptyArrayByteToHex() {
        assertEquals("", byteToHex(new byte[] {}));
    }
}
