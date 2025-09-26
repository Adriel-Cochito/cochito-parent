package br.edu.infnet.servicos.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.edu.infnet.servicos.model.domain.Servico;
import br.edu.infnet.servicos.service.ServicoService;

@Component
@Order(2)
public class ServicoLoader implements ApplicationRunner {
	
	private final ServicoService servicoService;
	
	public ServicoLoader(ServicoService servicoService) {
		this.servicoService = servicoService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		
		ClassPathResource resource = new ClassPathResource("servico.txt");
		BufferedReader leitura = new BufferedReader(new InputStreamReader(resource.getInputStream()));
		
		String linha = leitura.readLine();

		String[] campos = null;


		System.out.println("---------------------------: ");
		System.out.println("Servicos carregados: ");
		
		while(linha != null) {
			
			if(linha.trim().isEmpty()) {
				linha = leitura.readLine();
				continue;
			}
			
			campos = linha.split(";");
			
			
			Servico servico = new Servico();				
			servico.setTitulo(campos[0]);
			servico.setPreco(new java.math.BigDecimal(campos[1]));
			servico.setDescricao(campos[2]);

			
			servicoService.incluir(servico);

			System.out.println(servico);
			
			linha = leitura.readLine();
		}
		
		System.out.println("- Total de Serviços: " + servicoService.obterLista().size());

		leitura.close();
	}
}
