import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {
        GameProgress progress1 = new GameProgress(7, 4, 5, 200.5);
        GameProgress progress2 = new GameProgress(10, 2, 1, 82.1);
        GameProgress progress3 = new GameProgress(3, 15, 7, 315.4);

        saveGame("C:/Users/Admin/IdeaProjects/Games/savegames/save1.dat", progress1);
        saveGame("C:/Users/Admin/IdeaProjects/Games/savegames/save2.dat", progress2);
        saveGame("C:/Users/Admin/IdeaProjects/Games/savegames/save3.dat", progress3);

        zipFiles("C:/Users/Admin/IdeaProjects/Games/savegames/savegames.zip", "C:/Users/Admin/IdeaProjects/Games/savegames)");
    }

    public static void saveGame (String filePath, GameProgress gameProgress){
        try (FileOutputStream fileOutputStream = new FileOutputStream(filePath);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {

            objectOutputStream.writeObject(gameProgress);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles (String zipPath, String savegamePath){
        try (FileOutputStream fileOutputStream = new FileOutputStream(zipPath);
             ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream)) {

            Files.walk(Path.of(savegamePath))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
                            zipOutputStream.putNextEntry(zipEntry);
                            zipOutputStream.write(Files.readAllBytes(file));
                            zipOutputStream.closeEntry();

                            Files.delete(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}