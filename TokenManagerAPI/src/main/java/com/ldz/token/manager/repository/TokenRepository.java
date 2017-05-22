package com.ldz.token.manager.repository;

import com.ldz.token.manager.model.Token;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by loicd on 22/05/2017.
 */
public interface TokenRepository extends JpaRepository<Token, String> {
}
