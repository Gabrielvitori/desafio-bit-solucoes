package com.bit.backend.config;

import com.bit.backend.model.Usuario;
import com.bit.backend.model.enums.Role;
import com.bit.backend.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.setRole(Role.ROLE_ADMIN);
            usuarioRepository.save(admin);

            Usuario comum = new Usuario();
            comum.setUsername("comum");
            comum.setPassword(passwordEncoder.encode("123456"));
            comum.setRole(Role.ROLE_USER);
            usuarioRepository.save(comum);

            System.out.println("====== USUÁRIOS 'admin' E 'comum' CRIADOS COM SUCESSO ======");
        }
    }
}