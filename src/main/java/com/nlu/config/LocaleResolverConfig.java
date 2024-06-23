package com.nlu.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.List;
import java.util.Locale;

@Configuration
public class LocaleResolverConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

    private static final List<Locale> LOCALES = List.of(new Locale("en"), new Locale("vi"));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String languageHeader = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        if (StringUtils.hasLength(languageHeader)) {
            try {
                List<Locale.LanguageRange> ranges = Locale.LanguageRange.parse(languageHeader);
                return Locale.lookup(ranges, LOCALES);
            } catch (IllegalArgumentException e) {
                return Locale.US;
            }
        }
        return Locale.US;
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("i18n/messages");
        source.setDefaultEncoding("UTF-8");
        source.setUseCodeAsDefaultMessage(true);
        source.setCacheSeconds(3600);
        return source;
    }
}
