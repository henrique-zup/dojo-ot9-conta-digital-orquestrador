package br.com.zup.orquestrador.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class ProducerKafka {

    String topic = "transacoes";

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaTemplate<String, MensagemKafka> kafkaTemplate;

    public void sendMessage(MensagemKafka mensagem) {
        this.kafkaTemplate.send(topic, mensagem);
    }
}