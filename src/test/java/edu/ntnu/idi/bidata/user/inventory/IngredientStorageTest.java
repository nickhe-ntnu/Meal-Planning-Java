package edu.ntnu.idi.bidata.user.inventory;

import edu.ntnu.idi.bidata.util.unit.ValidUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nick Heggø
 * @version 2024-11-28
 */
class IngredientStorageTest {
  static IngredientStorage ingredientStorage;

  @BeforeEach
  void BeforeEach() {
    ingredientStorage = new IngredientStorage("Test Storage");
  }

  //  @Test
  //  void testHas

  @Test
  void testGetAllExpired() {
    ingredientStorage.addIngredient(new Ingredient("expiredDemo"));
    List<Ingredient> expiredList = ingredientStorage.getAllExpired();
    assertEquals(1, expiredList.size());
  }

  @Test
  void testAddIngredient() {
    Ingredient ingredient = new Ingredient("testIngredient", 23, ValidUnit.KG, 20, 4);
    assertFalse(ingredientStorage.isIngredientPresent("TESTINGREDIENT"));
    ingredientStorage.addIngredient(ingredient);
    assertTrue(ingredientStorage.isIngredientPresent("TESTINGREDIENT"));
  }

  @Test
  void restRemoveIngredient() {
    Ingredient ingredient = new Ingredient("testIngredient", 23, ValidUnit.KG, 20, 4);
    ingredientStorage.addIngredient(ingredient);
    assertTrue(ingredientStorage.isIngredientPresent("TESTINGREDIENT"));
    ingredientStorage.removeIngredient("TESTINGREDIENT", LocalDate.now().plusDays(4));
    assertFalse(ingredientStorage.isIngredientPresent("TESTINGREDIENT"));
  }

  @Test
  void testMergeIngredient() {
    Ingredient ingredient1 = new Ingredient("testIngredient", 2, ValidUnit.KG, 20, 4);
    Ingredient ingredient2 = new Ingredient("testIngredient", 300, ValidUnit.G, 20, 4);
    ingredientStorage.addIngredient(ingredient1);
    ingredientStorage.addIngredient(ingredient2);
    String name = ingredient1.getName();
    LocalDate expiryDate = ingredient1.getExpiryDate();
    Ingredient ingredient = ingredientStorage.findIngredient(name, expiryDate);
    assertEquals(2.3f, ingredient.getAmount());
  }
}