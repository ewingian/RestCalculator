Restful calculator:

Greeting: A test uri during my intial coding
http://localhost:8080/calculator/greeting

Add: a uri that adds an integer to a running total
http://localhost:8080/calculator/add?val=5

Total: a uri that returns the running total
http://localhost:8080/calculator/total

<h1>Latest update</h1>
2/25/18 - I fixed the container error. I have also fixed the producer unit test. Had to wire the producer to use embedded kafka
Removed the greeting tests from this project. They were unecessary outside of helping me get a restful spring application up and running.
2/25/18 - I seem to have completely broken the project.<br>
   2018-02-25 11:01:08.408 ERROR 16400 --- [ntainer#0-0-C-1] essageListenerContainer$ListenerConsumer : Container exception<br>
I cant even run the project any more. <br>
Attempting to start from scratch.

<h2>Notes on the unit testing:</h2><br>
I do not want to take forever on this project. So I am submitting the code now for review, while I continue to work on the unit tests. I am having issues running unit tests on the kafka side of this code.

<h3>Kafka Prodcuer Test</h3>
2/25/18 <b>Solved</b><br>
For the kafka producer, I have a basic unit test that sends an integer. No exceptions are thrown. I tried to add a consumer record to pick up the integers being passed for extra validation. As far a I can tell right now, the container listener is not working, so I am currently trying to figure out what I did wrong.

<h3>Kafka Consumer Test</h3>
This test has proven a little trickier to even pass successfully. I have what I think is a basic unit test for a Kafka Consumer, but I am getting an exception in the test. <br>
<ul>
   <li> org.junit.ComparisonFailure: </li>
   <li> Expected :2 </li>
   <li> Actual   :1 </li>
</ul><br>
The error is thrown from a failed assertEquals. So I am trying to figure out where the root of the failure.

