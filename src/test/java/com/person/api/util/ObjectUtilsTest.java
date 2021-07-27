package com.person.api.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.person.api.dto.AddressDTO;
import com.person.api.dto.BarDTO;
import com.person.api.dto.FooRequestDTO;
import com.person.api.exception.AbstractRuntimeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ObjectUtilsTest {

    @Test
    void patchObjectWithList() throws IOException {

        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

        List<BarDTO> target = new ArrayList<>();

        BarDTO addressDto = new BarDTO();
        addressDto.setNumber("DSADSADSA");
        target.add(addressDto);

        final String s = ObjectMapperUtil.toJson(target);

        Map<String, Object> fields = new HashMap<>();

        List<Map<String, Object>> data = objectMapper.readValue(s, new TypeReference<List<Map<String, Object>>>() {
        });

        fields.put("dtoList", data);

        FooRequestDTO fooRequestDTO = new FooRequestDTO();

        List<BarDTO> barDTO = new ArrayList<>();
        barDTO.add(new BarDTO());

        fooRequestDTO.setDtoList(barDTO);

        ObjectUtils.patch(fields, fooRequestDTO);

        assertNotNull(fooRequestDTO.getDtoList().get(0).getNumber());

    }

    @Test
    void patchInSimpleObject() {

        Map<String, Object> fields = new HashMap<>();

        fields.put("number", "955");
        fields.put("complement", "brinanteolandia");
        fields.put("street", "SCS");
        fields.put("state", null);

        BarDTO target = new BarDTO();
        target.setState("ls");

        ObjectUtils.patch(fields, target);

        assertEquals("955", target.getNumber());
        assertEquals("brinanteolandia", target.getComplement());
        assertEquals("SCS", target.getStreet());
        assertNull(target.getState());

    }

    @Test
    void patchInObjectInOtherObject() {

        Map<String, Object> fields = new HashMap<>();

        LinkedHashMap<String, Object> barDTO = new LinkedHashMap<>();

        barDTO.put("number", "55555");
        fields.put("barDTO", barDTO);
        fields.put("name", "Foo Bar da silva");

        FooRequestDTO target = new FooRequestDTO();

        ObjectUtils.patch(fields, target);

        assertEquals("55555", target.getBarDTO().getNumber());
        assertEquals("Foo Bar da silva", target.getName());

    }


    @Test
    void patchObjectWithDate() throws ParseException {

        Map<String, Object> fields = new HashMap<>();

        LinkedHashMap<String, Object> barDTo = new LinkedHashMap<>();
        barDTo.put("number", "55555");

        fields.put("barDTO", barDTo);
        fields.put("name", "foo bar brincante");
        fields.put("birthDate", "1996-05-22");
        fields.put("fooDate", "1996-05-28");

        FooRequestDTO target = new FooRequestDTO();
        target.setBarDTO(new BarDTO());

        ObjectUtils.patch(fields, target);

        assertEquals("55555", target.getBarDTO().getNumber());
        assertEquals("foo bar brincante", target.getName());
        assertEquals(LocalDate.parse("1996-05-22"), target.getBirthDate());
        assertEquals(new SimpleDateFormat("yyyy-MM-dd").parse("1996-05-28"), target.getFooDate());

    }

    @Test
    void patchObjectWithInvalidDate() throws ParseException {

        Map<String, Object> fields = new HashMap<>();

        LinkedHashMap<String, Object> barDTo = new LinkedHashMap<>();
        barDTo.put("number", "55555");

        fields.put("barDTO", barDTo);
        fields.put("name", "foo bar brincante");
        fields.put("birthDate", "1996-05-22");
        fields.put("fooDate", "19960528");

        FooRequestDTO target = new FooRequestDTO();
        target.setBarDTO(new BarDTO());

        AbstractRuntimeException abstractRuntimeException = Assertions.assertThrows(AbstractRuntimeException.class, () -> ObjectUtils.patch(fields, target));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, abstractRuntimeException.getHttpStatus());


    }


    @Test
    void patchInObjectWithInvalidProperty() {

        Map<String, Object> fields = new HashMap<>();

        BarDTO addressDto = new BarDTO();
        addressDto.setNumber("767");

        fields.put("invalidProperty", addressDto);

        FooRequestDTO target = new FooRequestDTO();

        FooRequestDTO patch = ObjectUtils.patch(fields, target);

        assertEquals(patch, target);


    }

    @Test
    void getFieldValue() {

        Map<String, Object> fields = new HashMap<>();

        BarDTO barDTO = new BarDTO();
        barDTO.setNumber("DSADSADSA");

        fields.put("address", barDTO);

        BarDTO address = ObjectUtils.getFieldValue(fields, "address", BarDTO.class);

        assertEquals(barDTO, address);
    }

    @Test
    void getFieldValueNotFound() {

        Map<String, Object> fields = new HashMap<>();

        BarDTO barDTO = new BarDTO();
        barDTO.setNumber("DSADSADSA");

        fields.put("barDTO", barDTO);

        AddressDTO address = ObjectUtils.getFieldValue(fields, "fooProperty", AddressDTO.class);

        assertNull(address);
    }

    @Test
    void getFieldWithCastException() {

        Map<String, Object> fields = new HashMap<>();

        BarDTO barDTO = new BarDTO();
        barDTO.setNumber("DSADSADSA");

        fields.put("barDTO", barDTO);

        AbstractRuntimeException abstractRuntimeException = Assertions.assertThrows(AbstractRuntimeException.class, () -> ObjectUtils.getFieldValue(fields, "barDTO", FooRequestDTO.class));

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, abstractRuntimeException.getHttpStatus());


    }
}