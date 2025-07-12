package tn.esprit.spring.config;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.*;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;


@Component
@Aspect
@Slf4j
public class LoggingAspect {
    private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

    @Before("execution(public * tn.esprit.spring.service.*.*(..)) && !execution(public * tn.esprit.spring.service.DocumentSerializer.serialize(..))")
	public void logMethodEntry(JoinPoint joinPoint) {
		log.info("In method : " + joinPoint.getSignature().getName() + " : ");
	}

	@AfterReturning("execution(* tn.esprit.spring.service.*.*(..)) && !execution(public * tn.esprit.spring.service.DocumentSerializer.serialize(..))")
	public void logMethodExit1(JoinPoint joinPoint) {
		String name = joinPoint.getSignature().getName();
		log.info("Out of method without errors : " + name );
	}

	@AfterThrowing("execution(* tn.esprit.spring.service.*.*(..)) && !execution(public * tn.esprit.spring.service.DocumentSerializer.serialize(..))")
	public void logMethodExit2(JoinPoint joinPoint) {
		String name = joinPoint.getSignature().getName();
		log.error("Out of method with erros : " + name );
	}

	@After("execution(* tn.esprit.spring.service.*.*(..)) && !execution(public * tn.esprit.spring.service.DocumentSerializer.serialize(..))                              ")
	public void logMethodExit(JoinPoint joinPoint) {
		String name = joinPoint.getSignature().getName();
		log.info("Out of method : " + name );
	}
}