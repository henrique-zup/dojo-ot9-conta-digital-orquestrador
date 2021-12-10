package br.com.zup.orquestrador.recargacelular;

import br.com.zup.orquestrador.contadigital.*;
import br.com.zup.orquestrador.excecoes.impl.*;
import br.com.zup.orquestrador.kafka.*;
import com.google.gson.*;
import feign.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.request.*;
import org.springframework.test.web.servlet.result.*;

import java.math.*;

@SpringBootTest
@AutoConfigureMockMvc
class RecargaCelularControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private RecargaCelularClient recargaCelularClient;

    @MockBean
    private ContaDigitalService contaService;

    @MockBean
    private ProducerKafka producerKafka;

    private final String uri = "/recarga-celular/1";

    @ParameterizedTest
    @ValueSource(strings = { "TIM", "CLARO", "VIVO", "OI" })
    void deveRetornarStatusCodeOkQuandoForPossivelRealizarRecarga(String operadora) throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                operadora,
                new BigDecimal("10")
        );

        var recargaResponse = new NovaRecargaResponse(
                "(11)977208079",
                Operadora.valueOf(operadora),
                new BigDecimal("10"),
                "A recarga foi encaminhada com sucesso."
        );

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));
        Mockito.when(recargaCelularClient.novaRecarga(Mockito.any(NovaRecargaRequest.class))).thenReturn(recargaResponse);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = { "119230141", "0713281381", "82-413958251" })
    void deveRetornarStatusCodeBadRequestQuandoNumeroCelularDaNovaRecargaRequestForInvalido(String celular) throws Exception {
        var request = new NovaRecargaRequest(
                celular,
                "CLARO",
                new BigDecimal("10")
        );

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @ParameterizedTest
    @NullAndEmptySource
    void deveRetornarStatusCodeBadRequestQuandoOperadoraNovaRecargaRequestForInvalido(String operadora) throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                operadora,
                new BigDecimal("10")
        );

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deveRetornarStatusCodeBadRequestQuandoValorDaNovaRecargaRequestForNegativo() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                new BigDecimal("-1")
        );

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deveRetornarStatusCodeBadRequestQuandoValorDaNovaRecargaRequestForNulo() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                null
        );

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deveRetornarStatusCodeBadRequestQuandoContaNaoEncontrada() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                new BigDecimal("10")
        );

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenThrow(ContaNaoEncontradaException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(404));
    }

    @Test
    void deveRetornarStatusCodeUnprocessableEntityQuandoSaldoInsuficiente() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                new BigDecimal("10")
        );

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenThrow(SaldoInsuficienteException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(422));
    }

    @Test
    void deveRetornarStatusCodeUnprocessableEntityQuandoValorDeRecargaInvalido() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                new BigDecimal("10")
        );

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenThrow(ValorInvalidoException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }

    @Test
    void deveRetornarStatusServiceUnavailableQuandoContaServiceIndisponivel() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                new BigDecimal("10")
        );

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenThrow(ServicoIndisponivelException.class);

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(503));
    }

    @Test
    void deveRetornarStatusServiceUnavailableQuandoRecargaServiceIndisponivel() throws Exception {
        var request = new NovaRecargaRequest(
                "(11)977208079",
                "CLARO",
                new BigDecimal("10")
        );

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));
        Mockito.when(recargaCelularClient.novaRecarga(Mockito.any(NovaRecargaRequest.class))).thenThrow(Mockito.mock(FeignException.InternalServerError.class));
        Mockito.when(contaService.estornarValor(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(503));
    }

}