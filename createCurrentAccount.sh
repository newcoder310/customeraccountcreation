#!/bin/bash

echo "Enter the customerId: "
read customerId

if [ -z "$customerId" ]; then
  echo "Error: customerId cannot be empty"
  exit 1
fi

echo "Enter the initialCredit: "
read initialCredit

if [ -z "$initialCredit" ]; then
  echo "Error: initialCredit cannot be empty"
  exit 1
fi

curl -H 'Content-Type: application/json' -d "{ \"customerId\":\"$customerId\",\"initialCredit\":\"$initialCredit\"}" -X POST http://localhost:8080/account/createCurrentAccount
