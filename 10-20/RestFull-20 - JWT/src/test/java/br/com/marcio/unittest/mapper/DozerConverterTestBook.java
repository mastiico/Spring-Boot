package br.com.marcio.unittest.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.marcio.data.vo.v1.BookVO;
import br.com.marcio.mapper.DozerMapper;
import br.com.marcio.models.Book;
import br.com.marcio.unittest.mapper.mocks.MockBook;

public class DozerConverterTestBook {

    MockBook inputObject;

    @BeforeEach
    public void setUp() {
        inputObject = new MockBook();
    }

    @Test
    public void parseEntityToVOTest() {
        BookVO output = DozerMapper.parseObject(inputObject.mockEntity(0), BookVO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Title Test0", output.getTitle());
        assertEquals("Author Test0", output.getAuthor());
        assertEquals(3.0, output.getPrice());
        // Assuming LocalDate.of(124, 2, 15) is used in the mockEntity method
        assertEquals("2024-03-15", output.getLaunchDate().toString());
    }

    @Test
    public void parseEntityListToVOListTest() {
        List<BookVO> outputList = DozerMapper.parseListBook(inputObject.mockEntityList(), BookVO.class);
        BookVO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Title Test0", outputZero.getTitle());
        assertEquals("Author Test0", outputZero.getAuthor());
        assertEquals(3.0, outputZero.getPrice());
        // Assuming LocalDate.of(124, 2, 15) is used in the mockEntity method
        assertEquals("2024-03-15", outputZero.getLaunchDate().toString());
        
        BookVO outputSeven = outputList.get(7);
        
        assertEquals(Long.valueOf(7L), outputSeven.getKey());
        assertEquals("Title Test7", outputSeven.getTitle());
        assertEquals("Author Test7", outputSeven.getAuthor());
        assertEquals(3.0, outputSeven.getPrice());
        // Assuming LocalDate.of(124, 2, 15) is used in the mockEntity method
        assertEquals("2024-03-15", outputSeven.getLaunchDate().toString());
        
        BookVO outputTwelve = outputList.get(12);
        
        assertEquals(Long.valueOf(12L), outputTwelve.getKey());
        assertEquals("Title Test12", outputTwelve.getTitle());
        assertEquals("Author Test12", outputTwelve.getAuthor());
        assertEquals(3.0, outputTwelve.getPrice());
        // Assuming LocalDate.of(124, 2, 15) is used in the mockEntity method
        assertEquals("2024-03-15", outputTwelve.getLaunchDate().toString());
    }

    @Test
    public void parseVOToEntityTest() {
        Book output = DozerMapper.parseObject(inputObject.mockVO(0), Book.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Title Test0", output.getTitle());
        assertEquals("Author Test0", output.getAuthor());
        assertEquals(3.0, output.getPrice());
        // Assuming LocalDate.of(124, 2, 15) is used in the mockEntity method
        assertEquals("2024-03-15", output.getLaunchDate().toString());
    }
}
