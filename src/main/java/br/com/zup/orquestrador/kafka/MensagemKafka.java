package br.com.zup.orquestrador.kafka;

public class MensagemKafka {



    private String operacao;
    private String valor;
    private String data;
    private String cliente;
    private String conta;

    public MensagemKafka() {
    }

    public MensagemKafka(String operacao, String valor, String data, String cliente, String conta) {
        this.operacao = operacao;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
        this.conta = conta;
    }

    public String getOperacao() {
        return operacao;
    }

    public void setOperacao(String operacao) {
        this.operacao = operacao;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getConta() {
        return conta;
    }

    public void setConta(String conta) {
        this.conta = conta;
    }

    @Override
    public String toString() {
        return "MensagemKafka{" +
                "operacao='" + operacao + '\'' +
                ", valor='" + valor + '\'' +
                ", data='" + data + '\'' +
                ", cliente='" + cliente + '\'' +
                ", conta='" + conta + '\'' +
                '}';
    }
}
