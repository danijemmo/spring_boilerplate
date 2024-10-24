package com.project.spring_boilerplate.file_storage.services.dtos;

import com.project.spring_boilerplate.file_storage.entities.FileItem;

import java.util.Date;
import java.util.UUID;

public record FileItemDTO(
      UUID id,
      String fileName,
      String contentType,
      long sizeInBytes,
      Date createdAt
) {
   public static FileItemDTO fromEntity(FileItem fileItem) {
      return new FileItemDTO(
            fileItem.getId(),
            fileItem.getFileName(),
            fileItem.getContentType(),
            fileItem.getSizeInBytes(),
            fileItem.getCreatedOn()
      );
   }
}
