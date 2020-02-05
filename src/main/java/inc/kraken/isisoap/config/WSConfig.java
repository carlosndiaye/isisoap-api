/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inc.kraken.isisoap.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/**
 *
 * @author carlosndiaye
 */


@Configuration
@EnableWs
public class WSConfig extends WsConfigurerAdapter {
    @Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/api/*");
	}
	@Bean(name = "persons")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema personsSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("PersonsPort");
		wsdl11Definition.setLocationUri("/api");
		wsdl11Definition.setTargetNamespace("gs_ws.kraken.inc");
		wsdl11Definition.setSchema(personsSchema);
		return wsdl11Definition;
	}
	@Bean
	public XsdSchema personsSchema() {
		return new SimpleXsdSchema(new ClassPathResource("persons.xsd"));
	}
}
