package com.revolut.resources;

import com.codahale.metrics.annotation.Timed;
import com.revolut.db.dao.TransferDAO;
import com.revolut.managers.TransferManager;
import com.revolut.models.Transfer;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.ClientErrorException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TransferResource {

    final private TransferManager transferManager;
    final private TransferDAO transferDAO;

    public TransferResource(TransferDAO transferDAO, TransferManager transferManager) {
        this.transferDAO = transferDAO;
        this.transferManager = transferManager;
    }

    @POST
    @Timed
    @Path("/transfer")
    public Transfer transferFunds(@Valid Transfer transfer)  {
        try {
            return transferManager.processTransfer(transfer);
        } catch (ClientErrorException e) {
            throw new WebApplicationException(e.getMessage(), e, Response.Status.PRECONDITION_FAILED);
        }
    }

    @GET
    @Timed
    @Path("/transfer/{id}")
    public Transfer getTransfer(@PathParam("id") @NotNull UUID id) {
        final Transfer transfer = transferDAO.getTransfer(id);
        if (transfer == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return transfer;
    }

}
