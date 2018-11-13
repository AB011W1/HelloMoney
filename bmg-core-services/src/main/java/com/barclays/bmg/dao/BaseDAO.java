package com.barclays.bmg.dao;

import java.util.List;

public interface BaseDAO {

    /**
     * Execute select SQL with predefined parameter.
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public List queryForList(String sqlId, Object param);

    /**
     * Execute select SQL with predefined parameter.
     * 
     * @param sqlId
     * @return
     */
    public List queryForList(String sqlId);

    /**
     * Execute select SQL with parameter.
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public Object queryForObject(String sqlId, Object param);

    /**
     * Execute insert SQL with parameter.
     * 
     * @param sqlId
     * @param param
     * @return generated key or null
     */
    public Object insert(String sqlId, Object param);

    /**
     * Execute update SQL with parameter.
     * 
     * @param sqlId
     * @param param
     * @return
     */
    public int update(String sqlId, Object param);

}
