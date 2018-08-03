package com.usys.store.zuul.service;

import com.usys.store.interfaces.model.StoreApiInfo;

public interface IStoreApiInfoService {

	StoreApiInfo getStoreApiInfoByAppid(String appid);
}
