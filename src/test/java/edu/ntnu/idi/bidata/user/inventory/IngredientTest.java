package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.ValidUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test class for the Ingredient class.
 *
 * @author Nick Heggø
 * @version 2024-11-07
 */
class IngredientTest {

  @Test
  void testMerge() {
    Ingredient testIngredient = new Ingredient("test", 3, ValidUnit.G, 40, 4);
    Ingredient ingredientToMerge = new Ingredient("test", 3, ValidUnit.KG, 30, 4);
    Ingredient expectedMergeResult = new Ingredient("test", 6, ValidUnit.KG, 40, 4);
    testIngredient.merge(ingredientToMerge);
    assertEquals(testIngredient.getAmount(), expectedMergeResult.getAmount());
    assertEquals(testIngredient.getUnitPrice(), expectedMergeResult.getUnitPrice());
  }
}