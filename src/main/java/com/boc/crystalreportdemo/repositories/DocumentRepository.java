package com.boc.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.boc.crystalreportdemo.domain.Document;

public interface DocumentRepository extends JpaRepository<Document, String>{

}