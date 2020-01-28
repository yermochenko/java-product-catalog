package org.itstep;

import org.itstep.domain.Product;
import org.itstep.logic.ProductService;
import org.itstep.logic.ProductServiceImpl;
import org.itstep.storage.ProductStorage;
import org.itstep.storage.memory.ProductMemoryStorageImpl;
import org.itstep.ui.Command;
import org.itstep.ui.ProductListCommand;
import org.itstep.ui.ProductSaveCommand;

public class Factory {
	private Command productListCommand = null;
	public Command getProductListCommand() {
		if(productListCommand == null) {
			ProductListCommand command = new ProductListCommand();
			productListCommand = command;
			command.setProductService(getProductService());
		}
		return productListCommand;
	}

	private Command productSaveCommand = null;
	public Command getProductSaveCommand() {
		if(productSaveCommand == null) {
			ProductSaveCommand command = new ProductSaveCommand();
			productSaveCommand = command;
			command.setProductService(getProductService());
		}
		return productSaveCommand;
	}

	private ProductService productService = null;
	public ProductService getProductService() {
		if(productService == null) {
			ProductServiceImpl service = new ProductServiceImpl();
			productService = service;
			service.setProductStorage(getProductStorage());
			//*
			Product product;
			product = new Product();
			product.setCategory("канцтовары");
			product.setName("Ручка");
			product.setPrice(23L);
			product.setAmount(15);
			service.save(product);
			product = new Product();
			product.setCategory("канцтовары");
			product.setName("Карандаш");
			product.setPrice(12L);
			product.setAmount(25);
			service.save(product);
			product = new Product();
			product.setCategory("товары для кухни");
			product.setName("Нож");
			product.setPrice(123L);
			product.setAmount(5);
			service.save(product);
			//*/
		}
		return productService;
	}

	private ProductStorage productStorage = null;
	public ProductStorage getProductStorage() {
		if(productStorage == null) {
			productStorage = new ProductMemoryStorageImpl();
		}
		return productStorage;
	}
}
