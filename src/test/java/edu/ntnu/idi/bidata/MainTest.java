package edu.ntnu.idi.bidata;

import edu.ntnu.idi.bidata.inventory.Inventory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
  static Main userTest;
  static Scanner inputScannerTest;
  static Inventory userInventoryTest;

  @BeforeAll
  public static void BeforeAll() {
    inputScannerTest = new Scanner(System.in);
    userTest = new Main(inputScannerTest);
    userInventoryTest = new Inventory();
    userInventoryTest.addStorage("x");
  }

  @Test
  void testAddSameStorageNameThrowsException() {
    assertThrowsExactly(IllegalArgumentException.class, () -> userInventoryTest.addStorage("x"));
  }

  @Test
  public void testValidInput() {
    InputStream sysInBackup = System.in;
    ByteArrayInputStream in = new ByteArrayInputStream("  Hello World  \n".getBytes());
    System.setIn(in);
    inputScannerTest = new Scanner(System.in);
    userTest = new Main(inputScannerTest);
    assertEquals("Hello World", userTest.scanUserInput());
    System.setIn(sysInBackup);
  }

  @Test
  public void testInvalidInput() {
    InputStream sysInBackup = System.in;
    ByteArrayInputStream in = new ByteArrayInputStream("\n".getBytes());
    System.setIn(in);
    inputScannerTest = new Scanner(System.in);
    userTest = new Main(inputScannerTest);
    assertThrowsExactly(IllegalArgumentException.class, () -> userTest.scanUserInput());
    System.setIn(sysInBackup);
  }

  @Test
  void getUserAction() {
  }

  @Test
  void app() {
  }
}