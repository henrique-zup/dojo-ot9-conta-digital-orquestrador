package br.com.zup.orquestrador.pagamentoboleto;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.zup.orquestrador.contadigital.ContaDigital;
import br.com.zup.orquestrador.contadigital.ContaDigitalService;
import feign.FeignException;

@RestController
@RequestMapping("/pagamento-boleto")
public class PagamentoBoletoController {
	
	private PagamentoBoletoClient boletoClient;
	private ContaDigitalService contaService;
	
	public PagamentoBoletoController(PagamentoBoletoClient boletoClient) {
		this.boletoClient = boletoClient;
	}

	@PostMapping("/{idUsuario}")
	public ResponseEntity<DadosPagamentoBoletoResponse> pagarBoleto(@PathVariable Long idUsuario, @RequestBody @Valid DadosPagamentoBoletoRequest request) {
		
		ContaDigital conta = contaService.debitarSaldo(idUsuario, request.getValor());
		
		try {
			boletoClient.realizarPagamento(request.fromClient(conta));
			
		} catch (FeignException.InternalServerError ex) {
			contaService.estornarValor(idUsuario, request.getValor());
			return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
					.body(new DadosPagamentoBoletoResponse("Ocorreu uma falha de comunicação com o serviço de recarga."));
			
		} catch (FeignException.BadRequest ex) {
			contaService.estornarValor(idUsuario, request.getValor());
			return ResponseEntity.badRequest()
					.body(new DadosPagamentoBoletoResponse("Dados inválidos"));
			
		}
		
		return ResponseEntity.ok(new DadosPagamentoBoletoResponse("Seu pagamento está em processamento."));
	}

}
