package br.edu.infnet.servicos.exceptions;

public class RecursoInvalidoException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public RecursoInvalidoException(String mensagem) {
		super(mensagem);
	}
}
