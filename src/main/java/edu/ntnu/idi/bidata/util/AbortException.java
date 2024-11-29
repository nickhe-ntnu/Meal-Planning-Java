package edu.ntnu.idi.bidata.util;

/**
 * AbortException is a custom exception used to indicate that
 * an operation has been aborted. This exception can be thrown when
 * a process needs to be terminated while in input mode simply by typing "abort".
 *
 * @author Nick Heggø
 * @version 2024-11-29
 */
public class AbortException extends RuntimeException {

  private static final String DEFAULT_MESSAGE = "Operation aborted.";

  public AbortException() {
    super(DEFAULT_MESSAGE);
  }

  public AbortException(String message) {
    super(message);
  }
}
