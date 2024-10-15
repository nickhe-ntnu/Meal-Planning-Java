
import static org.junit.jupiter.api.Assertions.*;

import edu.ntnu.idi.bidata.ingredient.Ingredient;
import edu.ntnu.idi.bidata.inventory.Storage;
import org.junit.jupiter.api.*;

public class StorageTest {

  static Storage storageTest;
  static Ingredient apple;
  @BeforeAll
  public static void setUpAll() {
    storageTest = new Storage();
    storageTest.createCollection("collectionTest");
    storageTest.createCollection("collectionTest2");
    apple = new Ingredient("Apple", "kg", 1.0f);
  }

  @Test
  public void createCollectionTest() {
    assertTrue(storageTest.collectionExists("collectionTest"));
  }

  @Test
  public void testAddIngredientToCollection() {
    storageTest.addIngredient("collectionTest", apple);
    storageTest.addIngredient("collectionTest", new Ingredient("banana", "kg", 32f));
    storageTest.addIngredient("collectionTest", apple);
    storageTest.addIngredient("collectionTest", apple);
    storageTest.printAllInventory();
    assertTrue(storageTest.collectionContainsIngredient("collectionTest", "Apple"));
  }

  @Test
  public void testRemoveIngredientFromCollection() {
    storageTest.addIngredient("collectionTest", apple);
    storageTest.removeIngredient("collectionTest", apple);
    assertFalse(storageTest.collectionContainsIngredient("collectionTest", "apple"));
  }

}

