package com.project.stocktrading.dao.StockCSV;


import com.project.stocktrading.models.StockCSVFile.IStockCSVFile;
import com.project.stocktrading.models.StockCSVFile.StockCSVFile;

/**
 * @author abhishekuppe
 */
public class StockCSVFactory extends StockCSVAbstractFactory {

    @Override
    public IStockCSVRepository createNewStockCSVRepository() {
        return new StockCSVRepository();
    }

    @Override
    public IStockCSVFile createNewStockCSV() {
        return new StockCSVFile();
    }
}
