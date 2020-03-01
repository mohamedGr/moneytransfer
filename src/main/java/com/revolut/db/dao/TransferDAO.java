package com.revolut.db.dao;

import com.revolut.db.dao.mappers.TransferMapper;
import com.revolut.models.Transfer;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.util.List;
import java.util.UUID;

@RegisterRowMapper(TransferMapper.class)
public interface TransferDAO {

    @SqlQuery("SELECT * FROM transfers WHERE id = :id")
    Transfer getTransfer(@Bind("id") UUID id);

    @SqlUpdate("INSERT INTO transfers (source_account_id, destination_account_id, amount) " +
            "values (:transfer.sourceAccountId, :transfer.destinationAccountId, :transfer.amount)")
    @GetGeneratedKeys
    UUID createTransfer(@BindBean("transfer") Transfer transfer);

    @SqlQuery("SELECT * FROM transfers WHERE source_account_id=:account_id OR destination_account_id=:account_id")
    List<Transfer> getTransfersPerAccount(@Bind("account_id") UUID account_id);

    @SqlUpdate("UPDATE accounts SET balance = balance - :amount WHERE Id = :id")
    boolean debitAccount(@Bind("amount") Double amount, @Bind("id") UUID accountId);

    @SqlUpdate("UPDATE accounts SET balance = balance + :amount WHERE Id = :id")
    boolean creditAccount(@Bind("amount") Double amount, @Bind("id") UUID accountId);

    @Transaction(TransactionIsolationLevel.REPEATABLE_READ)
    default UUID transfer(Transfer transfer) {
        debitAccount(transfer.getAmount(), transfer.getSourceAccountId());
        creditAccount(transfer.getAmount(), transfer.getDestinationAccountId());
        UUID transferId = createTransfer(transfer);
        return transferId;
    }

}