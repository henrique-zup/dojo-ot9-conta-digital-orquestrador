package br.com.zup.orquestrador.recargacelular;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;

public class NovaRecargaResponse {

        @JsonProperty
        private String numeroCelular;

        @JsonProperty
        private Operadora operadora;

        @JsonProperty
        private BigDecimal valor;

        @JsonProperty
        private String mensagem;

}
