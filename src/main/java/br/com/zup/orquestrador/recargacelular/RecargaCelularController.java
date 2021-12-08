package br.com.zup.orquestrador.recargacelular;


import br.com.zup.orquestrador.contadigital.ContaDigital;
import br.com.zup.orquestrador.contadigital.ContaDigitalService;
import br.com.zup.orquestrador.pagamentoboleto.DadosPagamentoBoletoResponse;
import br.com.zup.orquestrador.pagamentoboleto.PagamentoBoletoClient;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private RecargaCelularClient recargaCelularClient;
    private PagamentoBoletoClient boletoClient;
    private ContaDigitalService contaService;

    @PostMapping("/{idUsuario}")
    public ResponseEntity<NovaRecargaResponse> recargaCelular(@PathVariable Long idUsuario,
                                                              @RequestBody @Valid NovaRecargaRequest recargaRequest){

        ContaDigital conta = contaService.debitarSaldo(idUsuario, recargaRequest.getValor());
        try {
            recargaCelularClient.novaRecarga(recargaRequest);

        } catch (FeignException.InternalServerError ex) {
            contaService.estornarValor(idUsuario, recargaRequest.getValor());
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                    .body(new NovaRecargaResponse("Ocorreu uma falha de comunicação com o serviço de recarga."));

        } catch (FeignException.BadRequest ex) {
            contaService.estornarValor(idUsuario, recargaRequest.getValor());
            return ResponseEntity.badRequest()
                    .body(new NovaRecargaResponse("Dados inválidos"));

        }

        return ResponseEntity.ok(new NovaRecargaResponse("Sua recarga esta sendo processada"));
    }

}
