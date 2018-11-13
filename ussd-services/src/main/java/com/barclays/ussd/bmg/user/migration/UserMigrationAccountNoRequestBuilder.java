package com.barclays.ussd.bmg.user.migration;

import com.barclays.ussd.svc.context.USSDBaseRequest;
import com.barclays.ussd.bmg.factory.request.BmgBaseRequestBuilder;
import com.barclays.ussd.bmg.dto.RequestBuilderParamsDTO;

public class UserMigrationAccountNoRequestBuilder implements BmgBaseRequestBuilder {
    public USSDBaseRequest getRequestObject(RequestBuilderParamsDTO requestBuilderParamsDTO) {
	return null;
    }
}