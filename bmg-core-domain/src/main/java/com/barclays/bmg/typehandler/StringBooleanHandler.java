package com.barclays.bmg.typehandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.type.TypeHandler;

public class StringBooleanHandler implements TypeHandler {

    public void setParameter(PreparedStatement ps, int position, Object value, String jdbcType) throws SQLException {

	Boolean bValue = (Boolean) value;
	ps.setString(position, bValue.booleanValue() ? "Y" : "N");

    }

    public Object getResult(ResultSet rs, String name) throws SQLException {
	return valueOf(rs.getString(name));
    }

    public Object getResult(ResultSet rs, int position) throws SQLException {
	return valueOf(rs.getString(position));
    }

    public Object getResult(CallableStatement cs, int position) throws SQLException {
	return valueOf(cs.getString(position));
    }

    public Object valueOf(String value) {
	if ("Y".equals(value)) {
	    return Boolean.TRUE;
	} else {
	    return Boolean.FALSE;
	}
    }

    public boolean equals(Object value1, String value2) {
	return valueOf(value2).equals(value1);
    }

}
