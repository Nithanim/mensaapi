package jkumensa.api.data;

import java.util.List;
import java.util.Map;
import jkumensa.api.Mensa;
import jkumensa.api.MensaApiResult;
import jkumensa.api.MensaCategory;
import lombok.Value;

@Value
public class MensaApiResultData implements MensaApiResult {
    long datestamp;
    
    Map<Mensa, List<? extends MensaCategory>> data;
}
