package com.usys.store.zuul.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usys.store.interfaces.dao.StoreApiInfoMapper;
import com.usys.store.interfaces.model.StoreApiInfo;
import com.usys.store.zuul.service.IStoreApiInfoService;

@Service
public class StoreApiInfoServiceImpl implements IStoreApiInfoService{
	
	@Autowired 
	StoreApiInfoMapper storeApiInfoMapper;

	@Override
	public StoreApiInfo getStoreApiInfoByAppid(String appid) {
		return storeApiInfoMapper.getStoreApiInfoByAppid(appid);
	}

}
