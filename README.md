Restful calculator:

Greeting: A test uri during my intial coding
http://localhost:8080/calculator/greeting

Add: a uri that adds an integer to a running total
http://localhost:8080/calculator/add?val=5

Total: a uri that returns the running total
http://localhost:8080/calculator/total


Notes on the unit testing:
I am having issues running unit tests on the kafka side of this code.

For the kafka producer, I have a basic unit test that sends an integer. No exceptions are thrown. I tried to add a consumer record to pick up the integers being passed to add extra valida