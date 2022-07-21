## Money Transfer Demo

Tech stack used:

1. Java/Kotlin (Kotlin is used).
2. Spring Boot

### Application Requirements:
Service should expose a Rest API to `accept money transfers` to other accounts. 

Money transfers should `persist a new balance` of accounts.

Service should expose a Rest API for `getting the account details`. 

- You can disregard currencies at this time.



**API Created**:

**Get account**:

    GET localhost:8080/account/{id}

**Create account**: 

    POST localhost:8080/account/
    
**Add money**:

    PUT localhost:8080/account/{id}/add/{amount}
    
**Withdraw money**:

    PUT localhost:8080/account/{id}/withdraw/{amount}

**Money transfer**:

    PATCH localhost:8080/account/{fromId}/transfer/{toId}/{amount}
