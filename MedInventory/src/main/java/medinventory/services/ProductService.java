package medinventory.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.search.MatchQuery;

import org.hibernate.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.stereotype.Service;



import medinventory.elasticrepositories.ProductRepository;
import medinventory.models.ProductCatalogue;

@Service
public class ProductService {

	@Autowired
	ProductRepository productRepository;
	
	private ElasticsearchOperations elasticsearchOperations;
	private static final String PRODUCT_INDEX = "products";
	
	public List<ProductCatalogue> findbyKeyword(String keyword){
		QueryBuilder queryBuilder=QueryBuilders.matchQuery(keyword,"productname");
		Iterable<ProductCatalogue> products=productRepository.findByProductName(keyword);
		List<ProductCatalogue> productMatches = new ArrayList<ProductCatalogue>();
		products.forEach(productMatches::add);
		return productMatches;
	}
}
