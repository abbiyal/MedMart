package medinventory.services;

import java.util.HashMap;
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
	
	public HashMap<String,String> findShop(Long shopId){
		HashMap<String,String> response = new HashMap<String,String>();
		Optional<Shops> shop = shopsElasticRepository.findById(shopId);
		if(shop.isPresent()) {
			response.put("shopName", shop.get().getShopName());
			response.put("shopAddress",shop.get().getAddress());
			return response;
		}
		response.put("response","error");
		return response;
	}
	

}
