package productcatalogue.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class ProductCatalogue {
	@Id
	private long productId; /// company name, med name, Dosestrength, size
	@NotNull
	private String productName;
	private String companyName;
	private String doseStrength;
	private String size;
	
	public String getProductId(String productName, String companyName, String doseStength, String size) {
		return SHA256()
	}
}
