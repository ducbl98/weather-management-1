package com.example.weathermanagement1.seeder;

import com.example.weathermanagement1.entity.*;
import com.example.weathermanagement1.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataSeeder implements CommandLineRunner {

  @Autowired private CityService cityService;

  @Autowired private CloudsService cloudsService;

  @Autowired private CoordService coordService;

  @Autowired private MainService mainService;

  @Autowired private WeatherService weatherService;

  @Autowired private SysService sysService;

  @Autowired private WindService windService;

  @Autowired private RecordService recordService;

  @Override
  public void run(String... args) throws Exception {
    seedWeatherData();
  }

  public void seedWeatherData() {
    List<Integer> times = new ArrayList<>();
    times.add(1655079436);
    times.add(1653179014);
    times.add(1654379014);

    City city = new City(1835848, 200, "Seoul", 32400);
    cityService.save(city);

    for (Integer time : times) {
      Record seedRecord = new Record(time, 10000, "stations", city);
      seedRecord.setMeasureDate(new Date((long) time * 1000));
      seedRecord.setMeasureTime(new Date((long) time * 1000));
      recordService.save(seedRecord);

      Weather weather1 = new Weather(800, "800", "clear sky", "01d");
      Weather weather2 = new Weather(212, "432", "blizzard", "06d");
      Weather weather3 = new Weather(697, "132", "thunder gale", "01x");
      Set<Weather> weathers = Set.of(weather1, weather2, weather3);
      for (Weather weather : weathers) {
        Optional<Weather> existingWeather = weatherService.getWeatherById(weather.getWeatherId());
        if (existingWeather.isEmpty()) {
          weather.getRecords().add(seedRecord);
          weatherService.save(weather);
        } else {
          existingWeather.get().getRecords().add(seedRecord);
          weatherService.save(existingWeather.get());
        }
      }

      Clouds clouds = new Clouds(10);
      clouds.setRecord(seedRecord);
      cloudsService.save(clouds);

      Coord coord = new Coord(37.5683, 126.9778);
      coord.setRecord(seedRecord);
      coordService.save(coord);

      Main main = new Main(25.66, 25.12, 25.66, 25.69, 1008, 32);
      main.setRecord(seedRecord);
      mainService.save(main);

      Sys sys = new Sys(5509, "KR", 1655151018, 1655204060, 1);
      sys.setRecord(seedRecord);
      sysService.save(sys);

      Wind wind = new Wind(5, 97, 6.28);
      wind.setRecord(seedRecord);
      windService.save(wind);
    }
  }
}
