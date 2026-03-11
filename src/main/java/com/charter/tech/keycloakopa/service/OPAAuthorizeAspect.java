package com.charter.tech.keycloakopa.service;

import com.charter.tech.keycloakopa.annotaion.OPAAuthorize;
import com.charter.tech.keycloakopa.exception.CustomAccessDeniedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class OPAAuthorizeAspect {

    private final AuthorizationService authorizationService;

    @Around("@annotation(opa)")
    public Object authorize(ProceedingJoinPoint pjp, OPAAuthorize opa) throws Throwable {
        try {
            authorizationService.authorize(opa.resource(), opa.action());
            return pjp.proceed();
        } catch (CustomAccessDeniedException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Authorization failure", e);
        }
    }

    private Object resolveResource(ProceedingJoinPoint pjp, String spel) {

        if (spel.isBlank()) return null;

        MethodSignature sig = (MethodSignature) pjp.getSignature();
        String[] names = sig.getParameterNames();
        Object[] values = pjp.getArgs();

        EvaluationContext ctx = new StandardEvaluationContext();

        for (int i = 0; i < names.length; i++) {
            ctx.setVariable(names[i], values[i]);
        }
        return new SpelExpressionParser()
                .parseExpression(spel)
                .getValue(ctx);
    }
}
