package com.example.weathermanagement1.controller;

import com.example.weathermanagement1.dto.request.RecordDto;
import com.example.weathermanagement1.dto.response.General;
import com.example.weathermanagement1.dto.response.WeatherDataResponse;
import com.example.weathermanagement1.dto.response.WeatherDetail;
import com.example.weathermanagement1.dto.response.WindDetail;
import com.example.weathermanagement1.entity.*;
import com.example.weathermanagement1.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.*;
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
    public ResponseEntity<WeatherDataResponse> getWeatherInfo(@PathVariable String cityName) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=87cdd7f5bbfec3051219cb2cf72ef799&units=metric";
        String result = restTemplate.getForObject(url, String.class);
        boolean isExist = this.checkExistWeatherData(cityName);
        WeatherDataResponse weatherDataResponse = null;

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            RecordDto recordDto = objectMapper.readValue(result, RecordDto.class);
            System.out.println("Record Dto: " + recordDto);

            Optional<City> city = cityService.findByName(cityName);
            if (city.isPresent()) {
                List<Record> records = recordService.getAllRecordsByCityName(cityName);
                if (!records.isEmpty()) {
                    Record latestRecord = records.get(0);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    boolean isSameDate = formatter.format(latestRecord.getMeasureDate()).equals(formatter.format(new Date()));
                    if (isSameDate) {
                        System.out.println("Latest record is same date");
                        latestRecord.setBase(recordDto.getBase());
                        latestRecord.setVisibility(recordDto.getVisibility());
                        latestRecord.setDt(recordDto.getDt());
                        latestRecord.setMeasureDate(new Date(recordDto.getDt()*1000));
                        latestRecord.setMeasureTime(new Date(recordDto.getDt()*1000));
                        recordService.save(latestRecord);

                        Clouds clouds = latestRecord.getClouds();
                        clouds.setStorage(recordDto.getClouds().getAll());
                        cloudsService.save(clouds);

                        Coord coord = latestRecord.getCoordinate();
                        coord.setLat(recordDto.getCoord().getLat());
                        coord.setLon(recordDto.getCoord().getLon());
                        coordService.save(coord);

                        Main main = latestRecord.getMain();
                        main.setTemp(recordDto.getMain().getTemp());
                        main.setPressure(recordDto.getMain().getPressure());
                        main.setHumidity(recordDto.getMain().getHumidity());
                        main.setTemp_min(recordDto.getMain().getTemp_min());
                        main.setTemp_max(recordDto.getMain().getTemp_max());
                        main.setFeels_like(recordDto.getMain().getFeels_like());
                        mainService.save(main);

                        Sys sys = latestRecord.getSysDetail();
                        sys.setSunrise(recordDto.getSys().getSunrise());
                        sys.setSunset(recordDto.getSys().getSunset());
                        sys.setSysId(recordDto.getSys().getId());
                        sys.setType(recordDto.getSys().getType());
                        sys.setCountry(recordDto.getSys().getCountry());
                        sysService.save(sys);

                        Set<Weather> weathers = latestRecord.getWeathers();
                        for (Weather weather : weathers) {
                            weatherService.deleteWeather(weather);
                        }
                        Set<Weather> recordDtoWeathers = recordDto.getWeather().stream().map(weather -> {
                            Weather weather1 = new Weather();
                            weather1.setId(weather.getId());
                            weather1.setMain(weather.getMain());
                            weather1.setDescription(weather.getDescription());
                            weather1.setIcon(weather.getIcon());
                            return weather1;
                        }).collect(Collectors.toSet());

                        recordDtoWeathers.stream().filter(weather -> !weathers.contains(weather)).forEach(weather -> {
                            Optional<Weather> existingWeather = weatherService.getWeatherById(weather.getWeatherId());
                            if (existingWeather.isEmpty()) {
                                weather.getRecords().add(latestRecord);
                                weatherService.save(weather);
                            } else {
                                existingWeather.get().getRecords().add(latestRecord);
                                weatherService.save(existingWeather.get());
                            }
                        });

                        Wind wind = latestRecord.getWind();
                        wind.setDeg(recordDto.getWind().getDeg());
                        wind.setSpeed(recordDto.getWind().getSpeed());
                        wind.setGust(recordDto.getWind().getGust());
                        windService.save(wind);

                        WindDetail windDetail = new WindDetail(recordDto.getWind().getSpeed(), recordDto.getWind().getDeg(), recordDto.getWind().getGust());
                        General general = new General(recordDto.getMain().getTemp(), recordDto.getMain().getFeels_like(), recordDto.getMain().getTemp_min(), recordDto.getMain().getTemp_max(), recordDto.getMain().getPressure(), recordDto.getMain().getHumidity(), recordDto.getClouds().getAll(), recordDto.getVisibility(), windDetail);
                        List<WeatherDetail> weatherDetails = recordDto.getWeather().stream().map(weather -> new WeatherDetail(weather.getDescription(), weather.getIcon())).collect(Collectors.toList());
                        String measureDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(recordDto.getDt()*1000));
                        String measureTime = new SimpleDateFormat("HH:mm:ss").format(new Date(recordDto.getDt()*1000));
                        weatherDataResponse = new WeatherDataResponse(latestRecord.getId(),recordDto.getId(), recordDto.getName(), recordDto.getSys().getCountry(), recordDto.getTimezone(), weatherDetails, general, measureDate + " " + measureTime);

                    } else {
                        System.out.println("Latest recordDto is not same date");

                        Record record = new Record(recordDto.getDt(), recordDto.getVisibility(), recordDto.getBase(), latestRecord.getCity());
                        record.setMeasureDate(new Date(recordDto.getDt()*1000));
                        record.setMeasureTime(new Date(recordDto.getDt()*1000));
                        recordService.save(record);

                        Set<Weather> weathers= recordDto.getWeather().stream().map(weatherDto -> modelMapper.map(weatherDto,Weather.class)).collect(Collectors.toSet());
                        for (Weather weather : weathers) {
                            Optional<Weather> existingWeather = weatherService.getWeatherById(weather.getWeatherId());
                            if (existingWeather.isEmpty()) {
                                weather.getRecords().add(record);
                                weatherService.save(weather);
                            } else {
                                existingWeather.get().getRecords().add(record);
                                weatherService.save(existingWeather.get());
                            }
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

                        WindDetail windDetail = new WindDetail(recordDto.getWind().getSpeed(), recordDto.getWind().getDeg(), recordDto.getWind().getGust());
                        General general = new General(recordDto.getMain().getTemp(), recordDto.getMain().getFeels_like(), recordDto.getMain().getTemp_min(), recordDto.getMain().getTemp_max(), recordDto.getMain().getPressure(), recordDto.getMain().getHumidity(), recordDto.getClouds().getAll(), recordDto.getVisibility(), windDetail);
                        List<WeatherDetail> weatherDetails = recordDto.getWeather().stream().map(weather -> new WeatherDetail(weather.getDescription(), weather.getIcon())).collect(Collectors.toList());
                        String measureDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(recordDto.getDt()*1000));
                        String measureTime = new SimpleDateFormat("HH:mm:ss").format(new Date(recordDto.getDt()*1000));
                        weatherDataResponse = new WeatherDataResponse(record.getId(),recordDto.getId(), recordDto.getName(), recordDto.getSys().getCountry(), recordDto.getTimezone(), weatherDetails, general, measureDate + " " + measureTime);
                    }
                }
            } else {
                System.out.println("City is not exist");
                Record record= this.createRecord(recordDto);
                System.out.println("Record: " + record.getBase());
                WindDetail windDetail = new WindDetail(recordDto.getWind().getSpeed(), recordDto.getWind().getDeg(), recordDto.getWind().getGust());
                General general = new General(recordDto.getMain().getTemp(), recordDto.getMain().getFeels_like(), recordDto.getMain().getTemp_min(), recordDto.getMain().getTemp_max(), recordDto.getMain().getPressure(), recordDto.getMain().getHumidity(), recordDto.getClouds().getAll(), recordDto.getVisibility(), windDetail);
                List<WeatherDetail> weatherDetails = recordDto.getWeather().stream().map(weather -> new WeatherDetail(weather.getDescription(), weather.getIcon())).collect(Collectors.toList());
                String measureDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(recordDto.getDt()*1000));
                String measureTime = new SimpleDateFormat("HH:mm:ss").format(new Date(recordDto.getDt()*1000));
                weatherDataResponse = new WeatherDataResponse(record.getId(),recordDto.getId(), recordDto.getName(), recordDto.getSys().getCountry(), recordDto.getTimezone(), weatherDetails, general, measureDate + " " + measureTime);
            }

            return ResponseEntity.ok(weatherDataResponse);
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

            System.out.println("Record Details1:" + latestRecord.getMain().getTemp());
            System.out.println("Record Details2:" + latestRecord.getMain().getHumidity());
            System.out.println("Record Details3:" + latestRecord.getMain().getPressure());
            System.out.println("Record Details4:" + latestRecord.getMain().getTemp_min());
            System.out.println("Record Details5:" + latestRecord.getMain().getTemp_max());

        }
        return city.isPresent();
    }

    public Record createRecord(RecordDto recordDto) {
        City city = new City(recordDto.getId(), recordDto.getCod(), recordDto.getName(), recordDto.getTimezone());
        cityService.save(city);

        Record record = new Record(recordDto.getDt(), recordDto.getVisibility(), recordDto.getBase(), city);
        record.setMeasureDate(new Date(recordDto.getDt()*1000));
        record.setMeasureTime(new Date(recordDto.getDt()*1000));
        recordService.save(record);

        Set<Weather> weathers= recordDto.getWeather().stream().map(weatherDto -> modelMapper.map(weatherDto,Weather.class)).collect(Collectors.toSet());
        for (Weather weather : weathers) {
            Optional<Weather> existingWeather = weatherService.getWeatherById(weather.getWeatherId());
            if (existingWeather.isEmpty()) {
                weather.getRecords().add(record);
                weatherService.save(weather);
            } else {
                existingWeather.get().getRecords().add(record);
                weatherService.save(existingWeather.get());
            }
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
        return record;
    }

    @DeleteMapping("/delete/{id}")
    public String deleteRecord(@PathVariable Long id) {
        Record record = recordService.getRecordById(id);
        System.out.println("Record: " + record);
        for (Weather weather : record.getWeathers()) {
            weather.getRecords().remove(record);
            weatherService.save(weather);
            Weather weather1 = weatherService.getWeatherById(weather.getWeatherId()).get();
//            System.out.println("Weather: " + weather1.getRecords());
        }
//        recordService.deleteWeathers(id);
//        System.out.println("Weathers: " + record.getWeathers());
        return recordService.delete(id);
    }

    @GetMapping("/recent/{cityName}")
    public ResponseEntity<List<WeatherDataResponse>> getRecentRecords(@PathVariable String cityName) {
        List<Record> records = recordService.getAllRecordsByCityName(cityName);
        if (records.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        List<WeatherDataResponse> weatherDataResponses = new ArrayList<>();
        for (Record record : records) {
            WindDetail windDetail = new WindDetail(record.getWind().getSpeed(), record.getWind().getDeg(), record.getWind().getGust());
            General general = new General(record.getMain().getTemp(), record.getMain().getFeels_like(), record.getMain().getTemp_min(), record.getMain().getTemp_max(), record.getMain().getPressure(), record.getMain().getHumidity(), record.getClouds().getStorage(), record.getVisibility(), windDetail);
            List<WeatherDetail> weatherDetails = record.getWeathers().stream().map(weather -> new WeatherDetail(weather.getDescription(), weather.getIcon())).collect(Collectors.toList());
            String measureDate = new SimpleDateFormat("yyyy-MM-dd").format(record.getMeasureDate());
            String measureTime = new SimpleDateFormat("HH:mm:ss").format(record.getMeasureTime());
            WeatherDataResponse weatherDataResponse = new WeatherDataResponse(record.getId(),record.getCity().getCityId(), record.getCity().getCityName(), record.getSysDetail().getCountry(), record.getCity().getTimezone(), weatherDetails, general, measureDate + " " + measureTime);
            weatherDataResponses.add(weatherDataResponse);
//            System.out.println(weatherDataResponse.toString());
        }
        return ResponseEntity.ok(weatherDataResponses);
    }

    @GetMapping("/update/{cityName}")
    public ResponseEntity<WeatherDataResponse> updateLatestRecord(@PathVariable String cityName) {
        Optional<City> city = cityService.findByName(cityName);
        if (!city.isEmpty()) {
            Record latestRecord = recordService.getAllRecordsByCityName(cityName).get(0);
            String url = "https://api.openweathermap.org/data/2.5/weather?q=" + cityName + "&appid=87cdd7f5bbfec3051219cb2cf72ef799&units=metric";
            String result = restTemplate.getForObject(url, String.class);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                RecordDto recordDto = objectMapper.readValue(result, RecordDto.class);
                System.out.println("Record Dto: " + recordDto);

                System.out.println("Latest record is same date");
                latestRecord.setBase(recordDto.getBase());
                latestRecord.setVisibility(recordDto.getVisibility());
                latestRecord.setDt(recordDto.getDt());
                latestRecord.setMeasureDate(new Date(recordDto.getDt()*1000));
                latestRecord.setMeasureTime(new Date(recordDto.getDt()*1000));
                recordService.save(latestRecord);

                Clouds clouds = latestRecord.getClouds();
                clouds.setStorage(recordDto.getClouds().getAll());
                cloudsService.save(clouds);

                Coord coord = latestRecord.getCoordinate();
                coord.setLat(recordDto.getCoord().getLat());
                coord.setLon(recordDto.getCoord().getLon());
                coordService.save(coord);

                Main main = latestRecord.getMain();
                main.setTemp(recordDto.getMain().getTemp());
                main.setPressure(recordDto.getMain().getPressure());
                main.setHumidity(recordDto.getMain().getHumidity());
                main.setTemp_min(recordDto.getMain().getTemp_min());
                main.setTemp_max(recordDto.getMain().getTemp_max());
                main.setFeels_like(recordDto.getMain().getFeels_like());
                mainService.save(main);

                Sys sys = latestRecord.getSysDetail();
                sys.setSunrise(recordDto.getSys().getSunrise());
                sys.setSunset(recordDto.getSys().getSunset());
                sys.setSysId(recordDto.getSys().getId());
                sys.setType(recordDto.getSys().getType());
                sys.setCountry(recordDto.getSys().getCountry());
                sysService.save(sys);

                Set<Weather> weathers = latestRecord.getWeathers();
                for (Weather weather : weathers) {
                    weatherService.deleteWeather(weather);
                }
                Set<Weather> recordDtoWeathers = recordDto.getWeather().stream().map(weather -> {
                    Weather weather1 = new Weather();
                    weather1.setId(weather.getId());
                    weather1.setMain(weather.getMain());
                    weather1.setDescription(weather.getDescription());
                    weather1.setIcon(weather.getIcon());
                    return weather1;
                }).collect(Collectors.toSet());

                recordDtoWeathers.stream().filter(weather -> !weathers.contains(weather)).forEach(weather -> {
                    Optional<Weather> existingWeather = weatherService.getWeatherById(weather.getWeatherId());
                    if (existingWeather.isEmpty()) {
                        weather.getRecords().add(latestRecord);
                        weatherService.save(weather);
                    } else {
                        existingWeather.get().getRecords().add(latestRecord);
                        weatherService.save(existingWeather.get());
                    }
                });

                Wind wind = latestRecord.getWind();
                wind.setDeg(recordDto.getWind().getDeg());
                wind.setSpeed(recordDto.getWind().getSpeed());
                wind.setGust(recordDto.getWind().getGust());
                windService.save(wind);

                WindDetail windDetail = new WindDetail(recordDto.getWind().getSpeed(), recordDto.getWind().getDeg(), recordDto.getWind().getGust());
                General general = new General(recordDto.getMain().getTemp(), recordDto.getMain().getFeels_like(), recordDto.getMain().getTemp_min(), recordDto.getMain().getTemp_max(), recordDto.getMain().getPressure(), recordDto.getMain().getHumidity(), recordDto.getClouds().getAll(), recordDto.getVisibility(), windDetail);
                List<WeatherDetail> weatherDetails = recordDto.getWeather().stream().map(weather -> new WeatherDetail(weather.getDescription(), weather.getIcon())).collect(Collectors.toList());
                String measureDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(recordDto.getDt()*1000));
                String measureTime = new SimpleDateFormat("HH:mm:ss").format(new Date(recordDto.getDt()*1000));
                WeatherDataResponse weatherDataResponse = new WeatherDataResponse(latestRecord.getId(),recordDto.getId(), recordDto.getName(), recordDto.getSys().getCountry(), recordDto.getTimezone(), weatherDetails, general, measureDate + " " + measureTime);


                return ResponseEntity.ok(weatherDataResponse);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().build();
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
