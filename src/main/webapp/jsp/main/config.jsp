<%--
  Created by IntelliJ IDEA.
  User: floyd
  Date: 2017/3/22
  Time: 15:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<link rel="stylesheet" href="<%=request.getContextPath()%>/css/checkstyle.css" type="text/css"/>
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/bootstrap-datetimepicker.min.css" type="text/css"/>
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
    <div class="col-md-offset-1 col-sm-offset-1" id="content">
        <div id="check_info" class="panel panel-default panel-no-boder col-md-4 col-sm-4">
            <form class="form-horizontal" role="form" id="newCheckForm">
                <fieldset>
                    <legend>添加新的检查</legend>
                    <div class="form-group">
                        <label for="newCheckDate" class="col-md-2 col-sm-2 control-label">检查日期</label>
                        <div class="input-group date form_date col-sm-6 col-md-6" data-date="" data-date-format="dd MM yyyy" data-link-field="newCheckDate" data-link-format="yyyy-mm-dd">
                            <input class="form-control" type="text" value="" readonly>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                            <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                        </div>
                        <input type="hidden" id="newCheckDate" value="" /><br/>
                    </div>
                    <div class="form-group">
                        <label for="newCheckDescription" class="col-sm-2 col-md-2 control-label">检查描述</label>
                        <div class="input-group col-sm-6 col-md-6">
                            <input type="text" class="form-control" id="newCheckDescription" placeholder="添加描述">
                        </div><br/>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-offset-2 col-md-offset-2 ">
                            <button type="button" class="btn btn-default" id="newCheckSubmit">提交</button>
                        </div><br/>
                    </div>
                </fieldset>
            </form><br />
            <div id="line_charts"></div>
        </div>
        <div class="col-md-1 col-sm-1"></div>
        <div class="panel panel-default panel-no-boder col-md-4 col-sm-4" id="panel2">
            <form id="upload_grade" class="form" role="form" enctype="multipart/form-data">
                <fieldset>
                    <legend>成绩上传</legend>
                    <div class="form-group">
                        <label for="check_time">检查时间</label>
                        <select id="check_time" class="form-control"></select>
                    </div>
                    <div class="form-group">
                        <label for="upload_file">成绩csv[groupId, score, comment]</label>
                        <input type="file" class="file-loading" id="upload_file" name="file" >
                        <br/>
                    </div>
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

    </div>
</div>

