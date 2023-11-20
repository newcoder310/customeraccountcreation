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

`If you would like to use curl requests, then the api gateway runs on 8080. so http://localhost:8080/ followed by paths for the respective applications as mentioned in the swagger should work.`

#!/bin/bash

# Check if "config.txt" exists
if [ -f config.txt ]; then
    echo "Config file found."

    # Display main menu options
    echo "Menu:"
    echo "1. Update Bedrock"
    echo "2. Add Additional Bedrocks"
    echo "3. Exit"

    # Get user choice
    read -p "Enter your choice (1/2/3): " choice

    case $choice in
        1)
            # Update Bedrock
            echo "Updating Bedrock..."
            
            # Get user input for remote name and URL
            echo "Enter remote name:"
            read remote_name

            echo "Enter remote URL:"
            read remote_url

            # Add remote
            git remote add $remote_name $remote_url

            # Show remote information
            git remote -v

            # Fetch from the remote
            git fetch $remote_name

            # Merge with unrelated histories
            git merge $remote_name/master --allow-unrelated-histories

            # Commit the merge
            git commit -m "Merged"
            ;;
        2)
            # Add Additional Bedrocks
            echo "Adding Additional Bedrocks..."

            # Display sub-menu for selecting multiple Bedrocks
            echo "Select multiple Bedrocks (comma-separated):"
            echo "1. Infra Bedrock"
            echo "2. Producer Bedrock"
            echo "3. Consumer Bedrock"

            read -p "Enter your choice (comma-separated): " bedrock_choices

            # Process user choices (add your commands accordingly)
            IFS=',' read -ra bedrocks <<< "$bedrock_choices"
            for bedrock in "${bedrocks[@]}"; do
                case $bedrock in
                    1)
                        echo "Adding Infra Bedrock..."
                        # Add your commands for Infra Bedrock
                        ;;
                    2)
                        echo "Adding Producer Bedrock..."
                        # Add your commands for Producer Bedrock
                        ;;
                    3)
                        echo "Adding Consumer Bedrock..."
                        # Add your commands for Consumer Bedrock
                        ;;
                    *)
                        echo "Invalid Bedrock choice: $bedrock"
                        ;;
                esac
            done
            ;;
        3)
            # Exit
            echo "Exiting."
            exit 0
            ;;
        *)
            echo "Invalid choice. Exiting."
            exit 1
            ;;
    esac
else
    # "config.txt" not found
    echo "Config file not found. Exiting."
fi
