package com.ldz.token.manager.repository;

import com.ldz.token.manager.model.Token;
import org.joda.time.DateTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by loicd on 22/05/2017.
 */
public interface TokenRepository extends JpaRepository<Token, String> {

    public List<Token> findAllBytsBefore(DateTime ts);

}
