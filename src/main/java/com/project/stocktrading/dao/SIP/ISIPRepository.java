package com.project.stocktrading.dao.SIP;

import com.project.stocktrading.models.SIP.ISIP;
import com.project.stocktrading.models.User.IUser;

import java.util.ArrayList;

/**
 * @author abhishekuppe
 */
public interface ISIPRepository {
    int createTable(String name, String createTableQuery);

    int createSIPTable();

    int createSIPDataTable();

    int createSIPWithBaskets(ISIP sip, ArrayList<Integer> basketIds, IUser user);

    ArrayList<ISIP> getSIPs(IUser user);

    ISIP getBasketIdsForSIP(int sipId);

    int updateSIPPurchasedMonth(int month, int id);

    int deleteSIPData(int sipId);

    int deleteSIP(int sipId);
}
