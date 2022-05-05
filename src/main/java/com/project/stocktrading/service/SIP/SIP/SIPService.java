package com.project.stocktrading.service.SIP.SIP;

import com.project.stocktrading.dao.Basket.IBasketRepository;
import com.project.stocktrading.dao.SIP.ISIPRepository;
import com.project.stocktrading.dao.StockCSV.IStockCSVRepository;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.service.Basket.IBasketService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author abhishekuppe
 */

@Service
public class SIPService implements ISIPService {

    private final ISIPRepository sipRepository;
    private final IBasketRepository basketRepository;

    private final IBasketService basketService;
    private final IUserActions iUserActions;
    private final IStockRepository stockRepository;


    public SIPService(ISIPRepository sipRepository, IBasketRepository basketRepository, IStockCSVRepository stockCSVRepository,
                      IBasketService basketService, IUserActions iUserActions) {

        this.sipRepository = sipRepository;
        this.sipRepository.createSIPTable();
        this.sipRepository.createSIPDataTable();

        this.basketRepository = basketRepository;
        this.basketService = basketService;
        this.iUserActions = iUserActions;
        this.stockRepository = StockAbstractFactory.getInstance().createNewStockRepository();

        stockCSVRepository.createStockTable();
        stockCSVRepository.createStockPriceTable();
    }

    public ArrayList<IBasket> getAllBaskets() {
        return this.basketRepository.getBaskets(iUserActions.getUserLoggedIn());
    }

    public int deleteSIP(int id) {
        this.sipRepository.deleteSIPData(id);
        return this.sipRepository.deleteSIP(id);
    }

    public int createSIPWithBaskets(ISIP sip) {
        String customTableValue = sip.getCustom_table_value();
        ArrayList<Integer> sipIds = new ArrayList<>();
        String[] array = customTableValue.split("_");
        for (String ele : array) {
            sipIds.add(Integer.valueOf(ele));
        }
        return this.sipRepository.createSIPWithBaskets(sip, sipIds, iUserActions.getUserLoggedIn());
    }

    public ArrayList<ISIP> getSIPs() {
        return this.sipRepository.getSIPs(iUserActions.getUserLoggedIn());
    }

    public BigDecimal getLatestBasketPrice(int id) {
        return this.basketService.getLatestBasketPrice(id);
    }

    public BigDecimal getSIPPrice(int sipId) {
        ISIP sip = this.sipRepository.getBasketIdsForSIP(sipId);
        String[] basketList = sip.getBasket_ids().split(",");
        BigDecimal totalSum = BigDecimal.valueOf(0);

        for (String basketId : basketList) {
            if (Objects.equals(basketId, "")) {
                totalSum = new BigDecimal(0);
            } else {
                totalSum = totalSum.add(this.basketService.getLatestBasketPrice(Integer.parseInt(basketId)));
            }
        }
        return totalSum;
    }
}
