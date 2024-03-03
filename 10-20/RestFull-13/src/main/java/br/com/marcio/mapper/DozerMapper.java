package br.com.marcio.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import br.com.marcio.models.Person;

public class DozerMapper {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <O, D> D parseObject(O origin, Class<D> destination){
        return mapper.map(origin, destination);
    }

    public static <O, D> List<D> parseListObject(List<Person> list, Class<D> class1){
        List<D> destinantionObjects = new ArrayList<>();
        for (Person o : list) {
            destinantionObjects.add((D) mapper.map(o, class1));
        }
        return destinantionObjects;
    }
    
}
