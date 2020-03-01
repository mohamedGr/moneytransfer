package com.revolut.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.db.dao.AccountDAO;
import com.revolut.db.dao.TransferDAO;
import com.revolut.models.Account;
import com.revolut.models.Transfer;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountResource {

    final private AccountDAO accountDAO;
    final private TransferDAO transferDAO;

    public AccountResource(AccountDAO accountDAO, TransferDAO transferDAO) {
        this.accountDAO = accountDAO;
        this.transferDAO = transferDAO;
    }

    @GET
    @Timed
    @Path("ping")
    public String ping() {
        return "Pong";
    }

    @GET
    @Timed
    @Path("/accounts")
    public List<Account> getAllAccounts() {
        return accountDAO.getAllAccounts();
    }

    @GET
    @Timed
    @Path("/account/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccount(@PathParam("id") UUID id) {
        Account account = accountDAO.getAccountById(id);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok(account).build();
    }

    @GET
    @Timed
    @Path("/account/{id}/transfers")
    public List<Transfer> getTransfersPerAccount(@PathParam("id") UUID accountID) {
        Account account = accountDAO.getAccountById(accountID);
        if (account == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return transferDAO.getTransfersPerAccount(accountID);
    }

    @POST
    @Timed
    @Path("/account/update/{id}")
    public Response updateAccount(@PathParam("id") UUID accountID, @QueryParam("balance") Double balance) {

        Account account = accountDAO.getAccountById(accountID);
        account.setBalance(balance);

        try{
            int updateStatus = accountDAO.updateAccount(account);
            if(updateStatus == 1){
                return Response.ok(account).build();
            }
        } catch (Exception e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return null;
    }

    @POST
    @Timed
    @Path("/account/create")
    public Response createAccount(@QueryParam("balance") Double balance) {

        UUID accountID = UUID.randomUUID();
        int updateStatus = accountDAO.createAccount(accountID, balance);
        try{
            if(updateStatus == 1){
                Account account = accountDAO.getAccountById(accountID);
                return Response.ok(account).build();
            }
        } catch (Exception e){
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        return null;
    }

}
