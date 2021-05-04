package medinventory.config;

import java.time.Duration;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.client.RestTemplate;

import medinventory.elasticrepositories.ProductRepository;
import medinventory.jparepositories.ProductCatalogueJpaRepository;
import medinventory.models.ProductCatalogue;

@Configuration
@EnableJpaRepositories(basePackages = "medinventory.jparepositories")
@EnableElasticsearchRepositories(basePackages = "medinventory.elasticrepositories")
public class ElasticConfiguration {
	
	@Autowired
	ProductCatalogueJpaRepository productJpaRepository;
	
	@Autowired
	ProductRepository productElasticRepository;
	
	@Bean
    public RestHighLevelClient client() {
        ClientConfiguration clientConfiguration 
            = ClientConfiguration.builder()
                .connectedTo("localhost:9200")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }

//    @Bean
//    public ElasticsearchOperations elasticsearchTemplate() {
//        return new ElasticsearchRestTemplate(client());
//    }
	
	
	    @Bean
	    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) 
	    {
	        return restTemplateBuilder
	           .setConnectTimeout(Duration.ofSeconds(60))
	           .setReadTimeout(Duration.ofSeconds(60))
	           .build();
	    }
	    
//	    @PostConstruct
//	    @Transactional
//	    public void loadDataInElastic() {
//	    	Iterable<ProductCatalogue> products = productJpaRepository.findAll();
//	    	productElasticRepository.saveAll(products);
//	    	
//	    }
	

}
