package com.psl.dto;

import io.micronaut.core.annotation.Introspected;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CustomerDto response Object
 */
@Data
@NoArgsConstructor
@Introspected
@Schema(description = "Describes the Customer details")

public class CustomerDetailsDto {
    @Schema(description = "Customer RelationShip Number")
    private Long id;
    @Schema(description = "Customer Account Number")
    private String accountNumber;
    @Schema(description = "Customer Mobile Number")
    private String mobileNumber;
    @Schema(description = "Customer PAN Number")
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
