package com.barclays.bmg.service.lookup;

import java.util.List;

import com.barclays.bmg.dto.BranchLookUpDTO;
import com.barclays.bmg.service.request.lookup.BranchLookUpServiceRequest;
import com.barclays.bmg.service.response.lookup.BranchLookUpServiceResponse;

public interface BranchLookUpService {

    public BranchLookUpServiceResponse getBranchList(BranchLookUpServiceRequest branchLookUpServiceRequest);

    public BranchLookUpServiceResponse getBranchName(BranchLookUpServiceRequest branchLookUpServiceRequest);

    public BranchLookUpServiceResponse getAllBranches(BranchLookUpServiceRequest branchLookUpServiceRequest);

    public List<BranchLookUpDTO> getBankSwiftCode(String bankCode, String branchCode);
}
