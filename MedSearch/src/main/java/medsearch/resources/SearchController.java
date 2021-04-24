package medsearch.resources;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearchController {

	@RequestMapping("/search")
	public HashMap<String,List<Result>> getResults(@RequestParam(name="keyword") String keyword){
		
	}
	
}
