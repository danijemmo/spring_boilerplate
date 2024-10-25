package com.project.spring_boilerplate.file_storage.services;

import com.project.spring_boilerplate.file_storage.entities.FileItem;
import com.project.spring_boilerplate.file_storage.repositories.FileItemRepository;
import com.project.spring_boilerplate.file_storage.services.dtos.DownloadFileResponseDTO;
import com.project.spring_boilerplate.file_storage.services.dtos.FileItemDTO;
import com.project.spring_boilerplate.file_storage.services.dtos.GetAllFilesResponseDTO;
import com.project.spring_boilerplate.global.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileStorageService {
   private final FileItemRepository fileItemRepository;

   private static final Path uploadDirectory;

   static {
      uploadDirectory = Path
            .of("/file_store")
            .toAbsolutePath()
            .normalize();
   }

   public FileItem _getFileItemById(UUID fileId) {
      return fileItemRepository
            .findById(fileId)
            .orElseThrow(
                  () -> new NotFoundException(
                        String.format("File %s not found", fileId.toString())));
   }

   public void _deleteFile(FileItem fileItem) {
      fileItemRepository.delete(fileItem);
   }

   public FileItemDTO uploadFile(MultipartFile file) {
      final var fileItem = new FileItem(
            file.getOriginalFilename(),
            file.getContentType(),
            file.getSize()
      );

      final var tempFileItem = fileItemRepository.save(fileItem);

      final var targetLocation = uploadDirectory.resolve(tempFileItem.getId().toString());

      try {
         Files.copy(file.getInputStream(), targetLocation);
      } catch (Exception e) {
         e.printStackTrace();
      }

      return FileItemDTO.fromEntity(fileItem);
   }

   public FileItemDTO getFileInfo(UUID fileId) {
      final var fileItem = fileItemRepository
            .findById(fileId)
            .orElseThrow(
                  () -> new NotFoundException(
                        String.format("File %s not found", fileId.toString())));

      return FileItemDTO.fromEntity(fileItem);
   }

   public GetAllFilesResponseDTO getAllFiles() {
      final var fileItems = fileItemRepository.findAll();

      return new GetAllFilesResponseDTO(
            fileItems.size(),
            fileItems.stream()
                  .map(FileItemDTO::fromEntity)
                  .toList()
      );
   }

   public DownloadFileResponseDTO downloadFile(UUID fileId) {
      final var fileItem = fileItemRepository
            .findById(fileId)
            .orElseThrow(
                  () -> new NotFoundException(
                        String.format("File %s not found", fileId.toString())));

      final var resource = loadFileAsResource(fileItem.getId().toString());

      return new DownloadFileResponseDTO(
            fileItem.getFileName(),
            fileItem.getContentType(),
            resource
      );
   }

   public static Resource loadFileAsResource(String fileName) {
      try {
         final var filePath = uploadDirectory.resolve(fileName).normalize();
         final var resource = new UrlResource(filePath.toUri());

         if (resource.exists())
            return resource;
         else
            throw new NotFoundException("File xnot found " + fileName);

      } catch (MalformedURLException e) {
         e.printStackTrace();
         throw new NotFoundException("File not found " + fileName);
      }
   }
}
