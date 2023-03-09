package ru.astondevs.asber.infoservice.service;

import org.springframework.data.domain.Page;
import ru.astondevs.asber.infoservice.entity.BankBranch;


/**
 * Interface that works with {@link BankBranch} entity.
 */
public interface BankBranchService {

    /**
     * Method that gets list of operations by client id with pagination
     * @param page current page of pagination
     * @return List of operations with pagination
     */

    Page<BankBranch> getBankBranches(Integer page);
}
