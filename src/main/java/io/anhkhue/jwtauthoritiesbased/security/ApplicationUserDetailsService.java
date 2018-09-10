package io.anhkhue.jwtauthoritiesbased.security;

import io.anhkhue.jwtauthoritiesbased.model.Account;
import io.anhkhue.jwtauthoritiesbased.repository.AccountRepository;
import io.anhkhue.jwtauthoritiesbased.repository.AuthorityRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;
    private final AuthorityRepository authorityRepository;

    public ApplicationUserDetailsService(AccountRepository accountRepository,
                                         AuthorityRepository authorityRepository) {
        this.accountRepository = accountRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUsername(username);

        if (account == null) {
            throw new UsernameNotFoundException(username);
        }

        Collection<GrantedAuthority> authorities = Collections.singletonList((GrantedAuthority) ()
                -> authorityRepository.findById(account.getId()).getAuthorityName());

        return new User(account.getUsername(),
                        account.getPassword(),
                        authorities);
    }
}
