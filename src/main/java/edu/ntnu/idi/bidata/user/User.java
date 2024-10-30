package edu.ntnu.idi.bidata.user;

import edu.ntnu.idi.bidata.user.inventory.Inventory;
import edu.ntnu.idi.bidata.user.recipe.CookBook;

public class User {
  private String name;
  private Inventory inventory;
  private CookBook cookBook;



  public User(String name) {
    setName(name);
    inventory = new Inventory();
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public String getInventoryString() {
    return inventory.getInventoryString();
  }

  public boolean addStorage(String storageName) {
    return inventory.addNewCollection(storageName);
  }


}
