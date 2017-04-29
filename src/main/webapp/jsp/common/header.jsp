<%--
  Created by IntelliJ IDEA.
  User: floyd
  Date: 2017/3/22
  Time: 15:18
  To change this template use File | Settings | File Templates.
--%>
<header>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <span class="navbar-brand">Gitlab Monitor</span>
            </div>
	            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
	                <ul class="nav navbar-nav">
	                    <li id="home" class=""><a href="<%=request.getContextPath()%>/jsp/main/index.jsp">Home</a></li>
	                    <li id="drop" class="dropdown">
								<a href="#" class="dropdown-toggle" data-toggle="dropdown">Tools<strong class="caret"></strong></a>
								<ul class="dropdown-menu active">
									<li>
										 <a href="<%=request.getContextPath()%>/api/checkstyle">CheckStyle</a>
									</li>
									<li>
										 <a href="<%=request.getContextPath()%>/api/pmd">PMD</a>
									</li>
								</ul>
							</li>
	                    <li id="sta" class=""><a href="<%=request.getContextPath()%>/api/statistics">Statistics</a></li>
	                </ul>
					<ul class="nav navbar-nav navbar-right">
						<li><a href="<%=request.getContextPath()%>/api/config"><span class="glyphicon glyphicon-cog" aria-hidden="true"></span> Config</a></li>
					</ul>
	            </div>
        </div>
    </nav>
</header>
