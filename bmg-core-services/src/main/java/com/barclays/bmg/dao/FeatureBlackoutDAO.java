package com.barclays.bmg.dao;

import java.util.Map;

import com.barclays.bmg.dto.FeatureBlackoutDTO;

/**
 * DAO to access S_FEATURE_BLACKOUT_MST table.
 */
public interface FeatureBlackoutDAO {
    FeatureBlackoutDTO findByActivityId(Map<String, String> params);
}
