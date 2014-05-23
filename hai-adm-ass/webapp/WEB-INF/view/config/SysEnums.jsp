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
       	getEnums: '${page_action}?func=getEnums',
       	delEnums: '${page_action}?func=delEnums',
       	saveEnums: '${page_action}?func=saveEnums'
    };

    
 	//------------------------------------------------------------------------------------//
 	
    
	//应用模块添加/编辑窗口
	var winEnums = Ext.create('widget.window', {
		id: 'winEnums',
		title: '应用模块信息',
		width: 400,
		height: 236,
		layout: 'fit',
		closable: true,
		closeAction: 'hide',
		modal: true,
		resizable: false,
		items: [Ext.create('Ext.form.Panel', {
			id: 'frmEnums',
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
              	autoFitErrors: false, 
				width: 355
			},
			items: [{
			    xtype: 'hiddenfield',
			    id: 'hddId',
			    name: 'enums.id',
			},{
			    xtype:'textfield',
			    id: 'txtClazz',
			    name: 'enums.clazz',
			    fieldLabel: '枚举类',
			    emptyText: '如:com.haiegoo.commons.enums.State',
			    allowBlank: false
			},{
			    xtype:'textfield',
			    id: 'txtKey',
			    name: 'enums.key',	
			    fieldLabel: '键(key)',
			    emptyText: '枚举键,英文',
			    allowBlank: false
			},{
			    xtype:'numberfield',
			    id: 'txtCode',
			    name: 'enums.code',
			    fieldLabel: '枚举编码',
			    emptyText: '枚举编码,数字',
			    allowBlank: false
			},{
			    xtype:'textfield',
			    id: 'txtText',
			    name: 'enums.text',
			    fieldLabel: '中文名称',
			    emptyText: '枚举名称,一般为中文，用于显示',
			    allowBlank: false
			}]
		})],
		buttons: [{
			id: "btnOK",
		    text:'确定',
		    width: 80,
		    handler: function(){			    		 		
		    	saveEnums(Ext.getCmp("winEnums"), Ext.getCmp("frmEnums"), Ext.getCmp("treeEnums"));
		    }
		},{
			id: "btnCancel",
		    text:'取消',
		    width: 80,
		    handler: function(){
		    	Ext.getCmp("winEnums").hide();
		    }
		}]
	});
 	
    

 	//------------------------------------------------------------------------------------//
    
    var treeEnums = Ext.create('Ext.tree.Panel', {
		id: 'treeEnums',
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
	  	    idProperty: 'id',
			fields: [
				'id', 'clazz', 'key', 'code', 'text'
			],
	        proxy: {
	            type: 'ajax',
	            url: Url.getEnums,
				listeners: {
	    			exception: function(proxy, request, operation, options) {
	    		    	Ext.storeException(proxy, request, operation, options);
	    			}
	    		}
	        }
	    }),
        columns: [{
            xtype: 'treecolumn',
            text: '枚举',
            dataIndex: 'key',
            width: 300
        },{
            text: '枚举编码',
            dataIndex: 'code',
            width: 300
        },{
            text: '中文名称',
            dataIndex: 'text',
            width: 300
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
		    	winEnums.show(this,function(){
		    		this.setTitle("添加枚举");
		    		var frmEnums = Ext.getCmp("frmEnums");
		    		frmEnums.getForm().reset();
		    		frmEnums.items.get("hddId").setValue(null);

		    		//如果选择一记录，则赋一些默认值
					var rows = Ext.getCmp("treeEnums").selModel.getSelection();
					if (rows.length == 1) {
		    			frmEnums.items.get("txtClazz").setValue(rows[0].get("clazz") || rows[0].get("key"));
					}
		    	});
		    }
        },{
        	id: 'btnEdit',
            text: '编辑',
            iconCls: 'icon-edit',
		    handler: function(btn, event) {
				//判断是否选择一条记录
				var rows = Ext.getCmp("treeEnums").selModel.getSelection();
				if (rows.length != 1) {
					Ext.Msg.alert("提示", "请选择一条记录");
					return;
				}
		    	
		    	//显示窗口
		    	if(rows[0].get("id")){
			    	winEnums.show(this,function(){
			    		this.setTitle("编辑枚举");
			    		var frmEnums = Ext.getCmp("frmEnums");
			    		frmEnums.getForm().reset();
			    		frmEnums.items.get("hddId").setValue(rows[0].get("id"));
			    		frmEnums.items.get("txtClazz").setValue(rows[0].get("clazz"));
			    		frmEnums.items.get("txtKey").setValue(rows[0].get("key"));
			    		frmEnums.items.get("txtCode").setValue(rows[0].get("code"));
			    		frmEnums.items.get("txtText").setValue(rows[0].get("text"));
			    	});
		    	}
		   }
        },{
			text: '删除',
			iconCls: 'icon-del',
			handler: function(btn, event) {
				delEnums(Ext.getCmp("treeEnums"));
			}
		},'-',{
			text: '展开',
			iconCls: 'icon-up',
			handler: function(btn, event) {
				window.setTimeout(function(){
				    Ext.getCmp("treeEnums").expandAll();
				}, 200);
			}
		},{
			text: '合并',
			iconCls: 'icon-down',
			handler: function(btn, event) {
				window.setTimeout(function(){
				    Ext.getCmp("treeEnums").collapseAll();
				}, 200);	
			}
		}]
	});
    
    
	//------------------------------------------------------------------------------------//
 	
	
	//保存应用模块
	function saveEnums(win, frm, tree){
		// 提交表单
		frm.submit({
		    url: Url.saveEnums,
			waitTitle : "提示",
			waitMsg : "正在保存...",
			submitEmptyText: false,
		    success: function(form, action) {
		    	win.hide();
		    	location.reload();
		    	//tree.store.reload();
		    },
		    failure: function(form, action) {
		    	Ext.formFailure(form, action);
		    }
		});
	}
		
		
	//删除应用模块
	function delEnums(tree){
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
    					var ajaxparams = "id=" + rows[0].get('id');
    					
    					// 发送请求
    					tree.el.mask("正在删除应用模块...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delEnums,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在删除...",
    						success : function(response, options) {
    							tree.el.unmask();
    							var json = Ext.JSON.decode(response.responseText);
								if (json.success) {
							    	location.reload();
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
	
	
	//获取两位字符的code
	var CODE_SEED = new Array('A','B','C','D','E','F','G','H','I','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z');
	function getRandomCode(){
		return CODE_SEED[Math.round(Math.random()*25)]+CODE_SEED[Math.round(Math.random()*25)];
	}
    
    
 	//------------------------------------------------------------------------------------//

 	
    //界面布局
	Ext.create('Ext.Viewport', {
		id: 'viewport',
	    layout: 'border',
	    title: '${module_title}',
	    items: [treeEnums],
		renderTo: document.body
	});

});
</script>
</head>
<body>
</body>
</html>