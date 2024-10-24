package com.project.spring_boilerplate.file_storage.services.dtos;

import java.util.List;

public record GetAllFilesResponseDTO(
      int length,
      List<FileItemDTO> fileItems
) {
}
