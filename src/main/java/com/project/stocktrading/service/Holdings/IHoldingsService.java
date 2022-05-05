package com.project.stocktrading.service.Holdings;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */
public interface IHoldingsService {

    List<Map<String, Object>> getAllHoldingsFromDb();

}
