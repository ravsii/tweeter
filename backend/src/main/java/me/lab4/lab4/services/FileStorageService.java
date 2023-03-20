package me.lab4.lab4.services;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface FileStorageService {
	void init();

	void save(MultipartFile file, String as);

	Resource load(String filename);

	void deleteAll();

	Stream<Path> loadAll();
}