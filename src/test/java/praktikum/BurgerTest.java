package praktikum;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class BurgerTest {

    // Константы для замены магических чисел
    private static final float BUN_PRICE = 100f;
    private static final float DELTA = 0.001f;
    private static final float HOT_SAUCE_PRICE = 50f;
    private static final float CUTLET_PRICE = 75f;
    private static final float CHILI_SAUCE_PRICE = 60f;

    // Ожидаемые суммы
    private static final float BUN_ONLY_PRICE = 200f; // BUN_PRICE * 2
    private static final float TOTAL_BURGER_PRICE = 325f; // BUN_ONLY_PRICE + HOT_SAUCE_PRICE + CUTLET_PRICE

    /**
     * Мок-объект булки для тестирования
     */
    @Mock
    private Bun bun;

    /**
     * Мок-объект первого ингредиента для тестирования
     */
    @Mock
    private Ingredient hotSauceIngredient;

    /**
     * Мок-объект второго ингредиента для тестирования
     */
    @Mock
    private Ingredient cutletFillingIngredient;

    /**
     * Мок-объект третьего ингредиента для тестирования
     */
    @Mock
    private Ingredient chiliSauceIngredient;

    /**
     * Экземпляр тестируемого класса Burger
     */
    private Burger burger;

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

        when(bun.getPrice()).thenReturn(BUN_PRICE);
        when(bun.getName()).thenReturn("black bun");

        when(chiliSauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(chiliSauceIngredient.getName()).thenReturn("chili sauce");
        when(chiliSauceIngredient.getPrice()).thenReturn(CHILI_SAUCE_PRICE);
    }

    /**
     * Тестирует установку булки в бургер.
     * Проверяет, что булка корректно устанавливается в объекте бургера.
     */
    @Test
    public void setBunsShouldSetBunInBurger() {
        burger.setBuns(bun);
        assertSame(bun, burger.bun);
    }

    /**
     * Тестирует добавление ингредиента в список ингредиентов бургера.
     * Проверяет, что размер списка увеличивается после добавления.
     */
    @Test
    public void addIngredientShouldIncreaseIngredientsListSize() {
        burger.addIngredient(hotSauceIngredient);
        assertEquals(1, burger.ingredients.size());
    }

    /**
     * Тестирует, что добавленный ингредиент сохраняется в списке.
     * Проверяет корректность сохранения ссылки на ингредиент.
     */
    @Test
    public void addIngredientShouldStoreCorrectIngredient() {
        burger.addIngredient(hotSauceIngredient);
        assertSame(hotSauceIngredient, burger.ingredients.get(0));
    }

    /**
     * Тестирует удаление ингредиента по индексу.
     * Проверяет, что размер списка уменьшается после удаления.
     */
    @Test
    public void removeIngredientShouldDecreaseIngredientsListSize() {
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
        burger.removeIngredient(0);
        assertEquals(1, burger.ingredients.size());
    }

    /**
     * Тестирует, что после удаления ингредиента остается правильный элемент.
     * Проверяет корректность состава списка после удаления.
     */
    @Test
    public void removeIngredientShouldKeepCorrectIngredient() {
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
        burger.removeIngredient(0);
        assertSame(cutletFillingIngredient, burger.ingredients.get(0));
    }

    /**
     * Тестирует перемещение ингредиента на новую позицию.
     * Проверяет, что размер списка не изменяется после перемещения.
     */
    @Test
    public void moveIngredientShouldKeepListSize() {
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
        burger.addIngredient(chiliSauceIngredient);
        burger.moveIngredient(0, 2);
        assertEquals(3, burger.ingredients.size());
    }

    /**
     * Тестирует корректность новой позиции перемещенного ингредиента.
     * Проверяет, что ингредиент оказывается на правильной позиции после перемещения.
     */
    @Test
    public void moveIngredientShouldSetCorrectFirstPosition() {
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
        burger.addIngredient(chiliSauceIngredient);
        burger.moveIngredient(0, 2);
        assertSame(cutletFillingIngredient, burger.ingredients.get(0));
    }

    /**
     * Тестирует корректность установки второй позиции после перемещения ингредиента.
     * Проверяет, что ингредиент на второй позиции соответствует ожиданиям после перемещения.
     */
    @Test
    public void moveIngredientShouldSetCorrectSecondPosition() {
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
        burger.addIngredient(chiliSauceIngredient);
        burger.moveIngredient(0, 2);
        assertSame(chiliSauceIngredient, burger.ingredients.get(1));
    }

    /**
     * Тестирует корректность установки третьей позиции после перемещения ингредиента.
     * Проверяет, что ингредиент на третьей позиции соответствует ожиданиям после перемещения.
     */
    @Test
    public void moveIngredientShouldSetCorrectThirdPosition() {
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
        burger.addIngredient(chiliSauceIngredient);
        burger.moveIngredient(0, 2);
        assertSame(hotSauceIngredient, burger.ingredients.get(2));
    }

    /**
     * Тестирует расчет общей стоимости бургера с булкой и ингредиентами.
     * Проверяет корректность вычисления итоговой цены.
     */
    @Test
    public void getPriceShouldCalculateTotalPriceWithBunAndIngredients() {
        setupSauceIngredient();
        setupFillingIngredient();
        createBurgerWithBunAndTwoIngredients();

        float price = burger.getPrice();

        assertEquals(TOTAL_BURGER_PRICE, price, DELTA);
    }

    /**
     * Проверяет, что при расчете цены бургера вызываются методы getPrice()
     * у булки и каждого ингредиента.
     */
    @Test
    public void getPriceShouldCallGetPriceMethods() {
        setupSauceIngredient();
        setupFillingIngredient();
        createBurgerWithBunAndTwoIngredients();

        burger.getPrice();

        verify(bun).getPrice();
        verify(hotSauceIngredient).getPrice();
        verify(cutletFillingIngredient).getPrice();
    }


    /**
     * Тестирует формирование чека для бургера без ингредиентов.
     * Проверяет корректность форматирования и отображения только булки.
     */
    @Test
    public void getReceiptShouldFormatReceiptWithoutIngredients() {
        when(bun.getName()).thenReturn("white bun");
        burger.setBuns(bun);

        String receipt = burger.getReceipt();

        String expected = "(==== white bun ====)" + System.lineSeparator() +
                "(==== white bun ====)" + System.lineSeparator() + System.lineSeparator() +
                "Price: 200,000000" + System.lineSeparator();
        assertEquals(expected, receipt);
    }

    /**
     * Проверяет вызов методов getName() у булки при формировании чека без ингредиентов.
     */
    @Test
    public void getReceiptShouldCallGetNameForBunWithoutIngredients() {
        when(bun.getName()).thenReturn("white bun");
        burger.setBuns(bun);

        burger.getReceipt();

        verify(bun, times(2)).getName(); // Два раза: для верхней и нижней булки
    }

    /**
     * Тестирует формирование чека для бургера с ингредиентами.
     * Проверяет корректность отображения булки, ингредиентов и общей стоимости.
     */
    @Test
    public void getReceiptShouldFormatReceiptWithIngredients() {
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
     * Проверяет вызов методов getName() у булки и ингредиентов при формировании чека.
     */
    @Test
    public void getReceiptShouldCallGetNameMethodsWithIngredients() {
        setupSauceIngredient();
        setupFillingIngredient();
        createBurgerWithBunAndTwoIngredients();

        burger.getReceipt();

        verify(bun, times(2)).getName(); // Два раза: для верхней и нижней булки
        verify(hotSauceIngredient).getType();
        verify(hotSauceIngredient).getName();
        verify(cutletFillingIngredient).getType();
        verify(cutletFillingIngredient).getName();
    }


    /**
     * Тестирует генерацию исключения при попытке удаления с неверным индексом.
     * Ожидает исключение IndexOutOfBoundsException при удалении из пустого списка.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void removeIngredientShouldThrowExceptionWhenIndexInvalid() {
        burger.removeIngredient(0);
    }

    /**
     * Тестирует генерацию исключения при попытке перемещения с неверным индексом.
     * Ожидает исключение IndexOutOfBoundsException при указании несуществующего индекса.
     */
    @Test(expected = IndexOutOfBoundsException.class)
    public void moveIngredientShouldThrowExceptionWhenIndexInvalid() {
        burger.addIngredient(hotSauceIngredient);
        burger.moveIngredient(5, 0);
    }

    /**
     * Тестирует расчет стоимости бургера только с булкой.
     * Проверяет, что стоимость равна удвоенной цене булки.
     */
    @Test
    public void getPriceShouldCalculatePriceWithBunOnly() {
        burger.setBuns(bun);

        float price = burger.getPrice();

        assertEquals(BUN_ONLY_PRICE, price, DELTA);
    }

    /**
     * Тестирует вызов метода получения цены булки.
     * Проверяет, что метод getPrice() вызывается у булки.
     */
    @Test
    public void getPriceShouldCallBunGetPrice() {
        burger.setBuns(bun);

        burger.getPrice();

        verify(bun).getPrice();
    }

    /**
     * Настраивает мок ингредиента с начинкой.
     */
    private void setupFillingIngredient() {
        when(cutletFillingIngredient.getType()).thenReturn(IngredientType.FILLING);
        when(cutletFillingIngredient.getName()).thenReturn("cutlet");
        when(cutletFillingIngredient.getPrice()).thenReturn(CUTLET_PRICE);
    }

    /**
     * Создает бургер с булкой и двумя ингредиентами для тестирования.
     */
    private void createBurgerWithBunAndTwoIngredients() {
        burger.setBuns(bun);
        burger.addIngredient(hotSauceIngredient);
        burger.addIngredient(cutletFillingIngredient);
    }

    /**
     * Настраивает мок ингредиента с соусом.
     */
    private void setupSauceIngredient() {
        when(hotSauceIngredient.getType()).thenReturn(IngredientType.SAUCE);
        when(hotSauceIngredient.getName()).thenReturn("hot sauce");
        when(hotSauceIngredient.getPrice()).thenReturn(HOT_SAUCE_PRICE);
    }
}