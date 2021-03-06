package com.exhange.api.service.impl;

import com.exhange.api.config.HttpClientConfig;
import com.exhange.api.exception.model.ChannelException;
import com.exhange.api.model.BaseResponseModel;
import com.exhange.api.model.ProtocolEnum;
import com.exhange.api.service.IHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class HttpClientService extends HttpClientConfig implements IHttpClient {

    private Logger LOG = LoggerFactory.getLogger(getClass());
    private ObjectMapper objectMapper;

    public HttpClientService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T get(String url, String data, ProtocolEnum service, Class<T> clazz) throws ChannelException {
        String params = processedUrl(service, data);
        if (params != null && params.length() > 0) {
            url = url + params;
        }
        try {
            HttpGet get = new HttpGet(url);

            get.addHeader("Content-Type", "application/json");
            get.addHeader("Accept", "*/*");

            String result = null;

            try {
                HttpResponse response = getClient().execute(get);
                HttpEntity entity = response.getEntity();
                StatusLine statusLine = response.getStatusLine();

                if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                    result = EntityUtils.toString(entity, "UTF-8");

                    if (LOG.isInfoEnabled()) {
                        LOG.info("::get service:{} url:{} returnJson:{} ", service, url, result);
                    }
                } else {
                    String content = null;
                    if (entity != null)
                        content = EntityUtils.toString(entity);

                    int httpErrorCode = statusLine.getStatusCode();
                    LOG.error("::get Error service:{} url:{}  content:{} status:{}", service, url, content, httpErrorCode);
                    throw new ChannelException(content, httpErrorCode);
                }
            } catch (Exception e) {
                LOG.error("::get  ", e);
            } finally {
                get.releaseConnection();
            }
            return objectMapper.readValue(result, clazz);
        } catch (Exception e) {
            LOG.error("::get Error ", e);
            throw new ChannelException(e.getMessage(), 500);
        }

    }

    private String processedUrl(ProtocolEnum service, String data) {
        if (data == null && data.length() > 0) {
            return null;
        }
        StringBuilder buf = new StringBuilder();
        String newParams = service.getPath();
        if (service.isUsePath()) {
            String[] path = data.split("/");
            for (int i = 0; i < path.length; i++) {
                newParams = newParams.replace("{" + (i + 1) + "}", path[i]);
            }
        } else {
            newParams = service.getPath();
        }
        buf.append(newParams);
        return buf.toString();
    }
}
