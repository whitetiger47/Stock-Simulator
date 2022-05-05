package com.project.stocktrading.service.SIP.SIPBackground;

import com.project.stocktrading.models.SIP.ISIP;

import java.math.BigDecimal;

/**
 * @author abhishekuppe
 */
public interface ISIPBackgroundService {

    boolean checkMonthlySIPS();

    boolean shouldBuySIP(ISIP sip);

    BigDecimal getSIPChange(int sipId);

    boolean runSIPInBackground();
}
