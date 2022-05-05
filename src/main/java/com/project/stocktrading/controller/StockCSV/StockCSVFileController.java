package com.project.stocktrading.controller.StockCSV;

import com.project.stocktrading.dao.StockCSV.StockCSVAbstractFactory;
import com.project.stocktrading.models.StockCSVFile.StockCSVFile;
import com.project.stocktrading.service.StockCSV.IStockCSVFileService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class StockCSVFileController {

    private final IStockCSVFileService stockFileStorageService;

    public StockCSVFileController(IStockCSVFileService stockFileStorageService) {
        this.stockFileStorageService = stockFileStorageService;
    }

    @GetMapping("/csv_upload")
    public String CSVUploadHome(Model model) {
        model.addAttribute("stockCSVFile", StockCSVAbstractFactory.getInstance().createNewStockCSV());
        return "csv/index";
    }

    @PostMapping("/csv_upload")
    public String uploadCSVFile(@ModelAttribute StockCSVFile stockCSVFile) {
        this.stockFileStorageService.uploadStocks(stockCSVFile);
        return "csv/index";
    }
}
