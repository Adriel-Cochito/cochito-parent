package br.edu.infnet.servicos.loader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import br.edu.infnet.servicos.model.domain.Endereco;
import br.edu.infnet.servicos.model.domain.Funcionario;
import br.edu.infnet.servicos.service.FuncionarioService;

@Component
@Order(1)
public class FuncionarioLoader implements ApplicationRunner {

	private final FuncionarioService funcionarioService;

	public FuncionarioLoader(FuncionarioService funcionarioService) {
		this.funcionarioService = funcionarioService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		ClassPathResource resource = new ClassPathResource("funcionario.txt");
		BufferedReader leitura = new BufferedReader(new InputStreamReader(resource.getInputStream()));

		String linha = leitura.readLine();

		String[] campos = null;

		while (linha != null) {

			campos = linha.split(";");

			try {
				Funcionario funcionario = new Funcionario();
				funcionario.setNome(campos[0]);
				funcionario.setEmail(campos[1]);
				funcionario.setCpf(campos[2]);
				funcionario.setTelefone(campos[3]);
				// Pular campos[4] que é a senha (não mais necessária no modelo)
				funcionario.setAtivo(Boolean.valueOf(campos[5]));

				Endereco endereco = new Endereco();
				endereco.setCep(campos[6]);
				endereco.setLogradouro(campos[7]);
				endereco.setComplemento(campos[8]);
				endereco.setUnidade(campos[9]);
				endereco.setBairro(campos[10]);
				endereco.setLocalidade(campos[11]);
				endereco.setUf(campos[12]);
				endereco.setEstado(campos[13]);

				funcionario.setEndereco(endereco);

				funcionarioService.incluir(funcionario);
				System.out.println("  [OK] Funcionario " + funcionario.getNome() + " incluído com sucesso.");
			} catch (Exception e) {
				System.err.println("Erro ao incluir funcionário " + campos[0] + ": " + e.getMessage());
			}

			linha = leitura.readLine();
		}

		System.out.println("---------------------------: ");
		System.out.println("Profisisonais carregados: ");

		List<Funcionario> funcionarios = funcionarioService.obterLista();
		funcionarios.forEach(System.out::println);

		System.out.println("- Total de Funcionarios: " + funcionarios.size());

		leitura.close();
	}
}
