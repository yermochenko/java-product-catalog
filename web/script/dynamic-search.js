window.addEventListener('load', function() {
	var searchElement = document.getElementById('search-text');
	searchElement.oninput = function () {
		if(this.value.length >= 3) {
			var request = new XMLHttpRequest();
			request.onload = function() {
				var products = JSON.parse(this.responseText);
				console.log('***** list of products *****');
				products.forEach((product) => {
					console.log(`${product.name}`);
				});
			};
			request.open('GET', searchUrl + '?' + encodeURIComponent(this.value));
			request.send();
		}
	};
}, false);
