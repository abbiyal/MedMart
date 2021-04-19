package medgateway.models;

import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<Users,String>{
	
	 public Users findByResetToken(String token);
}
