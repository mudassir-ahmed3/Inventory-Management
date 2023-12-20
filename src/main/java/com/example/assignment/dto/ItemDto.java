package com.example.assignment.dto;

import com.example.assignment.utils.ItemStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Instant;
@Data
public class ItemDto {
    @NotNull
    @Positive(message = "Item ID must be positive number")
    Long itemId;
    @NotNull
    private String itemName;

    @NotNull
    @DecimalMin(value = "0.0", message = "Buying price must be greater than 0")
    @DecimalMax(value = "9999999.9", inclusive = true)
    @Digits(integer = 7, fraction = 1)
    private Double itemBuyingPrice;

    @NotNull
    @DecimalMin(value = "0.0", message = "Buying price must be greater than 0")
    @DecimalMax(value = "9999999.9", inclusive = true)
    @Digits(integer = 7, fraction = 1)
    private Double itemSellingPrice;

    @NotBlank
    private String itemEnteredByUser;

    @NotBlank
    private String itemLastModifiedByUser;

    private Instant itemEnteredDate;

    private Instant itemLastModifiedDate;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;
}
