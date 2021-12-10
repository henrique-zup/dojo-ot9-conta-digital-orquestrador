package br.com.zup.orquestrador.recargacelular;


import br.com.zup.orquestrador.contadigital.ContaDigital;
import br.com.zup.orquestrador.contadigital.ContaDigitalService;
import br.com.zup.orquestrador.excecoes.clients.RecargaCelularErrorResponse;
import br.com.zup.orquestrador.excecoes.impl.ServicoIndisponivelException;
import br.com.zup.orquestrador.kafka.MensagemKafka;
import br.com.zup.orquestrador.kafka.ProducerKafka;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/recarga-celular")
public class RecargaCelularController {

    private final Logger logger = LoggerFactory.getLogger(RecargaCelularController.class);
    private RecargaCelularClient recargaCelularClient;
    private ContaDigitalService contaService;
    private ProducerKafka producerKafka;

    public RecargaCelularController(RecargaCelularClient recargaCelularClient, ContaDigitalService contaService, ProducerKafka producerKafka) {
        this.recargaCelularClient = recargaCelularClient;
        this.contaService = contaService;
        this.producerKafka = producerKafka;
    }

    @PostMapping("/{idUsuario}")
    public ResponseEntity<?> recargaCelular(@PathVariable Long idUsuario,
                                            @RequestBody @Valid NovaRecargaRequest recargaRequest) throws JsonMappingException, JsonProcessingException {
        ContaDigital conta = contaService.debitarSaldo(idUsuario, recargaRequest.getValor());

        MensagemKafka mensagemKafkaEstorno = producerKafka.newMessage("Recarga de Celular",
                Boolean.FALSE,
                recargaRequest.getValor(), conta);

        try {
            NovaRecargaResponse response = recargaCelularClient.novaRecarga(recargaRequest);

            MensagemKafka mensagemKafkaDebito = producerKafka.newMessage("Recarga de Celular",
                    Boolean.TRUE,
                    recargaRequest.getValor(), conta);

            producerKafka.sendMessage(mensagemKafkaDebito, "DEBITO");
            return ResponseEntity.ok(response);

        } catch (FeignException.InternalServerError ex) {
            logger.error(ex.toString());
            contaService.estornarValor(idUsuario, recargaRequest.getValor());
            producerKafka.sendMessage(mensagemKafkaEstorno, "ESTORNO");
            throw new ServicoIndisponivelException();

        } catch (FeignException.BadRequest ex) {
            contaService.estornarValor(idUsuario, recargaRequest.getValor());
            producerKafka.sendMessage(mensagemKafkaEstorno, "ESTORNO");
            RecargaCelularErrorResponse error = new ObjectMapper()
                    .readValue(ex.contentUTF8(), RecargaCelularErrorResponse.class);

            return ResponseEntity.badRequest().body(error.toApiErrorResponse());
        }
    }

}
