package com.project.stocktrading.service.SIP.SIP;

import com.project.stocktrading.models.Basket.IBasket;
import com.project.stocktrading.models.SIP.ISIP;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface ISIPService {
    ArrayList<IBasket> getAllBaskets();

    int createSIPWithBaskets(ISIP sip);

    ArrayList<ISIP> getSIPs();

    BigDecimal getLatestBasketPrice(int id);

    BigDecimal getSIPPrice(int sipId);

    int deleteSIP(int id);
}
