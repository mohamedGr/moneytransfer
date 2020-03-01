package com.revolut.db.dao.mappers;

import com.revolut.models.Account;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class AccountMapper implements RowMapper<Account> {
    @Override
    public Account map(ResultSet r, StatementContext ctx) throws SQLException {
        return new Account(r.getObject("id", UUID.class), r.getDouble("balance"), r.getObject("creation_date", OffsetDateTime.class));
    }
}
