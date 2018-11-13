package com.barclays.bmg.chequebook.dao.adapter;

import com.barclays.bem.AddCheckBookRequest.CheckBook;
import com.barclays.bem.Branch.Branch;
import com.barclays.bmg.chequebook.service.request.ChequeBookExecuteServiceRequest;
import com.barclays.bmg.dao.core.context.WorkContext;
import com.barclays.bmg.dao.core.context.impl.DAOContext;
import com.barclays.bmg.dto.ChequeBookRequestDTO;

public class ChequeBookExecutePayloadAdapter {

	public CheckBook adaptPayLoad(WorkContext workContext){
		DAOContext daoContext = (DAOContext)workContext;

		Object[] args = daoContext.getArguments();

		CheckBook checkBookRequest = new CheckBook();

		ChequeBookExecuteServiceRequest request = (ChequeBookExecuteServiceRequest)args[0];

		ChequeBookRequestDTO chequeDTO = request.getChequeBookRequestDTO();

		checkBookRequest.setCASAAccountNumber(chequeDTO.getAccount().getAccountNumber());
		checkBookRequest.setCheckBookletSize(chequeDTO.getBookSize());
		Branch branch = new Branch();
        branch.setBranchCode(chequeDTO.getAccount().getBranchCode());
        checkBookRequest.setBranch(branch);
        Branch collectBranch = new Branch();
        collectBranch.setBranchName(chequeDTO.getBranchName());
        collectBranch.setBranchCode(chequeDTO.getBranchCode());
        checkBookRequest.setCollectionBranch(collectBranch);

        return checkBookRequest;
	}

}
