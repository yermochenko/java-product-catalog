window.addEventListener('load', function() {
	var searchElement = document.getElementById('search-text');
	var datalist = searchElement.list;
	searchElement.oninput = function () {
		while(datalist.firstChild) {
			datalist.removeChild(datalist.firstChild);
		}
		if(this.value.length >= 3) {
			var request = new XMLHttpRequest();
			request.onload = function() {
				if(this.status === 200) {
					var products = JSON.parse(this.responseText);
					products.forEach((product) => {
						var option = document.createElement('option');
						option.appendChild(document.createTextNode(product.name));
						datalist.appendChild(option);
					});
				}
			};
			request.open('GET', searchUrl + '?' + encodeURIComponent(this.value));
			request.send();
		}
	};
}, false);
