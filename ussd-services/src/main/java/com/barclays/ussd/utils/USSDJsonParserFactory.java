package com.barclays.ussd.utils;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.barclays.ussd.bmg.dto.ResponseBuilderParamsDTO;

@Service
public class USSDJsonParserFactory implements IUSSDJsonParserFactory {

    @Resource(name = "parserMap")
    private Map<String, BmgBaseJsonParser> parserMap;

    public BmgBaseJsonParser getParser(ResponseBuilderParamsDTO responseBuilderParamsDTO) {
	return parserMap.get(responseBuilderParamsDTO.getTranDataId());
    }

    @Override
    public BmgBaseJsonParser getParser(String tranDataId) {
	return parserMap.get(tranDataId);
    }
}
