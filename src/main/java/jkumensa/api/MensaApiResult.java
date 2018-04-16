package jkumensa.api;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface MensaApiResult {
    Date getDate();
    
    Map<Mensa, List<? extends MensaCategory>> getData();
}
