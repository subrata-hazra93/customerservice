package com.psl.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.psl.dto.CustomerDetailsDto;
import com.psl.dto.CustomerDto;
import com.psl.entity.Customer;
import com.psl.exception.CustomException;
import com.psl.exception.Error;
import com.psl.mapper.MapCustomerDetails;
import com.psl.service.CustomerService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

/**
 * Create Customer Data and store it to the database
 *
 * @return HttpResponse<CustomerDetailsDto>
 */

@Controller("/customer")
@Tag(name = "Customers", description = "The Customer Record")
public class CustomerController {
    @Inject
    CustomerService customerService;
    final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Operation(summary = "Creates a Customer Record", description = "This operation creates a Customer entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created", content = @Content(mediaType = "application", schema = @Schema(implementation = CustomerDetailsDto.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))})
    @Post(produces = {"application/json"})
    public HttpResponse<CustomerDetailsDto> createCustomer(@Body CustomerDto customerCreateDto) throws CustomException {
        logger.info("CustomerController.createCustomer :: Create Customer Object in database {}", customerCreateDto);
        Customer customer = MapCustomerDetails.mapCustomerDTOtoCustomerEntity(customerCreateDto);
        Customer response = customerService.createCustomer(customer);
        return HttpResponse.created(MapCustomerDetails.mapCustomerEntityToCustomerDto(response));
    }

    /**
     * Delete Customer data from Database
     *
     * @param id
     * @return HttpResponse<String>
     */

    @Operation(summary = "Deletes a CustomerDetails", description = "This operation deletes a CustomerDetails entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted"),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))})
    @Delete(value = "/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public HttpResponse<String> deleteCustomerDetails(Long id) {
        logger.info("CustomerController.deleteCustomerDetails :: Delete Customer details by Id {}", id);
        customerService.deleteCustomerDetails(id);
        return HttpResponse.ok("Deleted Successfully");
    }

    /**
     * Search Customer data from Database
     *
     * @param fields
     * @return HttpResponse<List<CustomerDetailsDto>>
     */
    @Operation(summary = "Get list of record by filtering Customer objects", description = "This operation list or find Customer entities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CustomerDetailsDto.class)))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))})
    @Post("/list")
    public HttpResponse<List<CustomerDetailsDto>> getListOfCustomerDetails(@Body CustomerDto fields) throws CustomException {
        logger.info("CustomerController.getListOfCustomerDetails :: Get List of Customers by passing all the fields of CustomerDto {}", fields);
        Customer customer = MapCustomerDetails.mapCustomerDTOtoCustomerEntity(fields);
        List<Customer> listOfCustomer = customerService.getListOfCustomer(customer);
        List<CustomerDetailsDto> customerDetailsDtoList = new ArrayList<>();
        listOfCustomer.forEach(c -> {
            CustomerDetailsDto map = MapCustomerDetails.mapCustomerEntityToCustomerDto(c);
            customerDetailsDtoList.add(map);
        });
        return HttpResponse.ok(customerDetailsDtoList);
    }

    /**
     * Update Customer data from Database
     *
     * @param id, body
     * @return HttpResponse<CustomerDetailsDto>
     */
    @Operation(summary = "Partially update CustomerDetails", description = "This operation updates partially a CustomerDetails entity.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDetailsDto.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))})
    @Patch(value = "/{id}",
            produces = {"application/json"},
            consumes = {"application/json"})
    public HttpResponse<CustomerDetailsDto> updateCustomerDetails(@PathVariable Long id, @Body CustomerDto body) {
        logger.info("CustomerController.updateCustomerDetails :: Update CustomerDetails By passing Customer Id and CustomerDto Object {} " ,body );
        Customer customer = MapCustomerDetails.mapCustomerDTOtoCustomerEntity(body);
        Customer response = customerService.updateCustomer(id, customer);
        CustomerDetailsDto customerDetailsDto = MapCustomerDetails.mapCustomerEntityToCustomerDto(response);
        return HttpResponse.ok(customerDetailsDto);
    }

    /**
     * Retrieve Customer data from Database
     *
     * @param id
     * @return HttpResponse<CustomerDetailsDto>
     */
    @Operation(summary = "Retrieves a CustomerDetails by ID", description = "This operation retrieves a CustomerDetails entity. Attribute selection is enabled for all first level attributes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CustomerDetailsDto.class))),

            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "405", description = "Method Not allowed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class))),

            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Error.class)))})
    @Get(value = "/{id}",
            produces = {"application/json"},
            consumes = {"application/json"})

    public HttpResponse<CustomerDetailsDto> retrieveCustomerDetails(Long id) {
        logger.info("CustomerController.retrieveCustomerDetails :: Get Customer details by using  customer Id {}", id);
        Customer response = customerService.retrieveCustomer(id);
        CustomerDetailsDto customerDetailsDto = MapCustomerDetails.mapCustomerEntityToCustomerDto(response);
        return HttpResponse.ok(customerDetailsDto);
    }

}
