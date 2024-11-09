package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.UnitConverter;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Ingredient class.
 *
 * @author Nick Heggø
 * @version 2024-11-09
 */
class IngredientTest {
  private Ingredient mergedIngredient;
  private Ingredient testIngredient;

  @BeforeEach
  void beforeEach() {
    testIngredient = new Ingredient("test", 3, ValidUnit.KG, 30, 4);
    mergedIngredient = new Ingredient("test", 300, ValidUnit.G, 40, 4);
  }

  @Test
  void testMerge() {
    testIngredient.merge(mergedIngredient);
    Ingredient expectedMergeResult = new Ingredient("test", 3.3f, ValidUnit.KG, 40, 4);
    assertEquals(expectedMergeResult.getName(), testIngredient.getName());
    assertEquals(expectedMergeResult.getAmount(), testIngredient.getAmount());
    assertEquals(expectedMergeResult.getValidUnit(), testIngredient.getValidUnit());
    assertEquals(expectedMergeResult.getStandardUnitPrice(), testIngredient.getStandardUnitPrice());
    assertEquals(expectedMergeResult.getExpiryDate(), testIngredient.getExpiryDate());
  }

  @Test
  void testConvert() {
    UnitConverter unitConverter = new UnitConverter();
    unitConverter.convertToGrams(mergedIngredient);
  }
}