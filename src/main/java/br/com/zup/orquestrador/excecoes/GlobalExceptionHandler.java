package br.com.zup.orquestrador.excecoes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.zup.orquestrador.excecoes.impl.ContaNaoEncontradaException;
import br.com.zup.orquestrador.excecoes.impl.SaldoInsuficienteException;
import br.com.zup.orquestrador.excecoes.impl.ServicoIndisponivelException;
import br.com.zup.orquestrador.excecoes.impl.ValorInvalidoException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse valorInvalidoException(ValorInvalidoException ex) {
		return new ApiErrorResponse("O valor informado não é válido.");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErrorResponse dadosInvalidosException(MethodArgumentNotValidException ex) {
		return new ApiErrorWithFieldsResponse(ex.getBindingResult().getFieldErrors());
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public ApiErrorResponse saldoInsuficienteException(SaldoInsuficienteException ex) {
		return new ApiErrorResponse("Saldo insuficiente.");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErrorResponse saldoInsuficienteException(ContaNaoEncontradaException ex) {
		return new ApiErrorResponse("Conta não encontrada.");
	}
	
	@ExceptionHandler
	@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
	public ApiErrorResponse servicoIndisponivelException(ServicoIndisponivelException ex) {
		return new ApiErrorResponse("Serviço indisponível.");
	}
}
