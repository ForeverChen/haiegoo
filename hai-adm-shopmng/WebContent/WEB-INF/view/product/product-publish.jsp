<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="../meta.jsp" %>
<title>${module_title}</title>
<script src="${app_ass_url}/WEB-RES/js/extjs4.2/ux/TreePicker.js" type="text/javascript"></script>
<script type="text/javascript">
Ext.onReady(function(){
    
    //业务请求的URL
    Url = {
    	saveProduct: '${page_context}/product/product-publish.jspx?func=saveProduct',
		getCategoryList : 'brand-cate.jspx?func=getCategoryList'
    };
    
 	
 	//添加和编辑窗口
 	Ext.create('Ext.form.Panel', {
    	id: 'frmProduct',
		renderTo: Ext.get("devProduct"),
        bodyStyle:'padding:15px',
        border: false,
       	autoScroll: true,
        fieldDefaults: {
            labelAlign: 'left',
            msgTarget: 'side'
        },
        items: [{
            xtype: 'container',
            defaults: {
                margin: '10 0 10 0'
            },
            items: [{
                xtype: 'container',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype:'textfield',
                    id: 'txtProductName',
                    name: 'name',
                    fieldLabel: '商品标题',
                    width: 600,
                    allowBlank: false,
                    value: '${product.name}'
                },{
                    xtype: 'label',
                    text: '还能输入25字',
                    margin: '2 0 0 5'
                }]
            },{
                xtype: 'container',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype:'combobox',
                    id: 'cbbBrandId',
                    name: 'brandId',
                    fieldLabel: '品牌',
                    width: 500,
			        editable: false,
			      	emptyText: '请选择品牌', 
                    store: Ext.decode('${cbbBrand}'),                    
                    value: '${product.brandId}'==''?'':Number('${product.brandId}')
                },{
                    xtype: 'label',
                    html: '<a href="javascript:loadPage(\'brand-cate.jspx\')" target="_blank">编辑品牌</a>',
                    margin: '2 0 0 5'
                }]
            },{
                xtype: 'container',
                layout: {
                    type: 'hbox'
                },
                items: [{
    				xtype: 'treepicker',
                    id: 'cbbCateId',
                    name: 'cateId',
                    fieldLabel: '分类',
                    width: 500,
    			    forceSelection : true,
    		        editable : false,
			      	emptyText: '请选择分类', 
    				minPickerHeight: 200,
    				rootVisible: false, 
					value: '${product.cateId}'==''?0:Number('${product.cateId}'),
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
    			},{
                    xtype: 'label',
                    html: '<a href="javascript:loadPage(\'brand-cate.jspx\')" target="_blank">编辑分类</a>',
                    margin: '2 0 0 5'
                }]
            },{
                xtype: 'container',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype:'numberfield',
                    fieldLabel: '重量',
                    name: 'weight',
                    minValue: 0,
                    value: '${product.weight}'
                },{
                    xtype: 'label',
                    text: '千克',
                    margin: '2 0 0 5'
                }]
            },{
                xtype: 'container',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype:'numberfield',
                    fieldLabel: '数量',
                    name: 'stockNumber',
                    minValue: 0,
                    value: '${product.stockNumber}'
                },{
                    xtype: 'label',
                    text: '件',
                    margin: '2 0 0 5'
                }]
            },{
                xtype: 'container',
                layout: {
                    type: 'hbox'
                },
                items: [{
                    xtype:'numberfield',
                    fieldLabel: '价格',
                    name: 'price',
                    minValue: 0,
                    value: '${product.price}'
                },{
                    xtype: 'label',
                    text: '元',
                    margin: '2 0 0 5'
                }]
            }]
        },{
            xtype: 'container',
            layout: 'column',
            items: [{
                xtype: 'label',
                text: '商品规格:',
                margin: '5 0 10 0'
            }, Ext.create('Ext.grid.property.Grid', {
            	id: 'productSpecs',
                width: 550,
                height: 200,
                margin: '5 0 10 48',
                source: {},
                loader: {
                    url: Url.getProductProps,
                    renderer: function(loader, response, active) {
                        var json = Ext.decode(response.responseText);
                        if(json.success){
	                        loader.getTarget().setSource(json.data);
	                        return true;
                        }else{
                        	Ext.Msg.alert('提示', json.msg);
	                        return false;
                        }
                    }
                }
            })]
        },{
            xtype: 'container',
            layout: 'column',
            items: [{
                xtype: 'label',
                text: '商品属性:',
                margin: '5 0 10 0'
            }, Ext.create('Ext.grid.property.Grid', {
            	id: 'productProps',
                width: 550,
                height: 200,
                margin: '5 0 10 48',
                source: {},
                loader: {
                    url: Url.getProductProps,
                    renderer: function(loader, response, active) {
                        var json = Ext.decode(response.responseText);
                        if(json.success){
	                        loader.getTarget().setSource(json.data);
	                        return true;
                        }else{
                        	Ext.Msg.alert('提示', json.msg);
	                        return false;
                        }
                    }
                }
            })]
        },{
            xtype: 'container',
            layout: 'column',
            items: [{
                xtype: 'label',
                text: '商品图片:',
                margin: '10 0 10 0'
            }, {
			    xtype:'panel',
			    width: 550,
			    height: 120,
                margin: '10 0 10 48',
			    html: '${product.image}'
			}]
        },{
            xtype: 'container',
            layout: 'column',
            defaults: {
                margin: '10 0 10 0'
            },
            items: [{
                xtype:'textarea',
                fieldLabel: '摘要(副标题)',
                name: 'subName',
                value: '${product.subName}',
                height: 50,
                width: 855
            }, {
                xtype: 'htmleditor',
                fieldLabel: '详细描述',
                name: 'pcDesc',
                value: '${product.pcDesc}',
                height: 500,
                width: 855
    	        },{
    	            xtype:'textarea',
                fieldLabel: '商家备注（仅商家可见）',
                name: 'sellerNote',
                value: '${product.sellerNote}',
                height: 50,
                width: 855
            }]
        },{
            xtype: 'container',
            layout: 'anchor',
            defaults: {
                margin: '10 0 10 0'
            },
            items: [{
                xtype: 'radiogroup',
                fieldLabel: '是否上架销售',
                items: [
                    {boxLabel: '是', name: 'saleState', inputValue: 1, checked: '${product.saleState}'=='1'||'${product.saleState}'==''?true:false},
                    {boxLabel: '否', name: 'saleState', inputValue: 0, checked: '${product.saleState}'=='0'?true:false}
                ],
                anchor:'25%'
            }]
        },{
            xtype: 'container',
            width: 340,
            layout:'column',
            style: 'margin: 30px 300px 30px 300px',
            items:[{
	            xtype: 'button',
	            width: 250,
	            margin: '0 5px 0 5px',
	            scale: 'medium',
				text: '我已阅读以下规则，现在发布商品',
				handler: function(){					
					// 提交表单
					var _this = this;					
					_this.disable();
					Ext.getCmp("frmProduct").submit({
					    url: Url.saveProduct,
					    params: {
					    	id: '${product.id}'
					    },
						waitTitle : "提示",
						waitMsg : "正在保存...",
					    success: function(form, action) {
					    	window.location.href = "ProductManage.jspx";
					    },
					    failure: function(form, action) {
					    	Ext.formFailure(form, action);
					    	_this.enable();
					    }
					});
				}
			},{
	            xtype: 'button',
	            width: 70,
	            margin: '0 5px 0 5px',
	            scale: 'medium',
				text: '浏览',
				handler: function(){
					
				}
			}]
        }]
        
    });
 	
});


// 打开页面框架的tab页
function loadPage(url){
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

</script>
</head>
<body style="background: white; padding-top: 8px">
<span class="title t1-icon">${module_title}</span>
<div id="devProduct"></div>
</body>
</html>