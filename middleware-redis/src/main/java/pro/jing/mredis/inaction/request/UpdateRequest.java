package pro.jing.mredis.inaction.request;

import pro.jing.mredis.inaction.model.Product;

public class UpdateRequest implements Request{
	
	private Product product;
	
	public UpdateRequest(Product product) {
		super();
		this.product = product;
	}




	public void process() {
		
	}

}
