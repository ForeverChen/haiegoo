<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../meta.jsp" %>
<title>${module_title}</title>
<script type="text/javascript">
Ext.onReady(function() {
	//初始化
	Ext.tip.QuickTipManager.init();
	
	//业务请求的URL
	Url = {
			getBrandList: 'brand-cate.jspx?func=getBrandList',
			saveBrand:'brand-cate.jspx?func=saveBrand',
			delBrand:'brand-cate.jspx?func=delBrand',
			upBrand: 'brand-cate.jspx?func=upBrand',
			downBrand: 'brand-cate.jspx?func=downBrand',
			getCategoryList : 'brand-cate.jspx?func=getCategoryList',
			delCategory: 'brand-cate.jspx?func=delCategory',
			saveCategory: 'brand-cate.jspx?func=saveCategory',
			upCategory: 'brand-cate.jspx?func=upCategory',
			downCategory: 'brand-cate.jspx?func=downCategory'
	};


	//-----------------------------------品牌管理-----------------------------------//
  
  	//添加/编辑窗口,放在工具面板中
	var winBrand = Ext.create('widget.window', {
		id: 'winBrand',
		title: '品牌信息',
		width: 350,
		height: 410,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmBrand',
			bodyStyle:'padding:20px 0 0 23px',
			border: false,
			autoScroll: true,
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 75, 
				msgTarget: 'side',
              	autoFitErrors: false,  //展示错误信息时 是否自动调整字段组件的宽度 
				width: 300
			},
			defaults: { 
				listeners: {
					specialkey: function(obj,e){
						 if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp("btnOKBrand").handler();
						}
					}
				}
			},
			items: [{
			    xtype: 'hiddenfield',
				id: 'brand.id',
			    name: 'brand.id',
				fieldLabel: '品牌id'
			},{
			    xtype:'textfield',
			    id: 'brand.name',
			    name: 'brand.name',
			    fieldLabel: '品牌名称',
			    allowBlank: false,
				msgTarget: 'side'
			},{
			    xtype:'filefield',
			    id: 'brand.logo',
			    name: 'brand.logo',
			    fieldLabel: '品牌图片',
		        buttonText: '浏览',
				msgTarget: 'side'
			},{
			    xtype:'textfield',
			    id: 'brand.url',
			    name: 'brand.url',
			    fieldLabel: '品牌URL',
				msgTarget: 'side'
			},{
			    xtype:'textareafield',
			    id: 'brand.description',
			    name: 'brand.description',
			    grow: true,
			    fieldLabel: '品牌介绍',
				msgTarget: 'side'
			}, {
				xtype:'textfield',
			    id: 'brand.seoTitle',
			    name: 'brand.seoTitle',
			    fieldLabel: 'SEO标题',
				msgTarget: 'side'
			}, {
				xtype:'textfield',
			    id: 'brand.seoKeywords',
			    name: 'brand.seoKeywords',
			    fieldLabel: 'SEO关键字',
				msgTarget: 'side'
             }, {
				xtype:'textareafield',
			    id: 'brand.seoDescription',
			    name: 'brand.seoDescription',
			    fieldLabel: 'SEO描述',
				msgTarget: 'side'
             }]
		})],
		buttons: [{
			id: "btnOKBrand",
		    text:'确定',
		    width: 80,
		    handler: function(){
		    	saveBrand(Ext.getCmp("winBrand"), Ext.getCmp("frmBrand"), Ext.getCmp("gridBrand"));
		    }
		},{
		    text:'取消',
		    width: 80,
		    handler: function(){
		    	Ext.getCmp("winBrand").hide();
		    }
		}]
	});
	
    var gridBrand = Ext.create('Ext.grid.Panel', {
		id: 'gridBrand',
		border: false,
		disableSelection: false,
		loadMask: true,
		style: 'vertical-align:middle',
		store: Ext.create('Ext.data.Store', {
	        idProperty: 'id',
	        fields: [
                 'id','name','logo','url','description','seoTitle',
                 'seoKeywords','seoDescription','sort','productCount'
			],
	        proxy: { 
	           	type: 'jsonp',
	            url: Url.getBrandList,
	            reader: {
	            	successProperty: 'success',
	                root: 'records'
	            },
	            simpleSortMode: true,
	            listeners: {
	    			exception: function(proxy, request, operation, options) {
	    		    	Ext.storeException(proxy, request, operation, options);
	    			}
	       		}
	        },
	        //自动加载 
	        autoLoad: true   
	    }),
        columns:[{
            dataIndex: 'logo',
            width: 125,
            sortable: false,
            renderer: function(value, p, record) {
            	return Ext.String.format(
   					'<div style="width:100px;height:50px; margin:5px 2px 5px 2px;">' +
   						'<a href="${page_context}/{0}" target="_blank">' +
   							'<img src="${page_context}/{0}" style="width:100%;height:100%;"/>' +
   						'</a>' +
   					'</div>',
   					value
   				);
			}
        },{
            text: '品牌名称',
            dataIndex: 'name',
            width: 250,
            sortable : true
        },{
            text: '商品数量',
            dataIndex: 'productCount',
            width: 150,
            sortable : true,
            align:'center'
        },{
            text: '操作',
            dataIndex: '',
            width: 150,
            sortable : false,
            align:'center',
			renderer: function(value, p, record) {
            	return '<a href="javascript:void(0);" onclick="readGoods(\''+record.get('id')+'\');">查看商品</a>';
        	}
        }],
		listeners : {
        	itemdblclick : function(view, record, item, index, e, options){
        		Ext.getCmp("btnEditBrand").handler();	
        	}
        },
       	tbar:[{
		        text: '添加',
		        iconCls: 'icon-add',
			    handler: function(btn, event) {
			    	//显示窗口
			    	winBrand.show(this,function(){
			    		this.setTitle("添加品牌");
			    		var frmBrand = Ext.getCmp("frmBrand");
			    		frmBrand.getForm().reset();
			    	});
			    }
		    },{
		    	id: 'btnEditBrand',
		        text: '编辑',
		        iconCls: 'icon-edit',
			    handler: function(btn, event) {
					//获取行 
					var rows = Ext.getCmp("gridBrand").selModel.getSelection();
					if (rows.length != 1) {
						Ext.Msg.alert("提示", "请选择一条记录");
						return;
					}
					
			    	//显示窗口
			    	winBrand.show(this,function(){
			    		this.setTitle("编辑品牌");
						var frmBrand = Ext.getCmp("frmBrand");
						frmBrand.getForm().reset();
						Ext.getCmp("brand.id").setValue(rows[0].get("id"));
						Ext.getCmp("brand.name").setValue(rows[0].get("name"));
						Ext.getCmp("brand.logo").setRawValue(rows[0].get("logo"));
						Ext.getCmp("brand.url").setValue(rows[0].get("url"));
						Ext.getCmp("brand.description").setValue(rows[0].get("description"));
						Ext.getCmp("brand.seoTitle").setValue(rows[0].get("seoTitle"));
						Ext.getCmp("brand.seoKeywords").setValue(rows[0].get("seoKeywords"));
						Ext.getCmp("brand.seoDescription").setValue(rows[0].get("seoDescription"));
			    	});	
			    }
		    },{
		        text: '删除',
		        iconCls: 'icon-del',
			    handler: function(btn, event) {
			    	delBrand(Ext.getCmp("gridBrand"));
			    }
		    },'-',{
				text: '上移',
				iconCls: 'icon-up',
				handler: function(btn, event) {
					upBrand();
				}
			},{
				text: '下移',
				iconCls: 'icon-down',
				handler: function(btn, event) {
					downBrand();				
				}
			}
		]
	});
    
    
	//-----------------------------------分类管理-----------------------------------//

	//添加/编辑窗口
	var winCategory = Ext.create('widget.window', {
		id: 'winCategory',
		title: '分类信息',
		width: 350,
		height: 317,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmCategory',
			bodyStyle:'padding:20px 0 0 23px',
			border: false,
			autoScroll: true,
			fieldDefaults: {
				labelAlign: 'left',
				labelWidth: 75, 
				msgTarget: 'side',
              	autoFitErrors: false,  //展示错误信息时 是否自动调整字段组件的宽度 
				width: 300
			},
			defaults: { 
				listeners: {
					specialkey: function(obj,e){
						 if (e.getKey() == Ext.EventObject.ENTER) {
							Ext.getCmp("btnOKCategory").handler();
						}
					}
				}
			},
			items: [{
			    xtype: 'hiddenfield',
			    id: 'category.id',
			    name: 'category.id'
			},{
			    xtype: 'hiddenfield',
			    id: 'category.pid',
			    name: 'category.pid'
			},{
			    xtype:'hiddenfield',
			    id: 'category.path',
			    name: 'category.path'
			},{
			    xtype:'textfield',
			    id: 'category.name',
			    name: 'category.name',
			    fieldLabel: '分类名称',
			    allowBlank: false
			},{
			    xtype:'textfield',
			    id: 'category.url',
			    name: 'category.url',
			    fieldLabel: '分类URL',
				msgTarget: 'side'
			},{
			    xtype:'textfield',
			    id: 'category.seoTitle',
			    name: 'category.seoTitle',
			    fieldLabel: 'SEO标题'
			},{
			    xtype:'textfield',
			    id: 'category.seoKeywords',
			    name: 'category.seoKeywords',
			    fieldLabel: 'SEO关键字'
			},{
			    xtype:'textareafield',
			    id: 'category.seoDescription',
			    name: 'category.seoDescription',
			    fieldLabel: 'SEO描述'
			}]
		})],
		buttons: [{
			id: "btnOKCategory",
		    text:'确定',
		    width: 80,
		    handler: function(){			    		 		
		    	saveCategory(Ext.getCmp("winCategory"), Ext.getCmp("frmCategory"), Ext.getCmp("treeCategory"));
		    }
		},{
		    text:'取消',
		    width: 80,
		    handler: function(){
		    	Ext.getCmp("winCategory").hide();
		    }
		}]
	});
	
	var treeCategory = Ext.create('Ext.tree.Panel', {
		id : 'treeCategory',
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
			fields : [ 
				'id', 'pid', 'path', 'name', 'url',
				'seoTitle', 'seoKeywords', 'seoDescription', 'sort' ,'productCount'
			],
			proxy : {
				type : 'ajax',
				url : Url.getCategoryList,
			},
			root: {
				id: 0,
				name: '商品分类',
		        iconCls: 'icon-sub-system',
		        expanded: false
			}
		}),
		columns : [ {
			xtype : 'treecolumn',
			text : '分类名称',
			dataIndex : 'name',
			width :320,
			sortable : false
		}, {
			text : '商品数量',
			dataIndex : 'productCount',
			width : 150,
			sortable : false,
			align:'center'
		}, {
			text : '操作',
			dataIndex : '',
			width : 150,
			sortable : false,
			align:'center',
			renderer : function(key, p, record) {
				if(record.data.id!=0)
					return '<a href="javascript:void(0);" onclick="readGoods(\''+record.get('id')+'\');">查看商品</a>';
				else
					return "";
			}
		}, ],
		listeners : {
			itemdblclick : function(view, record, item, index, e, options) {
				Ext.getCmp("btnEditCategory").handler();
			}
		},
		tbar : [{
			text : '添加',
			iconCls : 'icon-add',
			handler : function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeCategory").selModel.getSelection();
				if (rows.length==0) {
					Ext.Msg.alert("提示", "请选择父分类");
					return;
				}
				//判断层级，只能添加三级
				if(rows[0].data.path.split('/').length>=3){
					Ext.Msg.alert("提示", "不能添加下一级分类");
					return;
				}
				
				//显示窗口 
				winCategory.show(this,function(){
					this.setTitle("添加分类");
					var frmCategory = Ext.getCmp("frmCategory");
					frmCategory.getForm().reset();
					Ext.getCmp("category.pid").setValue(rows[0].get("id"));
				});
			}
		},{
			id : 'btnEditCategory',
			text : '编辑',
			iconCls : 'icon-edit',
			handler : function(btn, event) {
				var rows = Ext.getCmp("treeCategory").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}

				if(rows[0].data.id==0){
					return;
				}
				//显示窗口
				winCategory.show(this,function(){
					this.setTitle("编辑分类");
					var frmCategory = Ext.getCmp("frmCategory");
					frmCategory.getForm().reset();
					Ext.getCmp("category.id").setValue(rows[0].get("id"));
					Ext.getCmp("category.pid").setValue(rows[0].get("pid"));
					Ext.getCmp("category.path").setValue(rows[0].get("path"));
					Ext.getCmp("category.name").setValue(rows[0].get("name"));
					Ext.getCmp("category.url").setValue(rows[0].get("url"));
					Ext.getCmp("category.seoTitle").setValue(rows[0].get("seoTitle"));
					Ext.getCmp("category.seoKeywords").setValue(rows[0].get("seoKeywords"));
					Ext.getCmp("category.seoDescription").setValue(rows[0].get("seoDescription"));
				});
			}
		},{
			text : '删除',
			iconCls : 'icon-del',
			handler : function(btn, event) {
				delCategory(Ext.getCmp("treeCategory"));
			}
		},'-', {
			text : '上移',
			iconCls : 'icon-up',
			handler : function(btn, event) {
				upCategory(Ext.getCmp("treeCategory"));
			}
		}, {
			text : '下移',
			iconCls : 'icon-down',
			handler : function(btn, event) {
				downCategory(Ext.getCmp("treeCategory"));				
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
			    	id: 'tabBrand',
			        title: '品牌管理',
			   	    layout: 'fit',
		       	    showed: true,
		    		autoScroll: false,
			   	    items:[gridBrand]
			    },{
			    	id: 'tabCategory',
			        title: '分类管理',
			   	    layout: 'fit',
		       	    showed: false,
					listeners: {
						activate: function(obj, options) {
							if(!obj.shwoed){
								obj.shwoed = true;
								obj.add(treeCategory);
								treeCategory.getStore().getRootNode().expand();
							}
						}
					}
			    }]
			})
		],
		renderTo: document.body
	});
	
	
	//-------------------------------------以下是相关function-----------------------------------------------//
	
	//[添加/编辑]品牌
	function saveBrand(win, frm, grid){
		// 提交表单
		frm.submit({
		    url: Url.saveBrand,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    failure: Ext.formFailure,
		    success: function(form, action) {
		    	grid.store.load();
		    	grid.selModel.clearSelections();
				win.hide();
		    }
		});
	}

	//删除品牌
	function delBrand(grid){
		var rows = grid.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要删除吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "id=" + rows[0].get('id');
    					
    					// 发送请求
    					grid.el.mask("正在删除...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delBrand,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							grid.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
									gridBrand.getStore().load();
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
			Ext.Msg.alert("提示", "请选择一条记录");
    	}
	}
	
	//上移
	function upBrand(){
		var rows = gridBrand.selModel.getSelection();
    	if (rows.length > 0) {
			var id = rows[0].get('id');
			var parentId="";
			var record = gridBrand.getStore().getAt(gridBrand.getStore().indexOf(rows[0])-1);
			if(record!=null && record!=""){
				parentId=record.get('id');
			}else{
				return;
			}
			//alert(id+","+parentId);
			var ajaxparams ="id="+id+'&parentId='+parentId;
			// 发送请求
			gridBrand.el.mask("正在设置...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.upBrand,
				params : ajaxparams,
				method : "POST",
				waitMsg : "正在读取...",
				success : function(response, options) {
					var json = Ext.JSON.decode(response.responseText);
					gridBrand.el.unmask();
					if(json['success']==false){
						Ext.Msg.alert("提示", json['msg']);
						return;
					}
					gridBrand.getStore().load();
				},
				failure : function(response, options) {
					Ext.ajaxFailure(response, options);
				}
			});	
			
		} else {
			Ext.Msg.alert("提示", "请选择一条记录");
		}
	}
	//下移
	function downBrand(){
		var rows = gridBrand.selModel.getSelection();
    	if (rows.length > 0) {
			var id = rows[0].get('id');
			var nextId="";
			var record = gridBrand.getStore().getAt(gridBrand.getStore().indexOf(rows[0])+1);
			if(record!=null && record!=""){
				nextId=record.get('id');
			}else{
				return;
			}
			//alert(id+","+nextId);
			var ajaxparams ="id="+id+'&nextId='+nextId;
			// 发送请求
			gridBrand.el.mask("正在设置...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.downBrand,
				params : ajaxparams,
				method : "POST",
				waitMsg : "正在读取...",
				success : function(response, options) {
					var json = Ext.JSON.decode(response.responseText);
					gridBrand.el.unmask();
					if(json['success']==false){
						Ext.Msg.alert("提示", json['msg']);
						return;
					}
					gridBrand.getStore().load();
					//var model = gridBrand.getSelectionModel();
					//model.selectRows(id);
				},
				failure : function(response, options) {
					Ext.ajaxFailure(response, options);
				}
			});	
			
		} else {
			Ext.Msg.alert("提示", "请选择一条记录");
		}
	}
	
	
	//添加&编辑
	function saveCategory(win, frm, tree){
		// 提交表单
		frm.submit({
		    url: Url.saveCategory,
			waitTitle : "提示",
			waitMsg : "正在保存...",
		    failure: Ext.formFailure,
		    success: function(form, action) {
		    	win.hide();
		    	
		    	var id = Ext.getCmp("treeCategory").selModel.getSelection()[0].data.id;
		    	var node;
		    	if(id==0){
		    		node = tree.store.getRootNode();
		    	}else{
		    		node = tree.store.getNodeById(id);	
		    	}
		    	
				if(Ext.getCmp("category.id").getValue()==null || Ext.getCmp("category.id").getValue()==""){
		    		//添加
			    	var level = action.result.category.path.split('/').length;
					if(level==1){
			    		action.result.category.iconCls = "icon-module-group";
					} else if(level==2){
			    		action.result.category.iconCls = "icon-module-app";
					}else if(level==3){
			    		action.result.category.iconCls = "icon-module-function";
					}
		    		
			    	action.result.category.leaf = true;
		    		node.set('leaf', false);
		    		node.appendChild(action.result.category);
		    		node.expand();
		    	}else{
		    		//编辑
		    		node.set('name', action.result.category.name);
		    		node.set('url', action.result.category.url);
		    		node.set('seoTitle', action.result.category.seoTitle);
		    		node.set('seoKeywords', action.result.category.seoKeywords);
		    		node.set('seoDescription', action.result.category.seoDescription);
		    	}
		    }
		});
	}
	
	//删除
	function delCategory(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
				Ext.Msg.alert("提示", "根节点不能删除");
				return;
			}
    		
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的该分类下所有分类吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "id=" + rows[0].get('id');
    					
    					// 发送请求
    					tree.el.mask("正在删除...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delCategory,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							tree.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
									var node = Ext.getCmp("treeCategory").selModel.getSelection()[0];
									node.remove();
									Ext.getCmp("treeCategory").selModel.select(0);
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
	function upCategory(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
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
			tree.el.mask("正在上移商品分类...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.upCategory,
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
	function downCategory(tree){
    	var rows = tree.selModel.getSelection();
    	if (rows.length > 0) {
			if(rows[0].data.id==0){
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
			tree.el.mask("正在下移商品分类...", 'x-mask-loading');
			Ext.Ajax.request({
				url : Url.downCategory,
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
	
});
</script>
</head>
<body>
</body>
</html>