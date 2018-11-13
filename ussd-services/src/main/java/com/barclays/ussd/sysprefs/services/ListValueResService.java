package com.barclays.ussd.sysprefs.services;

public interface ListValueResService {

    ListValueResServiceResponse getListValueRes(ListValueResServiceRequest request);

    ListValueResByGroupServiceResponse getListValueByGroup(ListValueResServiceRequest request);

    ListValueResByGroupServiceResponse getListValueByGroupKits(ListValueResServiceRequest request, String bankCodeLetter);

    ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request);

    ListValueResByGroupServiceResponse findListValueResByGroupKey(ListValueResServiceRequest request);

}
