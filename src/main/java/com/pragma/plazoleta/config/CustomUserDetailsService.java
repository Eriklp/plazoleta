package com.pragma.plazoleta.config;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    // Aquí puedes inyectar un repositorio o servicio que cargue los usuarios de la base de datos
    // Por ejemplo:
    // private final UserRepository userRepository;

    // public CustomUserDetailsService(UserRepository userRepository) {
    //     this.userRepository = userRepository;
    // }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Lógica para cargar el usuario por nombre de usuario
        // return userRepository.findByUsername(username)
        //         .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
        throw new UsernameNotFoundException("Método no implementado");
    }
}