<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-3.2.0.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/fileinput.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/gijgo.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/echarts.common.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript">
    $(function () {
        var basePath = "<%=request.getContextPath()%>";
        var panel_width = ($(window).width() - $("#tab").width()) * 15/32;
        var panel_height = ($(window).height() - parseInt($('body').css('padding-top')) - $("header").height() );
        var line_charts_height = panel_height - $('#newCheckForm').height();
        var line_charts_width = panel_width * 7/8;

        $("#check_info").attr("style", "width:" + panel_width + "px;height:" + panel_height + "px;");
        $("#line_charts").attr("style", "width:" + line_charts_width + "px;height:" + line_charts_height + "px;")

        $('.form_date').datetimepicker({
            language:  'zh-CN',
            weekStart: 1,
            todayBtn:  1,
            autoclose: 1,
            todayHighlight: 1,
            startView: 2,
            minView: 2,
            forceParse: 0
        });

        $.ajax({
            url: "<%=request.getContextPath()%>/api/check/api",
            method: "GET"
        }).done(function (data) {
            var checkLog = [];
            try{
                checkLog = JSON.parse(data);
            } catch (err){
                console.log(err.message);
            }
            checkLog.sort(function (a, b) {
                var keyA = new Date(a.date),
                    keyB = new Date(b.date);
                if( keyA < keyB ) return -1;
                if( keyA > keyB ) return 1;
                return 0
            });
            console.log(checkLog);
            var select_option_html = [];
            $.each(checkLog, function (index, value) {
                select_option_html.push('<option value="'+value.id+'">'+ value.date+'-'+value.description+'</option>')
            });
            $("#check_time").append(select_option_html.join(''));

            var line_charts = echarts.init(document.getElementById("line_charts"));
            drawLineChart(line_charts, checkLog);
        });

        $('#newCheckForm').on('click', '#newCheckSubmit', function () {
            var date = $('.form_date').data('datetimepicker').getDate();
            var description = $('#newCheckDescription').val();
            $('.glyphicon-remove').click();
            $('#newCheckDescription').val('');
            $.ajax({
                url: "<%=request.getContextPath()%>/api/check/api",
                method: "POST",
                data: {
                    day: date.getDate(),
                    month: date.getMonth()+1,
                    year: date.getFullYear(),
                    description: description
                }
            }).done(function (data) {
                var line_charts = echarts.getInstanceByDom(document.getElementById("line_charts"));
                var option = line_charts.getOption();
                option.xAxis[0].data.push(data);
                if(option.series.length===1){
                    var future = [];
                    for(var i=0, len=option.series[0].data.length-1; i<len; ++i){
                        future.push('-');
                    }
                    future.push(option.series[0].data[option.series[0].data.length-1]);
                    future.push(0);
                    option.series[0].data.push('-');
                    option.series.push({
                        type: 'line',
                        data: future,
                        lineStyle:{
                            normal:{
                                type: 'dotted',
                            }
                        }
                    });
                } else if(option.series.length===2){
                    option.series[0].data.push('-');
                    option.series[1].data.push(0);
                }
                line_charts.setOption(option);
            });
        });

        $('#upload_file').fileinput({
            uploadUrl: '<%=request.getContextPath()%>/api/checkstyle/api/upload',
            allowedFileExtensions: ['csv', 'txt'],
            uploadAsync: true,
            autoReplace : true,
            maxFileCount : 1,
            previewSettings: {
                text: {width: "160px", height: "136px"},
            },
            uploadExtraData: function () {
                var data = {}
                data["check_id"] = $('#check_time').val();
                return data;
            }
        }).on('fileuploaded', function (e, data) {
            console.log(data.response.success);
            alert(data.response.success);
        });


        function drawLineChart(obj, data) {
            var checkdate = [], checkgroup=[], check_future=[];
            $.each(data, function (index, value) {
                checkdate.push(value.date);
                if(value.checkCount===0){
                    check_future.push(0);
                }else {
                    checkgroup.push(value.checkCount);
                }
            });
            check_future.unshift(checkgroup[checkgroup.length-1]);
            for(var i=0, len=data.length-check_future.length; i<len; ++i){
                check_future.unshift('-');
            }
            var option = {
                title: {
                    text: '每次检查组数',
                    subtext: '供参考'
                },
                tooltip: {
                    trigger: 'axis'
                },
                toolbox: {
                    show: true,
                    feature: {
                        dataZoom: {
                            yAxisIndex: 'none'
                        },
                        dataView: {readOnly: false},
                        magicType: {type: ['line', 'bar']},
                        restore: {},
                        saveAsImage: {}
                    }
                },
                xAxis:  {
                    type: 'category',
                    boundaryGap: false,
                    data: checkdate
                },
                yAxis: {
                    type: 'value',
                    axisLabel: {
                        formatter: '{value}'
                    }
                },
                series: [
                    {
                        name: 'check组数',
                        type: 'line',
                        data: checkgroup,
                        markPoint: {
                            data: [
                                {type: 'max', name: '最大值'},
                                {type: 'min', name: '最小值'}
                            ]
                        },
                        markLine: {
                            data: [
                                {type: 'average', name: '平均值'}
                            ]
                        }
                    },
                    {
                        type: 'line',
                        data: check_future,
                        lineStyle:{
                            normal:{
                                type: 'dotted',
                            }
                        }
                    }
                ]
            };
            obj.setOption(option);
        }

    })
</script>
