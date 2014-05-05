package com.haiegoo.commons.service.config.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.model.config.Enums;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.commons.service.config.EnumsService;

public class EnumsServiceImpl extends BaseService implements EnumsService {

	private static final long serialVersionUID = 6431588222617603394L;

	@Resource
	protected SqlMapClientTemplate sqlMapClientCommons;

	@Override
	public List<Enums> getEnums(String clazz) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("clazz", clazz);
		
		List<Enums> list = this.getEnumsList(params);
		return list;
	}

	@Override
	public Enums getEnumsByKey(String clazz, String key) {
		List<Enums> list = this.getEnums(clazz);
		for(Enums e : list){
			if(e.getKey().equals(key))
				return e;
		} 
		return null;
	}

	@Override
	public Enums getEnumsByCode(String clazz, String code) {
		List<Enums> list = this.getEnums(clazz);
		for(Enums e : list){
			if(e.getCode().equals(code))
				return e;
		} 
		return null;
	}
	
	@Override
	public Enums getEnumsByText(String clazz, String text) {
		List<Enums> list = this.getEnums(clazz);
		for(Enums e : list){
			if(e.getText().equals(text))
				return e;
		} 
		return null;
	}
	

	@Override
	public Enums getEnums(int id) {
		try{
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("id", id);
			Enums obj = (Enums)this.sqlMapClientCommons.queryForObject("enums.getEnums", params);
			return obj;
		}catch(Exception e){
			throw new RpcException("查询枚举出错", e);
		}
	}
	
	@Override
	public List<Enums> getEnumsList() {
		return this.getEnumsList(null);
	}
	
	@Override
	public List<Enums> getEnumsList(Map<String, Object> params) {
		try{
			if(params==null)
				params = new HashMap<String,Object>();
			
			@SuppressWarnings("unchecked")
			List<Enums> list = this.sqlMapClientCommons.queryForList("enums.getEnums", params);
			return list;
		}catch(Exception e){
			throw new RpcException("查询枚举出错", e);
		}
	}

	@Override
	public void delEnums(int id) {
		this.delEnums(new int[]{id});
	}

	@Override
	public void delEnums(int[] ids) {
		try{
			this.sqlMapClientCommons.delete("enums.delEnums", ids);
		}catch(Exception e){
			throw new RpcException("删除枚举出错", e);
		}
	}

	@Override
	public void addEnums(Enums enums) {
		try{
			this.sqlMapClientCommons.insert("enums.addEnums", enums);
	    }catch(DuplicateKeyException e){
			throw new RpcException("同一个枚举类下，枚举键和值不能原有的枚举重复", e);
        } catch (Exception e) {
			throw new RpcException("添加枚举出错", e);
		}
	}

	@Override
	public void editEnums(Enums enums) {
		try{
			this.sqlMapClientCommons.update("enums.editEnums", enums);
	    }catch(DuplicateKeyException e){
			throw new RpcException("同一个枚举类下，枚举键和值不能原有的枚举重复", e);
        } catch (Exception e) {
			throw new RpcException("编辑枚举出错", e);
		}
	}

}
