package com.calculator;

/**
 * Created by ian on 2/9/18.
 */
import com.calculator.kafka.services.KafkaProducer;
import com.calculator.kafka.services.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class KafkaProducerTest {

    // in case I need to send some integers
    private Integer i1 = 0;
    private Integer i2 = 3;

    private static final String SENDER_TOPIC = "input";

    private List<Integer> l1;

    @Autowired
    private KafkaProducer producer;

    @Autowired
    private KafkaConsumer consumer;

    private KafkaMessageListenerContainer<String, Integer> container;

    private BlockingQueue<ConsumerRecord<String, Integer>> records;

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducerTest.class);


    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, SENDER_TOPIC);



    @Before
    public void testTemplate() throws Exception {

        // set up the Kafka consumer properties
        Map<String, Object> consumerProperties = KafkaTestUtils.consumerProps("test-group", "false", embeddedKafka);

        // create a Kafka consumer factory
        DefaultKafkaConsumerFactory<String, Integer> consumerFactory = new DefaultKafkaConsumerFactory<>(consumerProperties);

        // set the topic that needs to be consumed
        ContainerProperties containerProperties = new ContainerProperties(SENDER_TOPIC);

        // create a Kafka MessageListenerContainer
        container = new KafkaMessageListenerContainer<>(consumerFactory, containerProperties);

        // create a thread safe queue to store the received message
        records = new LinkedBlockingQueue<>();

        // setup a Kafka message listener
        container.setupMessageListener(new MessageListener<String, Integer>() {
            @Override
            public void onMessage(ConsumerRecord<String, Integer> record) {
                LOGGER.debug("test-listener received message='{}'", record.toString());
                records.add(record);
            }
        });

        // start the container and underlying message listener
        container.start();

        // wait until the container has the required number of assigned partitions
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());





    }

    @After
    public void tearDown() {
        // stop the container
        container.stop();
    }

    @Test
    public void testSend() throws InterruptedException {
        // send the message
        producer.send(i1);

        // check that the message was received
        ConsumerRecord<String, Integer> received = records.poll(10, TimeUnit.SECONDS);
        // Hamcrest Matchers to check the value
        assertThat(received, hasValue(i1));

        // AssertJ Condition to check the key
        assertThat(received, hasKey(null));
    }
//        private static final String TEMPLATE_TOPIC = "addition";
//
//        @ClassRule
//        public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TEMPLATE_TOPIC);
//
//        @Autowired
//        private KafkaProducer producer;
//
//        @Autowired
//        private KafkaConsumer consumer;
//
//        @Test
//        public void testTemplate() throws Exception {
//            Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false",
//                    embeddedKafka);
//            DefaultKafkaConsumerFactory<Integer, String> cf =
//                    new DefaultKafkaConsumerFactory<Integer, String>(consumerProps);
//            ContainerProperties containerProperties = new ContainerProperties(TEMPLATE_TOPIC);
//            KafkaMessageListenerContainer<Integer, String> container =
//                    new KafkaMessageListenerContainer<>(cf, containerProperties);
//            final BlockingQueue<ConsumerRecord<Integer, String>> records = new LinkedBlockingQueue<>();
//            container.setupMessageListener(new MessageListener<Integer, String>() {
//
//                @Override
//                public void onMessage(ConsumerRecord<Integer, String> record) {
//        sender            System.out.println(record);
//                    records.add(record);
//                }
//
//            });
//            container.setBeanName("templateTests");
//            container.start();
//            ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
//            Map<String, Object> senderProps =
//                    KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
//            ProducerFactory<Integer, String> pf =
//                    new DefaultKafkaProducerFactory<Integer, String>(senderProps);
//            KafkaTemplate<Integer, String> template = new KafkaTemplate<>(pf);
//            template.setDefaultTopic(TEMPLATE_TOPIC);
//            template.sendDefault("foo");
//            assertThat(records.poll(10, TimeUnit.SECONDS), hasValue("foo"));
//            template.sendDefault(0, 2, "bar");
//            ConsumerRecord<Integer, String> received = records.poll(10, TimeUnit.SECONDS);
//            assertThat(received, hasKey(2));
//            assertThat(received, hasPartition(0));
//            assertThat(received, hasValue("bar"));
//            template.send(TEMPLATE_TOPIC, 0, 2, "baz");
//            received = records.poll(10, TimeUnit.SECONDS);
//            assertThat(received, hasKey(2));
//            assertThat(received, hasPartition(0));
//            assertThat(received, hasValue("baz"));
//        }
}
