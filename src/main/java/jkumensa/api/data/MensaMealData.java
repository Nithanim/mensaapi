package jkumensa.api.data;

import java.util.Set;
import jkumensa.api.AllergyCodeSet;
import jkumensa.api.MensaFoodCharacteristic;
import jkumensa.api.MensaMeal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensaMealData implements MensaMeal {
    String title;

    float priceStudentBonus;
    float priceStudent;
    float priceGuest;

    AllergyCodeSet allergyCodes;
    Set<MensaFoodCharacteristic> foodCharacteristics;
}
