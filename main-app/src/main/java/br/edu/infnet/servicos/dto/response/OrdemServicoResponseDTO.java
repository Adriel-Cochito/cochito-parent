package br.edu.infnet.servicos.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.infnet.servicos.model.domain.OrdemServico;

public class OrdemServicoResponseDTO {
    
    private Integer id;
    private String status;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataExecucao;
    
    private FuncionarioResponseDTO funcionario;
    private ClienteResponseDTO cliente;
    private List<ItemServicoResponseDTO> itensServicos;
    
    // Dados orquestrados com APIs externas
    private DistanciaResponseDTO distancia;

    // Construtores
    public OrdemServicoResponseDTO() {}

    public OrdemServicoResponseDTO(OrdemServico ordemServico) {
        this(ordemServico, null);
    }

    public OrdemServicoResponseDTO(OrdemServico ordemServico, DistanciaResponseDTO distancia) {
        this.id = ordemServico.getId();
        this.status = ordemServico.getStatus();
        this.dataCriacao = ordemServico.getDataCriacao();
        this.dataExecucao = ordemServico.getDataExecucao();
        
        // Mapear funcionário se existir
        if (ordemServico.getFuncionario() != null) {
            this.funcionario = new FuncionarioResponseDTO(ordemServico.getFuncionario());
        }
        
        // Mapear cliente se existir
        if (ordemServico.getCliente() != null) {
            this.cliente = new ClienteResponseDTO(ordemServico.getCliente());
        }
        
        // Mapear itens de serviço se existirem
        if (ordemServico.getItensServicos() != null) {
            this.itensServicos = ordemServico.getItensServicos().stream()
                .map(ItemServicoResponseDTO::new)
                .collect(Collectors.toList());
        }
        
        // Orquestração: dados de distância calculados
        this.distancia = distancia;
    }
    
    // Getters e Setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
    
    public FuncionarioResponseDTO getFuncionario() {
        return funcionario;
    }
    
    public void setFuncionario(FuncionarioResponseDTO funcionario) {
        this.funcionario = funcionario;
    }
    
    public ClienteResponseDTO getCliente() {
        return cliente;
    }
    
    public void setCliente(ClienteResponseDTO cliente) {
        this.cliente = cliente;
    }
    
    public List<ItemServicoResponseDTO> getItensServicos() {
        return itensServicos;
    }
    
    public void setItensServicos(List<ItemServicoResponseDTO> itensServicos) {
        this.itensServicos = itensServicos;
    }
    
    public DistanciaResponseDTO getDistancia() {
        return distancia;
    }
    
    public void setDistancia(DistanciaResponseDTO distancia) {
        this.distancia = distancia;
    }
}