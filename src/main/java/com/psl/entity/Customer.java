package com.psl.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Customer Entity Object
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Describes the Customer details")
@Entity
@Builder
public class Customer {

    @Schema(description = "Customer ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Schema(description = "Customer Account Number")
    @Column(unique = true)
    private String accountNumber;
    @Schema(description = "Customer Mobile Number")
    private String mobileNumber;
    @Schema(description = "Customer PAN Number")
    @Column(unique = true)
    private String panNumber;
    @Schema(description = "Customer Name")
    private String customerName;
    @Schema(description = "Customer Date of Birth")
    private String dateOfBirth;
    @Schema(description = "Customer Account Type")
    private String accountType;
    @Schema(description = "Customer Product Type")
    private String productType;
    @Schema(description = "Customer Date Of Opening")
    private String dateOfOpening;

}
