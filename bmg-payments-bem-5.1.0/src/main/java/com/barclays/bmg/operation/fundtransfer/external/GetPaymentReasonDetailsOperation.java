package com.barclays.bmg.operation.fundtransfer.external;

import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.dto.ListValueCacheDTO;
import com.barclays.bmg.dto.PaymentDetailsDTO;
import com.barclays.bmg.dto.PaymentReasonDTO;
import com.barclays.bmg.dto.PaymentRsonDtlsDTO;
import com.barclays.bmg.operation.BMBCommonOperation;
import com.barclays.bmg.operation.request.fundtransfer.external.GetPaymentReasonDetailsOperationRequest;
import com.barclays.bmg.operation.response.fundtransfer.external.GetPaymentReasonDetailsOperationResponse;
import com.barclays.bmg.service.product.ListValueResService;
import com.barclays.bmg.service.product.request.ListValueResServiceRequest;
import com.barclays.bmg.service.product.response.ListValueResByGroupServiceResponse;

public class GetPaymentReasonDetailsOperation extends BMBCommonOperation{

	private ListValueResService listValueResService;
	private String payRsonGrpId;
	private String payDtlsGrpId;


/*	public GetPaymentReasonDetailsOperationResponse getPaymentReasonList(GetPaymentReasonDetailsOperationRequest request){

		GetPaymentReasonDetailsOperationResponse response = new GetPaymentReasonDetailsOperationResponse();
		response.setContext(request.getContext());
		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup(groupId);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByGroup(listValueResServiceRequest);
		List<PaymentReasonDTO> paymentRsonLst = null;
		if(listValueResByGroupServiceResponse!=null && listValueResByGroupServiceResponse.getListValueCahceDTO() !=null){
			paymentRsonLst = new ArrayList<PaymentReasonDTO>();
			List<ListValueCacheDTO> listValueCacheDTOLst  =listValueResByGroupServiceResponse.getListValueCahceDTO();
			for(ListValueCacheDTO listValueCacheDTO : listValueCacheDTOLst){
				PaymentReasonDTO paymentReasonDTO = new PaymentReasonDTO();
				paymentReasonDTO.setPayRsonKey(listValueCacheDTO.getKey());
				paymentReasonDTO.setPayRsonValue(listValueCacheDTO.getLabel());
				paymentRsonLst.add(paymentReasonDTO);
			}
		}
		response.setPayRsonLst(paymentRsonLst);
		return response;
	}


	public GetPaymentReasonDetailsOperationResponse getPaymentDtls(GetPaymentReasonDetailsOperationRequest request){
		GetPaymentReasonDetailsOperationResponse response = new GetPaymentReasonDetailsOperationResponse();
		response.setContext(request.getContext());

		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup(groupId);
		listValueResServiceRequest.setFilterKey1(request.getPayRsonKey());
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByGroupWithFilter(listValueResServiceRequest);
		List<PaymentReasonDTO> paymentRsonLst = null;
		if(listValueResByGroupServiceResponse!=null && listValueResByGroupServiceResponse.getListValueCahceDTO() !=null){
			paymentRsonLst = new ArrayList<PaymentReasonDTO>();
			List<ListValueCacheDTO> listValueCacheDTOLst  =listValueResByGroupServiceResponse.getListValueCahceDTO();
			for(ListValueCacheDTO listValueCacheDTO : listValueCacheDTOLst){
				PaymentReasonDTO paymentReasonDTO = new PaymentReasonDTO();
				paymentReasonDTO.setPayRsonKey(listValueCacheDTO.getKey());
				paymentReasonDTO.setPayRsonValue(listValueCacheDTO.getLabel());
				paymentRsonLst.add(paymentReasonDTO);
			}
		}
		response.setPayRsonLst(paymentRsonLst);
		return response;

	}*/


	public List<PaymentReasonDTO> getPaymentReasonList(GetPaymentReasonDetailsOperationRequest request){


		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup(payRsonGrpId);
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByGroup(listValueResServiceRequest);
		List<PaymentReasonDTO> paymentRsonLst = new ArrayList<PaymentReasonDTO>();
		if(listValueResByGroupServiceResponse!=null && listValueResByGroupServiceResponse.getListValueCahceDTO() !=null){

			List<ListValueCacheDTO> listValueCacheDTOLst  =listValueResByGroupServiceResponse.getListValueCahceDTO();
			for(ListValueCacheDTO listValueCacheDTO : listValueCacheDTOLst){
				PaymentReasonDTO paymentReasonDTO = new PaymentReasonDTO();

				if(listValueCacheDTO.getKey()!=null && !listValueCacheDTO.getKey().equals("payreason_plsselect")) {
					paymentReasonDTO.setPayRsonKey(listValueCacheDTO.getKey());
					paymentReasonDTO.setPayRsonValue(listValueCacheDTO.getLabel());
				}
				if(paymentReasonDTO!=null && paymentReasonDTO.getPayRsonKey()!=null && paymentReasonDTO.getPayRsonValue()!=null) {
					paymentRsonLst.add(paymentReasonDTO);
				}
			}
		}
		return paymentRsonLst;
	}

