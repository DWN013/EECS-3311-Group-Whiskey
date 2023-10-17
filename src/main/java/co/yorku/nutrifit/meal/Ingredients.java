package co.yorku.nutrifit.meal;

import co.yorku.nutrifit.database.nutrient.NFDatabaseInterface;

public class Ingredients {

//    private NFDatabaseInterface nfDatabaseInterface;
//
//    public Ingredients(NFDatabaseInterface nfDatabaseInterface) {
//        this.nfDatabaseInterface = nfDatabaseInterface;
//    }

    private String name;
    private String quantity;

    public Ingredients(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
