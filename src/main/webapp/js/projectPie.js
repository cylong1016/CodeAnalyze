	var dataPie = [
				{
					label: "Basic",
					value: 82,
					color:"rgba(210,105,30,1)"
				},
				{
					label: "Naming",
					value : 663,
					color : "rgba(151,187,205,1)"
				},
				{
					label: "Unusedcode",
					value : 103,
					color : "rgba(147,112,219,1)"
				},
				{
					label: "Codesize",
					value : 64,
					color : "rgba(218,165,32,1)"
				},
				{
					label: "Clone",
					value : 10,
					color : "#999966"
				},
				{
					label: "Coupling",
					value : 1047,
					color : "rgba(220,220,220,1)"
				}		
			];
			
			var chartPie = null;
					
			var ctx = document.getElementById("pieChart").getContext("2d");
			chartPie = new Chart(ctx).Pie(dataPie, {segmentShowStroke : false, showTooltips : 1});
			