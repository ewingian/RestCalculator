package com.calculator;

/**
 * Created by ian on 2/9/18.
 */
import com.calculator.kafka.services.KafkaProducer;
import com.calculator.kafka.services.KafkaConsumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.listener.MessageListener;
import org.springframework.kafka.listener.config.ContainerProperties;
import org.springframework.kafka.test.rule.KafkaEmbedded;
import org.springframework.kafka.test.utils.ContainerTestUtils;
import org.springframework.kafka.test.utils.KafkaTestUtils;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThat;
import static org.springframework.kafka.test.hamcrest.KafkaMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaTest {

    private static final String TEMPLATE_TOPIC = "templateTopic";

    @ClassRule
    public static KafkaEmbedded embeddedKafka = new KafkaEmbedded(1, true, TEMPLATE_TOPIC);

    private Integer i1 = 0;
    private Integer i2 = 3;

    @Test
    public void testTemplate() throws Exception {
        Map<String, Object> consumerProps = KafkaTestUtils.consumerProps("testT", "false",
                embeddedKafka);
        KafkaProducer producer = new KafkaProducer();
        KafkaConsumer consumer = new KafkaConsumer();



        
        DefaultKafkaConsumerFactory<String, Integer> cf = new DefaultKafkaConsumerFactory<String, Integer>(consumerProps);
        ContainerProperties containerProperties = new ContainerProperties(TEMPLATE_TOPIC);
        KafkaMessageListenerContainer<String, Integer> container = new KafkaMessageListenerContainer<>(cf, containerProperties);
        final BlockingQueue<ConsumerRecord<String, Integer>> records = new LinkedBlockingQueue<>();
        container.setupMessageListener(new MessageListener<String, Integer>() {

            @Override
            public void onMessage(ConsumerRecord<String, Integer> record) {
                System.out.println(record);
                records.add(record);
            }

        });
        container.setBeanName("templateTests");
        container.start();
        ContainerTestUtils.waitForAssignment(container, embeddedKafka.getPartitionsPerTopic());
        Map<String, Object> senderProps =
                KafkaTestUtils.senderProps(embeddedKafka.getBrokersAsString());
        ProducerFactory<String, Integer> pf = new DefaultKafkaProducerFactory<String, Integer>(senderProps);
        KafkaTemplate<String, Integer> template = new KafkaTemplate<>(pf);
        template.setDefaultTopic(TEMPLATE_TOPIC);
        template.sendDefault(i1);
        assertThat(records.poll(10, TimeUnit.SECONDS), hasValue(i1));
        template.sendDefault(0, "input", i1);
        ConsumerRecord<String, Integer> received = records.poll(10, TimeUnit.SECONDS);
        assertThat(received, hasKey("input"));
        assertThat(received, hasPartition(0));
        assertThat(received, hasValue(i1));
        template.send(TEMPLATE_TOPIC, 0, "input", i2);
        received = records.poll(10, TimeUnit.SECONDS);
        assertThat(received, hasKey("input"));
        assertThat(received, hasPartition(0));
        assertThat(received, hasValue(i2));
    }
}
