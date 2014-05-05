<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../meta.jsp" %>
<title>${module_title}</title>
<script type="text/javascript">
Ext.onReady(function(){
    Ext.tip.QuickTipManager.init();

    //业务请求的URL
    Url = {
    	getSellingProductList: 'product-manager.jspx?func=getProductList&state=1&saleState=1',
    	getStorageProductList: 'product-manager.jspx?func=getProductList&state=1&saleState=0,2,3,4',
    	getDeletedProductList: 'product-manager.jspx?func=getProductList&state=3',
    	delProduct: 'product-manager.jspx?func=delProduct',
    	recoveryProduct: 'product-manager.jspx?func=recoveryProduct',
    	upShelfProduct: 'product-manager.jspx?func=upShelfProduct',
    	offShelfProduct: 'product-manager.jspx?func=offShelfProduct',
    	modifyPrice: 'product-manager.jspx?func=modifyPrice',
    	modifyInventory: 'product-manager.jspx?func=modifyInventory'
    };
    
     	
 	//------------------------------------------------------------------------------------//
  
    //出售中的商品
    var storeSelling = Ext.create('Ext.data.Store', {
        pageSize: 50,
        remoteSort: true,
        idProperty: 'id',
        fields: [
      			'id', 'name', 'image', 'price', 'stockNumber', 'sellCount', 'saleState', 'state',
    			'createTime', 'updateTime', 'deleteTime', 'publishTime', 'upShelfTime', 'offShelfTime'
        ],
        proxy: {
            type: 'jsonp',
            url: Url.getSellingProductList,
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
            property: 'upShelfTime',
            direction: 'desc'
        }],
        listeners: {
        	beforeload: function(store, operation, options) {
        		// 设置查询参数
                Ext.apply(store.proxy.extraParams, { 
                	searchKey: Ext.getCmp("searchKeySelling").getValue()
                });
			}
		}
    });


    //出售中的商品列表
    var gridSelling = Ext.create('Ext.grid.Panel', {
    	id: 'gridSelling',
        border: false,
        disableSelection: false,
        loadMask: true,
        store: storeSelling,
        selModel: Ext.create('Ext.selection.CheckboxModel'),
        columns:[{
            dataIndex: 'image',
            width: 70,
            sortable: false,
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<a href="/product.html?id={0}" target="_blank" title="{1}">'+
					'<img src="{2}" style="width: 54px;height: 54px; margin:5px 2px 5px 2px;"/>'+
					'</a>',
					record.data.id,
					record.data.name,
					value
				);
			}
        },{
            text: '商品名称',
            dataIndex: 'name',
            flex: 1,
            sortable: true,
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<a href="product-publish.jspx?action=edit&id={0}" title="{1}">{1}</a>',
					record.data.id,
					value
				);
			}
        },{
            text: '价格',
            dataIndex: 'price',
            width: 100,
            align: 'right',
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<span style="color: #F60;">￥{0}</span>',
					value.fomatMoney(2)
				);
			},
            sortable: true,
            field: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 99999999.99,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false
            }
        },{
            text: '库存',
            dataIndex: 'stockNumber',
            width: 100,
            align: 'right',
            sortable: true,
            field: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 999999999,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false
            }
        },{
            text: '总销量',
            dataIndex: 'sellCount',
            width: 100,
            align: 'right',
            sortable: true
        },{
            text: '创建时间',
            dataIndex: 'createTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后更新时间',
            dataIndex: 'updateTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '发布时间',
            dataIndex: 'publishTime',
            width: 150,
            align: 'center',
            hidden: false,
            sortable: true
        },{
            text: '最后上架时间',
            dataIndex: 'upShelfTime',
            width: 150,
            align: 'center',
            hidden: false,
            sortable: true
        },{
            text: '最后下架时间',
            dataIndex: 'offShelfTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        }],
        tbar: [{
            text: '我要卖',
            iconCls: 'icon-add',
		    handler: function(btn, event) {
		    	var url = "product-publish.jspx";
		    	if(window.parent){
		    		try{
			    		window.parent.Docs.App.getController("Shopmng").loadPage("#!/shopmng/product/"+ url);
		    		}catch(e){
			    		window.location.href = url;
		    		}
		    	}else{
		    		window.location.href = url;
		    	}
		    }
        },'-',{
        	id: 'btnEditSelling',
            text: '编辑',
            iconCls: 'icon-edit',
		    handler: function(btn, event) {
		    	var rows = Ext.getCmp("gridSelling").selModel.getSelection();
		    	if (rows.length > 0) {
		    		window.location.href = "product-publish.jspx?action=edit&id="+ rows[0].get('id');
		    	} else {
		    		Ext.Msg.alert("提示", "请选择商品");
		    	}
		    }
        },{
            text: '下架',
            iconCls: 'icon-down',
		    handler: function(btn, event) {
		    	offShelfProduct(Ext.getCmp("gridSelling"));
		    }
        },'->',{
        	id: 'searchKeySelling',
            xtype: 'textfield',
            name: 'searchKey',
            emptyText: '请输入商品名称或货号',
            listeners : {
            	specialkey: function(obj,e){
					if(e.getCharCode()==e.ENTER)
						Ext.getCmp("btnSearchSelling").handler();
				}
			}
        },{
        	id: 'btnSearchSelling',
            text: '搜索',
            iconCls: 'icon-search',
		    handler: function(btn, event) {
		    	storeSelling.loadPage(1);
		    }
        }],
        bbar: Ext.create('Ext.PagingToolbar', {
            store: storeSelling,
            displayInfo: true,
            displayMsg: '显示商品 {0} - {1} 共 {2}',
            emptyMsg: "没有可显示的商品",
            items:[
			    '-', {
			    text: '橱窗推荐',
			    handler: function(btn, event) {
			
			    }
			}, {
			    text: '设置促销',
			    handler: function(btn, event) {
			
			    }
			}]
        }),
        plugins: [
		    Ext.create('Ext.grid.plugin.CellEditing', {
		        clicksToEdit: 1
		    })
		],
        listeners : {
        	edit: function(editor, e, options){
        		switch(e.field){
        		case "price":
	        		modifyPrice(e.grid, e.record, e.value);
	        		break;
        		case "stockNumber":
        			modifyInventory(e.grid, e.record, e.value);
        			break;
        		}
			}
		}
    });
    
    
    //------------------------------------------------------------------------------------//
    
    //仓库中的商品
    var storeStorage = Ext.create('Ext.data.Store', {
        pageSize: 50,
        remoteSort: true,
        idProperty: 'id',
        fields: [
     			'id', 'name', 'image', 'price', 'stockNumber', 'sellCount', 'saleState', 'state',
    			'createTime', 'updateTime', 'deleteTime', 'publishTime', 'upShelfTime', 'offShelfTime'
        ],
        proxy: {
            type: 'jsonp',
            url: Url.getStorageProductList,
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
            property: 'offShelfTime',
            direction: 'desc'
        }],
        listeners: {
        	beforeload: function(store, operation, options) {
        		// 设置查询参数
                Ext.apply(store.proxy.extraParams, { 
                	searchKey: Ext.getCmp("searchKeyStorage").getValue()
                });
			}
		}
    });


    //仓库中的商品列表
    var gridStorage = Ext.create('Ext.grid.Panel', {
    	id: 'gridStorage',
        border: false,
        disableSelection: false,
        loadMask: true,
        store: storeStorage,
        selModel: Ext.create('Ext.selection.CheckboxModel'),
        columns:[{
            dataIndex: 'image',
            width: 70,
            sortable: false,
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<a href="/product.html?id={0}" target="_blank" title="{1}">'+
					'<img src="{2}" style="width: 54px;height: 54px; margin:5px 2px 5px 2px;"/>'+
					'</a>',
					record.data.id,
					record.data.name,
					value
				);
			}
        },{
            text: '商品名称',
            dataIndex: 'name',
            flex: 1,
            sortable: true,
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<a href="product-publish.jspx?action=edit&id={0}" title="{1}">{1}</a>',
					record.data.id,
					value
				);
			}
        },{
            text: '价格',
            dataIndex: 'price',
            width: 100,
            align: 'right',
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<span style="color: #F60;">￥{0}</span>',
					value.fomatMoney(2)
				);
			},
            sortable: true,
            field: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 99999999.99,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false
            }
        },{
            text: '库存',
            dataIndex: 'stockNumber',
            width: 100,
            align: 'right',
            sortable: true,
            field: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 999999999,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false
            }
        },{
            text: '总销量',
            dataIndex: 'sellCount',
            width: 100,
            align: 'right',
            sortable: true
        },{
            text: '状态',
            dataIndex: 'saleState',
            width: 100,
            align: 'center', 
            sortable: true,
            renderer: function(value, p, record) {
            	switch(value){
            	case 0:
            		return "待销售";
            	case 1:
            		return "销售中";
            	case 2:
            		return "已售完";
            	case 3:
            		return "手动下架";
            	case 4:
            		return "系统下架";
            	default:
            		return value;
            	}
			}
        },{
            text: '创建时间',
            dataIndex: 'createTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后更新时间',
            dataIndex: 'updateTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '发布时间',
            dataIndex: 'publishTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后上架时间',
            dataIndex: 'upShelfTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后下架时间',
            dataIndex: 'offShelfTime',
            width: 150,
            align: 'center',
            hidden: false,
            sortable: true
        }],
        tbar: [{
            text: '上架　',
            iconCls: 'icon-up',
		    handler: function(btn, event) {
		    	upShelfProduct(Ext.getCmp("gridStorage"));
		    }
        },'-',{
        	id: 'btnEditStorage',
            text: '编辑',
            iconCls: 'icon-edit',
		    handler: function(btn, event) {
		    	var rows = Ext.getCmp("gridStorage").selModel.getSelection();
		    	if (rows.length > 0) {
		    		window.location.href = "product-publish.jspx?action=edit&id="+ rows[0].get('id');
		    	} else {
		    		Ext.Msg.alert("提示", "请选择商品");
		    	}
		    }
        },{
            text: '删除',
            iconCls: 'icon-del',
		    handler: function(btn, event) {
		    	delProduct(Ext.getCmp("gridStorage"));
		    }
        },'->',{
        	id: 'searchKeyStorage',
            xtype: 'textfield',
            name: 'searchKey',
            emptyText: '请输入商品名称或货号',	
            listeners : {
            	specialkey: function(obj,e){
					if(e.getCharCode()==e.ENTER)
						Ext.getCmp("btnSearchStorage").handler();
				}
			}
        },{
        	id: 'btnSearchStorage',
            text: '搜索',
            iconCls: 'icon-search',
		    handler: function(btn, event) {
		    	storeStorage.loadPage(1);
		    }
        }],
        bbar: Ext.create('Ext.PagingToolbar', {
            store: storeStorage,
            displayInfo: true,
            displayMsg: '显示商品 {0} - {1} 共 {2}',
            emptyMsg: "没有可显示的商品",
            items:[]
        }),
        plugins: [
   		    Ext.create('Ext.grid.plugin.CellEditing', {
   		        clicksToEdit: 1
   		    })
   		],
        listeners : {
        	edit: function(editor, e, options){
        		switch(e.field){
        		case "price":
	        		modifyPrice(e.grid, e.record, e.value);
	        		break;
        		case "stockNumber":
        			modifyInventory(e.grid, e.record, e.value);
        			break;
        		}
			}
		}
    });
    
    
  	//------------------------------------------------------------------------------------//
  	
  	//已删除的商品
    var storeDeleted = Ext.create('Ext.data.Store', {
        pageSize: 50,
        remoteSort: true,
        idProperty: 'id',
        fields: [
      			'id', 'name', 'image', 'price', 'stockNumber', 'sellCount', 'saleState', 'state',
    			'createTime', 'updateTime', 'deleteTime', 'publishTime', 'upShelfTime', 'offShelfTime'
        ],
        proxy: {
            type: 'jsonp',
            url: Url.getDeletedProductList,
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
            property: 'deleteTime',
            direction: 'desc'
        }],
        listeners: {
        	beforeload: function(store, operation, options) {
        		// 设置查询参数
                Ext.apply(store.proxy.extraParams, { 
                	searchKey: Ext.getCmp("searchKeyDeleted").getValue()
                });
			}
		}
    });


    //已删除的商品列表
    var gridDeleted = Ext.create('Ext.grid.Panel', {
    	id: 'gridDeleted',
        border: false,
        disableSelection: false,
        loadMask: true,
        store: storeDeleted,
        selModel: Ext.create('Ext.selection.CheckboxModel'),
        columns:[{
            dataIndex: 'image',
            width: 70,
            sortable: false,
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<a href="/product.html?id={0}" target="_blank" title="{1}">'+
					'<img src="{2}" style="width: 54px;height: 54px; margin:5px 2px 5px 2px;"/>'+
					'</a>',
					record.data.id,
					record.data.name,
					value
				);
			}
        },{
            text: '商品名称',
            dataIndex: 'name',
            flex: 1,
            sortable: true,
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<a href="product-publish.jspx?id={0}" title="{1}">{1}</a>',
					record.data.id,
					value
				);
			}
        },{
            text: '价格',
            dataIndex: 'price',
            width: 100,
            align: 'right',
            renderer: function(value, p, record) {
				return Ext.String.format(
					'<span style="color: #F60;">￥{0}</span>',
					value.fomatMoney(2)
				);
			},
            sortable: true,
            field: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 99999999.99,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false
            }
        },{
            text: '库存',
            dataIndex: 'stockNumber',
            width: 100,
            align: 'right',
            sortable: true,
            field: {
                xtype: 'numberfield',
                allowBlank: false,
                minValue: 0,
                maxValue: 999999999,
                hideTrigger: true,
                keyNavEnabled: false,
                mouseWheelEnabled: false
            }
        },{
            text: '总销量',
            dataIndex: 'sellCount',
            width: 100,
            align: 'right',
            sortable: true
        },{
            text: '创建时间',
            dataIndex: 'createTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后更新时间',
            dataIndex: 'updateTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '发布时间',
            dataIndex: 'publishTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后上架时间',
            dataIndex: 'upShelfTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '最后下架时间',
            dataIndex: 'offShelfTime',
            width: 150,
            align: 'center',
            hidden: true,
            sortable: true
        },{
            text: '删除时间',
            dataIndex: 'deleteTime',
            width: 150,
            align: 'center',
            hidden: false,
            sortable: true
        }],
        tbar: [{
            text: '恢复到仓库',
            iconCls: 'icon-redo',
		    handler: function(btn, event) {
		    	recoveryProduct(Ext.getCmp("gridDeleted"));
		    }
        },'-',{
        	id: 'btnViewDeleted',
            text: '查看',
            iconCls: 'icon-view',
		    handler: function(btn, event) {
		    	var rows = Ext.getCmp("gridDeleted").selModel.getSelection();
		    	if (rows.length > 0) {
		    		window.location.href = "product-publish.jspx?action=view&id="+ rows[0].get('id');
		    	} else {
		    		Ext.Msg.alert("提示", "请选择商品");
		    	}
		    }
        },'->',{
        	id: 'searchKeyDeleted',
            xtype: 'textfield',
            name: 'searchKey',
            emptyText: '请输入商品名称或货号',	
            listeners : {
            	specialkey: function(obj,e){
					if(e.getCharCode()==e.ENTER)
						Ext.getCmp("btnSearchDeleted").handler();
				}
			}
        },{
        	id: 'btnSearchDeleted',
            text: '搜索',
            iconCls: 'icon-search',
		    handler: function(btn, event) {
		    	storeDeleted.loadPage(1);
		    }
        }],
        bbar: Ext.create('Ext.PagingToolbar', {
            store: storeDeleted,
            displayInfo: true,
            displayMsg: '显示商品 {0} - {1} 共 {2}',
            emptyMsg: "没有可显示的商品",
            items:[]
        })
    });   
  
    
 	//------------------------------------------------------------------------------------//

 	
    //界面布局
	Ext.create('Ext.Viewport', {
		id: 'viewport',
	    layout: 'border',
	    title: '${module_title}',
	    items: [
			Ext.createWidget('tabpanel', {
		      	region: 'center',
		        activeTab: 0,
		        plain: true,
		        border: true,
		        items: [{
		            title: '出售中的商品',
		       	    layout: 'fit',
		       	    showed: true,
		       	    items:[gridSelling],
					listeners: {
						activate: function(obj, options) {
							Ext.getCmp("btnSearchSelling").handler();
						}
					}
		        },{
		            title: '仓库中的',
		       	    layout: 'fit',
		       	    showed: false,
					listeners: {
						activate: function(obj, options) {
							if(!obj.shwoed){
								obj.shwoed = true;
								obj.add(gridStorage);
							}
							Ext.getCmp("btnSearchStorage").handler();
						}
					}
		        },{
		            title: '回收站',
		       	    layout: 'fit',
		       	    showed: false,
					listeners: {
						activate: function(obj, options) {
							if(!obj.shwoed){
								obj.shwoed = true;
								obj.add(gridDeleted);
							}
							Ext.getCmp("btnSearchDeleted").handler();
						}
					}
		        }]
		    })
		],
		renderTo: document.body
	});
 	
 	
	//------------------------------------------------------------------------------------//
 	 	
 	
 	//恢复商品
 	function recoveryProduct(grid) {
    	var rows = grid.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要恢复所选择的商品吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "ids=";
    					for ( var i = 0; i < rows.length; i++) {
    						ajaxparams += rows[i].get('id');
    						if (i < rows.length - 1) 
    							ajaxparams += ",";
    					}
    					
    					// 发送请求
    					grid.el.mask("正在恢复商品...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.recoveryProduct,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在恢复...",
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
    		Ext.Msg.alert("提示", "请选择商品");
    	}
 	}
 	
 	
 	//删除商品
 	function delProduct(grid) {
    	var rows = grid.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的商品删除吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "ids=";
    					for ( var i = 0; i < rows.length; i++) {
    						ajaxparams += rows[i].get('id');
    						if (i < rows.length - 1) 
    							ajaxparams += ",";
    					}
    					
    					// 发送请求
    					grid.el.mask("正在删除商品...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.delProduct,
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
    		Ext.Msg.alert("提示", "请选择商品");
    	}
 	}
 	
 	
 	//上架
 	function upShelfProduct(grid) {
    	var rows = grid.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的商品上架吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "ids=";
    					for ( var i = 0; i < rows.length; i++) {
    						ajaxparams += rows[i].get('id');
    						if (i < rows.length - 1) 
    							ajaxparams += ",";
    					}
    					
    					// 发送请求
    					grid.el.mask("正在上架商品...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.upShelfProduct,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在上架...",
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
    		Ext.Msg.alert("提示", "请选择商品");
    	}
 	}
 	
 	
 	//下架
 	function offShelfProduct(grid){
    	var rows = grid.selModel.getSelection();
    	if (rows.length > 0) {
    		Ext.Msg.show({
    			title : '提示',
    			msg : '确定要将选择的商品下架吗？',
    			buttons : Ext.Msg.OKCANCEL,
    			icon : Ext.MessageBox.QUESTION,
    			fn : function(btn, text) {
    				if (btn == 'ok') {
    					// 构建Ajax参数
    					var ajaxparams = "ids=";
    					for ( var i = 0; i < rows.length; i++) {
    						ajaxparams += rows[i].get('id');
    						if (i < rows.length - 1) 
    							ajaxparams += ",";
    					}
    					
    					// 发送请求
    					grid.el.mask("正在下架商品...", 'x-mask-loading');
    					Ext.Ajax.request({
    						url : Url.offShelfProduct,
    						params : ajaxparams,
    						method : "POST",
    						waitMsg : "正在下架...",
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
    		Ext.Msg.alert("提示", "请选择商品");
    	}
 	}
 	
 	
 	//修改价格
 	function modifyPrice(grid, record, value){
		// 构建Ajax参数
		var ajaxparams = "id="+record.data.id+"&price="+value;
 					
		// 发送请求
		grid.el.mask("正在修改...", 'x-mask-loading');
		Ext.Ajax.request({
			url : Url.modifyPrice,
			params : ajaxparams,
			method : "POST",
			waitMsg : "正在修改...",
			success : function(response, options) {
				grid.el.unmask();
				var json = Ext.JSON.decode(response.responseText);
				if (json.success) {
					record.commit();
				}else{				
					record.reject();
					Ext.Msg.alert("提示", json.msg);
				}
			},
			failure : function(response, options) {
				grid.el.unmask();				
				record.reject();
				Ext.ajaxFailure(response, options);
			}
		});
 	}
 	
 	
 	//修改库存
 	function modifyInventory(grid, record, value){
		// 构建Ajax参数
		var ajaxparams = "id="+record.data.id+"&stockNumber="+value;
 					
		// 发送请求
		grid.el.mask("正在修改...", 'x-mask-loading');
		Ext.Ajax.request({
			url : Url.modifyInventory,
			params : ajaxparams,
			method : "POST",
			waitMsg : "正在修改...",
			success : function(response, options) {
				grid.el.unmask();
				var json = Ext.JSON.decode(response.responseText);
				if (json.success) {
					record.commit();
				}else{					
					record.reject();
					Ext.Msg.alert("提示", json.msg);
				}
			},
			failure : function(response, options) {
				grid.el.unmask();				
				record.reject();
				Ext.ajaxFailure(response, options);
			}
		});
 	}
    
});

</script>
</head>
<body>
</body>
</html>