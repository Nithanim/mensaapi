package jkumensa.api.serialization;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashSet;
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
import org.junit.Assert;
import org.junit.Test;

public class OrgjsonMensaSerializationTest {
    @Test
    public void test() {
        Map<Mensa, List<? extends MensaCategory>> expectedData = new EnumMap(Mensa.class);
        expectedData.put(Mensa.CLASSIC,
            Arrays.asList(new MensaCategoryData(
                    "Classic 1",
                    Arrays.asList(new MensaMealData(
                            "Klare Gemüsesuppe mit Schöberl",
                            -1,
                            -1,
                            -1,
                            setOf('C', 'G', 'L'),
                            Collections.EMPTY_SET
                        ),
                        new MensaMealData(
                            "Mini Frühlingsröllchen mit gebratenem Wokgemüse",
                            -1,
                            -1,
                            -1,
                            setOf('A', 'F', 'N'),
                            Collections.EMPTY_SET
                        ),
                        new MensaMealData(
                            "und Basmatireis , dazu Blattsalat",
                            -1,
                            -1,
                            -1,
                            setOf('E'),
                            Collections.EMPTY_SET
                        )
                    ),
                    5f,
                    5f,
                    5.7f,
                    setOf(MensaFoodCharacteristic.VEGAN)
                )
            )
        );
        expectedData.put(Mensa.CHOICE,
            Arrays.asList(new MensaCategoryData(
                    "Pasta",
                    Arrays.asList(new MensaMealData(
                            "Spaghetti Bolognese/Speck-Champignon-Rahmsauce",
                            -1,
                            -1,
                            3.9f,
                            new AllergyCodeSet(),
                            setOf(MensaFoodCharacteristic.VEGETARIAN)
                        )
                    ),
                    -1,
                    -1,
                    -1,
                    Collections.EMPTY_SET
                ),
                new MensaCategoryData(
                    "Eintopf",
                    Arrays.asList(new MensaMealData(
                            "Wiener Schnitzel vom Schwein mit Reis",
                            -1,
                            -1,
                            5.1f,
                            new AllergyCodeSet(),
                            Collections.EMPTY_SET
                        )
                    ),
                    -1,
                    -1,
                    -1,
                    Collections.EMPTY_SET
                )
            )
        );
        
        MensaApiResultData expected = new MensaApiResultData(new Date(2018-1900, 4-1, 9), expectedData);

        OrgjsonMensaSerialization ser = new OrgjsonMensaSerialization();
        String json = ser.toJson(expected);
        MensaApiResult acutal = ser.fromJson(json);
        assertMensaApiResult(expected, acutal);
    }
    
    private AllergyCodeSet setOf(Character... cs) {
        AllergyCodeSet s = new AllergyCodeSet();
        s.addAll(Arrays.asList(cs));
        return s;
    }

    private <T> Set<T> setOf(T... elements) {
        if (elements.length == 0) {
            return Collections.emptySet();
        } else if (elements[0] instanceof Enum) {
            EnumSet s = EnumSet.noneOf((Class<Enum>) elements[0].getClass());
            s.addAll(Arrays.asList(elements));
            return s;
        } else {
            HashSet<T> s = new HashSet<T>();
            s.addAll(Arrays.asList(elements));
            return s;
        }
    }
    private void assertMensaApiResult(MensaApiResult expected, MensaApiResult actual) {
        Assert.assertEquals(expected.getDate(), actual.getDate());
        assertData(expected.getData(), actual.getData());
    }

    private void assertData(Map<Mensa, List<? extends MensaCategory>> expected, Map<Mensa, List<? extends MensaCategory>> actual) {
        Assert.assertEquals(expected.size(), actual.size());
        for (Mensa k : expected.keySet()) {
            try {
            List<? extends MensaCategory> e = expected.get(k);
            List<? extends MensaCategory> a = actual.get(k);
            
            assertCategories(e, a);
            } catch(Throwable err) {
                throw new AssertionError("Error asserting Mensa " + k, err);
            }
        }
    }

    private void assertCategories(List<? extends MensaCategory> expected, List<? extends MensaCategory> actual) {
        int i = 0;
        try {
            for (; i < expected.size(); i++) {
                MensaCategory catExpected = expected.get(i);
                MensaCategory catActual = actual.get(i);

                assertCategory(catExpected, catActual);
            }
        } catch (Throwable err) {
            throw new AssertionError("Error asserting Category" + i, err);
        }
    }

    private void assertCategory(MensaCategory catExpected, MensaCategory catActual) throws AssertionError {
        Assert.assertEquals(catExpected.getTitle(), catActual.getTitle());
        Assert.assertEquals(catExpected.getPriceGuest(), catActual.getPriceGuest(), 0.1);
        Assert.assertEquals(catExpected.getPriceStudent(), catActual.getPriceStudent(), 0.1);
        Assert.assertEquals(catExpected.getPriceStudentBonus(), catActual.getPriceStudentBonus(), 0.1);
        Assert.assertEquals(catExpected.getFoodCharacteristics(), catActual.getFoodCharacteristics());

        int i = 0;
        try {
            for (; i < catExpected.getMeals().size(); i++) {
                MensaMeal mealExpected = catExpected.getMeals().get(i);
                MensaMeal mealActual = catActual.getMeals().get(i);

                assertMeal(mealExpected, mealActual);
            }
        } catch (Throwable err) {
            throw new AssertionError("Error asserting Meal" + i, err);
        }
    }

    private void assertMeal(MensaMeal mealExpected, MensaMeal mealActual) {
        Assert.assertEquals(mealExpected.getTitle(), mealActual.getTitle());
        Assert.assertEquals(mealExpected.getPriceStudentBonus(), mealActual.getPriceStudentBonus(), 0.1);
        Assert.assertEquals(mealExpected.getPriceStudent(), mealActual.getPriceStudent(), 0.1);
        Assert.assertEquals(mealExpected.getPriceGuest(), mealActual.getPriceGuest(), 0.1);
        Assert.assertEquals(mealExpected.getFoodCharacteristics(), mealActual.getFoodCharacteristics());
        Assert.assertEquals(mealExpected.getAllergyCodes(), mealActual.getAllergyCodes());
    }
}
