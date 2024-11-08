package edu.ntnu.idi.bidata.util.unit;

import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Unit tests for the UnitConverter class.
 * This class contains various test methods to verify the correct
 * functionality of unit conversion methods within the UnitConverter class.
 *
 * @author Nick Heggø
 * @version 2024-11-08
 */
class UnitConverterTest {

  UnitConverter unitConverter;
  Ingredient solidIngredient;
  Ingredient liquidIngredient;

  @BeforeEach
  void beforeEach() {
    unitConverter = new UnitConverter();
    solidIngredient = new Ingredient("test object", 123.456f, ValidUnit.KG, 200, 12);
    liquidIngredient = new Ingredient("test object", 123.456f, ValidUnit.DL, 200, 12);
  }

  @Test
  void convertToStandard() {
    unitConverter.convertToStandard(solidIngredient);
    unitConverter.convertToStandard(liquidIngredient);
    assertEquals(123.46f, solidIngredient.getAmount());
    assertEquals(ValidUnit.KG, solidIngredient.getValidUnit());
    assertEquals(12.35f, liquidIngredient.getAmount());
    assertEquals(ValidUnit.L, liquidIngredient.getValidUnit());
  }

  @Test
  void convertToGrams() {
    unitConverter.convertToGrams(solidIngredient);
    assertEquals(123456, solidIngredient.getAmount());
    assertEquals(ValidUnit.G, solidIngredient.getValidUnit());
  }

  @Test
  void convertToKG() {
    unitConverter.convertToKG(solidIngredient);
    assertEquals(123.46f, solidIngredient.getAmount());
    assertEquals(ValidUnit.KG, solidIngredient.getValidUnit());
  }

  @Test
  void convertToLiter() {
    unitConverter.convertToLiter(liquidIngredient);
    assertEquals(12.35f, liquidIngredient.getAmount());
  }

  @Test
  void convertToDeciLiter() {
    unitConverter.convertToDeciLiter(liquidIngredient);
    assertEquals(123.46f, liquidIngredient.getAmount());
    assertEquals(ValidUnit.DL, liquidIngredient.getValidUnit());
  }

  @Test
  void convertToMilliLiter() {
    unitConverter.convertToMilliLiter(liquidIngredient);
    assertEquals(12345.6f, liquidIngredient.getAmount());
    assertEquals(ValidUnit.ML, liquidIngredient.getValidUnit());
  }
}