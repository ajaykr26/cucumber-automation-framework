package library.engine.core.objectmatcher.fetch;

import library.common.Constants;
import org.apache.commons.io.FilenameUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static library.database.core.Constants.DBOBJECTPATH;

public class FetchFlatFileObjects {

    private FetchFlatFileObjects() {
    }

    private static Logger getLogger() {
        return LogManager.getLogger(FetchFlatFileObjects.class);
    }

    public static Set<String> populateListOfAPIObjects() {
        List<String> fileExtensions = Collections.singletonList("feature");
        if (Paths.get(Constants.API_OBJECT).toFile().exists()) {
            return findMatchingFileInPath(Constants.API_OBJECT, fileExtensions);
        } else {
            return Collections.emptySet();
        }
    }

    public static Set<String> populateListOfDBObjects() {
        List<String> fileExtensions = Arrays.asList("sql", "json");
        if (Paths.get(DBOBJECTPATH).toFile().exists()) {
            return findMatchingFileInPath(DBOBJECTPATH, fileExtensions);
        } else {
            return Collections.emptySet();
        }
    }

    private static Set<String> findMatchingFileInPath(String rootDir, List<String> fileExtension) {
        Path filePath = Paths.get(rootDir);
        final Set<String> fileList = new HashSet<>();
        try (Stream<Path> files = Files.find(filePath, 5,
                (path, attributes) -> {
                    File file = path.toFile();
                    return !file.isDirectory() && getEndsWithFilter(file, fileExtension);
                })) {
            files.forEach(file -> fileList.add(getFileNameWithoutExtension(file)));
            if (fileList.isEmpty()) {
                getLogger().error("no file found in the path \"{}\"", rootDir);
            } else {
                return fileList;
            }
        } catch (IOException ioException) {
            getLogger().error(ioException.getMessage(), ioException);
        }
        return fileList;
    }

    private static boolean getEndsWithFilter(File file, List<String> fileExtension) {
        if (fileExtension.size() == 1) {
            return file.getName().endsWith(fileExtension.get(0));
        } else if (fileExtension.size() == 2) {
            return (file.getName().endsWith(fileExtension.get(0)) || file.getName().endsWith(fileExtension.get(1)));
        } else if (!fileExtension.isEmpty()) {
            getLogger().warn("got more than 2 file extensions. skipping checks");
            return false;
        }
        return false;
    }

    private static String getFileNameWithoutExtension(Path path) {
        String filename = path.getFileName().toString();
        filename = FilenameUtils.removeExtension(filename);
        return filename;
    }

}
