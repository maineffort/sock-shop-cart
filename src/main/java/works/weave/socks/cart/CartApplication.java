package works.weave.socks.cart;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.FileReader;
import java.io.IOException;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.sleuth.sampler.AlwaysSampler;
import org.springframework.context.annotation.Bean;

@EnableDiscoveryClient
@SpringBootApplication
@EnableSwagger2
@EnablePrometheusEndpoint
public class CartApplication {
    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
    
    @Bean
	public AlwaysSampler defaultSampler() {
	  return new AlwaysSampler();
	}
	
    @Bean
    public Docket api() throws IOException, XmlPullParserException {
        MavenXpp3Reader reader = new MavenXpp3Reader();
        Model model = reader.read(new FileReader("pom.xml"));
        return new Docket(DocumentationType.SWAGGER_2)  
          .select() 
          .apis(RequestHandlerSelectors.basePackage("works.weave.socks.cart"))
          .paths(PathSelectors.any())                          
          .build().apiInfo(new ApiInfo("ShippingServiceApplication Api Documentation", "Documentation automatically generated", model.getParent().getVersion(), null, new Contact("Kennedy Torkura", "kennedy.wordpress.com", "run2obtain@gmail.com"), null, null));                                           
    }
}
