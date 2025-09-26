package br.edu.infnet.servicos.exceptions;

public class ViolacaoIntegridadeReferencialException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public ViolacaoIntegridadeReferencialException(String mensagem) {
        super(mensagem);
    }

    public ViolacaoIntegridadeReferencialException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
