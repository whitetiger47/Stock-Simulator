package com.project.stocktrading.service.SIP.SIPBackground;

import com.project.stocktrading.dao.Basket.IBasketRepository;
import com.project.stocktrading.dao.SIP.ISIPRepository;
import com.project.stocktrading.dao.Stocks.Stocks.IStockRepository;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.service.Basket.IBasketService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author abhishekuppe
 */
@Service
public class SIPBackgroundService implements ISIPBackgroundService {

    private final IUserActions iUserActions;
    private final ISIPRepository sipRepository;
    private final IBasketRepository basketRepository;
    private final IBasketService basketService;
    private final IStockRepository stockRepository;

    public SIPBackgroundService(IUserActions iUserActions, ISIPRepository sipRepository, IBasketRepository basketRepository, IBasketService basketService, IStockRepository stockRepository) {
        this.iUserActions = iUserActions;
        this.sipRepository = sipRepository;
        this.basketRepository = basketRepository;
        this.basketService = basketService;
        this.stockRepository = stockRepository;
    }

    public boolean shouldBuySIP(ISIP sip) {
        boolean isCurrentDateMatched = Calendar.getInstance().get(Calendar.DATE) == sip.getMonth_schedule();
        boolean isSIPPurchasedDateNull = sip.getSip_purchased_month() == 1000;
        int previousMonth = sip.getSip_purchased_month();
        int currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        boolean isSIPNotPurchased = (previousMonth < currentMonth) || (previousMonth == 11 && currentMonth == 0);
        return isCurrentDateMatched && (isSIPNotPurchased || isSIPPurchasedDateNull);
    }

    public boolean checkMonthlySIPS() {
        IUser user = iUserActions.getUserLoggedIn();
        ArrayList<ISIP> sips = this.sipRepository.getSIPs(user);
        BigDecimal funds = iUserActions.getUserLoggedIn().getFunds();
        BigDecimal totalCost = new BigDecimal("0");
        for (ISIP sip : sips) {
            if (shouldBuySIP(sip)) {
                this.sipRepository.updateSIPPurchasedMonth(new Date().getMonth(), sip.getId());
                for (String basketId : sip.getBasket_ids().split(",")) {
                    ArrayList<Integer> stockIds = basketRepository.getStockIdsForBasket(Integer.parseInt(basketId)).getStock_ids();
                    for (Integer stockId : stockIds) {
                        totalCost = totalCost.add(this.stockRepository.getLatestStockPrice(stockId).getPrice());
                    }
                }
            }
        }

        if (totalCost.compareTo(new BigDecimal(0)) > 0 && totalCost.compareTo(funds) < 0) {
            user.setFunds(funds.subtract(totalCost));
            iUserActions.updateFunds(user);
        }
        return true;
    }

    public boolean runSIPInBackground() {
        Timer timer = new Timer();
        int INITIAL_DELAY = 1000;
        int SIP_RUNNING_DURATION = 10000 * 100;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkMonthlySIPS();
            }
        }, INITIAL_DELAY, SIP_RUNNING_DURATION);
        return true;
    }

    public BigDecimal getSIPChange(int sipId) {
        ISIP sip = this.sipRepository.getBasketIdsForSIP(sipId);
        String[] basketList;

        if (sip.getBasket_ids() == null) {
            basketList = new String[]{};
        } else {
            basketList = sip.getBasket_ids().split(",");
        }

        BigDecimal totalChange = BigDecimal.valueOf(0);

        for (int i = 0; i < basketList.length; i++) {
            if (Objects.equals(basketList[i], "")) {
                totalChange = new BigDecimal(0);
            } else {
                totalChange = (totalChange.add(this.basketService.getLatestBasketPrice(Integer.parseInt(basketList[i]))
                        .subtract(this.basketService.getSecondLatestBasketPrice(Integer.parseInt(basketList[i])))))
                        .divide(BigDecimal.valueOf(100));
            }
        }
        return totalChange;
    }
}
