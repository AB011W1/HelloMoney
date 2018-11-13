package com.barclays.bmg.json.model;

import com.barclays.bmg.json.response.BMBPayloadData;

public interface BMGJSONAdapter {

	BMBPayloadData adaptToJSONModel(Object obj);

}
