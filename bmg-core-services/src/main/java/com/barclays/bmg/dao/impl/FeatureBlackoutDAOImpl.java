package com.barclays.bmg.dao.impl;

import java.util.Map;

import com.barclays.bmg.constants.SqlMapSystemConstants;
import com.barclays.bmg.dao.FeatureBlackoutDAO;
import com.barclays.bmg.dto.FeatureBlackoutDTO;

/**
 * Ibatis implementation of FeattureBlackoutDAO interface.
 */

public class FeatureBlackoutDAOImpl extends BaseDAOImpl implements FeatureBlackoutDAO {

    public FeatureBlackoutDTO findByActivityId(Map<String, String> params) {
	return (FeatureBlackoutDTO) this.queryForObject(SqlMapSystemConstants.FEATURE_BLACKOUT_FIND_BY_ACTIVITYID, params);
    }

}
