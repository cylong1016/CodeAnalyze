			
			
			var chartBar = null;
			function setBar(barData) {				
				var ctx = document.getElementById("barChart").getContext("2d");
				chartBar = new Chart(ctx).Bar(barData);
				
				initEvent(chartBar, clickCall);
			}
			
			function clickCall(evt){
				var activeBar = chartBar.getBarSingleAtEvent(evt);
				if ( activeBar !== null )
					alert(activeBar.label + ": " + activeBar.barItemName + " ____ " + activeBar.value);
			}
			
			function initEvent(chart, handler) {
				var method = handler;
				var eventType = "click";
				var node = chart.chart.canvas;
								
				if (node.addEventListener) {
					node.addEventListener(eventType, method);
				} else if (node.attachEvent) {
					node.attachEvent("on" + eventType, method);
				} else {
					node["on" + eventType] = method;
				}
			}