
			var data = {
				labels : ["Basic","Naming","Unusedcode","Codesize","Clone","Coupling"],
				datasets : [
					{
						barItemName: "name1",
						fillColor : "rgba(220,220,220,0.5)",
						strokeColor : "rgba(220,220,220,0)",
						data : [83,663,103,64,0,1407]
					},
					{
						barItemName: "name2",
						fillColor : "rgba(151,187,205,0.5)",
						strokeColor : "rgba(151,187,205,0)",
						data : [8,614,272,109,3,1400]
					},
					{
						barItemName: "name3",
						fillColor : "rgba(147,112,219,0.5)",
						strokeColor : "rgba(147,112,219,0)",
						data : [12,570,200,32,3,1100]
					}
				]
			};
			
			var chartBar = null;
			window.onload = function() {				
				var ctx = document.getElementById("barChart").getContext("2d");
				chartBar = new Chart(ctx).Bar(data);
				
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