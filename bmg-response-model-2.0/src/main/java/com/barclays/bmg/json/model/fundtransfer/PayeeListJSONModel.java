package com.barclays.bmg.json.model.fundtransfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.barclays.bmg.json.response.BMBPayloadData;

public class PayeeListJSONModel extends  BMBPayloadData implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -5820459945202880511L;
	//categorizedPayeeBeanList
	private List<CategorizedPayeeJSONModel> catzedPayLst = new ArrayList<CategorizedPayeeJSONModel>();
	/*private List<TransferFacadeDTO> tranList = new ArrayList<TransferFacadeDTO>();


	public List<TransferFacadeDTO> getTransferFacadeDTOList() {
		return tranList;
	}*/



	public List<CategorizedPayeeJSONModel> getCatzedPayLst() {
		return catzedPayLst;
	}



	public void setCatzedPayLst(List<CategorizedPayeeJSONModel> catzedPayLst) {
		this.catzedPayLst = catzedPayLst;
	}



	public void addCategorizedPayeeBean(CategorizedPayeeJSONModel categorizedPayeeBean)
	{
		catzedPayLst.add(categorizedPayeeBean);
	}

	/*public void addTransferFacadeDTO(List<TransferFacadeDTO> trList)
	{
		tranList.addAll(trList);
	}

	public void removeTransferFacadeDTO()
	{
		tranList = null;
	}*/

}
