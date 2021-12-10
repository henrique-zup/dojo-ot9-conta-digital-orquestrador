package br.com.zup.orquestrador.kafka;

import br.com.zup.orquestrador.contadigital.ContaDigital;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProducerKafka {

    String topic = "transacoes";

    @Autowired
    private KafkaProperties kafkaProperties;

    @Autowired
    private KafkaTemplate<String, MensagemKafka> kafkaTemplate;

    public void sendMessage(MensagemKafka mensagem, String operacao) {
        this.kafkaTemplate.send(topic, operacao, mensagem);
    }

    public MensagemKafka newMessage(String operacao,
                                    Boolean pagamentoComSucesso,
                                    BigDecimal valor,
                                    ContaDigital conta){
        String mensagem = operacao + "efetuada com sucesso";

        if (!pagamentoComSucesso){
            mensagem = operacao + "estornada, transação não efetivada";
        }

        return new MensagemKafka(
                mensagem,
                mensagem,
                "email@remetente.com.br",
                conta.getEmail(),
                pagamentoComSucesso,
                valor,
                conta.getNumeroConta());
    }
}