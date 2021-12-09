package br.com.zup.orquestrador.contadigital;

import javax.validation.Valid;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="conta-digital", url = "${clients.conta-digital.url}")
public interface ContaDigitalClient {
	
	@PostMapping("/api/v1/contasdigitas/{idCliente}/transacoes")
	public TransacaoResponse novaTransacao(@PathVariable Long idCliente, @RequestBody @Valid TransacaoRequest request);

}
