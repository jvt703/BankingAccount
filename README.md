# Account Microservice

## Description
Handles CRUD of different types of banking accounts.

## Usage
### GET /user/{userId}/accounts
Example output:
```
GET /user/1/accounts
[
    {
        "id": 4,
        "user": {
            "id": 1,
            "firstname": "blaze",
            "lastname": "vincent",
            "email": "blaze.vincent@smoothstack.com",
            "emailValidated": true,
            "password": "test1",
            "active": true,
            "address": {
                "id": 1,
                "city": "fort madison",
                "state": "iowa",
                "street": "1200 avenue c",
                "zipCode": "52627"
            },
            "role": {
                "id": 1,
                "roleName": "testuser"
            }
        },
        "accountType": {
            "id": 1,
            "accountTypeName": "test account",
            "description": "an account for testing purposes"
        },
        "balance": 0.0,
        "confirmation": false,
        "active": true,
        "pointsBalance": 0,
        "accountName": "College savings",
        "createdDate": 1677391301402
    }
]
```
### POST /user/{userId}/account
Expected input:
```
{
    accountTypeId: int,
    accountName: string
}
```
Example input:
```
POST /user/1/accounts
{
    accountTypeId: 1,
    accountName: "College savings"
}
```
Example output:
```
{
    "id": 4,
    "user": {
        "id": 1,
        "firstname": "blaze",
        "lastname": "vincent",
        "email": "blaze.vincent@smoothstack.com",
        "emailValidated": true,
        "password": "test1",
        "active": true,
        "address": {
            "id": 1,
            "city": "fort madison",
            "state": "iowa",
            "street": "1200 avenue c",
            "zipCode": "52627"
        },
        "role": {
            "id": 1,
            "roleName": "testuser"
        }
    },
    "accountType": {
        "id": 1,
        "accountTypeName": "test account",
        "description": "an account for testing purposes"
    },
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "College savings",
    "createdDate": 1677391301402
}
```
### DELETE /user/{userId}/account/{accountId}
Example output
```
DELETE /user/1/account/4
{
    "id": 4,
    "user": {
        "id": 1,
        "firstname": "blaze",
        "lastname": "vincent",
        "email": "blaze.vincent@smoothstack.com",
        "emailValidated": true,
        "password": "test1",
        "active": true,
        "address": {
            "id": 1,
            "city": "fort madison",
            "state": "iowa",
            "street": "1200 avenue c",
            "zipCode": "52627"
        },
        "role": {
            "id": 1,
            "roleName": "testuser"
        }
    },
    "accountType": {
        "id": 1,
        "accountTypeName": "test account",
        "description": "an account for testing purposes"
    },
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "College savings",
    "createdDate": 1677391301402
}
```
## Errors
Error responses should observe the following format:
```
{
    error: string
}
```
Example response demonstrating an account not found error:
```
DELETE /user/1/account/4
{
    "error": "Account with id 4 not found"
}
```
Example response demonstrating a user not found error:
```
DELETE /user/2/account/4
{
    "error": "User with id 2 not found"
}
```