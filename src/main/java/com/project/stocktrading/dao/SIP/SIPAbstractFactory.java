package com.project.stocktrading.dao.SIP;

import com.project.stocktrading.models.SIP.ISIP;

/**
 * @author abhishekuppe
 */
public abstract class SIPAbstractFactory {
    private static SIPAbstractFactory sipAbstractFactory = null;

    public static SIPAbstractFactory getInstance() {
        if (sipAbstractFactory == null) {
            sipAbstractFactory = new SIPFactory();
        }
        return sipAbstractFactory;
    }

    public abstract ISIP createNewSIP();

    public abstract ISIPRepository createNewSIPRepository();
}
