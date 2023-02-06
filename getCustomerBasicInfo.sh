#!/bin/bash

echo "Enter the customer id: "
read customerId

if [ -z "$customerId" ]; then
  echo "Error: customer id cannot be empty"
  exit 1
fi

curl -H 'Content-Type: application/json' http://localhost:8080/customer/$customerId/getCustomerInformation
