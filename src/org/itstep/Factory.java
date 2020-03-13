package org.itstep;

import org.itstep.logic.ProductService;
import org.itstep.logic.ProductServiceImpl;
import org.itstep.storage.ProductDao;
import org.itstep.storage.memory.ProductMemoryDaoImpl;
import org.itstep.ui.Command;
import org.itstep.ui.ProductDeleteCommand;
import org.itstep.ui.ProductListCommand;
import org.itstep.ui.ProductSaveCommand;

public class Factory {
	private Command productDeleteCommand = null;
	public Command getProductDeleteCommand() {
		if(productDeleteCommand == null) {
			ProductDeleteCommand command = new ProductDeleteCommand();
			productDeleteCommand = command;
			command.setProductService(getProductService());
		}
		return productDeleteCommand;
	}

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
			/*
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

	private ProductDao productStorage = null;
	public ProductDao getProductStorage() {
		if(productStorage == null) {
			productStorage = new ProductMemoryDaoImpl();
		}
		return productStorage;
	}
}
