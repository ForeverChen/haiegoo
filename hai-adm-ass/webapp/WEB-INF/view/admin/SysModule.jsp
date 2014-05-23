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
       	getModule: '${page_context}/admin/SysModule.jspx?func=getModule',
       	delModule: '${page_context}/admin/SysModule.jspx?func=delModule',
       	saveModule: '${page_context}/admin/SysModule.jspx?func=saveModule',
       	upModule: '${page_context}/admin/SysModule.jspx?func=upModule',
       	downModule: '${page_context}/admin/SysModule.jspx?func=downModule',
       	getRole: '${page_context}/admin/SysRole.jspx?func=getRole&action=SysModule',
        getRoleByModule: '${page_context}/admin/SysModule.jspx?func=getRoleByModule',
        getUsersByModule: '${page_context}/admin/SysModule.jspx?func=getUsersByModule'
    };

    
 	//------------------------------------------------------------------------------------//
 	
    
	//应用模块添加/编辑窗口
	var winModule = Ext.create('widget.window', {
		id: 'winModule',
		title: '应用模块信息',
		width: 350,
		height: 255,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmModule',
			bodyStyle:'padding:20px 0 0 23px',
			border: false,
			autoScroll: true,
			defaults: { 
				listeners: {
					specialkey: function(obj,e){
						 if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp("btnOK").handler();
						}
					}
				}
			},
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 60, 
				msgTarget: 'side',
              	//autoFitErrors: false, 
				width: 280
			},
			items: [{
			    xtype: 'hiddenfield',
			    id: 'hddModuleId',
			    name: 'module.moduleId',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'hddPid',
			    name: 'module.pid',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'hddType',
			    name: 'module.type',
			    value: ''
			},
			{
				id: 'conCode',
				xtype: 'container',
			    layout: 'table',
			    items:[{
				    xtype:'textfield',
				    id: 'txtCode',
				    name: 'module.code',
				    fieldLabel: '编码',
					width: 235,
					regex:/^\d+$/,  
	                regexText:'编码必须是数字',
                    allowBlank: false, 
                    blankText: "编码不能为空，且父节点编码也不能为空", 
				    readOnly: true
				},{
					xtype: 'button',
					id: 'btnChangeCode',
				    text:'变更',
					width: 40,
					style:'margin:0 0 5px 5px',
				    handler: function(){
				    	var txtCode = Ext.getCmp("frmModule").items.get('conCode').items.get("txtCode");
				    	txtCode.setValue(txtCode.getValue().replace(/.{2}$/, getRandomCode()));
				    }
				}]
			},{
			    xtype:'textfield',
			    id: 'txtModuleText',
			    name: 'module.text',
			    fieldLabel: '模块名称',
			    allowBlank: false
			},{
			    xtype:'textfield',
			    id: 'txtUrl',
			    name: 'module.url',
			    fieldLabel: 'URL地址'
			},{
		        xtype: 'filefield',
		        id: 'fileImage',
			    name: 'module.image',
		        fieldLabel: '图片',
		        msgTarget: 'side',
		        buttonText: '浏览'
		    },{
				xtype:'combobox',
				id: 'cbbExpanded',
				name: 'module.expanded',
				fieldLabel: '是否展开',
				editable: false,
				store: [[1,'是'],[0,'否']],
				value: 1
			},{
			    xtype:'textfield',
			    id: 'txtDescription',
			    name: 'module.description',
			    fieldLabel: '描述'
			}]
		})],
		buttons: [{
			id: "btnOK",
		    text:'确定',
		    width: 80,
		    handler: function(){			    		 		
		    	saveModule(Ext.getCmp("winModule"), Ext.getCmp("frmModule"), Ext.getCmp("treeModule"));
		    }
		},{
			id: "btnCancel",
		    text:'取消',
		    width: 80,
		    handler: function(){
		    	Ext.getCmp("winModule").hide();
		    }
		}]
	});
 	
 	
	//查看用户窗口
    var winModuleAdmin = Ext.create('widget.window', {
		id: 'winModuleAdmin',
		title: '查看用户',
		width: 375,
		height: 487,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		buttonAlign: 'center',
		userId: null,
		items: [Ext.create('Ext.grid.Panel', {
			id: 'gridModuleAdmin',
			border: false,
			disableSelection: false,
			loadMask: true,
			store: Ext.create('Ext.data.Store', {
		        idProperty: 'userId',
		        fields: [
					'userId', 'username', 'name', 'code'
		        ],
		        proxy: {
		            type: 'jsonp',
		            url: Url.getUsersByModule,
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
	        columns:[Ext.create('Ext.grid.RowNumberer', {width:30}), {
	            text: '用户名',
	            dataIndex: 'username',
	            width: 140,
	            sortable: true
	        },{
	            text: '姓名',
	            dataIndex: 'name',
	            width: 190,
	            sortable: true,
	            renderer: function(value, p, record) {
	            	return "["+ record.get("code") +"] " + value;
				}
	        }]
		})]
	});
 	
 	
	//查看角色窗口
    var winModuleRole = Ext.create('widget.window', {
		id: 'winModuleRole',
		title: '查看角色',
		width: 375,
		height: 487,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		buttonAlign: 'center',
		userId: null,
		items: [Ext.create('Ext.tree.Panel', {
			id: 'treeModuleRole',
	        useArrows: true,
	        rootVisible: false,
	        store: Ext.create('Ext.data.TreeStore', {
	            proxy: {
	                type: 'ajax',
	                url: Url.getRole
	            }
	        }),
			viewConfig : {
				onCheckboxChange : function(e, t) {
				    return false;
				}
			}
	    })]
	});
    

 	//------------------------------------------------------------------------------------//
    
    var treeModule = Ext.create('Ext.tree.Panel', {
		id: 'treeModule',
		region: 'center',
		border: true,
		split:true,
        animate: true,
		autoScroll: true,
		useArrows: true,
		multiSelect: false,
		singleExpand: false,
		rootVisible: false,
		store: Ext.create('Ext.data.TreeStore', {
	  	    idProperty: 'moduleId',
			fields: [
				'moduleId', 'pid', 'text', 'code', 'type', 'url', 'image', 'description', 'expanded', 'sort'
			],
	        proxy: {
	            type: 'ajax',
	            url: Url.getModule,
				listeners: {
	    			exception: function(proxy, request, operation, options) {
	    		    	Ext.storeException(proxy, request, operation, options);
	    			}
	    		}
	        }
	    }),
        columns: [{
            xtype: 'treecolumn',
            text: '子系统、模块组、应用模块',
            dataIndex: 'text',
            width: 300,
          	sortable: false
        },{
            text: '编码',
            dataIndex: 'code',
            width: 150,
          	sortable: false
        },{
            text: '模块URL、功能URL、常量值',
            dataIndex: 'url',
            width: 400,
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
				text: '子系统',
				iconCls: 'icon-sub-system',
				handler: function(btn, event) {					
					//显示窗口
					winModule.show(this,function(){
						this.setTitle("添加子系统");
						this.setHeight(205);
						var frmModule = Ext.getCmp("frmModule");
						frmModule.getForm().reset();
						frmModule.items.get("txtModuleText").setFieldLabel("子系统");
						frmModule.items.get("hddModuleId").setValue(null);
						frmModule.items.get("hddPid").setValue(0);
						frmModule.items.get("hddType").setValue("SYSTEM");
						frmModule.items.get("txtUrl").setVisible(false);
						frmModule.items.get("fileImage").setVisible(false);
						frmModule.items.get("cbbExpanded").setVisible(false);
						Ext.getCmp("txtCode").setValue(getRandomCode());
						Ext.getCmp("txtCode").setReadOnly(false);
						Ext.getCmp("btnChangeCode").enable();
					});
				}
			},{
				text: '模块组',
				iconCls: 'icon-module-group',
				handler: function(btn, event) {
					//判断是否选择一条记录
					var rows = Ext.getCmp("treeModule").selModel.getSelection();
					if (rows.length==1 && (rows[0].data.type=="SYSTEM" || rows[0].data.type=="GROUP")) {	
						//显示窗口
						winModule.show(this,function(){
							this.setTitle("添加模块组");
							this.setHeight(233);
							var frmModule = Ext.getCmp("frmModule");
							frmModule.getForm().reset();
							frmModule.items.get("txtModuleText").setFieldLabel("模块组");
							frmModule.items.get("hddModuleId").setValue(null);
							frmModule.items.get("hddPid").setValue(rows[0].get("moduleId"));
							frmModule.items.get("hddType").setValue("GROUP");
							frmModule.items.get("txtUrl").setVisible(false);
							frmModule.items.get("fileImage").setVisible(false);
							frmModule.items.get("cbbExpanded").setVisible(true);	
							Ext.getCmp("txtCode").setValue(rows[0].get("code") ? rows[0].get("code") + getRandomCode():"");
							Ext.getCmp("txtCode").setReadOnly(true);
							Ext.getCmp("btnChangeCode").enable();
						});
					}else{
						Ext.Msg.alert("提示", "请选择子系统或模块组记录");
						return;
					}
				}
			},{
				text: '应用模块',
				iconCls: 'icon-module-app',
				handler: function(btn, event) {
					//判断是否选择一条记录
					var rows = Ext.getCmp("treeModule").selModel.getSelection();
					if (rows.length==1 && (rows[0].data.type=="SYSTEM" || rows[0].data.type=="GROUP")) {					
						//显示窗口
						winModule.show(this,function(){
							this.setTitle("添加应用模块");
							this.setHeight(263);
							var frmModule = Ext.getCmp("frmModule");
							frmModule.getForm().reset();
							frmModule.items.get("txtModuleText").setFieldLabel("应用模块");
							frmModule.items.get("txtUrl").setFieldLabel("URL");
							frmModule.items.get("hddModuleId").setValue(null);
							frmModule.items.get("hddPid").setValue(rows[0].get("moduleId"));
							frmModule.items.get("hddType").setValue("MODULE");
							frmModule.items.get("txtUrl").setVisible(true);
							frmModule.items.get("fileImage").setVisible(true);
							frmModule.items.get("cbbExpanded").setVisible(false);
							Ext.getCmp("txtCode").setValue(rows[0].get("code") ? rows[0].get("code") + getRandomCode():"");
							Ext.getCmp("txtCode").setReadOnly(true);
							Ext.getCmp("btnChangeCode").enable();
						});
					}else{
						Ext.Msg.alert("提示", "请选择子系统或模块组记录");
						return;
					}
				}
			},{
				text: '操作功能',
				iconCls: 'icon-module-function',
				handler: function(btn, event) {
					//判断是否选择一条记录
					var rows = Ext.getCmp("treeModule").selModel.getSelection();
					if (rows.length==1 && rows[0].data.type=="MODULE") {					
						//显示窗口
						winModule.show(this,function(){
							this.setTitle("添加操作功能");
							this.setHeight(233);
							var frmModule = Ext.getCmp("frmModule");
							frmModule.getForm().reset();
							frmModule.items.get("txtModuleText").setFieldLabel("功能名");
							frmModule.items.get("txtUrl").setFieldLabel("URL");
							frmModule.items.get("hddModuleId").setValue(null);
							frmModule.items.get("hddPid").setValue(rows[0].get("moduleId"));
							frmModule.items.get("hddType").setValue("FUNCTION");
							frmModule.items.get("txtUrl").setVisible(true);
							frmModule.items.get("fileImage").setVisible(false);
							frmModule.items.get("cbbExpanded").setVisible(false);
							Ext.getCmp("txtCode").setValue(rows[0].get("code") ? rows[0].get("code") + getRandomCode():"");
							Ext.getCmp("txtCode").setReadOnly(true);
							Ext.getCmp("btnChangeCode").enable();
						});
					}else{
						Ext.Msg.alert("提示", "请选择应用模块记录");
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
				var rows = Ext.getCmp("treeModule").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
	    	
				//显示窗口
				winModule.show(this,function(){
					var frmModule = Ext.getCmp("frmModule");
					frmModule.items.get("hddModuleId").setValue(rows[0].get("moduleId"));
					frmModule.items.get("hddPid").setValue(rows[0].get("pid"));
					frmModule.items.get("hddType").setValue(rows[0].get("type"));
					frmModule.items.get("txtModuleText").setValue(rows[0].get("text"));
					frmModule.items.get("txtUrl").setValue(rows[0].get("url"));
					frmModule.items.get("fileImage").setRawValue(rows[0].get("image"));
					frmModule.items.get("txtDescription").setValue(rows[0].get("description"));
					frmModule.items.get("cbbExpanded").setValue(rows[0].data.nExpanded!=null? rows[0].data.nExpanded : rows[0].raw.nExpanded);
					
					//获取code
					var textCode = Ext.getCmp("txtCode");
					var btnChangeCode = Ext.getCmp("btnChangeCode");
					if(rows[0].get("code")){
						textCode.setReadOnly(true);
						btnChangeCode.disable();
						textCode.setValue(rows[0].get("code"));
					}else{
						textCode.setReadOnly(true);
						btnChangeCode.enable();
						if(rows[0].parentNode && rows[0].parentNode.get("code")){
							textCode.setValue(rows[0].parentNode.get("code") + getRandomCode());
						}else{
							if(rows[0].data.type=="SYSTEM"){
								textCode.setReadOnly(false);
								textCode.setValue(getRandomCode());
							}else{
								textCode.setValue("");
							}
						}
					}
					
					switch(rows[0].data.type){
					case "SYSTEM":
						this.setTitle("编辑子系统");
						this.setHeight(205);
						frmModule.items.get("txtModuleText").setFieldLabel("子系统");
						frmModule.items.get("txtUrl").setVisible(false);
						frmModule.items.get("fileImage").setVisible(false);
						frmModule.items.get("cbbExpanded").setVisible(false);
						break;
					case "GROUP":
						this.setTitle("编辑模块组");
						this.setHeight(233);
						frmModule.items.get("txtModuleText").setFieldLabel("模块组");
						frmModule.items.get("txtUrl").setVisible(false);
						frmModule.items.get("fileImage").setVisible(false);
						frmModule.items.get("cbbExpanded").setVisible(true);
						break;
					case "MODULE":
						this.setTitle("编辑应用模块");
						this.setHeight(263);
						frmModule.items.get("txtModuleText").setFieldLabel("应用模块");
						frmModule.items.get("txtUrl").setFieldLabel("URL");
						frmModule.items.get("txtUrl").setVisible(true);
						frmModule.items.get("fileImage").setVisible(true);
						frmModule.items.get("cbbExpanded").setVisible(false);	
						break;
					case "FUNCTION":
						this.setTitle("编辑操作功能");
						this.setHeight(233);
						frmModule.items.get("txtModuleText").setFieldLabel("功能名");
						frmModule.items.get("txtUrl").setFieldLabel("URL");
						frmModule.items.get("txtUrl").setVisible(true);
						frmModule.items.get("fileImage").setVisible(false);
						frmModule.items.get("cbbExpanded").setVisible(false);	
						break;
					}					
				});
			}
		},{
			text: '删除',
			iconCls: 'icon-del',
			handler: function(btn, event) {
				delModule(Ext.getCmp("treeModule"));
			}
		},'-',{
			text: '上移',
			iconCls: 'icon-up',
			handler: function(btn, event) {
				upModule(Ext.getCmp("treeModule"));
			}
		},{
			text: '下移',
			iconCls: 'icon-down',
			handler: function(btn, event) {
				downModule(Ext.getCmp("treeModule"));				
			}
		},'-',{
			text: '合并',
			iconCls: 'icon-up',
			handler: function(btn, event) {
				window.setTimeout(function(){
				    Ext.getCmp("treeModule").collapseAll();
				}, 200);	
			}
		},{
			text: '展开',
			iconCls: 'icon-down',
			handler: function(btn, event) {
				window.setTimeout(function(){
				    Ext.getCmp("treeModule").expandAll();
				}, 200);
			}
		},'-',{
            text: '查看用户',
            iconCls: 'icon-search',
		    handler: function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeModule").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
		    	
		    	winModuleAdmin.show(this,function(_this, options){
		    		winModuleAdmin.items.get("gridModuleAdmin").getStore().load({
		    			params:{
		    				moduleId: rows[0].get('moduleId')
		    			}
		    		});
		    	});
		    }
        },{
            text: '查看角色',
            iconCls: 'icon-search',
		    handler: function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeModule").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
		    	
		    	winModuleRole.show(this,function(_this, options){
		    		Ext.Ajax.request({
						url : Url.getRoleByModule,
						params : "moduleId=" + rows[0].get('moduleId'),
						method : "POST",
						waitMsg : '正在载入数据...',
						success : function(response, options) {
		                	//勾选角色列表
				    		var roleIds = Ext.decode(response.responseText);
				    		winModuleRole.items.get("treeModuleRole").getStore().getRootNode().cascadeBy(function(node){
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
						},
						failure : function(response, options) {
							Ext.ajaxFailure(response, options);
						}
					});
		    	});
		    }
        }]
	});
    
    
	//------------------------------------------------------------------------------------//
 	
	
	//保存应用模块
	function saveModule(win, frm, tree){
		// 提交表单
		frm.submit({
		    url: Url.saveModule,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    success: function(form, action) {
		    	win.hide();
		    	
		    	var selRow = tree.selModel.getSelection()[0];
		    	var id = selRow ? selRow.data.id : 0;
		    	var node;
		    	
				if(frm.items.get("hddModuleId").getValue()==null || frm.items.get("hddModuleId").getValue()==""){
		    		//添加
			    	action.result.module.id = action.result.module.moduleId;
			    	action.result.module.leaf = true;
			    	action.result.module.nExpanded = 1;
			    	
			    	if(id==0 || action.result.module.type=="SYSTEM"){
			    		node = tree.store.getRootNode();
			    	}else{
			    		node = tree.store.getNodeById(id);	
			    	}
		    		node.set('leaf', false);
		    		node.appendChild(action.result.module);
		    		node.expand();
		    	}else{
		    		//编辑
		    		node = tree.store.getNodeById(id);	
		    		node.set('text', action.result.module.text);
		    		node.set('code', action.result.module.code);
		    		node.set('url', action.result.module.url);
		    		node.set('description', action.result.module.description);
		    		node.set('nExpanded', action.result.module.expanded);
		    	}
		    },
		    failure: function(form, action) {
		    	Ext.formFailure(form, action);
		    }
		});
	}
		
		
	//删除应用模块
	function delModule(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的应用模块删除吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "moduleId=" + rows[0].get('moduleId');
    					
    					// 发送请求
    					tree.el.mask("正在删除应用模块...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delModule,
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
    		Ext.Msg.alert("提示", "请选择应用模块");
    	}
	}
	
	
	//上移应用模块
	function upModule(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
    		//找到上一个节点
			var node = tree.store.getNodeById(rows[0].get('moduleId'));
			var prevModuleId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i-1<0){
						return;
					}else{
						prevModuleId = node.parentNode.childNodes[i-1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "moduleId=" + rows[0].get('moduleId') + "&prevModuleId=" + prevModuleId;    					
			
			// 发送请求
			tree.el.mask("正在上移应用模块...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.upModule,
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
    		Ext.Msg.alert("提示", "请选择应用模块");
    	}
	}
	
	//下移应用模块
	function downModule(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
    		//找到下一个节点
			var node = tree.store.getNodeById(rows[0].get('moduleId'));
			var nextModuleId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i+1>=node.parentNode.childNodes.length){
						return;
					} else {    							
						nextModuleId = node.parentNode.childNodes[i+1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "moduleId=" + rows[0].get('moduleId') + "&nextModuleId=" + nextModuleId;    					
			
			// 发送请求
			tree.el.mask("正在下移应用模块...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.downModule,
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
    		Ext.Msg.alert("提示", "请选择应用模块");
    	}
	}
	
	
	//获取两位字符的code
	function getRandomCode(){
	    return Math.floor(Math.random()*90+10);
	}
    
    
 	//------------------------------------------------------------------------------------//

 	
    //界面布局
	Ext.create('Ext.Viewport', {
		id: 'viewport',
	    layout: 'border',
	    title: '${module_title}',
	    items: [treeModule],
		renderTo: document.body
	});

});
</script>
</head>
<body>
</body>
</html>