package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

/**
 * Тестовый класс для проверки функциональности класса Burger.
 * Содержит unit-тесты для методов установки булки, добавления, удаления и перемещения ингредиентов,
 * расчета стоимости и формирования чека.
 *
 * @see Burger
 */
@RunWith(Parameterized.class)
public class BurgerTest {

    /** Мок-объект булки для тестирования */
    @Mock
    private Bun bun;

    /** Мок-объект первого ингредиента для тестирования */
    @Mock
    private Ingredient ingredient1;

    /** Мок-объект второго ингредиента для тестирования */
    @Mock
    private Ingredient ingredient2;

    /** Мок-объект третьего ингредиента для тестирования */
    @Mock
    private Ingredient ingredient3;

    /** Тип ингредиента для параметризованного теста */
    @Parameterized.Parameter
    public IngredientType ingredientType;

    /** Название ингредиента для параметризованного теста */
    @Parameterized.Parameter(1)
    public String ingredientName;

    /** Цена ингредиента для параметризованного теста */
    @Parameterized.Parameter(2)
    public float ingredientPrice;

    /** Ожидаемое количество ингредиентов для параметризованного теста */
    @Parameterized.Parameter(3)
    public int expectedCount;

    /** Экземпляр тестируемого класса Burger */
    private Burger burger;

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
     * Конструктор, инициализирующий моки с помощью Mockito.
     */
    public BurgerTest() {
        openMocks(this);
    }

    /**
     * Подготавливает тестовое окружение перед каждым тестом.
     * Создает новый экземпляр бургера и настраивает мок-объекты.
     */
    @Before
    public void setUp() {
        burger = new Burger();

        when(bun.getPrice()).thenReturn(100f);
        when(bun.getName()).thenReturn("black bun");

        when(ingredient3.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient3.getName()).thenReturn("chili sauce");
        when(ingredient3.getPrice()).thenReturn(60f);
    }

    /**
     * Тестирует установку булки в бургер.
     * Проверяет, что булка корректно устанавливается в объекте бургера.
     */
    @Test
    public void setBuns_ShouldSetBunInBurger() {
        burger.setBuns(bun);
        assertSame(bun, burger.bun);
    }

    /**
     * Тестирует добавление ингредиента в список ингредиентов бургера.
     * Проверяет, что ингредиент добавляется и список увеличивается на 1 элемент.
     */
    @Test
    public void addIngredient_ShouldAddIngredientToIngredientsList() {
        burger.addIngredient(ingredient1);

        assertEquals(1, burger.ingredients.size());
        assertSame(ingredient1, burger.ingredients.get(0));
    }

