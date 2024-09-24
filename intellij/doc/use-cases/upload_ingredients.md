# Upload Ingredients

## 1. Primary actor and goals
__User__: wants to upload all the ingredients in their pantry to find out what recipes they can make with them.


## 2. Other stakeholders and their goals

* __System__: Wants to save the ingredients into the database for easy access and analysis.



## 3. Preconditions

* The user has successfully uploaded all the ingredients to they system and specified any other filters/specifications.

## 4. Postconditions

* Ingredients are saved into their online pantry.


## 4. Workflow

The sequence of steps involved in the execution of the use case, in the form of one or more activity diagrams (please feel free to decompose into multiple diagrams for readability).

The workflow can be specified at different levels of detail:

* __Brief__: main success scenario only;
* __Casual__: most common scenarios and variations;
* __Fully-dressed__: all scenarios and variations.

Please be sure indicate what level of detail the workflow you include represents.

For example, for _process sale_:

```plantuml
@startuml

skin rose

title Process sale (casual level)

'define the lanes
|#application|Customer|
|#technology|Cashier|
|#implementation|System|

|Customer|
start
:Arrive at checkout with items to purchase;

|Cashier|
while (More items?) is (yes)
  :Enter item info (id and quantity);
  |System|
  :Validate line item;
  :Record line item;
  :Show line item detail and running total;
  |Cashier|
endwhile (no)


:Ask for payment type;

|Customer|
:Indicate payment type;

|Cashier|
if (Payment type?) is  ( Cash ) then
:Execute __Pay by cash__;
else ( Card ) 
:Execute __Pay by credit card__;
endif

|System|
:Validate payment;
:Record payment;
:Print receipt;
|Cashier|
:Hand receipt to customer;
stop
@enduml
```


