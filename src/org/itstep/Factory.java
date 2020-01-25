package org.itstep;

import org.itstep.domain.Product;
import org.itstep.logic.ProductService;
import org.itstep.logic.ProductServiceImpl;
import org.itstep.storage.ProductStorage;
import org.itstep.storage.memory.ProductMemoryStorageImpl;
import org.itstep.ui.Command;
import org.itstep.ui.ProductListCommand;

public class Factory {
	public Command getProductListCommand() {
		ProductListCommand command = new ProductListCommand();
		command.setProductService(getProductService());
		return command;
	}

	public ProductService getProductService() {
		ProductServiceImpl service = new ProductServiceImpl();
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
		return service;
	}

	public ProductStorage getProductStorage() {
		return new ProductMemoryStorageImpl();
	}
}
