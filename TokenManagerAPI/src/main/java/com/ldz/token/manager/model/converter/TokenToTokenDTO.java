package com.ldz.token.manager.model.converter;

import com.ldz.converter.container.annot.BeanConverter;
import com.ldz.converter.container.inter.IConverter;
import com.ldz.token.manager.model.Token;
import com.ldz.token.manager.model.TokenDTO;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by loicd on 23/05/2017.
 */
@Component
@BeanConverter(initialBeanClass = Token.class, targetBeanClass = TokenDTO.class)
public class TokenToTokenDTO implements IConverter<Token, TokenDTO>{

    @Value("${parser.date-time-format}")
    private String parserFormat;

    @Override
    public TokenDTO apply(Token token) {
        TokenDTO tokenDTO = new TokenDTO();

        if(token != null){
            tokenDTO.setId(token.getIpaddress());
            tokenDTO.setData(token.getDataCached());
            tokenDTO.setTs(DateTimeFormat.forPattern(parserFormat).print(token.getTs()));
        }

        return tokenDTO;
    }
}
