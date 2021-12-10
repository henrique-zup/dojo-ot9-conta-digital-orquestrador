package br.com.zup.orquestrador.recargacelular;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "recarga-celular", url = "${clients.recarga-celular.url}")
public interface RecargaCelularClient {

    @PostMapping
    NovaRecargaResponse novaRecarga(@RequestBody NovaRecargaRequest recargaRequestDto);

}
