package com.project.stocktrading.dao.SIP;

import com.project.stocktrading.models.SIP.ISIP;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author abhishekuppe
 */
public abstract class NewSIP {
    protected ISIP getNewSIP(ResultSet resultSet, String basketIds) {
        ISIP sip = SIPAbstractFactory.getInstance().createNewSIP();
        try {
            sip.setId(resultSet.getInt("id"));
            sip.setName(resultSet.getString("name"));
            sip.setMonth_schedule(resultSet.getInt("month_schedule"));
            sip.setBasket_ids(basketIds);
            sip.setSip_purchased_month(resultSet.getInt("sip_purchased_month"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sip;
    }
}
