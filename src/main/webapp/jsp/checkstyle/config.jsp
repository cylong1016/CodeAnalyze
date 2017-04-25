<%--
  Created by IntelliJ IDEA.
  User: floyd
  Date: 2017/3/22
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap.css" type="text/css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/checkstyle.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/fileinput.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/gijgo.css" type="text/css"/>
<style>
    .panel-no-padding {
        padding: 0;
    }
    .panel-no-boder {
        border: 0;
    }
</style>
<div>
    <ul class="nav nav-pills nav-stacked left-chart-nav col-md-2 col-sm-2" id="tab" role="tablist">
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Group</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/config">Config</a></li>
    </ul>
    <div class="col-md-offset-3 col-sm-offset-3" id="content">
        <div id="check_config" class="panel panel-warning panel-no-padding col-md-5 col-sm-5">
            <div class="panel-heading">
                <h4>CheckStyle Checks 配置</h4>
            </div>
            <div class="panel-body">
                <div id="check_tree">

                </div>
            </div>
            <div class="panel-footer">
                <button class="btn btn-info" id="save_config">Save Config</button>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fileinput.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gijgo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript">
    $(function () {
        var basePath = "<%=request.getContextPath()%>";
        var panel_height = ($(window).height() - parseInt($('body').css('padding-top')) - $("header").height()) * 11/15;

        $('#check_tree').attr('style','overflow-y: scroll; height:'+panel_height+'px;');
        var checkTree = $('#check_tree').tree({
            primaryKey: 'id',
            uiLibrary: 'bootstrap',
            dataSource: basePath+'/api/checkstyle/api/config',
            checkboxes: true,
            checkedField: 'status'
        });
        $('#save_config').on('click', function () {
            var checkIds = checkTree.getCheckedNodes();
            $.ajax({
                url: basePath+'/api/checkstyle/api/config',
                method: 'POST',
                data: {
                    checkIds: checkIds
                }
            }).done(function (data) {
                alert('Update '+ data);
            });
        });
    })
</script>
