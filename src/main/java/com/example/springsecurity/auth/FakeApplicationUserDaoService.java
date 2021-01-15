package com.example.springsecurity.auth;

import static com.example.springsecurity.security.ApplicationUserRole.*;

import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Repository("fake")
public class FakeApplicationUserDaoService implements ApplicationUserDao {
    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUsername(String username) {
        return getApplicationUsers()
                .stream()
                .filter(applicationUser -> username.equals(applicationUser.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers() {
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
            new ApplicationUser(
                "ricardo",
                passwordEncoder.encode("as1mn1"),
                ADMIN.getGrantedAuthorities(),
                true, true, true, true
            ),
            new ApplicationUser(
                "derek",
                passwordEncoder.encode("as1mn1"),
                ADMINTRAINEE.getGrantedAuthorities(),
                true, true, true, true
            ),
            new ApplicationUser(
                "ted",
                passwordEncoder.encode("as1mn1"),
                STUDENT.getGrantedAuthorities(),
                true, true, true, true
            )
        );

        return applicationUsers;
    }

}
