package org.music_player.web.service;

import jakarta.transaction.Transactional;
import org.music_player.web.dto.GenreDTO;
import org.music_player.web.entity.Album;
import org.music_player.web.entity.Genre;
import org.music_player.web.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;
    public GenreDTO convertGenreEntityToDTO(Genre genre){
        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setGenreId(genre.getGenreId());
        genreDTO.setName(genre.getGenreName());
        genreDTO.setImg(genre.getGenreImg());
        return genreDTO;
    }
    public List<GenreDTO> listALlGenre(){
        List<GenreDTO> listAllGenre = new ArrayList<>();
        for(Genre genre : genreRepository.findAllGenre()){
            listAllGenre.add(convertGenreEntityToDTO(genre));
        }
        return listAllGenre;
    }

    public void addGenre(Genre genre, MultipartFile genreImg) throws IOException {
        if (genreImg == null || genreImg.isEmpty())
            throw new IOException("Ảnh thể loại không được để trống");
        // Đọc dữ liệu hình ảnh từ MultipartFile
        if (ImageIO.read(genreImg.getInputStream()) == null)
            throw new IOException("Tệp vừa chọn không phải là hình ảnh");

        // Lấy đường dẫn để thêm ảnh vào
        String imgUploadDir = "/assets/img/genre/" + genreImg.getOriginalFilename();
        genre.setGenreImg(imgUploadDir);
        // Xử lý trường hợp trùng lặp
        if (imgIsExisted(genre.getGenreImg())) {
            throw new IOException("Đã có thể loại có hình ảnh này");
        } else {
            genreRepository.save(genre);
            Path imgPath = Paths.get("./src/main/resources/static" + imgUploadDir);
            Files.write(imgPath, genreImg.getBytes());
        }
    }

    @Transactional
    public void updateGenre(Genre genre, MultipartFile genreImg) throws IOException {
        // Nếu có ảnh mới thì sửa ko thì thôi
        boolean isDuplicateImage = imgIsExisted("/assets/img/genre/" + genreImg.getOriginalFilename());
        if (!genreImg.isEmpty() && !isDuplicateImage){
            // Đọc dữ liệu hình ảnh từ MultipartFile
            if (ImageIO.read(genreImg.getInputStream()) == null)
                throw new IOException("Tệp vừa chọn không phải là hình ảnh");
            //Xóa ảnh cũ
            deleteFile("./src/main/resources/static" + genre.getGenreImg());
            String imgUploadDir = "/assets/img/genre/" + genreImg.getOriginalFilename();
            Path imgPath = Paths.get("./src/main/resources/static" + imgUploadDir);
            Files.write(imgPath, genreImg.getBytes());
            genre.setGenreImg(imgUploadDir);
        }
        genreRepository.save(genre);
        genreRepository.flush();
    }

    public void deleteGenre(Genre genre) {
        Genre g = genreRepository.getReferenceById(genre.getGenreId());
        deleteFile("./src/main/resources/static" + genre.getGenreImg());
        genreRepository.delete(g);
    }
    public void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Xóa file thành công: " + filePath);
            } else {
                System.out.println("Không thể xóa file: " + filePath);
            }
        } else {
            System.out.println("File không tồn tại: " + filePath);
        }
    }
    public Genre findGenreByGenreId(Integer genreId){
        return genreRepository.findGenreByGenreId(genreId);
    }
    public boolean imgIsExisted(String img) {
        return genreRepository.existsByGenreImg(img);
    }
}