    /**
     * Тестирует удаление ингредиента по индексу.
     * Проверяет, что после удаления ингредиента список уменьшается и содержит правильный элемент.
     */
    @Test
    public void removeIngredient_ShouldRemoveIngredientByIndex() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);

        burger.removeIngredient(0);

        assertEquals(1, burger.ingredients.size());
        assertSame(ingredient2, burger.ingredients.get(0));
    }

    /**
     * Тестирует перемещение ингредиента на новую позицию.
     * Проверяет корректность изменения порядка ингредиентов в списке.
     */
    @Test
    public void moveIngredient_ShouldMoveIngredientToNewPosition() {
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
        burger.addIngredient(ingredient3);

        burger.moveIngredient(0, 2);

        assertEquals(3, burger.ingredients.size());
        assertSame(ingredient2, burger.ingredients.get(0));
        assertSame(ingredient3, burger.ingredients.get(1));
        assertSame(ingredient1, burger.ingredients.get(2));
    }

    /**
     * Тестирует расчет общей стоимости бургера с булкой и ингредиентами.
     * Проверяет корректность вычисления и вызовы методов получения цен.
     */
    @Test
    public void getPrice_ShouldCalculateTotalPriceWithBunAndIngredients() {
        setupSauceIngredient();
        setupFillingIngredient();
        createBurgerWithBunAndTwoIngredients();

        float price = burger.getPrice();

        assertEquals(325f, price, 0.001f);
        verify(bun, times(1)).getPrice();
        verify(ingredient1).getPrice();
        verify(ingredient2).getPrice();
    }

    /**
     * Тестирует формирование чека для бургера без ингредиентов.
     * Проверяет корректность форматирования и отображения только булки.
     */
    @Test
    public void getReceipt_ShouldFormatReceiptWithoutIngredients() {
        when(bun.getName()).thenReturn("white bun");
        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        String expected = "(==== white bun ====)" + System.lineSeparator() +
                "(==== white bun ====)" + System.lineSeparator() + System.lineSeparator() +
                "Price: 200,000000" + System.lineSeparator();
        assertEquals(expected, receipt);
    }

    /**
     * Тестирует формирование чека для бургера с ингредиентами.
     * Проверяет корректность отображения булки, ингредиентов и общей стоимости.
     */
    @Test
    public void getReceipt_ShouldFormatReceiptWithIngredients() {
        setupSauceIngredient();
        setupFillingIngredient();
        createBurgerWithBunAndTwoIngredients();

        String receipt = burger.getReceipt();

        String expected = "(==== black bun ====)" + System.lineSeparator() +
                "= sauce hot sauce =" + System.lineSeparator() +
                "= filling cutlet =" + System.lineSeparator() +
                "(==== black bun ====)" + System.lineSeparator() + System.lineSeparator() +
                "Price: 325,000000" + System.lineSeparator();
        assertEquals(expected, receipt);
    }

    /**
     * Тестирует генерацию исключения при попытке удаления с неверным индексом.
     * Ожидает исключение IndexOutOfBoundsException при удалении из пустого списка.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredient_ShouldThrowExceptionWhenIndexInvalid() {
        burger.removeIngredient(0);
    }

    /**
     * Тестирует генерацию исключения при попытке перемещения с неверным индексом.
     * Ожидает исключение IndexOutOfBoundsException при указании несуществующего индекса.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredient_ShouldThrowExceptionWhenIndexInvalid() {
        burger.addIngredient(ingredient1);
        burger.moveIngredient(5, 0);
    }

    /**
     * Тестирует расчет стоимости бургера только с булкой.
     * Проверяет, что стоимость равна удвоенной цене булки.
     */
    @Test
    public void getPrice_ShouldCalculatePriceWithBunOnly() {
        burger.setBuns(bun);

        float price = burger.getPrice();

        assertEquals(200f, price, 0.001f);
        verify(bun, times(1)).getPrice();
    }

    /**
     * Параметризованный тест добавления нескольких ингредиентов.
     * Проверяет корректность добавления разного количества ингредиентов одного типа.
     */
    @Test
    public void addIngredient_ShouldAddMultipleIngredientsParameterized() {
        when(ingredient1.getType()).thenReturn(ingredientType);
        when(ingredient1.getName()).thenReturn(ingredientName);
        when(ingredient1.getPrice()).thenReturn(ingredientPrice);

        for (int i = 0; i < expectedCount; i++) {
            burger.addIngredient(ingredient1);
        }

        assertEquals(expectedCount, burger.ingredients.size());
    }

    /**
     * Настраивает мок ингредиента с начинкой.
     */
    private void setupFillingIngredient() {
        when(ingredient2.getType()).thenReturn(IngredientType.FILLING);
        when(ingredient2.getName()).thenReturn("cutlet");
        when(ingredient2.getPrice()).thenReturn(75f);
    }

    /**
     * Создает бургер с булкой и двумя ингредиентами для тестирования.
     */
    private void createBurgerWithBunAndTwoIngredients() {
        burger.setBuns(bun);
        burger.addIngredient(ingredient1);
        burger.addIngredient(ingredient2);
    }

    /**
     * Настраивает мок ингредиента с соусом.
     */
    private void setupSauceIngredient() {
        when(ingredient1.getType()).thenReturn(IngredientType.SAUCE);
        when(ingredient1.getName()).thenReturn("hot sauce");
        when(ingredient1.getPrice()).thenReturn(50f);
    }

}