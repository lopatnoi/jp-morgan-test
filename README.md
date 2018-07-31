# Project Title

J.P Morgan Java Technical Test

## The Problem
Implement a small message processing application that satisfies the below requirements for processing sales notification messages. You should assume that an external company will be sending you the input messages, but for the purposes of this exercise you are free to define the interfaces.

### Processing requirements
* All sales must be recorded
* All messages must be processed
* After every 10th message received your application should log a report detailing the number
of sales of each product and their total value.
* After 50 messages your application should log that it is pausing, stop accepting new
messages and log a report of the adjustments that have been made to each sale type while the application was running.

### Sales and Messages
* A sale has a product type field and a value
```
    String product; // product type name
    String value;   // product value e.g. "10" or "0.01"
```
* Any number of different product types can be expected. There is no fixed set.
* A message notifying you of a sale could be one of the following types.<br>
***SaleMessage*** – contains the details of 1 sale e.g apple at 10.<br>
***MultiSaleMessage*** - contains the details of a sale and the number of occurrences of that sale. E.g 20 sales of apples at 10 each.<br>
***AdjustmentSaleMessage*** - contains the details of a sale and an adjustment operation to be applied to all stored sales of this product type. Operations can be add, subtract, or multiply e.g Add 20 apples would instruct your application to add 20 to each sale of apples you have recorded.

## Getting Started
To start use application please find an example below.
```
ReportHandler saleReportHandler = new SaleReportHandler();
ReportHandler adjustmentReportHandler = new AdjustmentReportHandler();
MessageProcess messageProcess = new MessageProcessImpl(saleReportHandler, adjustmentReportHandler);
MessageNotification messageNotification = new MessageNotificationImpl(messageProcess);

// add sale message
messageNotification.addSale("apple", "10");

// add multi sale message
messageNotification.addMultiSale("apple", "10", 2);

// add adjustment operation message
messageNotification.addAdjustment("apple", "10", AdjustmentType.ADD);
```
 
### Prerequisites

To create JAR file please use [Maven](https://maven.apache.org/) command.
```
mvn clean install
```
Which will create JAR file ***jp-morgan-test-1.0-SNAPSHOT.jar***

### Installing

To use jp-morgan-test application please add JAR file to your project classpath.
For example you may use local Maven repository which will contains library after Maven **install** lifecycle.

```
<dependency>
    <groupId>programming</groupId>
    <artifactId>jp-morgan-test</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## Running the tests

To run the tests for this system please use [Maven](https://maven.apache.org/) command.
```
mvn clean test
```

## Built With

* [Maven](https://maven.apache.org/) - Dependency Management

## Authors

* **Lopatnov Ivan** - *Initial work* - [lopatnoi](https://github.com/lopatnoi)

