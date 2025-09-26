package br.edu.infnet.servicos.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.infnet.servicos.model.domain.Cliente;
import br.edu.infnet.servicos.model.domain.Funcionario;
import br.edu.infnet.servicos.repository.ClienteRepository;
import br.edu.infnet.servicos.repository.FuncionarioRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClienteRepository clienteRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(ClienteRepository clienteRepository, 
                                 FuncionarioRepository funcionarioRepository,
                                 PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // Primeiro busca como Funcionario (ADMIN)
        Optional<Funcionario> funcionario = funcionarioRepository.findByEmail(email);
        if (funcionario.isPresent()) {
            return buildUserDetails(
                funcionario.get().getEmail(),
                getPasswordForEmail(email, "admin123"), // Senha padrão para funcionários
                "ROLE_ADMIN"
            );
        }

        // Se não encontrou como Funcionario, busca como Cliente (USER)
        Optional<Cliente> cliente = clienteRepository.findByEmail(email);
        if (cliente.isPresent()) {
            return buildUserDetails(
                cliente.get().getEmail(),
                getPasswordForEmail(email, "cliente123"), // Senha padrão para clientes
                "ROLE_USER"
            );
        }

        throw new UsernameNotFoundException("Usuário não encontrado: " + email);
    }

    private UserDetails buildUserDetails(String email, String password, String role) {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(role));

        return org.springframework.security.core.userdetails.User.builder()
                .username(email)
                .password(password)
                .authorities(authorities)
                .build();
    }

    private String getPasswordForEmail(String email, String defaultPassword) {
        // Retorna senha criptografada baseada no email
        return passwordEncoder.encode(defaultPassword);
    }
}
