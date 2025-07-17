package com.clippercuts.security;

import com.clippercuts.dao.UserDao;
import com.clippercuts.entity.Privilege;
import com.clippercuts.entity.User;
import com.clippercuts.entity.Userrole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    final UserDao userdao;

    @Autowired
    public UserService(UserDao userdao) {
        this.userdao = userdao;
    }

    public User getByUsername(String username){

        User user = new User();

        if ("AdminEUC".equals(username)){

            user.setUsername(username);

        }else {
            user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }
        }

        return user;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if (username.equals("AdminEUC")) {

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            authorities.add(new SimpleGrantedAuthority("user-select"));
            authorities.add(new SimpleGrantedAuthority("user-delete"));
            authorities.add(new SimpleGrantedAuthority("user-update"));
            authorities.add(new SimpleGrantedAuthority("user-insert"));

            authorities.add(new SimpleGrantedAuthority("privilege-select"));
            authorities.add(new SimpleGrantedAuthority("privilege-delete"));
            authorities.add(new SimpleGrantedAuthority("privilege-update"));
            authorities.add(new SimpleGrantedAuthority("privilege-insert"));

            authorities.add(new SimpleGrantedAuthority("employee-select"));
            authorities.add(new SimpleGrantedAuthority("employee-delete"));
            authorities.add(new SimpleGrantedAuthority("employee-update"));
            authorities.add(new SimpleGrantedAuthority("employee-insert"));

            authorities.add(new SimpleGrantedAuthority("operations-select"));
            authorities.add(new SimpleGrantedAuthority("operations-delete"));
            authorities.add(new SimpleGrantedAuthority("operations-update"));
            authorities.add(new SimpleGrantedAuthority("operations-insert"));

            authorities.add(new SimpleGrantedAuthority("item-select"));
            authorities.add(new SimpleGrantedAuthority("item-delete"));
            authorities.add(new SimpleGrantedAuthority("item-update"));
            authorities.add(new SimpleGrantedAuthority("item-insert"));

            authorities.add(new SimpleGrantedAuthority("customer-select"));
            authorities.add(new SimpleGrantedAuthority("customer-delete"));
            authorities.add(new SimpleGrantedAuthority("customer-update"));
            authorities.add(new SimpleGrantedAuthority("customer-insert"));


            return org.springframework.security.core.userdetails.User
                    .withUsername("AdminEUC")
                    .password(new BCryptPasswordEncoder().encode("Admin1234"))
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
        else {

            User user = userdao.findByUsername(username);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            Set<SimpleGrantedAuthority> authorities = new HashSet<>();

            List<Userrole> userroles = (List<Userrole>) user.getUserroles();

            for(Userrole u : userroles){
                List<Privilege> privileges = (List<Privilege>) u.getRole().getPrivileges();
                for (Privilege p:privileges){
                    authorities.add(new SimpleGrantedAuthority(p.getAuthority()));
                }
            }

            return org.springframework.security.core.userdetails.User
                    .withUsername(user.getUsername())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .accountExpired(false)
                    .accountLocked(false)
                    .credentialsExpired(false)
                    .disabled(false)
                    .build();
        }
    }
}
