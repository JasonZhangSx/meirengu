<!DOCTYPE html>
<html>
<head>
  	<title>任务调度中心</title>
  	<#import "/common/common.macro.ftl" as netCommon>
	<@netCommon.commonStyle />
	<!-- DataTables -->
  	<link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.css">
    <link rel="stylesheet" href="${request.contextPath}/static/adminlte/plugins/datetimepicker/datetimepicker.css">

</head>
<body class="hold-transition skin-blue sidebar-mini <#if cookieMap?exists && "off" == cookieMap["adminlte_settings"].value >sidebar-collapse</#if>">
<div class="wrapper">
	<!-- header -->
	<@netCommon.commonHeader />
	<!-- left -->
	<@netCommon.commonLeft "jobinfo" />
	
	<!-- Content Wrapper. Contains page content -->
	<div class="content-wrapper">
		<!-- Content Header (Page header) -->
		<section class="content-header">
			<h1>任务管理<small>任务调度中心</small></h1>
		</section>
		
		<!-- Main content -->
	    <section class="content">
	    
	    	<div class="row">
                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">JobGroup</span>
						<#--<input type="hidden" id="jobGroup" value="0">-->
                        <select class="form-control" id="jobGroup" >
						<#list JobGroupList as group>
                            <option value="${group.id}" >${group.name}</option>
						</#list>
                        </select>
                    </div>
                </div>
                <input type="hidden" id="executorHandler" value="">
                <div class="col-xs-4">
                    <div class="input-group">
                        <span class="input-group-addon">job完成状态</span>
                        <select class="form-control" id="finalized" >
                            <option value="" >全部</option>
                            <option value="0" >未完成</option>
                            <option value="1" >已完成</option>
                        </select>
                    </div>
                </div>
	            <div class="col-xs-2">
	            	<button class="btn btn-block btn-info" id="searchBtn">搜索</button>
	            </div>
	            <div class="col-xs-2">
	            	<button class="btn btn-block btn-success add" type="button">+新增任务</button>
	            </div>
          	</div>
	    	
			<div class="row">
				<div class="col-xs-12">
					<div class="box">
			            <div class="box-header">
			            	<h3 class="box-title">调度列表</h3>
			            </div>
			            <div class="box-body" >
			              	<table id="job_list" class="table table-bordered table-striped">
				                <thead>
					            	<tr>
					            		<th name="id" >id</th>
                                        <th name="jobGroup" >jobGroup</th>
                                        <th name="childJobKey" >JobKey</th>
					                  	<th name="jobDesc" >描述</th>
					                  	<th name="executorParam" >任务参数</th>
                                        <th name="startTime" >StartTime</th>
                                        <th name="jobCron" >Cron</th>
					                  	<th name="addTime" >新增时间</th>
					                  	<th name="updateTime" >更新时间</th>
					                  	<th name="author" >负责人</th>
					                  	<th name="alarmEmail" >报警邮件</th>
					                  	<th name="jobStatus" >状态</th>
					                  	<th>操作</th>
					                </tr>
				                </thead>
				                <tbody></tbody>
				                <tfoot></tfoot>
							</table>
						</div>
					</div>
				</div>
			</div>
	    </section>
	</div>
	
	<!-- footer -->
	<@netCommon.commonFooter />
</div>

<!-- job新增.模态框 -->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog"  aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
            	<h4 class="modal-title" >新增任务</h4>
         	</div>
         	<div class="modal-body">
				<form class="form-horizontal form" role="form" >
					<div class="form-group">
						<label for="firstname" class="col-sm-2 control-label">任务组<font color="red">*</font></label>
						<div class="col-sm-4">
							<select class="form-control" name="jobGroup" >
		            			<#list JobGroupList as group>
		            				<option value="${group.id}" >${group.name}</option>
		            			</#list>
		                  	</select>
						</div>
                        <label for="lastname" class="col-sm-2 control-label">任务描述<font color="red">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="jobDesc" placeholder="请输入“描述”" maxlength="50" ></div>
					</div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">JobHandler<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="executorHandler" placeholder="请输入“JobHandler”" maxlength="100" ></div>
                        <label for="lastname" class="col-sm-2 control-label">触发器<font color="red">*</font></label>
                        <div class="col-sm-4">
                            <select class="form-control" name="triggerType" >
                                <option value="SimpleTrigger" >SimpleTrigger</option>
                                <option value="CronTrigger" >CronTrigger</option>
                            </select>
						</div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">StartTime<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" id="startTime" name="startTime"></div>
                        <label for="lastname" class="col-sm-2 control-label">Cron<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="jobCron" placeholder="请输入“Cron”" maxlength="20" ></div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">执行参数<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="executorParam" placeholder="请输入“执行参数”" maxlength="100" ></div>
                        <label for="lastname" class="col-sm-2 control-label">失败处理策略<font color="red">*</font></label>
                        <div class="col-sm-4">
                            <select class="form-control" name="executorFailStrategy" >
							<#list ExecutorFailStrategyEnum as item>
                                <option value="${item}" >${item.title}</option>
							</#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-2 control-label">负责人<font color="red">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="author" placeholder="请输入“负责人”" maxlength="50" ></div>
                        <label for="lastname" class="col-sm-2 control-label">报警邮件<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="alarmEmail" placeholder="请输入“报警邮件”，多个邮件地址逗号分隔" maxlength="100" ></div>
                    </div>

                    <hr>
					<div class="form-group">
						<div class="col-sm-offset-3 col-sm-6">
							<button type="submit" class="btn btn-primary"  >保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						</div>
					</div>
					
				</form>
         	</div>
		</div>
	</div>
