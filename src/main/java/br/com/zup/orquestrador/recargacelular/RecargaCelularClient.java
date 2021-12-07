package br.com.zup.orquestrador.recargacelular;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name="pagamento-boleto", url = "${clients.recarga-celular.url}")
public interface RecargaCelularClient {

    @PostMapping
    NovaRecargaResponse novaRecarga(@RequestBody NovaRecargaRequest recargaRequestDto);

}
