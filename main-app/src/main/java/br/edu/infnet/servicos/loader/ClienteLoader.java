package br.edu.infnet.servicos.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.model.domain.Endereco;
import br.edu.infnet.servicos.service.ClienteService;

@Component
@Order(2)
public class ClienteLoader implements ApplicationRunner {
    
    private final ClienteService clienteService;
    
    public ClienteLoader(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        
        ClassPathResource resource = new ClassPathResource("cliente.txt");
        BufferedReader leitura = new BufferedReader(new InputStreamReader(resource.getInputStream()));
        
        String linha = leitura.readLine();
        String[] campos = null;

        System.out.println("---------------------------: ");
        System.out.println("Clientes carregados: ");
        
        while(linha != null) {
            
            campos = linha.split(";");
            
            try {
                Cliente cliente = new Cliente();                
                cliente.setNome(campos[0]);
                cliente.setCpf(campos[1]);
                cliente.setEmail(campos[2]);
                cliente.setTelefone(campos[3]);
                // Pular campos[4] que é a senha (não mais necessária no modelo)
                cliente.setFidelidade(campos[5]);
                
                // Criar e associar endereço
                Endereco endereco = new Endereco();
                endereco.setCep(campos[6]);
                endereco.setLogradouro(campos[7]);
                endereco.setComplemento(campos[8]);
                endereco.setUnidade(campos[9]);
                endereco.setBairro(campos[10]);
                endereco.setLocalidade(campos[11]);
                endereco.setUf(campos[12]);
                endereco.setEstado(campos[13]);
                
                cliente.setEndereco(endereco);
                
                clienteService.incluir(cliente);
                System.out.println("  [OK] Cliente " + cliente.getNome() + " incluído com sucesso.");
            } catch (Exception e) {
                System.err.println("Erro ao incluir cliente " + campos[0] + ": " + e.getMessage());
            }
            
            linha = leitura.readLine();
        }
        
        System.out.println("- Total de Clientes: " + clienteService.obterLista().size());

        leitura.close();
    }
}