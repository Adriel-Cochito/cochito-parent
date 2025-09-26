package br.edu.infnet.servicos.model.domain;

import java.time.LocalDateTime;
import java.util.Objects;

public class Notificacao {

	private String titulo;
	private String mensagem;
	private TipoNotificacao tipoNotificacao;
	private String funcionario;
	private LocalDateTime dataCriacao;
	private LocalDateTime dataLeitura;
	private boolean lida;

	// Construtor padrão que inicializa a notificação com valores padrão.
	// Por padrão, uma notificação é criada como não lida.

	public Notificacao() {
		this.lida = false;
	}

	// Marca a notificação como lida e registra automaticamente a data de leitura.
	// Operação idempotente - pode ser chamada múltiplas vezes sem efeitos
	// colaterais.
	public void marcarComoLida() {
		if (!this.lida) {
			this.lida = true;
			this.dataLeitura = LocalDateTime.now();
		}
	}

	// Verifica se a notificação está como não lida.
	// @return true se não foi lida, false caso contrário
	public boolean estaComoNaoLida() {
		return !this.lida;
	}

	// Verifica se a notificação já foi lida pelo funcionário.
	// @return true se foi lida, false caso contrário
	public boolean foiLida() {
		return this.lida && this.dataLeitura != null;
	}

	// Retorna uma representação resumida da notificação para logs.
	// @return string formatada com informações essenciais
	public String resumo() {
		return String.format("Notificacao[tipo=%s, funcionario=%s, lida=%s]", tipoNotificacao,
				funcionario != null ? funcionario : "N/A", lida);
	}

	// Métodos equals e hashCode para comparação adequada
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;

		Notificacao that = (Notificacao) obj;
		return Objects.equals(titulo, that.titulo) && Objects.equals(tipoNotificacao, that.tipoNotificacao)
				&& Objects.equals(funcionario, that.funcionario) && Objects.equals(dataCriacao, that.dataCriacao);
	}

	@Override
	public int hashCode() {
		return Objects.hash(titulo, tipoNotificacao, funcionario, dataCriacao);
	}

	// Getters e Setters com validações básicas

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public TipoNotificacao getTipoNotificacao() {
		return tipoNotificacao;
	}

	public void setTipoNotificacao(TipoNotificacao tipoNotificacao) {
		this.tipoNotificacao = tipoNotificacao;
	}

	public String getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(String funcionario) {
		this.funcionario = funcionario;
	}

	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public LocalDateTime getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(LocalDateTime dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public boolean isLida() {
		return lida;
	}

	public void setLida(boolean lida) {
		this.lida = lida;
	}
}