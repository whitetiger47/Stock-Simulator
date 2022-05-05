package com.project.stocktrading.service.StockCSV;

import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.StockCSVFile.IStockCSVFile;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author abhishekuppe
 */
public interface IStockCSVFileService {

    IStock setStockValues(int id, String name, String abbreviation, BigDecimal price, Date initialPublicOffering);

    String uploadStocks(IStockCSVFile stockCSVFile);
}
