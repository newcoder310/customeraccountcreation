#!/bin/bash

echo "Enter name: "
read name
if [ -z "$name" ]; then
  echo "Name should not be empty"
  exit 1
fi

echo "Enter surname: "
read surname
if [ -z "$surname" ]; then
  echo "Surname should not be empty"
  exit 1
fi

data='{"name":"'"$name"'", "surname":"'"$surname"'"}'

curl -H 'Content-Type: application/json' -d "$data" -X POST http://localhost:8080/customer/createCustomer

