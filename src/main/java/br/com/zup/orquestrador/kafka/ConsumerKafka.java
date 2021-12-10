package br.com.zup.orquestrador.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerKafka {

    @KafkaListener(topics = "transacoes",
            groupId = "${spring.kafka.consumer.group-id}")
    public void ouvir(ConsumerRecord<String, MensagemKafka> record)
    {
        if (record.key().equals("ESTORNO")){
            System.out.println(record);
        }
    }

}
