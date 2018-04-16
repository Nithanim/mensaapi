package jkumensa.api.data;

import java.util.Date;
import java.util.List;
import java.util.Map;
import jkumensa.api.Mensa;
import jkumensa.api.MensaApiResult;
import jkumensa.api.MensaCategory;
import lombok.Value;

@Value
public class MensaApiResultData implements MensaApiResult {
    Date date;
    
    Map<Mensa, List<? extends MensaCategory>> data;
}
