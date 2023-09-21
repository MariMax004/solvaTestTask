package com.example.solvatask.utils;

import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;

public class FeignUtils {

    public static <T> T buildFeignClient(Class<T> clazz, String url,
                                         ObjectFactory<HttpMessageConverters> messageConverters) {
        return Feign.builder()
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters))
                .errorDecoder(new ErrorDecoder.Default())
                .contract(new SpringMvcContract())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .retryer(Retryer.NEVER_RETRY)
                .target(clazz, url);
    }
}