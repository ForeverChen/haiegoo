<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../meta.jsp" %>
<title>${module_title}</title>
<script type="text/javascript">
Ext.onReady(function() {
	Ext.tip.QuickTipManager.init();
    
	//业务请求的URL
	Url = {
		getAdmin: '${page_context}/admin/SysAdmin.jspx?func=getAdmin',
		delAdmin: '${page_context}/admin/SysAdmin.jspx?func=delAdmin',
		saveAdmin: '${page_context}/admin/SysAdmin.jspx?func=saveAdmin',
    	getAdminRole: '${page_context}/admin/SysRole.jspx?func=getRole&action=SysAdmin',
    	getAdminModule: '${page_context}/admin/SysModule.jspx?func=getModule&action=SysAdmin'
	};
  	    
	//------------------------------------------------------------------------------------//
  	    
	//用户添加/编辑窗口
	var winAdmin = Ext.create('widget.window', {
		id: 'winAdmin',
		title: '用户信息',
		width: 375,
		height: 487,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmAdmin',
			bodyStyle:'padding:5px',
			border: false,
			autoScroll: false,
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 70, 
              	autoFitErrors: false,  //展示错误信息时 是否自动调整字段组件的宽度 
				msgTarget: 'side'
			},
			items: [{
				xtype: 'tabpanel',
				id: 'tabAdmin',
		        activeTab: 0, 
		        plain: true,
		        items: [{
		        	id: 'tabAdminNormal',
		            title: '常规',
		       	    showed: true,
		    		layout: 'form',
		    		height: 360,
					bodyStyle:'padding:20px 0px 20px 20px',
					defaults: { 
						listeners: {
							specialkey: function(obj,e){
								 if (e.getKey() == Ext.EventObject.ENTER) {
									Ext.getCmp("btnOK").handler();
								}
							}
						}
					},
		       	    items:[{
					    xtype: 'hiddenfield',
					    id: 'userId',
					    name: 'user.userId',
					    value: ''
					},{
					    xtype:'textfield',
					    id: 'txtUsername',
					    name: 'user.username',
					    fieldLabel: '用户名',
					    allowBlank: false
					},{
					    xtype:'textfield',
					    id: 'txtPassword',
					    name: 'user.password',
					    fieldLabel: '密码',
					    inputType:"password",
					    allowBlank: false
					},{
					    xtype:'textfield',
					    vtype:'repassword',
					    id: 'txtRePassword',
					    name: 'user.rePassword',
					    fieldLabel: '确认密码',
					    inputType:"password",
					    initialPassField:'txtPassword',
					    allowBlank: false
					},{
					    xtype:'textfield',
					    id: 'txtCode',
					    name: 'user.code',
					    fieldLabel: '工号',
					    allowBlank: false
					},{
					    xtype:'textfield',
					    id: 'txtName',
					    name: 'user.name',
					    fieldLabel: '姓名',
					    allowBlank: false
					},{
					    xtype:'combobox',
					    id: 'cbbSex',
					    name: 'user.sex',
					    fieldLabel: '性别',
						editable: false,
						store: [['男','男'],['女','女']]
					},{
					    xtype:'textfield',
					    id: 'txtDept',
					    name: 'user.dept',
					    fieldLabel: '部门'
		            },{
					    xtype:'textfield',
					    id: 'txtPosition',
					    name: 'user.position',
					    fieldLabel: '职务'
					},{
					    xtype:'textfield',
					    vtype:'mobile',
					    id: 'txtMobile',
					    name: 'user.mobile',
					    fieldLabel: '手机'
					},{
					    xtype:'textfield',
					    vtype: 'email',
					    id: 'txtEmail',
					    name: 'user.email',
					    fieldLabel: '邮箱'
					},{
						xtype: 'radiogroup',
						id: 'radState',
						fieldLabel: '是否启用',
						items: [
						    {boxLabel: '启用', name: 'user.state', inputValue: 1},
						    {boxLabel: '禁用', name: 'user.state', inputValue: 2}
						]
					}]
		        },{
		        	id: 'tabAdminRole',
		            title: '角色',
		       	    showed: true,
					bodyStyle:'padding:5px',
		            tabConfig: {
		                tooltip: '用户隶属于的角色'
		            },
		       	    items:[Ext.create('Ext.tree.Panel', {
		    			id: 'treeAdminRole',
			    		height: 350,
		    	        border: false,
		    	        useArrows: true,
		    	        rootVisible: false,
		    	        modified: false,
		    	        store: Ext.create('Ext.data.TreeStore', {
		    	            proxy: {
		    	                type: 'ajax',
		    	                url: Url.getAdminRole
		    	            }
		    	        }),
		    			viewConfig : {
		    				onCheckboxChange : function(e, t) {
		    				    var item = e.getTarget(this.getItemSelector(), this.getTargetEl()), record;  
		    				    if (item) {
		    				    	//标记为修改
		    				    	Ext.getCmp("treeAdminRole").modified = true;
		    				    	
		    				    	//获取节点
		    						record = this.getRecord(item);  
		    						var check = !record.get('checked');  
		    						record.set('checked', check);
		    						if (check) {
		    							record.bubble(function(parentNode) {
		    								parentNode.set('checked', true);  
		    							});  
		    							record.cascadeBy(function(node) {  
		    								node.set('checked', true);  
		    							});  
		    						} else {  
		    							record.cascadeBy(function(node) {  
		    								node.set('checked', false);  
		    							});
		    						}  
		    				    }  
		    				}
		    			}
		    	    })]
		        },{
		        	id: 'tabAdminModule',
		            title: '查看权限',
		       	    layout: 'fit',
		       	    showed: true,
					bodyStyle:'padding:5px',
		            tabConfig: {
		                tooltip: '查看用户所拥有的权限，不可编辑'
		            },
		       	    items:[Ext.create('Ext.tree.Panel', {
		    			id: 'treeAdminModule',
			    		height: 350,
		    	        border: false,
		    	        useArrows: true,
		    	        rootVisible: false,
		    	        store: Ext.create('Ext.data.TreeStore', {
		    	            proxy: {
		    	                type: 'ajax',
		    	                url: Url.getAdminModule
		    	            }
		    	        }),
		    			viewConfig : {
		    				onCheckboxChange : function(e, t) {
		    				    var item = e.getTarget(this.getItemSelector(), this.getTargetEl()), record;  
		    				    if (item) {		    				    	
		    				    	//获取节点
		    						record = this.getRecord(item);  
		    						var check = !record.get('checked');						
		    						
		    						//勾选角色模块权限
		    						var selRow = Ext.getCmp("gridAdmin").selModel.getSelection();
		    						if(selRow && selRow.length>0){
			    						var roleModuleIds = selRow[0].get("roleModuleIds");
			    						for(var i=0;i<roleModuleIds.length;i++){
			    							if(record.raw.moduleId==Number(roleModuleIds[i])){
			    								check = true;
			    							}
			    						}
		    						}
		    				    }  
		    				}
		    			}
		    	    })]
		        }]
			}]
		})],
		buttons: [{
			id: "btnOK",
		    text:'确定',
		    width: 80,
		    handler: function(){
		    	saveAdmin(Ext.getCmp("winAdmin"), Ext.getCmp("frmAdmin"), Ext.getCmp("gridAdmin"));
		    }
		},{
			id: "btnCancel",
		    text:'取消',
		    width: 80,
		    handler: function(){
		    	Ext.getCmp("winAdmin").hide();
		    }
		}]
	});
  	    
  	    
	//------------------------------------------------------------------------------------//
	
	var storeAdmin = Ext.create('Ext.data.Store', {
        pageSize: 50,
        remoteSort: true,
        idProperty: 'userId',
        fields: [
			'userId', 'username', 'name', 'code', 'sex', 'dept', 
			'position',	'mobile', 'email', 'regTime', 'state', 
			'roleIds', 'roleNames', 'roleModuleIds'
        ],
        proxy: {
            type: 'jsonp',
            url: Url.getAdmin,
            reader: {
                root: 'records',
                totalProperty: 'count'
            },
            simpleSortMode: true,
			listeners: {
    			exception: function(proxy, request, operation, options) {
    		    	Ext.storeException(proxy, request, operation, options);
    			}
    		}
        },
        sorters: [{
            property: 'userId',
            direction: 'asc'
        }],
        listeners: {
        	beforeload: function(store, operation, options) {
        		// 设置查询参数
                Ext.apply(store.proxy.extraParams, { 
                	searchKey: Ext.getCmp("searchKey").getValue()
                });
			}
		}
    });
	
    //用户列表
    var gridAdmin = Ext.create('Ext.grid.Panel', {
		id: 'gridAdmin',
		region: 'center',
		border: true,
		disableSelection: false,
		loadMask: true,
		store: storeAdmin,
		selModel: Ext.create('Ext.selection.CheckboxModel'),
        columns:[{
            text: '用户名',
            dataIndex: 'username',
            width: 150,
            sortable: true
        },{
            text: '姓名',
            dataIndex: 'name',
            width: 150,
            sortable: true,
            renderer: function(value, p, record) {
            	return "["+ record.get("code") +"] " + value;
			}
        },{
            text: '角色',
            dataIndex: 'roleNames',
            width: 300,
            sortable: false
        },{
            text: '部门',
            dataIndex: 'dept',
            width:200,
            sortable: false
        },{
            text: '注册时间',
            dataIndex: 'regTime',
            width: 140,
            align: 'center',
            hidden: true,
            sortable: true
        },/*{
            text: '最后登录时间',
            dataIndex: 'lastLoginTime',
            width: 140,
            align: 'center',
            hidden: false,
            sortable: true
        },{
            text: '最后登录IP',
            dataIndex: 'lastLoginIp',
            width: 110,
            align: 'center',
            hidden: false,
            sortable: true
        },{
            text: '访问次数',
            dataIndex: 'visitCount',
            width: 90,
            align: 'right',
            sortable: true
        },*/{
            text: '使用状态',
            dataIndex: 'state',
            width: 90,
            align: 'center',
            sortable: true,
            renderer: function(value, p, record) {
            	if(value=='1')
            		return '启用';
            	else
            	if(value=='2')
            		return '禁用';
            	else
            		return '删除';
			}
        }],
        listeners : {
        	itemdblclick : function(view, record, item, index, e, options){
        		Ext.getCmp("btnEdit").handler();	
        	}
        },
        tbar: [{
            text: '添加',
            iconCls: 'icon-add',
		    handler: function(btn, event) {
		    	//显示窗口
		    	winAdmin.show(this,function(){
		    		this.setTitle("添加用户");
		    		var frmAdmin = Ext.getCmp("frmAdmin");
		    		var tabAdmin = frmAdmin.items.get("tabAdmin");
		    		var tabAdminNormal = tabAdmin.items.get("tabAdminNormal");
		    		var tabAdminRole = tabAdmin.items.get("tabAdminRole");
		    		var tabAdminModule = tabAdmin.items.get("tabAdminModule");
		    		
		    		tabAdmin.setActiveTab(tabAdminNormal);
		    		
		    		//初始化输入
		    		frmAdmin.getForm().reset();
		    		tabAdminNormal.items.get("userId").setValue(null);
		    		tabAdminNormal.items.get("radState").setValue({
		    		    'user.state': 1
		    		});
		    		tabAdminNormal.items.get("txtUsername").setReadOnly(false);
		    		
		    		//初始化角色列表
		    		var treeAdminRole = tabAdminRole.items.get("treeAdminRole");
		    		treeAdminRole.modified = false;
		    		treeAdminRole.getStore().getRootNode().cascadeBy(function(node){
						if(node.raw){
							node.set('checked', false);
						}
					});
					
					//添加时，权限列表不可选
					tabAdminModule.setDisabled(true);				
		    	});
		    }
        },{
        	id: 'btnEdit',
            text: '编辑',
            iconCls: 'icon-edit',
		    handler: function(btn, event) {
	    		//判断是否选择一条记录
	    		var rows = Ext.getCmp("gridAdmin").selModel.getSelection();
		    	if (rows.length != 1) {
		    		Ext.Msg.alert("提示", "请选择一条记录");
		    		return;
		    	}
		    	
		    	//显示窗口
		    	winAdmin.show(this,function(){
		    		this.setTitle("编辑用户");
		    		var frmAdmin = Ext.getCmp("frmAdmin");
		    		var tabAdmin = frmAdmin.items.get("tabAdmin");
		    		var tabAdminNormal = tabAdmin.items.get("tabAdminNormal");
		    		var tabAdminRole = tabAdmin.items.get("tabAdminRole");
		    		var tabAdminModule = tabAdmin.items.get("tabAdminModule");
		    		
		    		tabAdmin.setActiveTab(tabAdminNormal);
		   			
		    		//初始化输入
		    		tabAdminNormal.items.get("userId").setValue(rows[0].get("userId"));
		    		tabAdminNormal.items.get("txtUsername").setValue(rows[0].get("username"));
		    		tabAdminNormal.items.get("txtPassword").setValue("********");
		    		tabAdminNormal.items.get("txtRePassword").setValue("********");
		    		tabAdminNormal.items.get("txtCode").setValue(rows[0].get("code"));
		    		tabAdminNormal.items.get("txtName").setValue(rows[0].get("name"));
		    		tabAdminNormal.items.get("cbbSex").setValue(rows[0].get("sex"));
		    		tabAdminNormal.items.get("txtDept").setValue(rows[0].get("dept"));
		    		tabAdminNormal.items.get("txtPosition").setValue(rows[0].get("position"));
		    		tabAdminNormal.items.get("txtMobile").setValue(rows[0].get("mobile"));
		    		tabAdminNormal.items.get("txtEmail").setValue(rows[0].get("email"));
		    		tabAdminNormal.items.get("radState").setValue({
		    		    'user.state': Number(rows[0].get("state"))
		    		});
		    		
		    		tabAdminNormal.items.get("txtUsername").setReadOnly(true);
		    		
		    		//勾选角色列表
		    		var roleIds = rows[0].get("roleIds");
		    		var treeAdminRole = tabAdminRole.items.get("treeAdminRole");
		    		treeAdminRole.modified = false;
		    		treeAdminRole.getStore().getRootNode().cascadeBy(function(node){
						if(node.raw){
							node.set('checked', false);
							for(var i=0;i<roleIds.length;i++){
								if(node.raw.roleId==Number(roleIds[i])){
									node.set('checked', true);
									node.parentNode.set('checked', true);
									break;
								}
							}
						}
					});	
					
					//勾选权限列表
					tabAdminModule.setDisabled(false);	
					var roleModuleIds = rows[0].get("roleModuleIds");
		    		var treeAdminModule = tabAdminModule.items.get("treeAdminModule");
		    		treeAdminModule.getStore().getRootNode().cascadeBy(function(node){
		    			if(node.raw){
							node.set('checked', false);
							
							//勾选角色模块权限
							for(var i=0;i<roleModuleIds.length;i++){
								if(node.raw.moduleId==Number(roleModuleIds[i])){
									node.set('checked', true);									
									break;
								}
							}
						}
					});
		    	});
		    }
        },{
            text: '删除',
            iconCls: 'icon-del',
		    handler: function(btn, event) {
		    	delAdmin(Ext.getCmp("gridAdmin"));
		    }
        },'->',{
        	id: 'searchKey',
            xtype: 'textfield',
            name: 'searchKey',
            emptyText: '请输入用户名/姓名/工号',
            listeners : {
            	specialkey: function(obj,e){
					if(e.getCharCode()==e.ENTER)
						Ext.getCmp("btnSearch").handler();
				}
			}
        },{
        	id: 'btnSearch',
            text: '搜索',
            iconCls: 'icon-search',
		    handler: function(btn, event) {
		    	Ext.getCmp("gridAdmin").getStore().loadPage(1);
		    }
        }],
        bbar: Ext.create('Ext.PagingToolbar', {
            store: storeAdmin,
            displayInfo: true,
            displayMsg: '显示记录 {0} - {1} 共 {2}',
            emptyMsg: "没有可显示的记录"
        })
	});
		
  	    
	//------------------------------------------------------------------------------------//

  	 	
    //界面布局
	Ext.create('Ext.Viewport', {
		id: 'viewport',
	    layout: 'border',
	    title: '${module_title}',
	    items: [gridAdmin],
		renderTo: document.body
	});
	    
	    	    
	//------------------------------------------------------------------------------------//
 	    
	
	//保存用户
	function saveAdmin(win, frm, grid){
		// 构建Ajax参数
		var ajaxparams = "";
		var checkedNodes;

		var treeAdminRole = Ext.getCmp("treeAdminRole");	
		var treeAdminModule = Ext.getCmp("treeAdminModule");
		
		//勾选的角色
		ajaxparams += "roleModified="+ treeAdminRole.modified +"&roleIds=";
		checkedNodes = treeAdminRole.getChecked();
		for ( var i = 0; i < checkedNodes.length; i++) {
			if(checkedNodes[i].raw && checkedNodes[i].raw.roleId && checkedNodes[i].raw.nLeaf==1){
				ajaxparams += checkedNodes[i].raw.roleId;
				if (i < checkedNodes.length - 1) 
					ajaxparams += ",";
			}
		}
		
		// 提交表单
		frm.submit({
		    url: Url.saveAdmin,
			params : ajaxparams,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    success: function(form, action) {
		    	grid.store.load();
		    	win.hide();
		    },
		    failure: function(form, action) {
		    	Ext.formFailure(form, action);
		    }
		});		
		
	}
	
	
	//删除用户
	function delAdmin(grid){
    	var rows = grid.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的用户删除吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "userIds=";
    					for ( var i = 0; i < rows.length; i++) {
    						ajaxparams += rows[i].get('userId');
    						if (i < rows.length - 1) 
    							ajaxparams += ",";
    					}
    					
    					// 发送请求
    					grid.el.mask("正在删除用户...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delAdmin,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							grid.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
									grid.store.load();
								}else{
									Ext.Msg.alert("提示", json.msg);
								}
    						},
    						failure : function(response, options) {
    							grid.el.unmask();
    							Ext.ajaxFailure(response, options);
    						}
    					});
    				}
    			}
    		});

    	} else {
    		Ext.Msg.alert("提示", "请选择用户");
    	}
	}
	
	
	//搜索用户
	Ext.getCmp("btnSearch").handler();	
	
});
</script>
</head>
<body>
</body>
</html>