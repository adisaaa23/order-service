package id.saputra.adi.orderservice.util;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.Conditions;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Slf4j
public class TransformUtil {

    private TransformUtil(){}
    public static Object transform (Object source, Object dest){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setAmbiguityIgnored(true);
        mapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
        if (source instanceof ArrayList){
            return ((ArrayList) source).stream()
                    .map(data -> mapper.map(data, dest.getClass()))
                    .collect(Collectors.toList());
        }
        mapper.map(source, dest);
        return dest;
    }
}