</div>

<!-- 更新.模态框 -->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog"  aria-hidden="true">
	<div class="modal-dialog modal-lg">
		<div class="modal-content">
			<div class="modal-header">
            	<h4 class="modal-title" >更新任务</h4>
         	</div>
         	<div class="modal-body">
				<form class="form-horizontal form" role="form" >
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">任务组<font color="red">*</font></label>
                        <div class="col-sm-4">
                            <select class="form-control" name="jobGroup" disabled >
							<#list JobGroupList as group>
                                <option value="${group.id}" >${group.name}</option>
							</#list>
                            </select>
                        </div>
                        <label for="lastname" class="col-sm-2 control-label">任务描述<font color="red">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="jobDesc" placeholder="请输入“描述”" maxlength="50" ></div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">JobHandler<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="executorHandler" placeholder="请输入“JobHandler”" maxlength="100" ></div>
                        <label for="lastname" class="col-sm-2 control-label">触发器<font color="red">*</font></label>
                        <div class="col-sm-4">
                            <select class="form-control" name="triggerType">
                                <option value="SimpleTrigger" >SimpleTrigger</option>
                                <option value="CronTrigger" >CronTrigger</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">StartTime<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" id="startTime2" name="startTime"></div>
                        <label for="lastname" class="col-sm-2 control-label">Cron<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="jobCron" placeholder="请输入“Cron”" maxlength="20" ></div>
                    </div>
                    <div class="form-group">
                        <label for="firstname" class="col-sm-2 control-label">执行参数<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="executorParam" placeholder="请输入“执行参数”" maxlength="100" ></div>
                        <label for="lastname" class="col-sm-2 control-label">失败处理策略<font color="red">*</font></label>
                        <div class="col-sm-4">
                            <select class="form-control" name="executorFailStrategy" >
                            <#list ExecutorFailStrategyEnum as item>
                                <option value="${item}" >${item.title}</option>
                            </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="lastname" class="col-sm-2 control-label">负责人<font color="red">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="author" placeholder="请输入“负责人”" maxlength="50" ></div>
                        <label for="lastname" class="col-sm-2 control-label">报警邮件<font color="black">*</font></label>
                        <div class="col-sm-4"><input type="text" class="form-control" name="alarmEmail" placeholder="请输入“报警邮件”，多个邮件地址逗号分隔" maxlength="100" ></div>
                    </div>

					<hr>
					<div class="form-group">
                        <div class="col-sm-offset-3 col-sm-6">
							<button type="submit" class="btn btn-primary"  >保存</button>
							<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                            <input type="hidden" name="id" >
						</div>
					</div>
				</form>
         	</div>
		</div>
	</div>
</div>

<@netCommon.commonScript />
<!-- datetimepicker -->
<script src="${request.contextPath}/static/adminlte/plugins/datetimepicker/datetimepicker.js"></script>
<!-- DataTables -->
<script src="${request.contextPath}/static/adminlte/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="${request.contextPath}/static/adminlte/plugins/datatables/dataTables.bootstrap.min.js"></script>
<script src="${request.contextPath}/static/plugins/jquery/jquery.validate.min.js"></script>
<!-- moment -->
<script src="${request.contextPath}/static/adminlte/plugins/daterangepicker/moment.min.js"></script>
<script src="${request.contextPath}/static/js/jobinfo.index.1.js"></script>
</body>
</html>
