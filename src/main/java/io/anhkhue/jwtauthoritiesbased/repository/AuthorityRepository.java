package io.anhkhue.jwtauthoritiesbased.repository;

import io.anhkhue.jwtauthoritiesbased.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority, Integer> {

    Authority findById(int id);
}
