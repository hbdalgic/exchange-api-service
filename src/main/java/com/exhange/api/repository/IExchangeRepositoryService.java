package com.exhange.api.repository;

import com.exhange.api.entity.ExchangeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.time.LocalDate;
import java.util.UUID;

public interface IExchangeRepositoryService extends JpaRepository<ExchangeEntity, Long>, PagingAndSortingRepository<ExchangeEntity, Long> {
    Page<ExchangeEntity> findByTrxId(String trxId, Pageable pageable);
    Page<ExchangeEntity> findByDate(LocalDate date, Pageable pageable);
}
