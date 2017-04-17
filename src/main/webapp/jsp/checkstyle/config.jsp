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
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle">Check</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/group">Group</a></li>
        <li role="presentation" class="active"><a href="<%=request.getContextPath()%>/api/checkstyle/config">Config</a></li>
        <li role="presentation"><a href="<%=request.getContextPath()%>/api/checkstyle/stats">Statistics</a></li>
    </ul>
    <div class="col-md-offset-2 col-sm-offset-2" id="content">
        <div class="panel panel-default panel-no-boder col-md-offset-1 col-sm-offset-1 col-md-4 col-sm-4">
            <form id="upload_grade" class="form" role="form" enctype="multipart/form-data">
                <fieldset>
                    <legend>成绩上传</legend>
                    <div class="form-group">
                        <label for="check_time">检查时间</label>
                        <select id="check_time" class="form-control">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="upload_file">成绩csv</label>
                        <input type="file" class="file-loading" id="upload_file" name="file" >
                        <br/>
                    </div>
                    <%--<div class="form-group">--%>
                        <%--<button type="button" class="btn btn-default" id="upload_file_btn">提交</button>--%>
                    <%--</div>--%>
                </fieldset>
            </form>
            <br />
            <form id="new_group" class="form" role="form">
                <fieldset>
                    <legend>添加项目小组</legend>
                    <div class="form-group">
                        <label for="group_id">检查时间</label>
                        <input type="text" class="form-control" id="group_id" >
                    </div>
                    <div class="form-group">
                        <label for="group_name">小组名称</label>
                        <input type="text" class="form-control" id="group_name">
                    </div>
                    <br/>
                    <div class="form-group">
                        <button type="button" class="btn btn-default" id="new_group_btn">提交</button>
                    </div>
                </fieldset>
            </form>
        </div>
        <div class="col-md-1 col-sm-1"></div>
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
        var panel_height = ($(window).height() - parseInt($('body').css('padding-top')) - $("header").height()) * 16/15;

        $('#upload_file').fileinput({
            uploadUrl: '<%=request.getContextPath()%>/api/checkstyle/api/upload',
            allowedFileExtensions: ['csv', 'txt'],
            uploadAsync: true,
            autoReplace : true,
            maxFileCount : 1,
            uploadExtraData: function () {
                var data = {}
                data["check_id"] = $('#check_time').val();
                return data;
            }
        }).on('fileuploaded', function (e, data) {
            console.log(data.response.success);
            alert(data.response.success);
        });

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
        })
//        $("#tab a").click(function (e) {
//            e.preventDefault();
//            $(this).tab('show')
//        });

//    $.ajax({
//        url: basePath + '/api/checkstyle/'
//    })
    })
</script>
