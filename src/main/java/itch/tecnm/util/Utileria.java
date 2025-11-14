package itch.tecnm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class Utileria {
	public static String guardarArchivo(MultipartFile archivo, String rutaDestino) {
        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        try {

            Path carpeta = Paths.get(rutaDestino);
            Files.createDirectories(carpeta);


            String original = archivo.getOriginalFilename();
            String extension = "";
            if (original != null && original.contains(".")) {
                extension = original.substring(original.lastIndexOf("."));
            }
            String nombreFinal = UUID.randomUUID().toString() + extension;


            Path destino = carpeta.resolve(nombreFinal);
            Files.copy(archivo.getInputStream(), destino, StandardCopyOption.REPLACE_EXISTING);

            return nombreFinal;
        } catch (IOException e) {
            throw new RuntimeException("Error guardando archivo: " + e.getMessage(), e);
        }
    }
}
