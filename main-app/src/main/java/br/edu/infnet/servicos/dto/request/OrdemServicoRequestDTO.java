package br.edu.infnet.servicos.dto.request;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Pattern;

public class OrdemServicoRequestDTO {

    @NotNull(message = "O cliente é obrigatório.")
    @Valid
    private ClienteRequestDTO cliente;

    @NotNull(message = "O funcionário é obrigatório.")
    @Valid
    private FuncionarioIdRequestDTO funcionario;

    @NotEmpty(message = "Pelo menos um item de serviço é obrigatório")
    @Valid
    private List<ItemServicoRequestDTO> itensServicos;

    @NotNull(message = "A data de criação é obrigatória.")
    @PastOrPresent(message = "A data de criação não pode ser futura.")
    private LocalDateTime dataCriacao;

    @Future(message = "A data de execução deve ser futura.")
    private LocalDateTime dataExecucao;

    @NotNull(message = "O status é obrigatório.")
    @Pattern(regexp = "PENDENTE|EM_ANDAMENTO|CONCLUIDO|CANCELADO", 
             message = "Status deve ser: PENDENTE, EM_ANDAMENTO, CONCLUIDO ou CANCELADO")
    private String status;
    
    private Double valorTotal;

    // Construtores
    public OrdemServicoRequestDTO() {}

    // Getters e Setters
    public ClienteRequestDTO getCliente() {
        return cliente;
    }

    public void setCliente(ClienteRequestDTO cliente) {
        this.cliente = cliente;
    }

    public FuncionarioIdRequestDTO getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(FuncionarioIdRequestDTO funcionario) {
        this.funcionario = funcionario;
    }

    public List<ItemServicoRequestDTO> getItensServicos() {
        return itensServicos;
    }

    public void setItensServicos(List<ItemServicoRequestDTO> itensServicos) {
        this.itensServicos = itensServicos;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public LocalDateTime getDataExecucao() {
        return dataExecucao;
    }

    public void setDataExecucao(LocalDateTime dataExecucao) {
        this.dataExecucao = dataExecucao;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }
}
