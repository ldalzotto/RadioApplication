package com.ldz.converter.container.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by ldalzotto on 26/04/2017.
 */
@Component
public class BasePackageNameConfiguration {

    @Value("${base.package}")
    private String basePackage;

    public String getBasePackage() {
        return basePackage;
    }
}
