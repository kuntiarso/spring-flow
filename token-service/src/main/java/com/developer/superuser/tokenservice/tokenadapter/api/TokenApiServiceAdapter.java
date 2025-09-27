package com.developer.superuser.tokenservice.tokenadapter.api;

import com.developer.superuser.shared.project.springodt.sign.Basic;
import com.developer.superuser.shared.project.springodt.sign.Sign;
import com.developer.superuser.tokenservice.token.Token;
import com.developer.superuser.tokenservice.token.TokenApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class TokenApiServiceAdapter implements TokenApiService {
    private final Basic basic;
    private final TokenApiMapper tokenApiMapper;
    private final TokenApi tokenApi;

    @Override
    public Token fetchTokenB2b(Token token) {
        Sign sign = basic.generate(tokenApiMapper.mapSign(token));
        log.info("Printing basic sign result --- {}", sign);
        token.setSignature(sign.getSignature());
        token.setTimestamp(sign.getTimestamp());
        return tokenApi.fetchTokenB2b(token);
    }
}