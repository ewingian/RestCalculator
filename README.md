Restful calculator:

Greeting: A test uri during my intial coding
http://localhost:8080/calculator/greeting

Add: a uri that adds an integer to a running total
http://localhost:8080/calculator/add?val=5

Total: a uri that returns the running total
http://localhost:8080/calculator/total


<h2>Notes on the unit testing:</h2><br>
I do not want to take forever on this project. So I am submitting the code now for review, while I continue to work on the unit tests. I am having issues running unit tests on the kafka side of this code.

<h3>Kafka Prodcuer Test</h3>
For the kafka producer, I have a basic unit test that sends an integer. No exceptions are thrown. I tried to add a consumer record to pick up the integers being passed for extra validation. As far a I can tell right now, the container listener is not working, so I am currently trying to figure out what I did wrong.

<h3>Kafka Consumer Test</h3>
This test has proven a little trickier to even pass successfully. I have what I think is a basic unit test for a Kafka Consumer, but I am getting an exception in the test. <br>
<ul>
   <li> org.junit.ComparisonFailure: </li>
   <li> Expected :2 </li>
   <li> Actual   :1 </li>
</ul><br>
I was going to attempt a MockConsumer test, but that would have created a extra work in the consumer, I think that would be well beyond the necessary scope. 

