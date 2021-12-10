package br.com.zup.orquestrador.kafka;

public class MensagemKafka {



    private String operação;
    private String valor;
    private String data;
    private String cliente;
    private String conta;

    public MensagemKafka() {
    }

    public MensagemKafka(String operação, String valor, String data, String cliente, String conta) {
        this.operação = operação;
        this.valor = valor;
        this.data = data;
        this.cliente = cliente;
        this.conta = conta;
    }

    public String getOperação() {
        return operação;
    }

    public void setOperação(String operação) {
        this.operação = operação;
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
                "operação='" + operação + '\'' +
                ", valor='" + valor + '\'' +
                ", data='" + data + '\'' +
                ", cliente='" + cliente + '\'' +
                ", conta='" + conta + '\'' +
                '}';
    }
}
