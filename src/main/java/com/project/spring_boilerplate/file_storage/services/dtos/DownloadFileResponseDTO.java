package com.project.spring_boilerplate.file_storage.services.dtos;

import org.springframework.core.io.Resource;

public record DownloadFileResponseDTO(
      String fileName,
      String contentType,
      Resource resource
) {
}
