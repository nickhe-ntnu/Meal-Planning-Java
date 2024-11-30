package edu.ntnu.idi.bidata.util.unit;

import edu.ntnu.idi.bidata.util.Utility;

import java.util.HashMap;

/**
 * The UnitRegistry class is used to manage valid measurement units within the application.
 * It provides functionality to initialize, validate, and retrieve information about measurement units.
 *
 * @author Nick Heggø
 * @version 2024-11-30
 */
public class UnitRegistry {
  private final HashMap<String, ValidUnit> validUnits;

  /**
   * Constructor - initialise the valid unit.
   */
  public UnitRegistry() {
    validUnits = new HashMap<>();
    for (ValidUnit unit : ValidUnit.values()) {
      validUnits.put(Utility.createKey(unit.name()), unit);
    }
  }

  public String getUnitList() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Valid units are:");
    for (String unitName : validUnits.keySet()) {
      stringBuilder.append(" ").append(unitName);
    }
    return stringBuilder.toString();
  }

  /**
   * @param unitInput input to check if it is valid unit
   * @return null if it does not exist in
   */
  public ValidUnit getUnitType(String unitInput) {
    ValidUnit unit = validUnits.get(Utility.createKey(unitInput));
    if (unit != null) {
      return unit;
    } else {
      return ValidUnit.UNKNOWN;
    }
  }

  public boolean isUnit(String unitInput) {
    return validUnits.containsKey(unitInput);
  }
}
