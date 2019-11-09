package ru.cft.focusstart.kolesnikov;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;
import static ru.cft.focusstart.kolesnikov.MultiplicationTable.*;

public class MultiplicationTableTest {

    private String strExpected1 = " 1| 2| 3| 4| 5" +
            System.lineSeparator() +
            "--+--+--+--+--" +
            System.lineSeparator() +
            " 2| 4| 6| 8|10" +
            System.lineSeparator() +
            "--+--+--+--+--" +
            System.lineSeparator() +
            " 3| 6| 9|12|15" +
            System.lineSeparator() +
            "--+--+--+--+--" +
            System.lineSeparator() +
            " 4| 8|12|16|20" +
            System.lineSeparator() +
            "--+--+--+--+--" +
            System.lineSeparator() +
            " 5|10|15|20|25";

    private final PrintStream printStream = mock(PrintStream.class);
    private final BufferedReader bufferedReader = mock(BufferedReader.class);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testGetTableSize() throws IOException {
        when(bufferedReader.readLine()).thenReturn("5");
        assertEquals(5, getTableSize(bufferedReader,32));
        verify(bufferedReader).readLine();
    }

    @Test
    public void testGetTableSizeWithIllegalArguments() throws IOException {
        when(bufferedReader.readLine()).thenReturn("A");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Wrong value, must be integer number");
        getTableSize(bufferedReader,32);
    }

    @Test
    public void testGetTableSizeWithWrongRange() throws IOException {
        when(bufferedReader.readLine()).thenReturn("33");
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage("Wrong value, must be integer number with range [0,32]");
        getTableSize(bufferedReader,32);
    }

    @Test
    public void testBuildTableAsString() {
        assertEquals(strExpected1, buildTableAsString(5));
    }

    @Test
    public void testWriteToConsole() {
        writeToConsole(printStream, strExpected1);
        verify(printStream).println(strExpected1);
    }

}
