package com.example.assignment.model;
import com.example.assignment.utils.ItemStatus;
import com.example.assignment.utils.CustomSerializer;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Item {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @NotNull
    @Column(name = "id")
    private Long itemId;

    @Column(name = "name")
//    @NotBlank
    private String itemName;

    @Column(name = "entered_by")
    private String itemEnteredByUser;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @JsonSerialize(using = CustomSerializer.class)
    @Column(name = "entered_date")
    private Instant itemEnteredDate;

//    @NotNull
    @Column(name = "buying_price")
    private Double itemBuyingPrice;

//    @NotNull
    @Column(name = "selling_price")
    private Double itemSellingPrice;

    @Column(name = "modified_by")
    private String itemLastModifiedByUser;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    @JsonSerialize(using = CustomSerializer.class)
    @Column(name = "modified_date")
    private Instant itemLastModifiedDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

}
