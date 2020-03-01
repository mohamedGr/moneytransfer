package com.revolut.managers;

import com.revolut.db.dao.AccountDAO;
import com.revolut.db.dao.TransferDAO;
import com.revolut.models.Account;
import com.revolut.models.Transfer;
import org.jdbi.v3.sqlobject.CreateSqlObject;

import javax.ws.rs.ClientErrorException;
import javax.ws.rs.core.Response;
import java.util.UUID;

public interface  TransferManager {

    @CreateSqlObject
    TransferDAO transferDAO();

    @CreateSqlObject
    AccountDAO accountDAO();

    default Transfer processTransfer(Transfer transfer) {
        if (transfer.getSourceAccountId().equals(transfer.getDestinationAccountId())) {
            throw new ClientErrorException("Source account cannot be the same as the destination account", Response.Status.BAD_REQUEST);
        }

        Account sourceAccount = accountDAO().getAccountById(transfer.getSourceAccountId());
        if (sourceAccount == null) {
            throw new ClientErrorException("Source account not found", Response.Status.BAD_REQUEST);
        }

        Account destinationAccount = accountDAO().getAccountById(transfer.getDestinationAccountId());
        if (destinationAccount == null) {
            throw new ClientErrorException("Destination account not found", Response.Status.BAD_REQUEST);
        }

        if (sourceAccount.getBalance() < transfer.getAmount()) {
            throw new ClientErrorException("Source account has not enough funds available for the transfer", Response.Status.BAD_REQUEST);
        }

        final UUID transferId = transferDAO().transfer(transfer);

        return transferDAO().getTransfer(transferId);
    }
}
