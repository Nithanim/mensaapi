package jkumensa.api;

import java.util.List;
import java.util.Set;

public interface MensaCategory extends Priced {
    String getTitle();

    List<? extends MensaMeal> getMeals();
    
    Set<? extends MensaFoodCharacteristic> getFoodCharacteristics();
}
