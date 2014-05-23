<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../meta.jsp" %>
<title>${module_title}</title>
<script src="${app_ass_url}/WEB-RES/js/extjs4.2/ux/TreePicker.js" type="text/javascript"></script>
<script type="text/javascript">
Ext.onReady(function() {
	//初始化
	Ext.tip.QuickTipManager.init();
	
	//业务请求的URL
	Url = {
		getSpecification : 'spec-prop.jspx?func=getSpecification',
		delSpecification: 'spec-prop.jspx?func=delSpecification',
		saveSpecification: 'spec-prop.jspx?func=saveSpecification',
		upSpecification: 'spec-prop.jspx?func=upSpecification',
		downSpecification: 'spec-prop.jspx?func=downSpecification',
		getProperty: 'spec-prop.jspx?func=getProperty',
		saveProperty: 'spec-prop.jspx?func=saveProperty',
		delProperty: 'spec-prop.jspx?func=delProperty',
		upProperty: 'spec-prop.jspx?func=upProperty',
		downProperty: 'spec-prop.jspx?func=downProperty',
		getCategoryList : 'brand-cate.jspx?func=getCategoryList'
	};
	
	
	//-----------------------------------规格管理-----------------------------------//
	
	//添加/编辑窗口
	var winSpecification = Ext.create('widget.window', {
		id: 'winSpecification',
		title: '规格信息',
		width: 400,
		height: 180,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmSpecification',
			bodyStyle:'padding:20px 0 0 23px',
			border: false,
			autoScroll: true,
			defaults: { 
				listeners: {
					specialkey: function(obj,e){
						 if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp("btnOKSpec").handler();
						}
					}
				}
			},
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 70, 
				msgTarget: 'side',
              	autoFitErrors: false,  //展示错误信息时 是否自动调整字段组件的宽度 
				width: 350
			},
			items: [{
			    xtype: 'hiddenfield',
			    id: 'specification.id',
			    name: 'specification.id'
			},{
			    xtype: 'hiddenfield',
			    id: 'specification.pid',
			    name: 'specification.pid'
			},{
			    xtype: 'hiddenfield',
			    id: 'specification.path',
			    name: 'specification.path'
			},{
			    xtype:'textfield',
			    id: 'specification.name',
			    name: 'specification.name',
			    fieldLabel: '规格名称',
			    allowBlank: false
			},{
				xtype:'combo',
				id: 'specification.hasImage',
				name: 'specification.hasImage',
				fieldLabel: '是否有图片',
				editable: false,
				store: [
				        [1,'是'],
				        [0,'否']
				]
			},,{
				xtype: 'treepicker',
			    id: 'specification.catePath',
			    name: 'specification.catePath',
			    fieldLabel: '所属分类',
			    forceSelection : true,
		        editable : false,
				minPickerHeight: 200,
				rootVisible: false, 
				value: '',
				displayField: 'name',
				store: Ext.create('Ext.data.TreeStore',{
					fields: ['id', 'pid', 'path', 'name'],
					root: {
						id: 0,
						name: '',
						expanded: true
					},
					proxy : {
						type : 'ajax',
						url : Url.getCategoryList,
					}
				})
			}]
		})],
		buttons: [{
			id: "btnOKSpec",
		    text:'确定',
		    width: 80,
		    handler: function(){			    		 		
		    	saveSpecification(Ext.getCmp("winSpecification"), Ext.getCmp("frmSpecification"), Ext.getCmp("treeSpecification"));
		    }
		},{
		    text:'取消',
		    width: 80,
		    handler: function(){
		    	Ext.getCmp("winSpecification").hide();
		    }
		}]
	});

	var treeSpecification = Ext.create('Ext.tree.Panel', {
		id : 'treeSpecification',
		border : false,
		split : true,
		animate : true,
		autoScroll : true,
		useArrows : true,
		multiSelect : false,
		singleExpand : false,
		rootVisible : true,
		store : Ext.create('Ext.data.TreeStore', {
			idProperty : 'id',
			fields : [ 'id', 'pid', 'path', 'name', 'hasImage', 'catePath', 'catePathName', 'sort'],
			proxy : {
				type : 'ajax',
				url : Url.getSpecification,
			},
			root: {
				id: 0,
				name: '商品规格',
		        iconCls: 'icon-sub-system',
		        expanded: true
			}
		}),
		columns : [{
			xtype : 'treecolumn',
			text : '规格名称',
			dataIndex : 'name',
			width :250,
			sortable : false
		},{
			text: '是否有图片',
			dataIndex: 'hasImage',
			width: 200,
			sortable: false,
            renderer: function(value, p, record) {
            	if(record.data.pid==0){
	            	switch(value){
	            	case 0:
	            		return "否";
	            	case 1:
	            		return "是";
	            	default:
	            		return value;
	            	}
            	}
        		return "";
			}
		},{
			text : '所属分类',
			dataIndex : 'catePathName',
			width : 350,
			sortable : false
		}],
		listeners : {
			itemdblclick : function(view, record, item, index, e,
					options) {
				Ext.getCmp("btnEditSpec").handler();
			}
		},
		tbar : [{
			text : '添加',
			iconCls : 'icon-add',
			handler : function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeSpecification").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
				if(rows[0].data.id!=0 && rows[0].data.pid!=0){
					Ext.Msg.alert("提示", "不能添加下一层记录");
					return;
				}
				
				//显示窗口
				winSpecification.show(this,function(){
					var frmSpecification = Ext.getCmp("frmSpecification");
					frmSpecification.getForm().reset();
					frmSpecification.items.get("specification.pid").setValue(rows[0].get("id"));
					if(rows[0].data.id==0){
						this.setTitle("添加规格");
						this.setHeight(208);
						frmSpecification.items.get("specification.name").setFieldLabel("规格");
						frmSpecification.items.get("specification.hasImage").setVisible(true);
						frmSpecification.items.get("specification.catePath").setVisible(true);
					}else {
						this.setTitle("添加规格值");
						this.setHeight(148);
						frmSpecification.items.get("specification.name").setFieldLabel("规格值");
						frmSpecification.items.get("specification.hasImage").setVisible(false);
						frmSpecification.items.get("specification.catePath").setVisible(false);
					}
				});
			}
		},{
			id : 'btnEditSpec',
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function(btn, event) {
				var rows = Ext.getCmp("treeSpecification").selModel.getSelection();
				//判断是否选择一条记录
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
				if(rows[0].data.id==0){
					return;
				}
				
				//显示窗口
				winSpecification.show(this,function(){
					var frmSpecification = Ext.getCmp("frmSpecification");
					frmSpecification.getForm().reset();
					frmSpecification.items.get("specification.id").setValue(rows[0].get("id"));
					frmSpecification.items.get("specification.pid").setValue(rows[0].get("pid"));
					frmSpecification.items.get("specification.path").setValue(rows[0].get("path"));
					frmSpecification.items.get("specification.name").setValue(rows[0].get("name"));
					if(rows[0].data.pid==0){
						this.setTitle("编辑规格");
						this.setHeight(208);
						frmSpecification.items.get("specification.name").setFieldLabel("规格");
						frmSpecification.items.get("specification.hasImage").setVisible(true);
						frmSpecification.items.get("specification.catePath").setVisible(true);
						frmSpecification.items.get("specification.hasImage").setValue(rows[0].get("hasImage"));
						frmSpecification.items.get("specification.catePath").setValue(rows[0].get("catePath").split('/').pop());
					} else {
						this.setTitle("编辑规格值");
						this.setHeight(148);
						frmSpecification.items.get("specification.name").setFieldLabel("规格值");
						frmSpecification.items.get("specification.hasImage").setVisible(false);
						frmSpecification.items.get("specification.catePath").setVisible(false);
					}
				});	
			}
		},{
			text : '删除',
			iconCls : 'icon-del',
			handler : function(btn, event) {
				delSpecification(Ext.getCmp("treeSpecification"));
			}
		},'-', {
			text : '上移',
			iconCls : 'icon-up',
			handler : function(btn, event) {
				upSpecification(Ext.getCmp("treeSpecification"));
			}
		}, {
			text : '下移',
			iconCls : 'icon-down',
			handler : function(btn, event) {
				downSpecification(Ext.getCmp("treeSpecification"));				
			}
		}]
	});
	

	
	//-----------------------------------属性管理-----------------------------------//
  	
 	//属性添加/编辑窗口
    var winProperty = Ext.create('widget.window', {
    	id: 'winProperty',
		title: '属性',
		width: 400,
		height: 250,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmProperty',
			bodyStyle:'padding:20px 0 0 23px',
			border: false,
			autoScroll: true,
			defaults: { 
				listeners: {
					specialkey: function(obj,e){
						 if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp("btnOKProp").handler();
						}
					}
				}
			},
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 70, 
				msgTarget: 'side',
              	autoFitErrors: false,  //展示错误信息时 是否自动调整字段组件的宽度 
				width: 350
			},
			items: [{
			    xtype: 'hiddenfield',
			    id: 'property.id',
			    name: 'property.id',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'property.pid',
			    name: 'property.pid',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'property.path',
			    name: 'property.path',
			    value: ''
			},{
			    xtype: 'hiddenfield',
			    id: 'property.type',
			    name: 'property.type',
			    value: '1'
			},{
				xtype:'textfield',
				id: 'property.name',
				name: 'property.name',
				fieldLabel: '属性',
				allowBlank: false
			},{
				xtype:'combo',
				id: 'property.inputType',
				name: 'property.inputType',
				fieldLabel: '输入类型',
				editable: false,
				store: [
				        [0,'文本框'],
				        [1,'可编辑下拉框'],
				        [2,'不可编辑下拉框'],
				        [3,'单选框'],
				        [4,'复选框']
				]
			},{
				xtype: 'treepicker',
				id: 'property.catePath',
				name: 'property.catePath',
			    fieldLabel: '所属分类',
			    forceSelection : true,
		        editable : false,
				minPickerHeight: 200,
				rootVisible: false, 
				value: '',
				displayField: 'name',
				store: Ext.create('Ext.data.TreeStore',{
					fields: ['id', 'pid', 'path', 'name'],
					root: {
						id: 0,
						name: '',
						expanded: true
					},
					proxy : {
						type : 'ajax',
						url : Url.getCategoryList,
					}
				})
			}]
		})],
		buttons: [{
			id: "btnOKProp",
			text:'确定',
			width: 80,
			handler: function(){			    		 		
				saveProperty(Ext.getCmp("winProperty"), Ext.getCmp("frmProperty"), Ext.getCmp("treeProperty"));
			}
		},{
			text:'取消',
			width: 80,
			handler: function(){
				Ext.getCmp("winProperty").hide();
			}
		}]
	});
	
 	var treeProperty = Ext.create('Ext.tree.Panel', {
      	region: 'center',
		id: 'treeProperty',
		width: 400,
		border: false,
		split:true,
        animate: true,
		autoScroll: true,
		useArrows: true,
		multiSelect: false,
		singleExpand: false,
		rootVisible: true,
		store: Ext.create('Ext.data.TreeStore', {
			idProperty: 'id',
			fields: [
				'id', 'pid', 'name', 'path', 'inputType', 'catePath', 'catePathName', 'sort'
			],
			root: {  
		    	id : 0,  
		    	name:'商品属性',
		        iconCls: 'icon-sub-system',
		        expanded: false
		    },
	        proxy: {
	            type: 'ajax',
	            url: Url.getProperty,
				listeners: {
	    			exception: function(proxy, request, operation, options) {
	    		    	Ext.storeException(proxy, request, operation, options);
	    			}
	    		}
	        }
		}),
		listeners : {
			itemdblclick : function(view, record, item, index, e, options) {
				Ext.getCmp("btnEditProp").handler();
			},
		},
		columns:[{
            xtype: 'treecolumn',
			text: '属性名称',
			dataIndex: 'name',
			width: 250,
			sortable: false
		},{
			text: '输入类型',
			dataIndex: 'inputType',
			width: 200,
			sortable: false,
            renderer: function(value, p, record) {
            	if(record.data.pid==0){
	            	switch(value){
	            	case 0:
	            		return "文本框";
	            	case 1:
	            		return "可编辑下拉框";
	            	case 2:
	            		return "不可编辑下拉框";
	            	case 3:
	            		return "单选框";
	            	case 4:
	            		return "复选框";
	            	default:
	            		return value;
	            	}
            	}
        		return "";
			}
		},{
			text: '所属分类',
			dataIndex: 'catePathName',
			width: 380,
			sortable: false
		}],
		tbar: [{
			id: 'btnAddProp',
			text: '添加',
			iconCls: 'icon-add',
			disabled: false,
			handler: function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeProperty").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
				if(rows[0].data.id!=0){
					if(rows[0].data.pid!=0){
						Ext.Msg.alert("提示", "不能添加下一层记录");
						return;
					}
					if(rows[0].data.inputType==0){
						Ext.Msg.alert("提示", "属性的输入类型为'文本框'时，不能再添加属性值");
						return;
					}
				}
				
				//显示窗口
				winProperty.show(this,function(){
					var frmProperty = Ext.getCmp("frmProperty");
					frmProperty.getForm().reset();
					frmProperty.items.get("property.pid").setValue(rows[0].get("id"));
					
					if(rows[0].data.id==0){
						this.setTitle("添加属性");
						this.setHeight(208);
						frmProperty.items.get("property.name").setFieldLabel("属性");
						frmProperty.items.get("property.inputType").setVisible(true);
						frmProperty.items.get("property.catePath").setVisible(true);
					}else{
						this.setTitle("添加属性值");
						this.setHeight(148);
						frmProperty.items.get("property.name").setFieldLabel("属性值");
						frmProperty.items.get("property.inputType").setVisible(false);
						frmProperty.items.get("property.catePath").setVisible(false);
					}
				});
			}			
		},{
			id: 'btnEditProp',
			text: '编辑',
			disabled: false,
			iconCls: 'icon-edit',
			handler: function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeProperty").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
				if(rows[0].data.id==0){
					return;
				}

				winProperty.show(this,function(){
					var frmProperty = Ext.getCmp("frmProperty");
					frmProperty.getForm().reset();
					frmProperty.items.get("property.id").setValue(rows[0].get("id"));
					frmProperty.items.get("property.pid").setValue(rows[0].get("pid"));
					frmProperty.items.get("property.path").setValue(rows[0].get("path"));
					frmProperty.items.get("property.name").setValue(rows[0].get("name"));
					

					if(rows[0].data.pid==0){
						this.setTitle("编辑属性");
						this.setHeight(208);
						frmProperty.items.get("property.name").setFieldLabel("属性");
						frmProperty.items.get("property.inputType").setVisible(true);
						frmProperty.items.get("property.catePath").setVisible(true);
						frmProperty.items.get("property.inputType").setValue(rows[0].get("inputType"));
						frmProperty.items.get("property.catePath").setValue(rows[0].get("catePath").split('/').pop());
					} else {
						this.setTitle("编辑属性值");
						this.setHeight(148);
						frmProperty.items.get("property.name").setFieldLabel("属性值");
						frmProperty.items.get("property.inputType").setVisible(false);
						frmProperty.items.get("property.catePath").setVisible(false);
					}
				});	
			}
		},{
			id: 'btnDelProp',
			text: '删除',
			disabled: false,
			iconCls: 'icon-del',
			handler: function(btn, event) {
				var rows = Ext.getCmp("treeProperty").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
				if(rows[0].data.id == 0){
					Ext.Msg.alert("提示", "根节点不能删除");
					return;
				}
				delProperty(Ext.getCmp("treeProperty"));
			}
        },'-', {
        	id: 'btnUpProp',
			text : '上移',
			disabled: false,
			iconCls : 'icon-up',
			handler : function(btn, event) {
				upProperty(Ext.getCmp("treeProperty"));
			}
		}, {
			id: 'btnDownProp',
			text : '下移',
			disabled: false,
			iconCls : 'icon-down',
			handler : function(btn, event) {
				downProperty(Ext.getCmp("treeProperty"));				
			}
		}]
    });
	

	//--------------------------------------界面布局----------------------------------------------//

	Ext.create('Ext.Viewport', {
		id: 'viewport',
	    layout: 'border',
	    title: '${module_title}',
	    items: [
			Ext.createWidget('tabpanel', {
				id: 'tabpanel',
			  	region: 'center',
			    activeTab: 0,
			    plain: true,
			    defaults :{
			        autoScroll: true
			    },
			    items: [{
			    	id: 'tabCategory',
			        title: '规格管理',
			   	    layout: 'fit',
		       	    showed: true,
			   	    items:[treeSpecification]
			    },{
			    	id: 'tabBrand',
			        title: '属性管理',
			   	    layout: 'fit',
		       	    showed: false,
					listeners: {
						activate: function(obj, options) {
							if(!obj.shwoed){
								obj.shwoed = true;
								obj.add(treeProperty);
								treeProperty.getStore().getRootNode().expand();
							}
						}
					}
			    }]
			})
		],
		renderTo: document.body
	});

	
	
	//-------------------------------------以下是相关function-----------------------------------------------//

	//添加/编辑
	function saveSpecification(win, frm, tree){
		//如果是规格，就必须填写分类信息；规格值不用。
		if(frm.items.get("specification.pid").getValue()==0){
			if(frm.items.get("specification.catePath").getValue()==""){
				Ext.Msg.alert("提示", "必须填写分类信息。");
				return;	
			}
		}
		
		// 提交表单
		frm.submit({
		    url: Url.saveSpecification,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    failure: Ext.formFailure,
		    success: function(form, action) {
		    	win.hide();
		    	
		    	var id = Ext.getCmp("treeSpecification").selModel.getSelection()[0].data.id;
		    	var node;
		    	if(id==0){
		    		node = tree.store.getRootNode();
		    	}else{
		    		node = tree.store.getNodeById(id);
		    	}
		    	
				if(Ext.getCmp("specification.id").getValue()==null || Ext.getCmp("specification.id").getValue()==""){
		    		//添加
		    		if(id==0)
		    			action.result.specification.iconCls = "icon-module-app";
		    		else
		    			action.result.specification.iconCls = "icon-module-function";
		    		
			    	action.result.specification.leaf = true;
		    		node.set('leaf', false);
		    		node.appendChild(action.result.specification);
		    		node.expand();
		    	}else{
		    		//编辑
		    		node.set('name', action.result.specification.name);
		    		node.set('hasImage', action.result.specification.hasImage);
		    		node.set('catePath', action.result.specification.catePath);
		    		node.set('catePathName', frm.items.get("specification.catePath").getRawValue());
		    	}
		    }
		});
	}
	
	//删除
	function delSpecification(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
    		if(rows[0].data.id==0){
    			Ext.Msg.alert("提示", "根节点不能删除");
    			return;
    		}
    		
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要删除规格或规格值吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "id=" + rows[0].get('id');
    					
    					// 发送请求
    					tree.el.mask("正在删除...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delSpecification,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							tree.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
									var node = Ext.getCmp("treeSpecification").selModel.getSelection()[0];
									node.remove();
									Ext.getCmp("treeSpecification").selModel.select(0);
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
			Ext.Msg.alert("提示", "请选择一条记录");
    	}
	}

	//上移
	function upSpecification(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
				return;
			}
			
			if(rows[0].data.pid==0){
				Ext.Msg.alert("提示", "请选择规格值，只有规格值才可以上移或下移。");
				return;
			}
			
    		//找到上一个节点
			var node = tree.store.getNodeById(rows[0].get('id'));
			var prevId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i-1<0){
						return;
					}else{
						prevId = node.parentNode.childNodes[i-1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "id=" + rows[0].get('id') + "&prevId=" + prevId;    					
			
			// 发送请求
			tree.el.mask("正在上移规格...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.upSpecification,
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
    		Ext.Msg.alert("提示", "请选择一条记录");
    	}
	}
	
	//下移
	function downSpecification(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
				return;
			}
			
			if(rows[0].data.pid==0){
				Ext.Msg.alert("提示", "请选择规格值，只有规格值才可以上移或下移。");
				return;
			}
			
    		//找到下一个节点
			var node = tree.store.getNodeById(rows[0].get('id'));
			var nextId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i+1>=node.parentNode.childNodes.length){
						return;
					} else {    							
						nextId = node.parentNode.childNodes[i+1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "id=" + rows[0].get('id') + "&nextId=" + nextId;    					
			
			// 发送请求
			tree.el.mask("正在下移规格...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.downSpecification,
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
    		Ext.Msg.alert("提示", "请选择一条记录");
    	}
	}
	
	
	//保存属性
	function saveProperty(win, frm, tree){
		//如果是属性，就必须填写分类信息；属性值不用。
		if(frm.items.get("property.pid").getValue()==0){
			if(frm.items.get("property.catePath").getValue()==""){
				Ext.Msg.alert("提示", "必须填写分类信息。");
				return;	
			}
		}
		
		frm.submit({
		    url: Url.saveProperty,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    failure: Ext.formFailure,
		    success: function(form, action) {
		    	win.hide();
		    	var id = Ext.getCmp("treeProperty").selModel.getSelection()[0].data.id;
		    	var node;
		    	if(id==0){
		    		node = tree.store.getRootNode();
		    	}else{
		    		node = tree.store.getNodeById(id);
		    	}
		    	
				if(frm.items.get("property.id").getValue()==null || frm.items.get("property.id").getValue()==""){
		    		//添加
		    		if(id==0)
		    			action.result.property.iconCls = "icon-module-app";
		    		else
		    			action.result.property.iconCls = "icon-module-function";

			    	action.result.property.leaf = true;
		    		node.set('leaf', false);
		    		node.appendChild(action.result.property);
		    		node.expand();
		    	}else{
		    		//编辑
		    		node.set('name', action.result.property.name);
		    		node.set('inputType', action.result.property.inputType);
		    		node.set('catePath', action.result.property.catePath);
		    		node.set('catePathName', frm.items.get("property.catePath").getRawValue());
		    	}
		    }
		});
	}
	
	//删除
	function delProperty(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要删除属性吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "id=" + rows[0].get('id');
    					
    					// 发送请求
    					tree.el.mask("正在删除...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delProperty,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							tree.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
									var node = Ext.getCmp("treeProperty").selModel.getSelection()[0];
									node.remove();
									Ext.getCmp("treeProperty").selModel.select(0);
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
	
	//属性上移
	function upProperty(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
				return;
			}
			
			if(rows[0].data.pid==0){
				Ext.Msg.alert("提示", "请选择属性值，只有规格值才可以上移或下移。");
				return;
			}
    		
    		//找到上一个节点
			var node = tree.store.getNodeById(rows[0].get('id'));
			var prevId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i-1<0){
						return;
					}else{
						prevId = node.parentNode.childNodes[i-1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "id=" + rows[0].get('id') + "&prevId=" + prevId;    					
			
			// 发送请求
			tree.el.mask("正在上移属性...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.upProperty,
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
    		Ext.Msg.alert("提示", "请选择属性");
    	}
	}
	
	//属性下移
	function downProperty(tree){
    	var rows = tree.selModel.getSelection();
    	
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
				return;
			}
			
			if(rows[0].data.pid==0){
				Ext.Msg.alert("提示", "请选择属性值，只有规格值才可以上移或下移。");
				return;
			}
			
    		//找到下一个节点
			var node = tree.store.getNodeById(rows[0].get('id'));
			var nextId = "";
			for(var i=0;i<node.parentNode.childNodes.length;i++){
				if(node.parentNode.childNodes[i]==node){
					if(i+1>=node.parentNode.childNodes.length){
						return;
					} else {    							
						nextId = node.parentNode.childNodes[i+1].data.id;
						break;
					}
				}
			}    					

			// 构建Ajax参数
			var ajaxparams = "id=" + rows[0].get('id') + "&nextId=" + nextId;    					
			
			// 发送请求
			tree.el.mask("正在下移属性...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.downProperty,
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
    		Ext.Msg.alert("提示", "请选择属性");
    	}
	}
	
});
</script>
</head>
<body>
</body>
</html>