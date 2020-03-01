package com.revolut.db.dao.mappers;

import com.revolut.models.Transfer;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.OffsetDateTime;
import java.util.UUID;

public class TransferMapper implements RowMapper<Transfer> {

    public Transfer map(ResultSet r, StatementContext ctx) throws SQLException {
        return new Transfer(r.getObject("id", UUID.class),
                r.getObject("source_account_id", UUID.class),
                r.getObject("destination_account_id", UUID.class),
                r.getDouble("amount"),
                r.getObject("transfer_time", OffsetDateTime.class));
    }

}