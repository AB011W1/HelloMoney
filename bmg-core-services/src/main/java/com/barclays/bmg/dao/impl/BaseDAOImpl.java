package com.barclays.bmg.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.barclays.bmg.dao.BaseDAO;

public class BaseDAOImpl extends SqlMapClientDaoSupport implements BaseDAO {
    /**
     * Execute select SQL with predefined parameter.
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public List queryForList(String sqlId, Object param) {
	return this.getSqlMapClientTemplate().queryForList(sqlId, param);
    }

    /**
     * Execute select SQL with predefined parameter.
     * 
     * @param sqlId
     * @return
     */
    public List queryForList(String sqlId) {
	return this.getSqlMapClientTemplate().queryForList(sqlId);
    }

    /**
     * Execute select SQL with parameter.
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public Object queryForObject(String sqlId, Object param) {
	return this.getSqlMapClientTemplate().queryForObject(sqlId, param);
    }

    /**
     * Execute insert SQL with parameter.
     * 
     * @param sqlId
     * @param param
     * @return generated key or null
     */
    public Object insert(String sqlId, Object param) {
	return this.getSqlMapClientTemplate().insert(sqlId, param);
    }

    /**
     * Execute update SQL with parameter.
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public int update(String sqlId, Object param) {
	return this.getSqlMapClientTemplate().update(sqlId, param);
    }
}
