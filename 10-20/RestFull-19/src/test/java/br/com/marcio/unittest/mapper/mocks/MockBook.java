package br.com.marcio.unittest.mapper.mocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.com.marcio.data.vo.v1.BookVO;
import br.com.marcio.models.Book;

public class MockBook {

    public Book mockEntity(){
        return mockEntity(0);
    }

    public BookVO mockVO(){
         return mockVO(0);
    }

    public List<Book> mockEntityList(){
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            books.add(mockEntity(i));
        }
        return books;
    }

    public List<BookVO> mockVOList(){
        List<BookVO> booksVOs = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            booksVOs.add(mockVO(i));
        }
        return booksVOs;
    }

    @SuppressWarnings("deprecation")
    public Book mockEntity(Integer number){
        Book book = new Book();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date(124, 2, 15));
        book.setPrice(3.0);
        book.setId(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }
    

    @SuppressWarnings("deprecation")
    public BookVO mockVO(Integer number){
        BookVO book = new BookVO();
        book.setAuthor("Author Test" + number);
        book.setLaunchDate(new Date(124, 2, 15));
        book.setPrice(3.0);
        book.setKey(number.longValue());
        book.setTitle("Title Test" + number);
        return book;
    }
}
