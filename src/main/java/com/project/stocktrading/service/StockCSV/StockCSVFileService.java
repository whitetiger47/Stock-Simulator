package com.project.stocktrading.service.StockCSV;

import com.project.stocktrading.dao.StockCSV.IStockCSVRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.StockCSVFile.IStockCSVFile;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;


/**
 * @author abhishekuppe
 */

@Service
public class StockCSVFileService implements IStockCSVFileService {

    private final IUserActions iUserActions;
    private final IStockCSVRepository stockCSVRepository;

    public StockCSVFileService(IUserActions iUserActions, IStockCSVRepository stockCSVRepository) {
        this.stockCSVRepository = stockCSVRepository;
        this.iUserActions = iUserActions;
    }

    public IStock setStockValues(int id, String name, String abbreviation, BigDecimal price, Date initialPublicOffering) {
        IStock stock = StockAbstractFactory.getInstance().createNewStock();
        stock.setId(id);
        stock.setName(name);
        stock.setAbbreviation(abbreviation);
        stock.setPrice(price);
        stock.setInitialPublicOffering(initialPublicOffering);
        return stock;
    }

    public String uploadStocks(IStockCSVFile stockCSVFile) {
        MultipartFile multipartFile = stockCSVFile.getFile();
        if (multipartFile.isEmpty()) {
            return "Empty File";
        }

        String folderPath = "src/main/resources/StocksCSV/";
        String currentFileName = StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try {
            Files.copy(multipartFile.getInputStream(), Paths.get(folderPath + currentFileName), StandardCopyOption.REPLACE_EXISTING);
            BufferedReader bufferedReader = new BufferedReader(new FileReader(folderPath + currentFileName));
            boolean isFirstLine = true;
            String line;
            while (true) {
                line = bufferedReader.readLine();

                if (line == null) {
                    break;
                }
                if (isFirstLine) {
                    isFirstLine = false;
                    this.stockCSVRepository.createStockTable();
                    this.stockCSVRepository.deleteStockTableEntries();
                    this.stockCSVRepository.createStockPriceTable();
                    this.stockCSVRepository.deleteStockTablePriceEntries();
                    continue;
                }

                String[] stockValues = line.split(",");
                try {
                    IStock stock = setStockValues(Integer.parseInt(stockValues[0]), stockValues[1], stockValues[2],
                            new BigDecimal(stockValues[3]), new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(stockValues[4]));
                    this.stockCSVRepository.insertIntoStockTable(stock, iUserActions.getUserLoggedIn());
                    this.stockCSVRepository.insertIntoStockPriceTable(stock, iUserActions.getUserLoggedIn());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            bufferedReader.close();
            return "Uploaded Successfully";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Failure";
    }
}
