package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.user.UserInput;
import edu.ntnu.idi.bidata.util.command.ValidCommand;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * The InputScannerTest class contains unit tests for the InputScanner class.
 * These tests validate the methods for reading and interpreting user input
 * from various input streams, ensuring correct functionality and handling of edge cases.
 *
 * @author Nick Heggø
 * @version 2024-11-30
 */
class InputScannerTest {

  @AfterEach
  void restoreSystemIn() {
    System.setIn(System.in); // Restore to the original System.in after each test
  }

  @Test
  void testAbortException() {
    System.setIn(new ByteArrayInputStream("AbOrt ".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertThrows(AbortException.class, inputScanner::nextLine);
  }

  @Test
  void testNextLine() {
    System.setIn(new ByteArrayInputStream("lISt   testSuBcOmmand    test   uSEr iNput stRing   ".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertEquals("lISt   testSuBcOmmand    test   uSEr iNput stRing", inputScanner.nextLine());
  }

  @Test
  void testNextLineNegative() {
    System.setIn(new ByteArrayInputStream("".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertThrows(IllegalArgumentException.class, inputScanner::nextLine);
  }

  @Test
  void testNextInteger() {
    System.setIn(new ByteArrayInputStream("123".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertEquals(123, inputScanner.nextInteger());
  }

  @Test
  void testNextIntegerNegative() {
    System.setIn(new ByteArrayInputStream("123.45".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertThrows(NumberFormatException.class, inputScanner::nextInteger);
  }

  @Test
  void testNextFloat() {
    System.setIn(new ByteArrayInputStream(".2349".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertEquals(0.2349f, inputScanner.nextFloat());
  }

  @Test
  void testNextFloatNegative() {
    System.setIn(new ByteArrayInputStream("string".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertThrows(NumberFormatException.class, inputScanner::nextFloat);
  }

  @Test
  void testFetchCommand() {
    System.setIn(new ByteArrayInputStream("lISt   testSuBcOmmand    test   uSEr iNput stRing   ".getBytes()));
    InputScanner inputScanner = new InputScanner();
    UserInput userInput = inputScanner.fetchCommand();
    assertEquals(ValidCommand.LIST, userInput.getCommand());
    assertEquals("list", userInput.getCommand().name().toLowerCase());
    assertEquals("testsubcommand", userInput.getSubcommand());
    assertEquals("test   uSEr iNput stRing", userInput.getInputString());
  }

  @Test
  void testFetchCommandNegative() {
    System.setIn(new ByteArrayInputStream("lst something".getBytes()));
    InputScanner inputScanner = new InputScanner();
    UserInput userInput = inputScanner.fetchCommand();
    assertEquals(ValidCommand.UNKNOWN, userInput.getCommand());
    assertEquals("something", userInput.getSubcommand());
  }

  @Test
  void testFetchUnit() {
    System.setIn(new ByteArrayInputStream("123.45 kg".getBytes()));
    InputScanner inputScanner = new InputScanner();
    UserInput userInput = inputScanner.fetchUnit();
    assertEquals(123.45f, userInput.getAmount());
    assertEquals(ValidUnit.KG, userInput.getUnit());
  }

  @Test
  void testFetchUnitNegativeOne() {
    System.setIn(new ByteArrayInputStream("k".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertThrows(IllegalArgumentException.class, inputScanner::fetchUnit);
  }

  @Test
  void testFetchUnitNegativeTwo() {
    System.setIn(new ByteArrayInputStream("123".getBytes()));
    InputScanner inputScanner = new InputScanner();
    assertThrows(IllegalArgumentException.class, inputScanner::fetchUnit);
  }
}
