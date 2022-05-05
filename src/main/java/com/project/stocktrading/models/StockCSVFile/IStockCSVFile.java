package com.project.stocktrading.models.StockCSVFile;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author abhishekuppe
 */
public interface IStockCSVFile {
    MultipartFile getFile();

    void setFile(MultipartFile file);
}
