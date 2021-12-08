package br.com.zup.orquestrador.contadigital;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import br.com.zup.orquestrador.excecoes.impl.ContaNaoEncontradaException;
import br.com.zup.orquestrador.excecoes.impl.SaldoInsuficienteException;
import br.com.zup.orquestrador.excecoes.impl.ServicoIndisponivelException;
import br.com.zup.orquestrador.excecoes.impl.ValorInvalidoException;
import feign.FeignException;

@Service
public class ContaDigitalService {

	private ContaDigitalClient contaClient;

	public ContaDigitalService(ContaDigitalClient contaClient) {
		this.contaClient = contaClient;
	}

	public ContaDigital debitarSaldo(Long idUsuario, BigDecimal valor) {
		TransacaoRequest transacao = TransacaoRequest.buildForDebito(valor);
		
		try {
			TransacaoResponse response = contaClient.novaTransacao(idUsuario, transacao);
			return response.fromModel();
			
		} catch (FeignException.NotFound ex) {
			throw new ContaNaoEncontradaException();
			
		} catch (FeignException.UnprocessableEntity ex) {
			throw new SaldoInsuficienteException();
			
		} catch (FeignException.BadRequest ex) {
			throw new ValorInvalidoException();
			
		} catch (FeignException ex) {
			throw new ServicoIndisponivelException();
			
		}
	}
	
	public ContaDigital estornarValor(Long idUsuario, BigDecimal valor) {
		TransacaoRequest transacao = TransacaoRequest.buildForCredito(valor);
		
		try {
			TransacaoResponse response = contaClient.novaTransacao(idUsuario, transacao);
			return response.fromModel();
			
		} catch (FeignException.NotFound ex) {
			throw new ContaNaoEncontradaException();
			
		}  catch (FeignException.BadRequest ex) {
			throw new ValorInvalidoException();
			
		} catch (FeignException ex) {
			throw new ServicoIndisponivelException();
			
		}
	}

}
