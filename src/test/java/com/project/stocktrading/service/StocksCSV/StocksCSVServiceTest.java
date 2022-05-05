package com.project.stocktrading.service.StocksCSV;

import com.project.stocktrading.dao.StockCSV.IStockCSVRepository;
import com.project.stocktrading.dao.StockCSV.StockCSVAbstractFactory;
import com.project.stocktrading.dao.StockCSV.StockCSVRepository;
import com.project.stocktrading.dao.Stocks.Stocks.StockAbstractFactory;
import com.project.stocktrading.dao.User.IUserActions;
import com.project.stocktrading.dao.User.UserAbstractFactory;
import com.project.stocktrading.dao.User.UserRepository;
import com.project.stocktrading.models.Stock.IStock;
import com.project.stocktrading.models.StockCSVFile.IStockCSVFile;
import com.project.stocktrading.models.User.IUser;
import com.project.stocktrading.service.StockCSV.IStockCSVFileService;
import com.project.stocktrading.service.StockCSV.StockCSVFileService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author abhishekuppe
 */
public class StocksCSVServiceTest {

    private static final int insertSuccess = 1;
    private static final IStock stock = StockAbstractFactory.getInstance().createNewStock();
    private static final IUser user = UserAbstractFactory.getInstance().createNewUser();
    private static final IStockCSVFile stockCSVFile = StockCSVAbstractFactory.getInstance().createNewStockCSV();
    private static IStockCSVFileService iStockCSVFileService;

    @BeforeAll
    public static void init() {
        IStockCSVRepository stockCSVRepository = Mockito.mock(StockCSVRepository.class);
        IUserActions userActions = Mockito.mock(UserRepository.class);

        Integer userId = 1;
        user.setId(userId);
        user.setEmail("dummy@gmail.com");
        user.setFunds(new BigDecimal(100));
        user.setFirstName("Abhishek");
        user.setLastName("Uppe");
        user.setResidentialId("Halifax");

        Mockito.when(stockCSVRepository.createStockTable()).thenReturn(insertSuccess);
        Mockito.when(stockCSVRepository.deleteStockTableEntries()).thenReturn(insertSuccess);
        Mockito.when(stockCSVRepository.createStockPriceTable()).thenReturn(insertSuccess);
        Mockito.when(stockCSVRepository.deleteStockTablePriceEntries()).thenReturn(insertSuccess);
        Mockito.when(stockCSVRepository.insertIntoStockTable(stock, user)).thenReturn(insertSuccess);
        Mockito.when(stockCSVRepository.insertIntoStockTable(stock, user)).thenReturn(insertSuccess);
        Mockito.when(userActions.getUserLoggedIn()).thenReturn(user);

        iStockCSVFileService = new StockCSVFileService(userActions, stockCSVRepository);

        stockCSVFile.setFile(new MultipartFile() {
            @Override
            public String getName() {
                return null;
            }

            @Override
            public String getOriginalFilename() {
                return "Testing";
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return true;
            }

            @Override
            public long getSize() {
                return 0;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return new byte[0];
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return new InputStream() {
                    @Override
                    public int read() throws IOException {
                        return 0;
                    }
                };
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {

            }
        });
    }

    @Test
    public void uploadStocksTest() {
        assertEquals(iStockCSVFileService.uploadStocks(stockCSVFile), "Empty File");
    }
}
