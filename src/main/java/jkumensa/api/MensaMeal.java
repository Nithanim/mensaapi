package jkumensa.api;

import java.util.Set;

public interface MensaMeal extends Priced {
    String getTitle();

    Set<Character> getAllergyCodes();

    Set<MensaFoodCharacteristic> getFoodCharacteristics();
}
