package com.project.stocktrading.service.Basket;

import com.project.stocktrading.dao.Basket.BasketAbstractFactory;
import com.project.stocktrading.dao.Basket.BasketRepository;
import com.project.stocktrading.dao.Basket.IBasketRepository;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.Stocks.Stocks.StockRepository;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.dao.User.UserAbstractFactory;
import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.User.IUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author abhishekuppe
 */
public class BasketServiceTest {

    private static final IBasket basket = BasketAbstractFactory.getInstance().createNewBasket();
    private static final int basketResult = 1;
    private static final ArrayList<Integer> stockIds = new ArrayList<>();
    private static final Integer stockId = 1;
    private static final Integer basketId = 1;
    private static final IStock stock = StockAbstractFactory.getInstance().createNewStock();
    private static final ArrayList<IStock> stockArrayList = new ArrayList<>();
    private static final BigDecimal stockPrice = new BigDecimal(100);
    private static final IUser user = UserAbstractFactory.getInstance().createNewUser();
    private static final ArrayList<IBasket> baskets = new ArrayList<>();
    private static IBasketService basketService;

    @BeforeAll
    public static void init() {
        IBasketRepository basketRepository = Mockito.mock(BasketRepository.class);
        IStockRepository stockRepository = Mockito.mock(StockRepository.class);
        IUserActions userActions = Mockito.mock(UserRepository.class);

        basket.setId(basketId);
        basket.setPrice(stockPrice);
        basket.setName("one");
        basket.setChange(new BigDecimal(1));
        basket.setStock_ids(new ArrayList<>());
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(stockId);
        basket.setStock_ids(arrayList);
        basket.setCustom_table_value("1_2");

        baskets.add(basket);

        Integer userId = 1;
        user.setId(userId);
        user.setEmail("dummy@gmail.com");
        user.setFunds(new BigDecimal(100));
        user.setFirstName("Abhishek");
        user.setLastName("Uppe");
        user.setResidentialId("Halifax");

        stock.setId(stockId);
        stock.setName("temp");
        stock.setAbbreviation("temp");
        stock.setPrice(stockPrice);
        stock.setInitialPublicOffering(new Date());
        stock.setBuyCount(new BigInteger("1"));
        stock.setSellCount(new BigInteger("1"));
        stock.setDifference(new BigDecimal(100));
        stock.setDateTimeCreated(new Date());

        stockArrayList.add(stock);


        stockIds.add(1);
        stockIds.add(2);

        Mockito.when(userActions.getUserLoggedIn()).thenReturn(user);

        Mockito.when(basketRepository.getStockIdsForBasket(1)).thenReturn(basket);

        Mockito.when(basketRepository.createBasketWithStocks(basket, stockIds, user)).thenReturn(basketResult);
        Mockito.when(basketRepository.getBaskets(user)).thenReturn(baskets);

        Mockito.when(stockRepository.getAllStocks()).thenReturn(stockArrayList);

        Mockito.when(stockRepository.getLatestStockPrice(stockId)).thenReturn(stock);
        Mockito.when(stockRepository.getSecondLatestStockPrice(stockId)).thenReturn(stock);

        basketService = new BasketService(basketRepository, stockRepository, userActions);
    }

    @Test
    public void testGetAllStocks() {
        assertEquals(basketService.getAllStocks(), stockArrayList);
    }

    @Test
    public void testLatestStockPrice() {
        assertEquals(basketService.getLatestStockPrice(stockId), stock);
    }

    @Test
    public void testCreateBasketWithStocks() {
        assertEquals(basketService.createBasketWithStocks(basket), basketResult);
    }

    @Test
    public void testGetBaskets() {
        assertEquals(basketService.getBaskets(), baskets);
    }

    @Test
    public void testGetSecondLatestBasketPrice() {
        assertEquals(basketService.getSecondLatestBasketPrice(basketId), basket.getPrice());
    }

    @Test
    public void testGetLatestBasketPrice() {
        assertEquals(basketService.getLatestBasketPrice(basketId), basket.getPrice());
    }

    @Test
    public void testGetBasketChange() {
        assertEquals(basketService.getBasketChange(basketId), stockPrice.subtract(stockPrice).divide(new BigDecimal(100)));
    }
}
