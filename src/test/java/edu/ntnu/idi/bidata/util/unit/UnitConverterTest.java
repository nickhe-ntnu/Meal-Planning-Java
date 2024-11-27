package edu.ntnu.idi.bidata.util.unit;

import edu.ntnu.idi.bidata.user.inventory.Measurement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the UnitConverter class.
 * This class contains various test methods to verify the correct
 * functionality of unit conversion methods within the UnitConverter class.
 *
 * @author Nick Heggø
 * @version 2024-11-27
 */
class UnitConverterTest {

  UnitConverter unitConverter;
  Measurement solidMeasurement;
  Measurement liquidMeasurement;

  @BeforeEach
  void beforeEach() {
    unitConverter = new UnitConverter();
    solidMeasurement = new Measurement("test object", 12345.6f, ValidUnit.G);
    liquidMeasurement = new Measurement("test object", 123.456f, ValidUnit.DL);
  }

  @Test
  void convertToStandard() {
    unitConverter.convertToStandard(solidMeasurement);
    unitConverter.convertToStandard(liquidMeasurement);
    assertEquals(12.35f, solidMeasurement.getAmount());
    assertEquals(ValidUnit.KG, solidMeasurement.getValidUnit());
    assertEquals(12.35f, liquidMeasurement.getAmount());
    assertEquals(ValidUnit.L, liquidMeasurement.getValidUnit());
  }

  @Test
  void convertToGrams() {
    unitConverter.convertToGrams(solidMeasurement);
    assertEquals(12345.6f, solidMeasurement.getAmount());
    assertEquals(ValidUnit.G, solidMeasurement.getValidUnit());
  }

  @Test
  void convertToKG() {
    unitConverter.convertToKG(solidMeasurement);
    assertEquals(12.35f, solidMeasurement.getAmount());
    assertEquals(ValidUnit.KG, solidMeasurement.getValidUnit());
  }

  @Test
  void convertToLiter() {
    unitConverter.convertToLiter(liquidMeasurement);
    assertEquals(12.35f, liquidMeasurement.getAmount());
  }

  @Test
  void convertToDeciLiter() {
    unitConverter.convertToDeciLiter(liquidMeasurement);
    assertEquals(123.46f, liquidMeasurement.getAmount());
    assertEquals(ValidUnit.DL, liquidMeasurement.getValidUnit());
  }

  @Test
  void convertToMilliLiter() {
    unitConverter.convertToMilliLiter(liquidMeasurement);
    assertEquals(12345.6f, liquidMeasurement.getAmount());
    assertEquals(ValidUnit.ML, liquidMeasurement.getValidUnit());
  }
}