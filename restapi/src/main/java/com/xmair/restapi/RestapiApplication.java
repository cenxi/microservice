package com.xmair.restapi;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import io.undertow.UndertowOptions;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.MDC;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.autoconfigure.jms.artemis.ArtemisAutoConfiguration;
import org.springframework.boot.autoconfigure.jmx.JmxAutoConfiguration;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.session.SessionAutoConfiguration;
import org.springframework.boot.autoconfigure.social.SocialWebAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.web.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.webservices.WebServicesAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.WebSocketAutoConfiguration;
import org.springframework.boot.context.embedded.undertow.UndertowEmbeddedServletContainerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication(exclude = {
		WebServicesAutoConfiguration.class,
		RedisAutoConfiguration.class,
		RabbitAutoConfiguration.class,
		SessionAutoConfiguration.class,
		SocialWebAutoConfiguration.class,
		ThymeleafAutoConfiguration.class,
		SecurityAutoConfiguration.class,
        JdbcTemplateAutoConfiguration.class,
        JmxAutoConfiguration.class,
        MultipartAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
        WebSocketAutoConfiguration.class,
        ActiveMQAutoConfiguration.class,
        ArtemisAutoConfiguration.class,
        EmbeddedLdapAutoConfiguration.class,
		HibernateJpaAutoConfiguration.class
})
@ComponentScan(basePackages = "com.xmair.restapi")
@MapperScan("com.xmair.core.mapper")
@EnableDiscoveryClient
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
@EnableRetry

public class RestapiApplication {

	@Bean
	public AlwaysSampler defaultSampler(){
		return new AlwaysSampler();
	}
	public static void main(String[] args) {

		try {
			MDC.put("ip", InetAddress.getLocalHost().getHostAddress());
		}catch (UnknownHostException e){
			e.printStackTrace();
		}
		SpringApplication.run(RestapiApplication.class, args);


	}

	




}
