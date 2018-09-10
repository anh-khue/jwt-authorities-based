package io.anhkhue.jwtauthoritiesbased.repository;

import io.anhkhue.jwtauthoritiesbased.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    Account findByUsername(String username);
}
