package jkumensa.api.serialization;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import jkumensa.api.AllergyCodeSet;
import jkumensa.api.Mensa;
import jkumensa.api.MensaApiResult;
import jkumensa.api.MensaCategory;
import jkumensa.api.MensaFoodCharacteristic;
import jkumensa.api.MensaMeal;
import jkumensa.api.data.MensaApiResultData;
import jkumensa.api.data.MensaCategoryData;
import jkumensa.api.data.MensaMealData;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OrgjsonMensaSerialization {
    private static final Logger logger = LoggerFactory.getLogger(OrgjsonMensaSerialization.class);

    public MensaApiResult fromJson(String json) {
        return mensaApiResultFromJson(new JSONObject(json));
    }

    private MensaApiResult mensaApiResultFromJson(JSONObject o) {
        long datestamp = o.getLong("datestamp");
        Map<Mensa, List<? extends MensaCategory>> data = mensaDataFromJson(o.getJSONObject("data"));
        return new MensaApiResultData(datestamp, data);
    }

    private Map<Mensa, List<? extends MensaCategory>> mensaDataFromJson(JSONObject o) {
        Map<Mensa, List<? extends MensaCategory>> data = new EnumMap<>(Mensa.class);
        for (String k : o.keySet()) {
            Mensa mensa;
            try {
                mensa = Mensa.valueOf(k);
            } catch (IllegalArgumentException ex) {
                logger.warn("Unknown Mensa \"{}\"", k);
                continue;
            }

            JSONArray jsonCategories = o.getJSONArray(k);

            List<MensaCategoryData> categories = new ArrayList<>();
            for (Object jsonCat : jsonCategories) {
                categories.add(categoryFromJson((JSONObject) jsonCat));
            }
            data.put(mensa, categories);
        }

        return data;
    }

    private MensaCategoryData categoryFromJson(JSONObject o) {
        MensaCategoryData c = new MensaCategoryData();
        c.setTitle(o.getString("title"));
        c.setPriceStudentBonus(o.getFloat("priceStudentBonus"));
        c.setPriceStudent(o.getFloat("priceStudent"));
        c.setPriceGuest(o.getFloat("priceGuest"));
        c.setMeals(mealsFromJson(o.getJSONArray("meals")));
        c.setFoodCharacteristics(foodCharacteristicsFromJson(o.getJSONArray("foodCharacteristics")));
        return c;
    }

    private List<MensaMealData> mealsFromJson(JSONArray a) {
        ArrayList<MensaMealData> meals = new ArrayList<>();
        for (Object o : a) {
            JSONObject d = (JSONObject) o;
            MensaMealData meal = new MensaMealData();
            meal.setTitle(d.getString("title"));
            meal.setPriceStudentBonus(d.getFloat("priceStudentBonus"));
            meal.setPriceStudent(d.getFloat("priceStudent"));
            meal.setPriceGuest(d.getFloat("priceGuest"));
            meal.setAllergyCodes(allergyCodesFromJson(d.getJSONArray("allergyCodes")));
            meal.setFoodCharacteristics(foodCharacteristicsFromJson(d.getJSONArray("foodCharacteristics")));
            meals.add(meal);
        }
        return meals;
    }

    private AllergyCodeSet allergyCodesFromJson(JSONArray a) {
        AllergyCodeSet s = new AllergyCodeSet();
        for (Object c : a) {
            try {
                s.add(((String) c).charAt(0));
            } catch (Exception ex) {
                logger.warn("Unknown AllergyCode \"{}\"", c);
            }
        }
        return s;
    }

    private Set<MensaFoodCharacteristic> foodCharacteristicsFromJson(JSONArray a) {
        EnumSet<MensaFoodCharacteristic> s = EnumSet.noneOf(MensaFoodCharacteristic.class);
        for (Object c : a) {
            try {
                s.add(MensaFoodCharacteristic.valueOf((String) c));
            } catch (Exception ex) {
                logger.warn("Unknown FoodCharacteristic \"{}\"", c);
            }
        }
        return s;
    }

    public String toJson(MensaApiResult mar) {
        JSONObject r = new JSONObject();
        r.put("datestamp", mar.getDatestamp());
        r.put("data", toJson(mar.getData()));

        return r.toString();
    }

    private JSONObject toJson(Map<Mensa, List<? extends MensaCategory>> data) {
        JSONObject map = new JSONObject();
        for (Map.Entry<Mensa, List<? extends MensaCategory>> e : data.entrySet()) {
            JSONArray a = new JSONArray();
            for (MensaCategory c : e.getValue()) {
                a.put(fromCategory(c));
            }
            map.put(e.getKey().toString(), a);
        }
        return map;
    }

    private JSONObject fromCategory(MensaCategory c) {
        JSONObject o = new JSONObject();
        o.put("title", c.getTitle());
        o.put("priceStudentBonus", c.getPriceStudentBonus());
        o.put("priceStudent", c.getPriceStudent());
        o.put("priceGuest", c.getPriceGuest());
        JSONArray jsonMeals = new JSONArray();
        for (MensaMeal meal : c.getMeals()) {
            jsonMeals.put(fromCategory(meal));
        }
        o.put("meals", jsonMeals);
        o.put("foodCharacteristics", new JSONArray(c.getFoodCharacteristics()));
        return o;
    }

    private JSONObject fromCategory(MensaMeal m) {
        JSONObject o = new JSONObject();
        o.put("title", m.getTitle());
        o.put("priceStudentBonus", m.getPriceStudentBonus());
        o.put("priceStudent", m.getPriceStudent());
        o.put("priceGuest", m.getPriceGuest());
        o.put("allergyCodes", new JSONArray(m.getAllergyCodes()));
        o.put("foodCharacteristics", new JSONArray(m.getFoodCharacteristics()));
        return o;
    }
}
