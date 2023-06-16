package com.hieunt.ebookapp.services;

import com.hieunt.ebookapp.entities.BookType;
import com.hieunt.ebookapp.repositories.BookTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookTypeService {
    private final Set<String> typeNames = Set.of("Hiện đại", "Sủng", "Xuyên không", "Cổ đại Ngôn tình", "Happy Ending", "Tổng tài", "Ngược tâm", "Sắc", "Đô thị tình duyên", "Trọng sinh ngôn tình", "Nữ cường", "Cung đấu", "Hài hước", "Dị giới ngôn tình", "Đam Mỹ", "Huyền huyễn", "Thanh mai trúc mã/Oan gia", "Hào môn thế gia", "Dị giới", "Võng du", "Cổ đại", "Dị năng", "Hắc bang/hắc đạo", "Thanh xuân vườn trường", "Trọng sinh", "Bách hợp", "Tu tiên", "Thương trường", "Quân nhân", "Sách ngoại văn", "Công Nghệ Thông Tin", "Truyện Ngắn - Ngôn Tình", "Kiếm Hiệp - Tiên Hiệp", "Tiểu Thuyết Phương Tây", "Trinh Thám - Hình Sự", "Tâm Lý - Kỹ Năng Sống", "Huyền bí - Giả Tưởng", "Truyện Ma - Truyện Kinh Dị", "Y Học - Sức Khỏe", "Thiếu Nhi- Tuổi Mới Lớn", "Tiểu Thuyết Trung Quốc", "Tài Liệu Học Tập", "Phiêu Lưu - Mạo Hiểm", "Kinh Tế - Quản Lý", "Cổ Tích - Thần Thoại", "Lịch Sử - Chính Trị", "Triết Học", "Hồi Ký - Tuỳ Bút", "Văn Học Việt Nam", "Marketing - Bán hàng", "Khoa Học - Kỹ Thuật", "Học Ngoại Ngữ", "Thư Viện Pháp Luật", "Truyện Cười - Tiếu Lâm", "Văn Hóa - Tôn Giáo", "Tử Vi - Phong Thủy", "Thể Thao - Nghệ Thuật");
    @Autowired
    BookTypeRepository bookTypeRepository;

    @PostConstruct
    public void initBookType() {
        Set<BookType> bookTypeSet = new HashSet<>();
        Set<String> typeNameDbs = bookTypeRepository.findByTypeNameIn(typeNames).stream().map(BookType::getTypeName).collect(Collectors.toSet());
        typeNames.forEach(typeName -> {
            if (!(typeNameDbs.contains(typeName))) {
                bookTypeSet.add(new BookType(typeName));
            }
            bookTypeRepository.saveAll(bookTypeSet);
        });
    }

    public List<BookType> getAllBookType() {
        List<BookType> bookTypes = bookTypeRepository.findAll();
        bookTypes.sort(Comparator.comparing(BookType::getTypeName));
        return bookTypes;
    }
}
