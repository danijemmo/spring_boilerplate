package com.project.spring_boilerplate.file_storage.repositories;

import com.project.spring_boilerplate.file_storage.entities.FileItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileItemRepository extends JpaRepository<FileItem, UUID> {
}
