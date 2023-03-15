# Account Microservice

## Description
Handles CRUD of different types of banking accounts.

## Usage

### Application prefill data
#### GET /user/{userID}/applicationPrefill (ex. /user/1/applicationPrefill)
Example response
```json
{
    "address": {
        "id": 1,
        "city": "Test City",
        "state": "Iowa",
        "street": "2000 Test Avenue",
        "zipCode": "00000"
    },
    "firstname": "Testathan",
    "lastname": "Testman",
    "email": "test@testing.test",
    "birthDate": 981525600000
}
```
### Accounts
#### GET /user/{userId}/accounts (ex. /user/1/accounts)
Example response
```json
[
    {
        "id": 1,
        "userId": 1,
        "accountTypeName": "Checking",
        "accountTypeDescription": "General purpose account for everyday expenses",
        "balance": 0.0,
        "confirmation": false,
        "active": true,
        "pointsBalance": 0,
        "accountName": "Checking",
        "createdDate": 1678801926044
    }
]
```
#### GET /user/{userId}/account/{accountId} (ex. /user/1/account/1)
Example response
```json
{
    "id": 1,
    "userId": 1,
    "accountTypeName": "Checking",
    "accountTypeDescription": "General purpose account for everyday expenses",
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "Checking",
    "createdDate": 1678897651077
}
```
#### POST /user/{userId}/account (ex. /user/1/account)
Example request body
```json
{
    "accountTypeId": 1,
    "accountName": "College savings"
}
```
Example response
```json
{
    "id": 2,
    "userId": 1,
    "accountTypeName": "Checking",
    "accountTypeDescription": "General purpose account for everyday expenses",
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "College savings",
    "createdDate": 1678897752921
}
```
#### DELETE /user/{userId}/account/{accountId} (ex. /user/1/account/2)
Example response
```json
{
    "id": 2,
    "userId": 1,
    "accountTypeName": "Checking",
    "accountTypeDescription": "General purpose account for everyday expenses",
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "College savings",
    "createdDate": 1678897752921
}
```
### Account types
#### GET /accountTypes
Example response
```json
{
    "id": 1,
    "userId": 1,
    "accountTypeName": "Checking",
    "accountTypeDescription": "General purpose account for everyday expenses",
    "balance": 0.0,
    "confirmation": false,
    "active": true,
    "pointsBalance": 0,
    "accountName": "Checking",
    "createdDate": 1678897651077
}
```
### Credit card applications
#### POST /user/{userId}/creditCardApplication (ex. /user/1/creditCardApplication)
Example request body
```json
{
    "creditCardTypeId": 1,

    "socialSecurityNumber": 123121234,

    "motherMaidenName": "susan",

    "residenceOwnershipStatus": "OWN",

    "employmentStatus": "EMPLOYED",

    "grossAnnualIncome": 15000.00
}
```
Example response
```json
{
    "message": "You have successfully applied for the Big Spender Rewards card!"
}
```
#### POST /creditCardApplication/{creditCardApplicationId} (ex. /creditCardApplication/1)
Example request body
```json
{
    "approved": false
}
```
Example response
```json
{
    "applicantFirstName": "Testathan",
    "applicantLastName": "Testman",
    "cardRewardsName": "Big Spender Rewards",
    "approved": false
}
```
#### GET /creditCardApplications
Example response
```json
[
    {
        "creditCardApplicationId": 1,
        "creditCardRewardsName": "Big Spender Rewards",
        "creditCardTypeId": 1,
        "userFirstName": "Testathan",
        "userLastName": "Testman",
        "userDOB": 981525600000,
        "userEmailAddress": "test@testing.test",
        "userAddress": {
            "id": 1,
            "city": "Test City",
            "state": "Iowa",
            "street": "2000 Test Avenue",
            "zipCode": "00000"
        },
        "socialSecurityNumber": 123121234,
        "motherMaidenName": "susan",
        "residenceOwnershipStatus": "OWN",
        "housingPayment": 0.0,
        "employmentStatus": "EMPLOYED",
        "grossAnnualIncome": 15000.0,
        "approved": false,
        "decisionDate": "2023-03-15T16:35:23.352221Z"
    }
]
```
### Credit card types
#### GET /creditCardTypes
Example response
```json
[
    {
        "id": 1,
        "rewardsName": "Big Spender Rewards",
        "interestRate": 0.08,
        "minimumPayment": 25.0,
        "creditLimit": 2500.0
    },
    {
        "id": 2,
        "rewardsName": "Credit Starter Rewards",
        "interestRate": 0.14,
        "minimumPayment": 25.0,
        "creditLimit": 1000.0
    }
]
```
#### POST /creditCardType
Example request body
```json
{
    "rewardsName": "test1",
    "interestRate": 1.0,
    "minimumPayment": 10.52,
    "creditLimit": 500.00
}
```
Example response
```json
{
    "id": 3,
    "rewardsName": "test1",
    "interestRate": 1.0,
    "minimumPayment": 10.52,
    "creditLimit": 500.0
}
```
### Loan applications
#### POST /user/{userId}/loanApplication (ex. /user/1/loanApplication)
Example request body
```json
{
    "loanAmount": 5000.00,

    "debitedAccountId": 1,

    "socialSecurityNumber": 100203000,

    "motherMaidenName": "susan",

    "residenceOwnershipStatus": "OWN",

    "employmentStatus": "EMPLOYED",

    "grossAnnualIncome": 15000.00
}
```
Example response
```json
{
    "message": "You have successfully applied for a loan of $5000.000000 to be debited to account of id 1"
}
```
#### POST /loanApplication/{loanApplicationId} (ex. /loanApplication/1)
Example request body
```json
{
    "approved": true
}
```
Example response
```json
{
    "applicantFirstName": "Testathan",
    "applicantLastName": "Testman",
    "loanAmount": 5000.0,
    "debitedAccountName": "Checking",
    "debitedAccountNewBalance": 5000.0,
    "approved": true
}
```

## Errors
Error responses should observe the following format:
```
{
    "error": string
}
```
Example response demonstrating an account not found error:\
DELETE /user/1/account/4
```json
{
    "error": "Account with id 4 not found"
}
```
Example response demonstrating a user not found error:\
DELETE /user/2/account/4
```json
{
    "error": "User with id 2 not found"
}
```