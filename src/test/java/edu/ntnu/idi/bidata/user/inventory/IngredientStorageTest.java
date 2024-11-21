package edu.ntnu.idi.bidata.user.inventory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author Nick Heggø
 * @version 2024-11-09
 */
class IngredientStorageTest {

  @BeforeEach
  void beforeEach() {
    IngredientStorage ingredientStorage = new IngredientStorage("Test Storage");
    int expiredIngredientToGenerate = 3;
    for (int i = 0; i < expiredIngredientToGenerate; i++) {
      ingredientStorage.addIngredient(new Ingredient("expiredDemo"));
    }
  }

  @Test
  void getAllExpired() {
  }

  @Test
  void addIngredient() {
  }

  @Test
  void removeIngredient() {
  }
}