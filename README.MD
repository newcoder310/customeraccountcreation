# Customer Account Creation

## Assignment Information

This assignment has been implemented using three main microservices:
1. CustomerService: Used for creating customers and retrieving information about them
2. AccountService: Used for creating accounts and retrieving the balance for a particular customer's account
3. TransactionService: Used for creating transactions and retrieving transaction details for a particular customer

## Endpoint Information

For more information about the endpoints of each service, refer to:
1. TranscationService: [TransactionService/doc/swagger.yml](TransactionService/doc/swagger.yml)
2. AccountsService: [AccountsService/doc/swagger.yml](AccountsService/doc/swagger.yml)
3. CustomerService: [CustomerService/doc/swagger.yml](CustomerService/doc/swagger.yml)

## API Gateway

The gateway springboot application acts as an API gateway for easy access to the endpoints.

## Technology Stack

- Java 11
- Springboot 2.7.7
- H2 In-memory database
- open api codegen
- mapstruct

## Starting the Application

To start the application, run `mvn spring-boot:run` from the root directory of each project individually. The order does not matter but all applications need to be started for a working implementation.

`Since there is some auto generated code, while using IDEs, referesh of maven might be need to ensure that the project is fully imported post a mvn clean install`

## Testing

`No preloaded data is provided. Hence customers and accounts need to be created using the scripts or rest endpoints`

Custom scripts are provided for each endpoint to test them with parameters. The scripts include:
- `createCustomer.sh`: Creates a new customer
- `createAccount.sh`: Creates a new account
- `getCustomerDetailedInfo.sh`: Retrieves information about a customer, including their balance and transaction details
- `getCustomerBasicInfo.sh`: Retrieves basic information about a customer
