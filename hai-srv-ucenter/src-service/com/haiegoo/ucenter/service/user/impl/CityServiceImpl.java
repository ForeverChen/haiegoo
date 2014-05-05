package com.haiegoo.ucenter.service.user.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.alibaba.dubbo.rpc.RpcException;
import com.haiegoo.commons.service.BaseService;
import com.haiegoo.ucenter.model.user.City;
import com.haiegoo.ucenter.service.user.CityService;

public class CityServiceImpl extends BaseService implements CityService {

	private static final long serialVersionUID = -868771914812382473L;
	
	@Resource
	protected SqlMapClientTemplate sqlMapClientUser;

	@Override
	public City getCity(int id) {
		List<City> list = this.getCityList();
		for(City city : list){
			if(city.getId().equals(id))
				return city;
		}
		return null;
	}

	@Override
	public City getCity(String code) {
		List<City> list = this.getCityList();
		for(City city : list){
			if(city.getCode().equals(code))
				return city;
		}
		return null;
	}

	@Override
	public List<City> getCityList() {
		return this.getCityList(null);
	}

	@Override
	public List<City> getCityList(Map<String,Object> params) {
		try{
			@SuppressWarnings("unchecked")
			List<City> list = this.sqlMapClientUser.queryForList("city.getCity", params);
			return list;
		}catch(Exception e){
			throw new RpcException("查询城市出错", e);
		}
	}

	@Override
	public List<City> getCityListByPid(int pid) {
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("pid", pid);
		List<City> list = this.getCityList(params);
		return list;
	}


	@Override
	public void delCity(int id) {		
		City city = this.getCity(id);
		
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("parentCode",city.getCode());
		
		List<City>	list = this.getCityList(params);		
		int[] ids = new int[list.size()+1];
		
		int i;
		for(i=0;i<list.size();i++){
			ids[i] = list.get(i).getId();
		}
		ids[i] = id;		
		
		this.delCity(ids);
	}
	
	@Override
	public void delCity(int[] ids) {
		try{
			this.sqlMapClientUser.delete("city.delCity", ids);
		}catch(Exception e){
			throw new RpcException("删除城市出错", e);
		}
	}


	@Override
	public void addCity(City city) {
		try{
			this.sqlMapClientUser.insert("city.addCity", city);			
	    }catch(DuplicateKeyException e){
			throw new RpcException("城市ID或编码重复", e);	
	    } catch (Exception e) {
			throw new RpcException("添加城市出错", e);
		}
	}

	@Override
	public void editCity(City city) {
		try{
			this.sqlMapClientUser.update("city.editCity", city);			
	    }catch(DuplicateKeyException e){
			throw new RpcException("城市ID或编码重复", e);	
	    } catch (Exception e) {
			throw new RpcException("编辑城市出错", e);
		}
	}

}
