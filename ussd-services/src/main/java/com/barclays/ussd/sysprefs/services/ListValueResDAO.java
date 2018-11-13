package com.barclays.ussd.sysprefs.services;

public interface ListValueResDAO {

	public ListValueResServiceResponse findListValueLabel(ListValueResServiceRequest request);

	public ListValueResByGroupServiceResponse findListValueResByGroup(ListValueResServiceRequest request);

	public ListValueResByGroupServiceResponse findListValueResByGroupKits(ListValueResServiceRequest request, String bankCodeLetter);

	public ListValueResByGroupServiceResponse getListValueByGroupWithFilter(ListValueResServiceRequest request);

	public ListValueResByGroupServiceResponse findListValueResByGroupKey(ListValueResServiceRequest request);

}
