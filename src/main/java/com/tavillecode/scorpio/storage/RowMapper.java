package com.tavillecode.scorpio.storage;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface RowMapper<T> {
    public abstract T mapRow(ResultSet rs, int index) throws SQLException;
}
