package br.com.zup.orquestrador.recargacelular;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.zup.orquestrador.contadigital.ContaDigitalService;
import br.com.zup.orquestrador.excecoes.clients.RecargaCelularErrorResponse;
import br.com.zup.orquestrador.excecoes.impl.ServicoIndisponivelException;
import br.com.zup.orquestrador.kafka.MensagemKafka;
import br.com.zup.orquestrador.kafka.ProducerKafka;
import feign.FeignException;

@RestController
@RequestMapping("/recarga-celular")
public class RecargaCelularController {

    private RecargaCelularClient recargaCelularClient;
    private ContaDigitalService contaService;
    private ProducerKafka producerKafka;

    private final Logger logger = LoggerFactory.getLogger(RecargaCelularController.class);
    
    public RecargaCelularController(RecargaCelularClient recargaCelularClient, ContaDigitalService contaService, ProducerKafka producerKafka) {
		this.recargaCelularClient = recargaCelularClient;
		this.contaService = contaService;
        this.producerKafka = producerKafka;
	}

	@PostMapping("/{idUsuario}")
    public ResponseEntity<?> recargaCelular(@PathVariable Long idUsuario,
                                            @RequestBody @Valid NovaRecargaRequest recargaRequest) throws JsonMappingException, JsonProcessingException{
        contaService.debitarSaldo(idUsuario, recargaRequest.getValor());
        
        try {
            NovaRecargaResponse response = recargaCelularClient.novaRecarga(recargaRequest);
            return ResponseEntity.ok(response);

        } catch (FeignException.InternalServerError ex) {
        	logger.error(ex.toString());
            contaService.estornarValor(idUsuario, recargaRequest.getValor());
            throw new ServicoIndisponivelException();

        } catch (FeignException.BadRequest ex) {
            contaService.estornarValor(idUsuario, recargaRequest.getValor());
            
            RecargaCelularErrorResponse error = new ObjectMapper()
            		.readValue(ex.contentUTF8(), RecargaCelularErrorResponse.class);
            
            return ResponseEntity.badRequest().body(error.toApiErrorResponse());
        }
    }

}
