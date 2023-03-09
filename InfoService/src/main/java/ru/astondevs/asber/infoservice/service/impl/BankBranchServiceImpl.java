package ru.astondevs.asber.infoservice.service.impl;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.astondevs.asber.infoservice.entity.BankBranch;
import ru.astondevs.asber.infoservice.repository.BankBranchRepository;
import ru.astondevs.asber.infoservice.service.BankBranchService;
import ru.astondevs.asber.infoservice.util.exception.InvalidPageIndexException;


/**
 * Service that works with {@link BankBranch} entity.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BankBranchServiceImpl implements BankBranchService {

    private final BankBranchRepository bankBranchRepository;

    /**
     * Method that gets all {@link BankBranch} from {@link BankBranchRepository}
     *
     * @return {@link Page<BankBranch>}
     */
    @Override
    @Cacheable("bankBranches")
    public Page<BankBranch> getBankBranches(Integer page) {
        log.info("Request for all bank branches with page {}", page);
        PageRequest pageRequest = PageRequest.of(page, 20);
        Page<BankBranch> pagebleBankBranches = bankBranchRepository.findAll(pageRequest);
        if (page >= pagebleBankBranches.getTotalPages()) {
            log.error("Invalid page");
            throw new InvalidPageIndexException();
        }
        return pagebleBankBranches;
    }

    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.HOURS)
    @CacheEvict(value = "bankBranches", allEntries = true)
    public void emptyBankBranchesCache () {
        log.info("emptying bank branches cache");
        }
}
