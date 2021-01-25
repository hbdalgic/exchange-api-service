package com.exhange.api.service;

import java.util.Map;

import com.exhange.api.exception.model.ChannelException;
import com.exhange.api.model.BaseResponseModel;
import com.exhange.api.model.ProtocolEnum;
import org.dom4j.Element;

public interface IHttpClient {

	public <T> T get(String url,String data, ProtocolEnum service, Class<T> clazz) throws ChannelException;

}
 