package br.com.zup.orquestrador.recargacelular;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class NovaRecargaResponse {

        private String resposta;

        public String getResposta() {
                return resposta;
        }

        public NovaRecargaResponse(String resposta) {
                this.resposta = resposta;
        }

}
