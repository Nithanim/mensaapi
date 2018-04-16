package jkumensa.api.data;

import java.util.List;
import java.util.Set;
import jkumensa.api.MensaFoodCharacteristic;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jkumensa.api.MensaCategory;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MensaCategoryData implements MensaCategory {
    String title;

    List<MensaMealData> meals;

    float priceStudentBonus;
    float priceStudent;
    float priceGuest;
    
    Set<MensaFoodCharacteristic> foodCharacteristics;
}
