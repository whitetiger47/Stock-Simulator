package com.project.stocktrading.dao.Holdings;

import java.util.List;
import java.util.Map;

/**
 * @author zhaoling
 */
public interface IHoldingsRepository {

    List<Map<String, Object>> getAllHoldingsFromDb();

}
