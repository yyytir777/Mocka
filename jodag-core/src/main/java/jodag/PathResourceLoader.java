package jodag;


import jodag.exception.GeneratorException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathResourceLoader {

    /**
     * 주어진 문자열 경로를 기반으로 파일을 읽어 {@link InputStream} 형태로 반환합니다.
     * <p>
     * 이 메서드는 두 가지 경로 유형을 모두 지원합니다:
     * <ul>
     *   <li><b>절대 경로</b>: 로컬 파일 시스템 상의 절대 경로를 통해 파일을 읽습니다.</li>
     *   <li><b>클래스패스 경로</b>: 클래스패스 내부의 리소스를 읽습니다.
     *       경로가 '/'로 시작하면 이를 제거한 뒤 클래스 로더를 통해 리소스를 로드합니다.</li>
     * </ul>
     * <p>
     * 파일이 존재하지 않거나 읽을 수 없는 경우 {@link FileNotFoundException} 또는 {@link IOException}이 발생합니다.
     *
     * @param path 절대 경로 또는 클래스패스 내부의 리소스 경로
     * @return 해당 경로의 파일을 읽기 위한 {@link InputStream}
     * @throws IllegalArgumentException 주어진 경로가 {@code null}이거나 비어 있는 경우
     * @throws FileNotFoundException 해당 경로에 파일이 존재하지 않는 경우
     * @throws IOException 파일 읽기 중 오류가 발생한 경우
     */
    public static InputStream getPath(String path) {
        if (path == null || path.isBlank()) {
            throw new GeneratorException("Path is blank");
        }

        try {
            Path filePath = Paths.get(path);
            if (filePath.isAbsolute()) {
                return Files.newInputStream(filePath);
            }

            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(path.startsWith("/") ? path.substring(1) : path);

            if (is == null) {
                throw new GeneratorException("Resource not found : " + path);
            }

            return is;
        } catch (IOException ioe) {
            throw new GeneratorException("Failed to load resource " + path, ioe);
        }
    }
}
