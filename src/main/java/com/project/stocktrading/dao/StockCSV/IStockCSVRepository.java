package com.project.stocktrading.dao.StockCSV;

import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.User.IUser;

/**
 * @author abhishekuppe
 */
public interface IStockCSVRepository {

    int createStockTable();

    int createStockPriceTable();

    int insertIntoStockPriceTable(IStock stock, IUser user);

    int deleteStockTableEntries();

    int deleteStockTablePriceEntries();

    int insertIntoStockTable(IStock stock, IUser user);
}
