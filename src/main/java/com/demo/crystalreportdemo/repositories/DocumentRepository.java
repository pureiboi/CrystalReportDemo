package com.demo.crystalreportdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demo.crystalreportdemo.domain.Document;

public interface DocumentRepository extends JpaRepository<Document, String>{

}