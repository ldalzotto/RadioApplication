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
@BeanConverter(initialBeanClass = TokenDTO.class, targetBeanClass = Token.class)
public class TokenDTOtoToken implements IConverter<TokenDTO, Token> {

    @Value("${parser.date-time-format}")
    private String parserFormat;

    @Override
    public Token apply(TokenDTO tokenDTO) {
        Token token = new Token();

        if (tokenDTO != null){
            token.setTs(DateTime.parse(tokenDTO.getTs(), DateTimeFormat.forPattern(parserFormat)));
            token.setDataCached(tokenDTO.getData());
            token.setIpaddress(tokenDTO.getId());
        }
        return token;
    }

}
