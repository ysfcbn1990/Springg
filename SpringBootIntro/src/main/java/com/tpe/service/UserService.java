package com.tpe.service;

import com.tpe.domain.Role;
import com.tpe.domain.User;
import com.tpe.domain.enums.RoleType;
import com.tpe.dto.UserRequest;
import com.tpe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    public void saveUser(UserRequest userRequest) {

        User user=new User();
        user.setFirstName(userRequest.getFirstName());
user.setLastName(userRequest.getLastName());
user.setUserName(userRequest.getUserName());
//Burada passwordü şifreleyerek DB göndereceğiz
//String password= userRequest.getPassword();
//String encodedPassword=
passwordEncoder.encode(userRequest.getPassword());//requesteki password karmaşıklaştırıldı
//user.setPassword(encodedPassword);
 user.setPassword(passwordEncoder.encode(userRequest.getPassword()));

//role setlenmesi
Set<Role> roles=new HashSet<>();
Role role=roleService.getRoleByType(RoleType.ROLE_ADMIN);
roles.add(role);

user.setRoles(roles);//defaultta user a ADMIN rolünü verdik

userRepository.save(user);

    }
}
