package br.com.zup.orquestrador.recargacelular;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/recargacelular")
public class RecargaCelularController {

    @Autowired
    RecargaCelularClient recargaCelularClient;

    @PostMapping
    public ResponseEntity<NovaRecargaResponse> recargaCelular(@RequestBody NovaRecargaRequest recargaRequest){

        NovaRecargaResponse response = recargaCelularClient.novaRecarga(recargaRequest);

        return ResponseEntity.ok(response);
    }

}
