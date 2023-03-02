# Account Microservice

## Description
Handles CRUD of different types of banking accounts.

## Usage

### Accounts
#### GET /user/{userId}/accounts
Example output:
```json
GET /user/1/accounts
[
  {
    "id": 5,
    "userId": 1,
    "accountTypeId": 1,
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "test account 1",
    "createdDate": 1677683776994
  }
]
```
#### POST /user/{userId}/account
Expected input:
```
{
    accountTypeId: int,
    accountName: string
}
```
Example input:
```json
POST /user/1/accounts
{
    accountTypeId: 1,
    accountName: "College savings"
}
```
Example output:
```json
{
    "id": 5,
    "userId": 1,
    "accountTypeId": 1,
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "College savings",
    "createdDate": 1677683776994
}
```
#### DELETE /user/{userId}/account/{accountId}
Example output:
```json
DELETE /user/1/account/5
{
    "id": 5,
    "userId": 1,
    "accountTypeId": 1,
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "College savings",
    "createdDate": 1677683776994
}
```
### AccountTypes
#### GET /accountTypes
Example response:
```json
[
    {
        "id": 1,
        "accountTypeName": "test account",
        "description": "an account for testing purposes"
    }
]
```
#### POST /accountType
Example input:
```json
{
  "accountTypeName": "test",
  "description": "an account for testing the post mapping"
}
```
Example response:
```json
{
  "id": 1,
  "accountTypeName": "test",
  "description": "an account for testing the post mapping"
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
```json
DELETE /user/1/account/4
{
    "error": "Account with id 4 not found"
}
```
Example response demonstrating a user not found error:
```json
DELETE /user/2/account/4
{
    "error": "User with id 2 not found"
}
```