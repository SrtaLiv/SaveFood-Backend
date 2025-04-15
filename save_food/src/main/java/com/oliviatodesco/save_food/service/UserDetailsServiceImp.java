package com.oliviatodesco.save_food.service;

import com.oliviatodesco.save_food.dto.AuthLoginRequestDTO;
import com.oliviatodesco.save_food.dto.AuthResponseDTO;
import com.oliviatodesco.save_food.dto.RegisterUserDto;
import com.oliviatodesco.save_food.model.Role;
import com.oliviatodesco.save_food.model.UserSec;
import com.oliviatodesco.save_food.repository.IRoleRepository;
import com.oliviatodesco.save_food.repository.IUserRepository;
import com.oliviatodesco.save_food.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserDetailsServiceImp implements UserDetailsService {

    @Autowired
    private IUserRepository userRepo;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    IRoleRepository roleRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //tenemos User sec y necesitamos devolver UserDetails
        //traemos el usuario de la bd
        UserSec userSec = userRepo.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("El email " + email + "no fue encontrado"));

        //con GrantedAuthority Spring Security maneja permisos
        List<SimpleGrantedAuthority> authorityList = new ArrayList<>();

        //tomamos roles y los convertimos en SimpleGrantedAuthority para poder agregarlos a la authorityList
        userSec.getRolesList()
                .forEach(role -> authorityList.add(new SimpleGrantedAuthority("ROLE_".concat(role.getRole()))));


        //ahora tenemos que agregar los permisos
        userSec.getRolesList().stream()
                .flatMap(role -> role.getPermissionsList().stream()) //acá recorro los permisos de los roles
                .forEach(permission -> authorityList.add(new SimpleGrantedAuthority(permission.getPermissionName())));


        return new User(userSec.getEmail(),
                userSec.getPassword(),
                userSec.isEnabled(),
                userSec.isAccountNotExpired(),
                userSec.isCredentialNotExpired(),
                userSec.isAccountNotLocked(),
                authorityList);
    }

    public AuthResponseDTO loginUser(AuthLoginRequestDTO authLoginRequest) {
        //recuperamos nombre de usuario y contraseña
        String username = authLoginRequest.email();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        //si todo sale bien

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.createToken(authentication);
        AuthResponseDTO authResponseDTO = new AuthResponseDTO(username, "login ok", accessToken, true);
        return authResponseDTO;
    }

    public UserSec signupUser(RegisterUserDto registerUserDto) {
        UserSec newUser = new UserSec();
        newUser.setEmail(registerUserDto.getEmail());
        newUser.setUsername(registerUserDto.getUsername());
        newUser.setPassword(this.passwordEncoder.encode(registerUserDto.getPassword()));
        newUser.setEnabled(false); // El usuario no está habilitado hasta verificar el email
        newUser.setAccountNotExpired(true);
        newUser.setAccountNotLocked(true);
        newUser.setCredentialNotExpired(true);

        Role role = roleRepository.findByRole("USER"); //Rol por defecto
        Set<Role> roles = new HashSet<>();
        roles.add(role);
        newUser.setRolesList(roles);

        UserSec savedUser = userRepo.save(newUser);

        return savedUser;
    }

    public Authentication authenticate(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if (userDetails == null) {
            throw new BadCredentialsException("Invalid email or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(), userDetails.getAuthorities());
    }

}
