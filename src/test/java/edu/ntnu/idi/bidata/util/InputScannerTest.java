package edu.ntnu.idi.bidata.util;

import edu.ntnu.idi.bidata.util.command.ValidCommand;
import edu.ntnu.idi.bidata.user.UserInput;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * The InputScannerTest class contains unit tests for the InputScanner class.
 * These tests validate the methods for reading and interpreting user input
 * from various input streams, ensuring correct functionality and handling of edge cases.
 *
 * @author Nick Heggø
 * @version 2024-11-03
 */
class InputScannerTest {

  @BeforeEach
  void beforeEach() {
    ByteArrayInputStream in;
    in = new ByteArrayInputStream("testCommand   testSubcommand    test   user input string   ".getBytes());
    System.setIn(in);
  }

  @Test
  void testGetUserInput() {
    InputScanner inputScanner = new InputScanner();
    assertEquals("testCommand   testSubcommand    test   user input string", inputScanner.getValidString());
  }

  @Test
  void testGetUserInputThrows() {
    ByteArrayInputStream empty;
    empty = new ByteArrayInputStream("".getBytes());
    System.setIn(empty);
    InputScanner inputScanner = new InputScanner();
    assertThrows(IllegalArgumentException.class, () -> inputScanner.getInputString());

  }

  @Test
  void testGetUserInputFloat() {
    ByteArrayInputStream floatStream;
    floatStream = new ByteArrayInputStream(".9".getBytes());
    System.setIn(floatStream);
    InputScanner inputScanner = new InputScanner();
    assertEquals(0.9f, inputScanner.getInputFloat());
    System.setIn(floatStream);
//    assertEquals("0.9", inputScanner.getInputString());
  }

  @Test
  void testFetchUserInput() {
    InputScanner inputScanner = new InputScanner();
    UserInput userInput = inputScanner.fetchUserInput();
    assertEquals(ValidCommand.UNKNOWN, userInput.getCommandWord());
    assertEquals("unknown", userInput.getCommandWord().name().toLowerCase());
    assertEquals("testsubcommand", userInput.getSubcommand());
    assertEquals("test   user input string", userInput.getInputString());
  }
}
