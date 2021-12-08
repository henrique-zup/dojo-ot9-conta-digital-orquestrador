package br.com.zup.orquestrador.pagamentoboleto;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="pagamento-boleto", url = "${clients.pagamento-boleto.url}")
public interface PagamentoBoletoClient {

	@PostMapping
	public ResponseEntity<?> realizarPagamento(@Valid @RequestBody PagamentoBoletoRequest request);

}
