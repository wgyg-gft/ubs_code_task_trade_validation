# ubs_code_task_trade_validation

## Reference Documentation
### Swagger UI url

**http://localhost:8080/swagger-ui/index.html**


### Trade validation endpoint

POST **http://localhost:8080/process/**

with JSON format request body


### Code Structure

TransactionValidationController.java
> Rest Controller

RecordService.java 
> Service that covert the input JSON String to a TransactionRecord object

FOREXTransactionValicationService.java 
> Service that extends the ValidationService and use **AllTypeValidator**, **OptionTypeValidator** and **SportFowardTypeValidator** to validate the TransactionRecord Object