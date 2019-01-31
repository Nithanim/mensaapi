package jkumensa.api;

import java.util.List;
import java.util.Map;

public interface MensaApiResult {
    long getDatestamp();
    
    Map<Mensa, List<? extends MensaCategory>> getData();
}
