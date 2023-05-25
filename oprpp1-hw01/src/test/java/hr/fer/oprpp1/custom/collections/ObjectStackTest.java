package hr.fer.oprpp1.custom.collections;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ObjectStackTest {

    //isEmpty()
    @Test
    public void testIsEmptyObjectStack() {
        ObjectStack stack = new ObjectStack();
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testIsNotEmptyObjectStack() {
        ObjectStack stack = new ObjectStack();
        stack.push("Test");
        assertFalse(stack.isEmpty());
    }

    //size()
    @Test
    public void testSizeObjectStack() {
        ObjectStack stack = new ObjectStack();
        assertEquals(0, stack.size());

        stack.push("Test1");
        assertEquals(1, stack.size());

        stack.push("Test2");
        assertEquals(2, stack.size());

        stack.pop();
        assertEquals(1, stack.size());
    }

    //push()
    @Test
    public void testPushToObjectStack() {
        ObjectStack stack = new ObjectStack();
        stack.push("Test");
        assertEquals("Test", stack.peek());
    }

    //pop()
    @Test
    public void testPopFromObjectStack() {
        ObjectStack stack = new ObjectStack();
        stack.push("Test");
        assertEquals("Test", stack.pop());
        assertTrue(stack.isEmpty());
    }

    @Test
    public void testPopOnEmptyObjectStack() {
        ObjectStack stack = new ObjectStack();
        assertThrows(EmptyStackException.class, stack::pop);
    }

    //peek()
    @Test
    public void testPeekFromObjectStack() {
        ObjectStack stack = new ObjectStack();
        stack.push("Test");
        assertEquals("Test", stack.peek());
        assertFalse(stack.isEmpty());
    }

    @Test
    public void testPeekOnEmptyObjectStack() {
        ObjectStack stack = new ObjectStack();
        assertThrows(EmptyStackException.class, stack::peek);
    }

    //clear()
    @Test
    public void testClearObjectStack() {
        ObjectStack stack = new ObjectStack();
        stack.push("Test1");
        stack.push("Test2");
        stack.clear();
        assertTrue(stack.isEmpty());
    }
}
