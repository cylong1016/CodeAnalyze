
			var dataBar = {
				labels : ["Basic","Naming","Unusedcode","Codesize","Clone","Coupling"],
				datasets : [
					{
						barItemName: "name1",
						fillColor : "rgba(220,220,220,1)",
						strokeColor : "rgba(220,220,220,0)",
						data : [83,663,103,64,0,1407]
					},
					{
						barItemName: "name2",
						fillColor : "rgba(151,187,205,1)",
						strokeColor : "rgba(151,187,205,0)",
						data : [8,614,272,109,3,1400]
					},
					{
						barItemName: "name3",
						fillColor : "rgba(147,112,219,1)",
						strokeColor : "rgba(147,112,219,0)",
						data : [12,570,200,32,3,1100]
					}
				]
			};
			
			var chartBar = null;
						
			var ctx = document.getElementById("barChart").getContext("2d");
			chartBar = new Chart(ctx).Bar(dataBar);