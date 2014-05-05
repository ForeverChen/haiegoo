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
    	getRole: '${page_context}/admin/SysRole.jspx?func=getRole',
    	delRole: '${page_context}/admin/SysRole.jspx?func=delRole',
    	saveRole: '${page_context}/admin/SysRole.jspx?func=saveRole',
      	upRole: '${page_context}/admin/SysRole.jspx?func=upRole',
   		downRole: '${page_context}/admin/SysRole.jspx?func=downRole',
   		getUsersByRole: '${page_context}/admin/SysRole.jspx?func=getUsersByRole',   				
    	getModuleByRole: '${page_context}/admin/SysModule.jspx?func=getModule&action=SysRole'
    };
  	    
  	    
	//------------------------------------------------------------------------------------//
	
	//角色组添加/编辑窗口
    var winGroup = Ext.create('widget.window', {
    	id: 'winGroup',
		title: '角色组',
		width: 350,
		height: 230,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmGroup',
			bodyStyle:'padding:20px 0px 20px 20px',
			border: false,
			autoScroll: true,
			defaults: { 
				listeners: {
					specialkey: function(obj,e){
						 if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp("btnGroupOK").handler();
						}
					}
				}
			},
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 60, 
				msgTarget: 'side',
              	autoFitErrors: false, 
				width: 310
			},
			items: [{
			    xtype: 'hiddenfield',
			    id: 'group_hddRoleId',
			    name: 'role.roleId',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'group_hddPid',
			    name: 'role.pid',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'group_hddLeaf',
			    name: 'role.leaf',
			    value: ''
			},{
				xtype:'textfield',
				id: 'group_txtName',
				name: 'role.name',
				fieldLabel: '角色组',
				allowBlank: false
			},{
				xtype:'textarea',
				id: 'group_txtDescription',
				name: 'role.description',
				fieldLabel: '描述'
			},{
				xtype:'hiddenfield',
				id: 'group_hddType',
				name: 'role.type',
				value: 'ROLE_GROUP'
			}]
		})],
		buttons: [{
			id: "btnGroupOK",
			text:'确定',
			width: 80,
			handler: function(){			    		 		
				saveRole(Ext.getCmp("winGroup"), Ext.getCmp("frmGroup"), Ext.getCmp("treeRole"));
			}
		},{
			text:'取消',
			width: 80,
			handler: function(){
				Ext.getCmp("winGroup").hide();
			}
		}]
	});
	
  	    
	//角色添加/编辑窗口
    var winRole = Ext.create('widget.window', {
    	id: 'winRole',
		title: '角色信息',
		width: 375,
		height: 487,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmRole',
			bodyStyle:'padding:5px',
			border: false,
			autoScroll: false,
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 50, 
				msgTarget: 'side',
              	autoFitErrors: false, 
				width: 290
			},
			items: [{
				xtype: 'tabpanel',
				id: 'tabRole',
		        activeTab: 0, 
		        plain: true,
		        items: [{
		        	id: 'tabRoleNormal',
		            title: '常规',
		       	    layout: 'form',
		    		height: 360,
		       	    showed: true,
					bodyStyle:'padding:20px 0px 20px 20px',
					defaults: { 
						listeners: {
							specialkey: function(obj,e){
								 if (e.getKey() == Ext.EventObject.ENTER) {
									Ext.getCmp("btnRoleOK").handler();
								}
							}
						}
					},
		       	    items:[{
					    xtype: 'hiddenfield',
					    id: 'hddRoleId',
					    name: 'role.roleId',
					    value: ''
					},{
					    xtype: 'hiddenfield',
					    id: 'hddPid',
					    name: 'role.pid',
					    value: ''
					},{
					    xtype: 'hiddenfield',
					    id: 'hddLeaf',
					    name: 'role.leaf',
					    value: ''
					},{
						xtype:'textfield',
						id: 'txtName',
						name: 'role.name',
						fieldLabel: '角色名',
						allowBlank: false
					},{
						xtype:'textarea',
						id: 'txtDescription',
						name: 'role.description',
						fieldLabel: '描述'
					},{
						xtype:'combobox',
						id: 'cbbType',
						name: 'role.type',
						fieldLabel: '类型',
						editable: false,
						store: [['ROLE_ADMIN','管理员'],['ROLE_USER','普通用户']],
						value: 'ROLE_USER'
					},{
			            xtype: 'container',
			            id: 'conRoleUsers',
			            layout: 'column',
			            items: [{
			                xtype: 'label',
			                text: '成员:',
			                margin: '5 0 5 0'
			            }, Ext.create('Ext.grid.Panel', {
			    			id: 'gridRoleAdmin',
			                margin: '5 0 5 25',
			        		width: 250,
			        		height: 165,
			    			border: true,
			    			disableSelection: false,
			    			loadMask: true,
			    			store: Ext.create('Ext.data.Store', {
			    		        idProperty: 'userId',
			    		        fields: [
			    					'userId', 'username', 'name', 'code'
			    		        ],
			    		        proxy: {
			    		            type: 'jsonp',
			    		            url: Url.getUsersByRole,
			    		            reader: {
			    		                root: 'records'
			    		            },
			    		            simpleSortMode: true,
			    					listeners: {
			    		    			exception: function(proxy, request, operation, options) {
			    		    		    	Ext.storeException(proxy, request, operation, options);
			    		    			}
			    		    		}
			    		        }
			    		    }),
			    	        columns:[{
			    	            text: '用户名',
			    	            dataIndex: 'username',
			    	            width: 100,
			    	            sortable: true
			    	        },{
			    	            text: '姓名',
			    	            dataIndex: 'name',
			    	            width: 105,
			    	            sortable: true,
			    	            renderer: function(value, p, record) {
			    	            	return "["+ record.get("code") +"] " + value;
			    				}
			    	        }]
			    		})]
			        }]
		        },{
		        	id: 'tabRoleModule',
		            title: '权限',
		       	    layout: 'fit',
		       	    showed: true,
					bodyStyle:'padding:5px',
		            tabConfig: {
		                tooltip: '角色拥有的模块权限'
		            },
		       	    items:[Ext.create('Ext.tree.Panel', {
		    			id: 'treeRoleModule',
			    		height: 350,
		    	        border: false,
		    	        useArrows: true,
		    	        rootVisible: false,
		    	        modified: false,
		    	        store: Ext.create('Ext.data.TreeStore', {
		    	            proxy: {
		    	                type: 'ajax',
		    	                url: Url.getModuleByRole
		    	            }
		    	        }),
		    			viewConfig : {
		    				onCheckboxChange : function(e, t) {
		    				    var item = e.getTarget(this.getItemSelector(), this.getTargetEl()), record;  
		    				    if (item) {
		    				    	//标记为修改
		    				    	Ext.getCmp("treeRoleModule").modified = true;
		    				    	
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
		        }]
			}]
		})],
		buttons: [{
			id: "btnRoleOK",
			text:'确定',
			width: 80,
			handler: function(){			    		 		
				saveRole(Ext.getCmp("winRole"), Ext.getCmp("frmRole"), Ext.getCmp("treeRole"));
			}
		},{
			text:'取消',
			width: 80,
			handler: function(){
				Ext.getCmp("winRole").hide();
			}
		}]
	});
  	    
  	    
	//------------------------------------------------------------------------------------//

	//角色列表
 	var treeRole = Ext.create('Ext.tree.Panel', {
		id: 'treeRole',
		region: 'center',
		border: true,
		split:true,
        animate: true,
		autoScroll: true,
		useArrows: true,
		multiSelect: false,
		singleExpand: false,
		rootVisible: true,
		store: Ext.create('Ext.data.TreeStore', {
			idProperty: 'roleId',
			fields: [
				'roleId', 'pid', 'name', 'type', 'description', 'sort', 'leaf', 'nLeaf', 'modules'
			], 
		    root: {  
		    	id : 0,
		        text:'用户角色',  
		    	roleId : 0,
		        name:'用户角色',
		        iconCls: 'icon-role-root',
		        expanded: true
		    },
	        proxy: {
	            type: 'ajax',
	            url: Url.getRole,
				listeners: {
	    			exception: function(proxy, request, operation, options) {
	    		    	Ext.storeException(proxy, request, operation, options);
	    			}
	    		}
	        }
		}),
		columns:[{
            xtype: 'treecolumn',
			text: '角色名',
			dataIndex: 'name',
			width: 200,
			sortable: false
		},{
			text: '描述',
			dataIndex: 'description',
			width: 450,
			sortable: false
		}],
        listeners : {
        	itemdblclick : function(view, record, item, index, e, options){
        		Ext.getCmp("btnEdit").handler();	
        	}
        },
		tbar: [{
			text: '添加',
			iconCls: 'icon-add',
			menu: [{
				text: '组',
				iconCls: 'icon-role-group',
				handler: function(btn, event) {
					//判断是否选择一条记录
					var rows = Ext.getCmp("treeRole").selModel.getSelection();
					if (rows.length==1 && (rows[0].data.id==0 || rows[0].data.nLeaf==0)) {	
						//显示窗口
						winGroup.show(this,function(){
							this.setTitle("添加角色组");
							var frmGroup = Ext.getCmp("frmGroup");
							frmGroup.getForm().reset();
							frmGroup.items.get("group_hddRoleId").setValue(null);
							frmGroup.items.get("group_hddPid").setValue(rows[0].get("roleId"));
							frmGroup.items.get("group_hddLeaf").setValue(0);
						});
					}else{
						Ext.Msg.alert("提示", "请选择角色组");
						return;
					}
				}
			},{
				text: '角色',
				iconCls: 'icon-role-leaf',
				handler: function(btn, event) {
					//判断是否选择一条记录
					var rows = Ext.getCmp("treeRole").selModel.getSelection();
					if (rows.length==1 && (rows[0].data.id==0 || rows[0].data.nLeaf==0)) {
						//显示窗口
						winRole.show(this,function(){
							this.setTitle("添加角色");
							var frmRole = Ext.getCmp("frmRole");
				    		var tabRole = frmRole.items.get("tabRole");
				    		var tabRoleNormal = tabRole.items.get("tabRoleNormal");
				    		var tabRoleModule = tabRole.items.get("tabRoleModule");
							
							frmRole.getForm().reset();
							tabRole.setActiveTab(tabRoleNormal);	
							tabRoleNormal.items.get("hddRoleId").setValue(null);
							tabRoleNormal.items.get("hddPid").setValue(rows[0].get("roleId"));
							tabRoleNormal.items.get("hddLeaf").setValue(1);
							tabRoleNormal.items.get("cbbType").setValue('ROLE_USER');

							//载入成员
							tabRoleNormal.items.get("conRoleUsers").items.get("gridRoleAdmin").store.load({
								params:{
									roleId: null
								}
							});
							
							//勾选权限模块
				    		var treeRoleModule = tabRoleModule.items.get("treeRoleModule");
				    		treeRoleModule.modified = false;
				    		treeRoleModule.getStore().getRootNode().cascadeBy(function(node){
								if(node.raw){
									node.set('checked', false);
								}
							});	
						});						
					}else{
						Ext.Msg.alert("提示", "请选择角色组");
						return;
					}
				}
			}]
		},{
			id: 'btnEdit',
			text: '编辑',
			iconCls: 'icon-edit',
			handler: function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeRole").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
				
				if(rows[0].data.id==0){
					Ext.Msg.alert("提示", "根节点不能编辑");
					return;
				}
				
				
				//显示窗口
				if(rows[0].data.nLeaf==0){
					winGroup.show(this,function(){
						this.setTitle("编辑组");
						var frmGroup = Ext.getCmp("frmGroup");
						frmGroup.items.get("group_hddRoleId").setValue(rows[0].get("roleId"));
						frmGroup.items.get("group_txtName").setValue(rows[0].get("name"));
						frmGroup.items.get("group_txtDescription").setValue(rows[0].get("description"));
					});		
				}else{
					winRole.show(this,function(){
						this.setTitle("编辑角色");
						var frmRole = Ext.getCmp("frmRole");
			    		var tabRole = frmRole.items.get("tabRole");
			    		var tabRoleNormal = tabRole.items.get("tabRoleNormal");
			    		var tabRoleModule = tabRole.items.get("tabRoleModule");
			    		
			    		tabRole.setActiveTab(tabRoleNormal);			    		
						tabRoleNormal.items.get("hddRoleId").setValue(rows[0].get("roleId"));
						tabRoleNormal.items.get("txtName").setValue(rows[0].get("name"));
						tabRoleNormal.items.get("txtDescription").setValue(rows[0].get("description"));
						tabRoleNormal.items.get("cbbType").setValue(rows[0].get("type"));
						
						//载入成员
						tabRoleNormal.items.get("conRoleUsers").items.get("gridRoleAdmin").store.load({
							params:{
								roleId: rows[0].get("roleId")
							}
						});
						
						//勾选权限模块
						var roleModuleIds = rows[0].get("modules");
			    		var treeRoleModule = tabRoleModule.items.get("treeRoleModule");
			    		treeRoleModule.modified = false;
			    		treeRoleModule.getStore().getRootNode().cascadeBy(function(node){
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
			}
		},{
			text: '删除',
			iconCls: 'icon-del',
			handler: function(btn, event) {
				delRole(Ext.getCmp("treeRole"));
			}
        },'-',{
            text: '上移',
            iconCls: 'icon-up',
		    handler: function(btn, event) {
				upRole(Ext.getCmp("treeRole"));
		    }
        },{
            text: '下移',
            iconCls: 'icon-down',
		    handler: function(btn, event) {		    	
				downRole(Ext.getCmp("treeRole"));				    	
		    }
        }]
    });
		
  	    
	//------------------------------------------------------------------------------------//

  	 	
	//界面布局
	Ext.create('Ext.Viewport', {
		id: 'viewport',
		layout: 'border',
		title: '${module_title}',
		items: [treeRole],
		renderTo: document.body
	});
	    
	    	    
	//------------------------------------------------------------------------------------//
 	    
	
	//保存角色
	function saveRole(win, frm, tree){
		// 构建Ajax参数
		var ajaxparams = "";
		var treeRoleModule = Ext.getCmp("treeRoleModule");
		
		//勾选的模块
		ajaxparams += "&moduleModified="+ treeRoleModule.modified +"&moduleIds=";
		var checkedNodes = treeRoleModule.getChecked();
		for ( var i = 0; i < checkedNodes.length; i++) {
			if(checkedNodes[i].raw && checkedNodes[i].raw.moduleId){
				ajaxparams += checkedNodes[i].raw.moduleId;
				if (i < checkedNodes.length - 1) 
					ajaxparams += ",";
			}
		}
		
		// 提交表单
		frm.submit({
		    url: Url.saveRole,
			params : ajaxparams,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    success: function(form, action) {
		    	win.hide();
		    	
		    	var id = tree.selModel.getSelection()[0].data.id;
		    	var node;
		    	if(id==0){
		    		node = tree.store.getRootNode();
		    	}else{
		    		node = tree.store.getNodeById(id);	
		    	}
		    	
		    	//勾选模块
		    	var modules = new Array(action.result.role.modules.length);
		    	for(var i=0;i<modules.length;i++){
		    		modules[i] = action.result.role.modules[i].moduleId;
		    	}
		    	action.result.role.modules = modules;
		    	
		    	if((win.id=="winRole" && !Ext.getCmp("hddRoleId").getValue()) || (win.id=="winGroup" && !Ext.getCmp("group_hddRoleId").getValue())) {
		    		//添加
			    	action.result.role.id = action.result.role.roleId;
			    	action.result.role.leaf = true;
			    	action.result.role.nExpanded = 1;
			    	if(win.id=="winGroup"){
			    		action.result.role.iconCls = "icon-role-group";
				    	action.result.role.nLeaf = 0;
			    	}
			    	if(win.id=="winRole"){
			    		action.result.role.iconCls = "icon-role-leaf";
				    	action.result.role.nLeaf = 1;
			    	}
					
		    		node.set('leaf', false);
		    		node.appendChild(action.result.role);
		    		node.expand();
		    	}else{
		    		//编辑
		    		node.set('name', action.result.role.name);
		    		node.set('description', action.result.role.description);
		    		node.set('type', action.result.role.type);
		    		node.set('modules', action.result.role.modules);
		    	}
		    },
		    failure: function(form, action) {
		    	Ext.formFailure(form, action);
		    }
		});
	}
		
		
	//删除角色
	function delRole(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
				Ext.Msg.alert("提示", "根节点不能删除");
				return;
			}
    		
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的角色删除吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "roleId=" + rows[0].get('roleId');
    					
    					// 发送请求
    					tree.el.mask("正在删除角色...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delRole,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							tree.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
									var node = tree.selModel.getSelection()[0];
									node.remove();
									tree.selModel.select(0);
								}else{
									Ext.Msg.alert("提示", json.msg);
								}
    						},
    						failure : function(response, options) {
    							tree.el.unmask();
    							Ext.ajaxFailure(response, options);
    						}
    					});
    				}
    			}
    		});

    	} else {
    		Ext.Msg.alert("提示", "请选择角色");
    	}
	}	
	
	
	//上移角色
	function upRole(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
	    	if(rows[0].data.id==0){
				Ext.Msg.alert("提示", "根节点不能上移");
				return;
			}

    		//找到上一个节点
			var node = tree.store.getNodeById(rows[0].get('roleId'));
			var prevRoleId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i-1<0){
						return;
					}else{
						prevRoleId = node.parentNode.childNodes[i-1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "roleId=" + rows[0].get('roleId') + "&prevRoleId=" + prevRoleId;
			
			// 发送请求
			tree.el.mask("正在上移角色...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.upRole,
				params : ajaxparams,
				method : "POST",
				waitMsg : "正在上移...",
				success : function(response, options) {
					tree.el.unmask();
					var json = Ext.JSON.decode(response.responseText);
					if (json.success) {
						node.parentNode.insertBefore(node,node.previousSibling);
					}else{
						Ext.Msg.alert("提示", json.msg);
					}
				},
				failure : function(response, options) {
					tree.el.unmask();
					Ext.ajaxFailure(response, options);
				}
			});

    	} else {
    		Ext.Msg.alert("提示", "请选择角色");
    	}
	}
	
	
	//下移角色
	function downRole(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
	    	if(rows[0].data.id==0){
				Ext.Msg.alert("提示", "根节点不能下移");
				return;
			}			

    		//找到下一个节点
			var node = tree.store.getNodeById(rows[0].get('roleId'));
			var nextRoleId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i+1>=node.parentNode.childNodes.length){
						return;
					} else {    							
						nextRoleId = node.parentNode.childNodes[i+1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "roleId=" + rows[0].get('roleId') + "&nextRoleId=" + nextRoleId; 			
			
			// 发送请求
			tree.el.mask("正在下移角色...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.downRole,
				params : ajaxparams,
				method : "POST",
				waitMsg : "正在下移...",
				success : function(response, options) {
					tree.el.unmask();
					var json = Ext.JSON.decode(response.responseText);
					if (json.success) {
						node.parentNode.insertBefore(node.nextSibling, node);
					}else{
						Ext.Msg.alert("提示", json.msg);
					}
				},
				failure : function(response, options) {
					tree.el.unmask();
					Ext.ajaxFailure(response, options);
				}
			});

    	} else {
    		Ext.Msg.alert("提示", "请选择角色");
    	}
	}
  		
});
</script>
</head>
<body>
</body>
</html>