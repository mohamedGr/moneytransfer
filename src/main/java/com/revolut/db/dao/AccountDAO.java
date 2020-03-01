package com.revolut.db.dao;


import com.revolut.db.dao.mappers.AccountMapper;
import com.revolut.models.Account;
import org.jdbi.v3.core.transaction.TransactionIsolationLevel;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;
import java.util.List;
import java.util.UUID;

@RegisterRowMapper(AccountMapper.class)
public interface AccountDAO {

    @SqlQuery("SELECT * FROM accounts")
    List<Account> getAllAccounts();

    @SqlQuery("SELECT * FROM accounts WHERE id=:id")
    Account getAccountById(@Bind("id") UUID id);

    @SqlUpdate("UPDATE accounts SET balance=:account.balance WHERE id=:account.id")
    @Transaction(TransactionIsolationLevel.READ_COMMITTED)
    int updateAccount(@BindBean("account") Account account);

    @SqlUpdate("INSERT INTO Accounts(id, balance) VALUES(:id, :balance)")
    int createAccount(@Bind("id") UUID accountId, @Bind("balance") Double currentBalance);


}