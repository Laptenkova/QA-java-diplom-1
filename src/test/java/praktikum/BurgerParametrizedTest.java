package praktikum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Параметризованный тестовый класс для проверки функциональности класса Burger.
 * Содержит параметризованные тесты для методов добавления ингредиентов.
 *
 * @see Burger
 */
@RunWith(Parameterized.class)
public class BurgerParametrizedTest {

    @Mock
    private Ingredient hotSauceIngredient;

    @Parameterized.Parameter
    public IngredientType ingredientType;

    @Parameterized.Parameter(1)
    public String ingredientName;

    @Parameterized.Parameter(2)
    public float ingredientPrice;

    @Parameterized.Parameter(3)
    public int expectedCount;

    private Burger burger;

    public BurgerParametrizedTest() {
        openMocks(this);
        burger = new Burger();
    }

    /**
     * Предоставляет тестовые данные для параметризованных тестов.
     * Каждый массив содержит: тип ингредиента, название, цену и ожидаемое количество.
     *
     * @return массив тестовых данных для параметризованного тестирования
     */
    @Parameterized.Parameters(name = "Тест {index}: {0} {1}")
    public static Object[][] ingredientCombinations() {
        return new Object[][]{
                {IngredientType.SAUCE, "hot sauce", 100f, 1},
                {IngredientType.FILLING, "cutlet", 200f, 2},
                {IngredientType.SAUCE, "sour cream", 150f, 3}
        };
    }

    /**
     * Параметризованный тест добавления нескольких ингредиентов.
     * Проверяет корректность добавления разного количества ингредиентов одного типа.
     */
    @Test
    public void addIngredientShouldAddMultipleIngredientsParameterized() {
        when(hotSauceIngredient.getType()).thenReturn(ingredientType);
        when(hotSauceIngredient.getName()).thenReturn(ingredientName);
        when(hotSauceIngredient.getPrice()).thenReturn(ingredientPrice);

        for (int i = 0; i < expectedCount; i++) {
            burger.addIngredient(hotSauceIngredient);
        }

        assertEquals(expectedCount, burger.ingredients.size());
    }
}