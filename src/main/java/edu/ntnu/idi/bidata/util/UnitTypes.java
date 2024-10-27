package edu.ntnu.idi.bidata.util;

import java.util.HashMap;

public class UnitTypes {
  private HashMap<String, ValidUnit> validUnits;

  /**
   * Constructor - initialise the valid unit.
   */
  public UnitTypes() {
    validUnits = new HashMap<>();
    for (ValidUnit unit : ValidUnit.values()) {
      validUnits.put(unit.name().toLowerCase(), unit);
    }
  }

  /**
   * @param unitInput input to check if it is valid unit
   * @return null if it does not exist in
   */
  public ValidUnit getUnitType(String unitInput) {
    ValidUnit unit = validUnits.get(unitInput);
    if (unit != null) {
      return unit;
    } else {
      return ValidUnit.UNKNOWN;
    }
  }

  public boolean isUnit(String unitInput) {
    return validUnits.containsKey(unitInput);
  }

  public String getUnitList() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Valid units are:");
    for (String unitName : validUnits.keySet()) {
      stringBuilder.append(" ").append(unitName);
    }
    return stringBuilder.toString();
  }
}
