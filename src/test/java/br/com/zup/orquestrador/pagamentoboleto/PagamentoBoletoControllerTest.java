package br.com.zup.orquestrador.pagamentoboleto;

import br.com.zup.orquestrador.contadigital.*;
import com.google.gson.*;
import feign.*;
import org.junit.jupiter.api.*;
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

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class PagamentoBoletoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @Autowired
    private PagamentoBoletoController pagamentoBoletoController;

    @MockBean
    private PagamentoBoletoClient boletoClient;

    @MockBean
    private ContaDigitalService contaService;

    private final String uri = "/pagamento-boleto/1";

    @Test
    void deveRetornarStatusCodeOkQuandoForPossivelPagarBoleto() throws Exception {
        var request = new DadosPagamentoBoletoRequest(new BigDecimal("500.00"), "3271837218973917298");

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));
        Mockito.when(boletoClient.realizarPagamento(Mockito.any(PagamentoBoletoRequest.class))).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(200));
    }

    @Test
    void deveRetornarStatusCodeServiceUnavailableQuandoServicoDeBoletoEstivarIndisponivel() throws Exception {
        var request = new DadosPagamentoBoletoRequest(new BigDecimal("500.00"), "3271837218973917298");

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));
        Mockito.when(boletoClient.realizarPagamento(Mockito.any(PagamentoBoletoRequest.class))).thenThrow(FeignException.InternalServerError.class);
        Mockito.when(contaService.estornarValor(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(503));
    }

    @Test
    void deveRetornarStatusCodeBadRequestQuandoDadosPagamentoBoletoRequestEstiverInvalido() throws Exception {
        var request = new DadosPagamentoBoletoRequest(new BigDecimal("-500.00"), "327183dsa17218973917298");

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));
        Mockito.when(boletoClient.realizarPagamento(Mockito.any(PagamentoBoletoRequest.class))).thenThrow(FeignException.BadRequest.class);
        Mockito.when(contaService.estornarValor(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(400));
    }
    @Test
    void deveRetornarStatusCodeServiceUnavailableQuandoServicoDeBoletoRetornarUmaExceptionNaoMapeada() throws Exception {
        var request = new DadosPagamentoBoletoRequest(new BigDecimal("500.00"), "327183dsa17218973917298");

        Mockito.when(contaService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));
        Mockito.when(boletoClient.realizarPagamento(Mockito.any(PagamentoBoletoRequest.class))).thenThrow(FeignException.UnprocessableEntity.class);
        Mockito.when(contaService.estornarValor(Mockito.anyLong(), Mockito.any())).thenReturn(Mockito.mock(ContaDigital.class));

        mockMvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(gson.toJson(request))
        ).andExpect(MockMvcResultMatchers.status().is(503));
    }


}