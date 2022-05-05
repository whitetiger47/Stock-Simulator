package com.project.stocktrading.service.SIP.SIP;

import com.project.stocktrading.dao.Basket.BasketAbstractFactory;
import com.project.stocktrading.dao.Basket.BasketRepository;
import com.project.stocktrading.dao.Basket.IBasketRepository;
import com.project.stocktrading.dao.SIP.ISIPRepository;
import com.project.stocktrading.dao.SIP.SIPAbstractFactory;
import com.project.stocktrading.dao.SIP.SIPRepository;
import com.project.stocktrading.dao.StockCSV.IStockCSVRepository;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.Stocks.Stocks.StockRepository;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.dao.User.UserAbstractFactory;
import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.service.Basket.BasketService;
import com.project.stocktrading.service.Basket.IBasketService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author abhishekuppe
 */
public class SIPServiceTest {

    private static final IUser user = UserAbstractFactory.getInstance().createNewUser();
    private static final IBasket basket = BasketAbstractFactory.getInstance().createNewBasket();
    private static final ArrayList<IBasket> baskets = new ArrayList<>();
    private static final Integer sipId = 1;
    private static final Integer successInsert = 1;
    private static final BigDecimal stockPrice = new BigDecimal(100);
    private static final ISIP sip = SIPAbstractFactory.getInstance().createNewSIP();
    private static final ArrayList<ISIP> sipArrayList = new ArrayList<>();
    private static final IStock stock = StockAbstractFactory.getInstance().createNewStock();
    private static final Integer basketId = 1;
    private static ISIPService sipService;
    private static BigDecimal basketPrice = new BigDecimal(0);
    private static BigDecimal sipPrice = new BigDecimal(0);

    @BeforeAll
    public static void init() {
        IBasketRepository basketRepository = Mockito.mock(BasketRepository.class);
        IStockRepository stockRepository = Mockito.mock(StockRepository.class);
        ISIPRepository sipRepository = Mockito.mock(SIPRepository.class);
        IStockCSVRepository stockCSVRepository = Mockito.mock(IStockCSVRepository.class);
        IUserActions userActions = Mockito.mock(UserRepository.class);

        IBasketService basketService = new BasketService(basketRepository, stockRepository, userActions);

        Integer basketId = 1;
        basket.setId(basketId);
        basket.setPrice(stockPrice);
        basket.setName("one");
        basket.setChange(new BigDecimal(1));
        basket.setStock_ids(new ArrayList<>());
        basketPrice = basketPrice.add(stockPrice);
        sipPrice = sipPrice.add(basketPrice);

        ArrayList<Integer> arrayList = new ArrayList<>();

        Integer stockId = 1;
        arrayList.add(stockId);
        basket.setStock_ids(arrayList);
        basket.setCustom_table_value("1_2");

        baskets.add(basket);

        ArrayList<Integer> sipIds = new ArrayList<>();

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


        sip.setId(sipId);
        sip.setMonth_schedule(Calendar.getInstance().get(Calendar.DATE));
        sip.setSip_purchased_month(1000);
        sip.setCustom_table_value("1_2");
        sip.setBasket_ids("1");
        sipIds.add(1);
        sipIds.add(2);

        sipArrayList.add(sip);

        Mockito.when(basketRepository.getBaskets(user)).thenReturn(baskets);
        Mockito.when(userActions.getUserLoggedIn()).thenReturn(user);

        Mockito.when(sipRepository.deleteSIPData(sipId)).thenReturn(sipId);
        Mockito.when(sipRepository.deleteSIP(sipId)).thenReturn(sipId);

        Mockito.when(sipRepository.createSIPWithBaskets(sip, sipIds, user)).thenReturn(successInsert);
        Mockito.when(sipRepository.getSIPs(user)).thenReturn(sipArrayList);


        Mockito.when(basketRepository.getStockIdsForBasket(basketId)).thenReturn(basket);
        Mockito.when(stockRepository.getLatestStockPrice(stockId)).thenReturn(stock);
        Mockito.when(stockRepository.getSecondLatestStockPrice(stockId)).thenReturn(stock);
        Mockito.when(sipRepository.getBasketIdsForSIP(sipId)).thenReturn(sip);

        sipService = new SIPService(sipRepository, basketRepository, stockCSVRepository, basketService, userActions);
    }

    @Test
    public void getAllBasketsTest() {
        assertEquals(sipService.getAllBaskets(), baskets);
    }


    @Test
    public void deleteSIPTest() {
        assertEquals(sipService.deleteSIP(sipId), sipId);
    }

    @Test
    public void createSIPWithBasketsTest() {
        assertEquals(sipService.createSIPWithBaskets(sip), successInsert);
    }

    @Test
    public void getSIPsTest() {
        assertEquals(sipService.getSIPs(), sipArrayList);
    }

    @Test
    public void getLatestBasketPriceTest() {
        assertEquals(sipService.getLatestBasketPrice(basketId), basketPrice);
    }


    @Test
    public void getSIPPriceTest() {
        assertEquals(sipService.getSIPPrice(sipId), basketPrice);
    }
}

