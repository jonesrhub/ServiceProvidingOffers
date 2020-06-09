package com.worldpay.offers.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.worldpay.offers.validation.FutureTimeConstraint;
import org.hibernate.validator.constraints.NotEmpty;
import org.junit.platform.commons.util.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

/**
 * Model class representing an Offer
 */
public class Offer implements Comparable<Offer> {

    @JsonProperty(value = "Currency")
    private final String DEFINED_CURRENCY_CODE = "GBP";

    @JsonProperty(required = true)
    @NotNull(message= "Name of offer cannot be null")
    @NotEmpty(message= "Name of offer cannot be empty")
    private String name;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDateTime creationTime;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @NotNull(message= "expiryTime must be provided")
    @FutureTimeConstraint
    private LocalDateTime expiryTime;
    private boolean isExpired;
    private boolean isCancelled;

    @NotNull(message= "A description must be provided")
    private String description;

    @NotNull(message= "A Unique offerId must be provided")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String uniqueOfferId;

    public Offer(){
    }

    public Offer(String name, LocalDateTime creationTime, LocalDateTime expiryTime, String description, String uniqueOfferId) {
        this.name = name;
        this.creationTime = creationTime;
        this.expiryTime = expiryTime;
        this.description = description;
        this.uniqueOfferId = uniqueOfferId;
    }

    public String getUniqueOfferId() {
        return uniqueOfferId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCreationTime(LocalDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public void setUniqueOfferId(String uniqueOfferId) {
        this.uniqueOfferId = uniqueOfferId;
    }

    public String getName() {
        return name;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
        isExpired = cancelled;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
            .append("Unique Offer Id", uniqueOfferId)
            .append("Name ", name)
            .append("Creation Time " , creationTime)
            .append("Expiry Time ", expiryTime)
            .append("Expired", isExpired)
            .append("Cancelled", isCancelled)
            .toString();
    }

    @Override
    public int compareTo(final Offer offer) {
        return Comparator.comparing(Offer::getName)
            .thenComparing(Offer::getExpiryTime)
            .compare(this, offer);
    }

    // Override hashCode as we have overriden equals.
    @Override
    public int hashCode() {
       return Objects.hash(uniqueOfferId);
    }

    // Compare only the uniqueOfferId.
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Offer objectToCompare = (Offer) obj;
        if (!uniqueOfferId.equals(objectToCompare.uniqueOfferId)) {
            return false;
        }
        return true;
    }
}