	public List<PaymentDetailsDTO> getPaymentDtls(GetPaymentReasonDetailsOperationRequest request){

		ListValueResServiceRequest listValueResServiceRequest = new ListValueResServiceRequest();
		listValueResServiceRequest.setContext(request.getContext());
		listValueResServiceRequest.setGroup(payDtlsGrpId);
		listValueResServiceRequest.setFilterKey1(request.getPayRsonKey());
		ListValueResByGroupServiceResponse listValueResByGroupServiceResponse = listValueResService.getListValueByGroupWithFilter(listValueResServiceRequest);
		List<PaymentDetailsDTO> paymentDtlsLst = new ArrayList<PaymentDetailsDTO>();
		if(listValueResByGroupServiceResponse!=null && listValueResByGroupServiceResponse.getListValueCahceDTO() !=null){
			List<ListValueCacheDTO> listValueCacheDTOLst  =listValueResByGroupServiceResponse.getListValueCahceDTO();
			for(ListValueCacheDTO listValueCacheDTO : listValueCacheDTOLst){
				PaymentDetailsDTO paymentDetailsDTO = new PaymentDetailsDTO();

				if(listValueCacheDTO.getKey()!=null && !listValueCacheDTO.getKey().equals("paydetail_plsselect")) {
					paymentDetailsDTO.setPayDtlsKey(listValueCacheDTO.getKey());
					paymentDetailsDTO.setPayDtlsValue(listValueCacheDTO.getLabel());
					paymentDetailsDTO.setFilterKey1(listValueCacheDTO.getFilterKey1());
				}
				if(paymentDetailsDTO!=null && paymentDetailsDTO.getPayDtlsKey()!=null && paymentDetailsDTO.getPayDtlsValue()!=null) {
					paymentDtlsLst.add(paymentDetailsDTO);
				}
			}
		}

		return paymentDtlsLst;

	}


	/**
	 * @param request
	 * @return
	 */
	public GetPaymentReasonDetailsOperationResponse getPaymentRsonDtls(GetPaymentReasonDetailsOperationRequest request){
		GetPaymentReasonDetailsOperationResponse response = new GetPaymentReasonDetailsOperationResponse();
		response.setContext(request.getContext());

		List<PaymentRsonDtlsDTO> paymentRsonDtlsLst = new ArrayList<PaymentRsonDtlsDTO>();

		List<PaymentReasonDTO> paymentRsonLst = getPaymentReasonList(request);

		if(paymentRsonLst!=null && !paymentRsonLst.isEmpty()){
			List<PaymentDetailsDTO> paymentDtlsLst = getPaymentDtls(request);

			for(PaymentReasonDTO paymentReasonDTO: paymentRsonLst){
				PaymentRsonDtlsDTO paymentRsonDtlsDTO = new PaymentRsonDtlsDTO();
				paymentRsonDtlsDTO.setPaymentReasonDTO(paymentReasonDTO);
				if(paymentDtlsLst!=null && !paymentDtlsLst.isEmpty()){
					for(PaymentDetailsDTO paymentDetailsDTO : paymentDtlsLst){
						if(paymentReasonDTO.getPayRsonKey()!=null){
							if(paymentReasonDTO.getPayRsonKey().equals(paymentDetailsDTO.getFilterKey1())){
								paymentRsonDtlsDTO.add(paymentDetailsDTO);
							}
						}

					}
				}
				paymentRsonDtlsLst.add(paymentRsonDtlsDTO);
			}

		}
		response.setPaymentRsonDtlsLst(paymentRsonDtlsLst);
		return response;
	}

	@Override
	public ListValueResService getListValueResService() {
		return listValueResService;
	}

	@Override
	public void setListValueResService(ListValueResService listValueResService) {
		this.listValueResService = listValueResService;
	}

	public String getPayRsonGrpId() {
		return payRsonGrpId;
	}

	public void setPayRsonGrpId(String payRsonGrpId) {
		this.payRsonGrpId = payRsonGrpId;
	}

	public String getPayDtlsGrpId() {
		return payDtlsGrpId;
	}

	public void setPayDtlsGrpId(String payDtlsGrpId) {
		this.payDtlsGrpId = payDtlsGrpId;
	}


}
