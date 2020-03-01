package com.revolut.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.validation.ValidationMethod;
import javax.annotation.Nullable;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

public class Transfer {

    @JsonIgnore
    @ValidationMethod(message = "sourceAccountId must be different from destinationAccountId")
    public boolean isNotSourceAndDestinationEqual() {
        return !(this.sourceAccountId != null && this.destinationAccountId != null) || !this.sourceAccountId.equals(this.destinationAccountId);
    }

    private @Nullable UUID id;
    private @Nullable UUID sourceAccountId;
    private @Nullable UUID destinationAccountId;

    @JsonProperty
    private Optional<UUID> sourceAccountId() {
        return Optional.ofNullable(sourceAccountId);
    }

    @JsonProperty
    private Optional<UUID> destinationAccountId() {
        return Optional.ofNullable(sourceAccountId);
    }

    @NotNull
    @Min(1)
    private Double amount;
    private OffsetDateTime transferTime;


    public Transfer() {
    }

    public Transfer(UUID id, UUID sourceAccountId, UUID destinationAccountId, Double amount, OffsetDateTime transferTime) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.amount = amount;
        this.transferTime = transferTime;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getSourceAccountId() {
        return sourceAccountId;
    }

    public void setSourceAccountId(UUID sourceAccountId) {
        this.sourceAccountId = sourceAccountId;
    }

    public UUID getDestinationAccountId() {
        return destinationAccountId;
    }

    public void setDestinationAccountId(UUID destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        try {
            this.amount = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }
}
