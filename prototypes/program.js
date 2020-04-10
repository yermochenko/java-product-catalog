var formSummHandler = function() {
	//var a = parseFloat(document.getElementById('number-a').value);
	//var b = parseFloat(document.getElementById('number-b').value);
	var a = parseFloat(this['numberA'].value);
	var b = parseFloat(this['numberB'].value);
	var message = document.getElementById('block-message');
	message.style.display = 'block';
	if(!isNaN(a) && !isNaN(b)) { // NaN - Not a Number - спец. значение
		message.className = 'popup';
		var s = '<div>' + a + ' + ' + b + ' = <span style="font-weight:bold; color:blue;">' + (a+b) + '</span></div>';
		message.innerHTML += s;
//		var div = document.createElement('div');
//		var text = document.createTextNode(a + ' + ' + b + ' = ');
//		div.appendChild(text);
//		var span = document.createElement('span');
//		span.style.fontWeight = 'bold';
//		span.style.color = 'blue';
//		var text = document.createTextNode(a + b);
//		span.appendChild(text);
//		div.appendChild(span);
//		message.appendChild(div);
	} else {
		message.className = 'popup_danger';
		message.innerText = 'ERROR';
	}
	return false;
};

var formSumm = document.getElementById('form-summ');
if(formSumm) {
	formSumm.onsubmit = formSummHandler;
} else {
	console.log('HANDLER ADDING ERROR');
}
