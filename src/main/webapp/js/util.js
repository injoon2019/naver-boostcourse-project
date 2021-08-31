const extractPathVariable = {
	getParameter : function(parameterName) {
		const urlSearchParams = new URLSearchParams(window.location.search);
		const params = Object.fromEntries(urlSearchParams.entries());
		return params[parameterName];
	}
}
