package me.park.springwebsocketfile.File;

import me.park.springwebsocketfile.util.CommonUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;

@RestController
@RequestMapping("/api/file")
public class FileAPIController {

	private final String FILE_DIR = "E:\\test\\";
	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {

		String path = null;
		if (!file.isEmpty()) {
			path = FILE_DIR + file.getOriginalFilename();
			file.transferTo(new File(path));
		}
		else {
			ResponseEntity.badRequest().body("업로드할 파일이 없습니다.");
		}

		return ResponseEntity.ok(path);
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadAttach(@RequestParam("path") String pathBase64) throws MalformedURLException {

		String path = new String(Base64.getDecoder().decode(pathBase64));

		UrlResource resource = new UrlResource("file:" + path);
		if(CommonUtil.isEmpty(resource) && !resource.exists()) {
			return ResponseEntity.notFound().build();
		}

		String encodedUploadFileName = UriUtils.encode(Objects.requireNonNull(resource.getFilename()), StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\"";

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.body(resource);
	}
}
