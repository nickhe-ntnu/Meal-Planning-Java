package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;
import org.junit.jupiter.api.BeforeEach;

class RemoveCommandTest {
  User testUser;
  InventoryManager testInventoryManager;
  String userName = "testUser";
  String storageName = "testStorage";
  String ingredientName = "testIngredient";

  @BeforeEach
  void beforeEach() {
    testUser = new User();
    testInventoryManager = testUser.getInventoryManager();
    testInventoryManager.createIngredientStorage(storageName);
    testInventoryManager.setCurrentStorage(storageName);
    testInventoryManager.addIngredientToCurrentStorage(new Ingredient(ingredientName, 200, ValidUnit.G, 23, 23));
  }
}