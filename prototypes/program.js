var buttonOKHandler = function() {
	console.log('FUNCTION START');
	console.log(this);
	var a = parseInt(document.getElementById('number-a').value);
	var b = parseInt(document.getElementById('number-b').value);
	if(!isNaN(a) && !isNaN(b)) { // NaN - Not a Number - спец. значение
		console.log(a + b);
	} else {
		console.log('ERROR');
	}
};

var buttonOK = document.getElementById('button-ok');
if(buttonOK) {
	buttonOK.onclick = buttonOKHandler;
}

var buttonTest = document.getElementById('button-test');
if(buttonTest) {
	buttonTest.onclick = buttonOKHandler;
}

buttonOKHandler();
