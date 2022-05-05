package com.project.stocktrading.dao.SIP;


import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.models.SIP.SIP;

/**
 * @author abhishekuppe
 */
public class SIPFactory extends SIPAbstractFactory {
    @Override
    public ISIP createNewSIP() {
        return new SIP();
    }

    @Override
    public ISIPRepository createNewSIPRepository() {
        return new SIPRepository();
    }
}
