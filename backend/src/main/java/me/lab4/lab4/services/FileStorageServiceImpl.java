package me.lab4.lab4.services;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Service
public class FileStorageServiceImpl implements FileStorageService {

	private final Path root = Paths.get("./src/main/webapp/static/pfp");

	@Override
	public void init() {
		try {
			Files.createDirectories(root);
		} catch(IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file, String as) {
		try {
			Path filePath = this.root.resolve(as);
			Files.deleteIfExists(filePath);
			Files.copy(file.getInputStream(), filePath);
		} catch(Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Resource load(String filename) {
//		try {
		Path file = root.resolve(filename);
//			Resource resource = new UrlResource(file.toUri());

		return new FileSystemResource(file);

//			if(resource.exists() || resource.isReadable()) {
//				return resource1;
//			} else {
//				throw new RuntimeException("Could not read the file!");
//			}
//		} catch(MalformedURLException e) {
//			throw new RuntimeException("Error: " + e.getMessage());
//		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(root.toFile());
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
		} catch(IOException e) {
			throw new RuntimeException("Could not load the files!");
		}
	}
}
