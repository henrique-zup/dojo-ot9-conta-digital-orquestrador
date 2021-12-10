package br.com.zup.orquestrador.contadigital;

import br.com.zup.orquestrador.excecoes.impl.*;
import feign.*;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.boot.test.mock.mockito.*;

import java.math.*;

@SpringBootTest
class ContaDigitalServiceTest {

    @Autowired
    private ContaDigitalService contaDigitalService;

    @MockBean
    private ContaDigitalClient contaClient;

    @Test
    void debitarSaldoDeveRetornarContaDigitalQuandoUsuarioPossuirValorParaSerDebitado() {
        var contaDigitalMock = Mockito.mock(ContaDigital.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenReturn(Mockito.mock(TransacaoResponse.class));
        Mockito.when(contaDigitalService.debitarSaldo(Mockito.anyLong(), Mockito.any())).thenReturn(contaDigitalMock);

        var contaDigital = contaDigitalService.debitarSaldo(1L, new BigDecimal("100.00"));
        Assertions.assertEquals(contaDigitalMock, contaDigital);
    }

    @Test
    void debitarSaldoDeveLancarContaNaoEncontradaExceptionQuandoContaNaoForEncontrada() {
        var feignException = Mockito.mock(FeignException.NotFound.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                ContaNaoEncontradaException.class, () -> contaDigitalService.debitarSaldo(100L, new BigDecimal("100.00"))
        );
    }

    @Test
    void debitarSaldoDeveLancarSaldoInsuficienteExceptionQuandoSaldoForInsuficienteParaDebito() {
        var feignException = Mockito.mock(FeignException.UnprocessableEntity.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                SaldoInsuficienteException.class, () -> contaDigitalService.debitarSaldo(1L, new BigDecimal("100000.00"))
        );
    }

    @Test
    void debitarSaldoDeveLancarValorInvalidoExceptionQuandoInformacoesForemInvalidasParaDebito() {
        var feignException = Mockito.mock(FeignException.BadRequest.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                ValorInvalidoException.class, () -> contaDigitalService.debitarSaldo(0L, new BigDecimal("-100.00"))
        );
    }

    @Test
    void debitarSaldoDeveLancarServicoIndisponivelExceptionQuandoServicoDeContaDigitalEstiverIndisponivel() {
        var feignException = Mockito.mock(FeignException.InternalServerError.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                ServicoIndisponivelException.class, () -> contaDigitalService.debitarSaldo(1L, new BigDecimal("100.00"))
        );
    }

    @Test
    void estornarValorDeveRetornarContaDigitalQuandoForPossivelRealizarEstorno() {
        var contaDigitalMock = Mockito.mock(ContaDigital.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenReturn(Mockito.mock(TransacaoResponse.class));
        Mockito.when(contaDigitalService.estornarValor(Mockito.anyLong(), Mockito.any())).thenReturn(contaDigitalMock);

        var contaDigital = contaDigitalService.estornarValor(1L, new BigDecimal("100.00"));
        Assertions.assertEquals(contaDigitalMock, contaDigital);
    }

    @Test
    void estornarValorDeveLancarContaNaoEncontradaExceptionQuandoContaNaoForEncontrada() {
        var feignException = Mockito.mock(FeignException.NotFound.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                ContaNaoEncontradaException.class, () -> contaDigitalService.estornarValor(100L, new BigDecimal("100.00"))
        );
    }

    @Test
    void estornarValorDeveLancarValorInvalidoExceptionQuandoValorDeEstornoForInvalido() {
        var feignException = Mockito.mock(FeignException.BadRequest.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                ValorInvalidoException.class, () -> contaDigitalService.estornarValor(1L, new BigDecimal("-100.00"))
        );
    }

    @Test
    void estornarValorDeveLancarServicoIndisponivelExceptionQuandoServicoDeContaDigitalEstiverIndisponivel() {
        var feignException = Mockito.mock(FeignException.InternalServerError.class);

        Mockito.when(contaClient.novaTransacao(Mockito.anyLong(), Mockito.any(TransacaoRequest.class))).thenThrow(feignException);

        Assertions.assertThrows(
                ServicoIndisponivelException.class, () -> contaDigitalService.estornarValor(1L, new BigDecimal("100.00"))
        );
    }


}