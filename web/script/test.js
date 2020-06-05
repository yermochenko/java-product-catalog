console.log('test js');
var request = new XMLHttpRequest();
request.onload = function() {
	var products = JSON.parse(this.responseText);
	var date = new Date();
	products.forEach((product) => {
		date.setTime(product.date);
		console.log(`${product.id}, ${product.category.name}, ${product.name}, ${product.price}, ${product.amount}, ${date}`);
	});
};
request.open('GET', url);
request.send();
console.log('request was send');