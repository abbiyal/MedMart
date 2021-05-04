package medinventory.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import medinventory.elasticrepositories.ShopsElasticRepository;
import medinventory.jparepositories.ShopsJpaRepository;
import medinventory.models.Shops;

@Service
public class ShopsService {
	
	@Autowired
	ShopsElasticRepository shopsElasticRepository;
	
	@Autowired
	ShopsJpaRepository shopsJpaRepository;
	
	public String addShop(Shops shop) {
		System.out.println(shop);
		try {
		Long id=shopsJpaRepository.save(shop).getShopId();
		Optional<Shops> shop2 = shopsJpaRepository.findById(id);
		shopsElasticRepository.save(shop2.get());}
		catch(Exception e) {
			return "Error";
		}
		return "success";
	}
	

}
