package com.example.weathermanagement1.controller;

import com.example.weathermanagement1.dto.RecordDto;
import com.example.weathermanagement1.entity.*;
import com.example.weathermanagement1.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/records")
public class MainController {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CityService cityService;

    @Autowired
    private CloudsService cloudsService;

    @Autowired
    private CoordService coordService;

    @Autowired
    private MainService mainService;

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private SysService sysService;

    @Autowired
    private WindService windService;

    @Autowired
    private RecordService recordService;

    @Autowired
    private RestTemplate restTemplate;

    @RequestMapping("/{cityName}")
    public ResponseEntity<RecordDto> createRecord(@PathVariable String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=87cdd7f5bbfec3051219cb2cf72ef799&units=metric";
        String result = restTemplate.getForObject(url, String.class);
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatter2 = new SimpleDateFormat("HH:mm:ss");
        String format = formatter.format(date);
        String format2 = formatter2.format(date);
        boolean isExist = this.checkExistWeatherData(cityName);

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RecordDto recordDto = objectMapper.readValue(result, RecordDto.class);
            System.out.println("Record Dto: " + recordDto);

            City city = new City(recordDto.getId(), recordDto.getCod(), recordDto.getName(), recordDto.getTimezone());
            cityService.save(city);

            Record record = new Record(recordDto.getDt(), recordDto.getVisibility(), recordDto.getBase(), city);
            record.setMeasureDate(new Date(recordDto.getDt()*1000));
            record.setMeasureTime(new Date(recordDto.getDt()*1000));
            recordService.save(record);

            Set<Weather> weathers= recordDto.getWeather().stream().map(weatherDto -> modelMapper.map(weatherDto,Weather.class)).collect(Collectors.toSet());
            for (Weather weather : weathers) {
                weather.getRecords().add(record);
                weatherService.save(weather);
            }

            Clouds clouds = new Clouds(recordDto.getClouds().getAll());
            clouds.setRecord(record);
            cloudsService.save(clouds);

            Coord coord = modelMapper.map(recordDto.getCoord(), Coord.class);
            coord.setRecord(record);
            coordService.save(coord);

            Main main = modelMapper.map(recordDto.getMain(), Main.class);
            main.setRecord(record);
            mainService.save(main);

            Sys sys = modelMapper.map(recordDto.getSys(), Sys.class);
            sys.setRecord(record);
            sysService.save(sys);

            Wind wind = modelMapper.map(recordDto.getWind(), Wind.class);
            wind.setRecord(record);
            windService.save(wind);


//            CloudsEntity cloudsEntity = modelMapper.map(recordDto.getClouds(),CloudsEntity.class);
//            cloudsEntity.setRecord(recordEntity);
//            recordEntity.setClouds(cloudsEntity);
//            System.out.println("Clouds Entity: " + cloudsEntity);
//
//            CoordEntity coordEntity = modelMapper.map(recordDto.getCoord(),CoordEntity.class);
//            coordEntity.setRecord(recordEntity);
//            recordEntity.setCoordinate(coordEntity);
//            System.out.println("Coord Entity: " + coordEntity);
//
//            MainEntity mainEntity = modelMapper.map(recordDto.getMain(),MainEntity.class);
//            mainEntity.setRecord(recordEntity);
//            recordEntity.setMain(mainEntity);
//            System.out.println("Main Entity: " + mainEntity);
//
//            SysEntity sysEntity = modelMapper.map(recordDto.getSys(),SysEntity.class);
//            sysEntity.setRecord(recordEntity);
//            recordEntity.setSysDetail(sysEntity);
//            System.out.println("Sys Entity: " + sysEntity);
//
//            WindEntity windEntity = modelMapper.map(recordDto.getWind(),WindEntity.class);
//            windEntity.setRecord(recordEntity);
//            recordEntity.setWind(windEntity);
//            windEntity.setRecord(recordEntity);
//
//            Set<WeatherEntity> weatherEntities= recordDto.getWeather().stream().map(weatherDto -> modelMapper.map(weatherDto,WeatherEntity.class)).collect(Collectors.toSet());
//            for (WeatherEntity weatherEntity : weatherEntities) {
//                weatherEntity.getRecords().add(recordEntity);
//            }
//            recordEntity.setWeathers(weatherEntities);
//            System.out.println("Weather Entities: " + weatherEntities);
//            System.out.println("Record Entity: " + recordEntity);

//            Set<RecordEntity> recordEntities = new HashSet<>();
//            recordEntities.add(recordEntity);
//            cityEntity.setRecords(recordEntities);
//
//            cityService.createCity(cityEntity);
            return ResponseEntity.ok(recordDto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/test/{cityName}")
    public boolean checkExistWeatherData(String cityName) {
        Optional<City> city = cityService.findByName(cityName);
        List<Record> records = recordService.getAllRecordsByCityName(cityName);
        if (city.isPresent() && !records.isEmpty()) {
            Record latestRecord =records.get(0);
            System.out.println("Latest Record: " + latestRecord.getMeasureDate());
            System.out.println(latestRecord.getMeasureDate().getClass().getSimpleName());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(2022-1900,06,16,00,00,00);
            System.out.println(formatter.format(latestRecord.getMeasureDate()));
            System.out.println(formatter.format(date));
            Date date2 = new Date();
            System.out.println("Current Date: " + date);
            System.out.println("Difference: " + formatter.format(latestRecord.getMeasureDate()).equals(formatter.format(date)));
            System.out.println("Difference: " + formatter.format(latestRecord.getMeasureDate()).equals(formatter.format(date2)));

        }
//        log.info("sendRequest {}", records);
        return city.isPresent();
    }


}
