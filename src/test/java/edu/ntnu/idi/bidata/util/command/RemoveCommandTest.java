package edu.ntnu.idi.bidata.util.command;

import edu.ntnu.idi.bidata.user.User;
import edu.ntnu.idi.bidata.user.inventory.Ingredient;
import edu.ntnu.idi.bidata.user.inventory.InventoryManager;
import edu.ntnu.idi.bidata.util.InputScanner;
import edu.ntnu.idi.bidata.util.unit.ValidUnit;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**/
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
    testInventoryManager.addStorage(storageName);
    testInventoryManager.setCurrentStorage(storageName);
    testInventoryManager.addIngredient(new Ingredient(ingredientName, 200, ValidUnit.G, 23, 23));
  }

  //  @Test
  void testRemoveIngredient() {
    ByteArrayInputStream in = new ByteArrayInputStream(("remove ingredient " + ingredientName).getBytes());
    System.setIn(in);
    InputScanner inputScanner = new InputScanner();
    testUser.setInput(inputScanner.fetchCommand());
    assertEquals(storageName, testInventoryManager.getCurrentStorage().getStorageName());
    assertEquals(true, testInventoryManager.getCurrentStorage().isIngredientPresent(ingredientName));
    new RemoveCommand(testUser);
    assertEquals(false, testInventoryManager.getCurrentStorage().isIngredientPresent(ingredientName));
  }
}