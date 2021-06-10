package isep.webtechnologies.homekeep;

import isep.webtechnologies.homekeep.models.house.HouseAmenities;
import isep.webtechnologies.homekeep.models.house.HouseRules;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebMvcConfig extends WebMvcConfigurationSupport {
    @Bean
    public StringArrayToHouseRulesConverter stringArrayToHouseRulesConverter() {
        return new StringArrayToHouseRulesConverter();
    }

    @Bean
    public StringToHouseRulesConverter stringToHouseRulesConverter() {
        return new StringToHouseRulesConverter();
    }

    @Bean
    public StringArrayToHouseAmenitiesConverter stringArrayToHouseAmenitiesConverter() {
        return new StringArrayToHouseAmenitiesConverter();
    }

    @Bean
    public StringToHouseAmenitiesConverter stringToHouseAmenitiesConverter() {
        return new StringToHouseAmenitiesConverter();
    }

    @Bean
    public MultipartFileToBlobConverter multipartFileToHouseImageConverter() {
        return new MultipartFileToBlobConverter();
    }
}

@Component
class StringArrayToHouseRulesConverter implements Converter<String[], HouseRules> {
    @Override
    public HouseRules convert(String[] strings) {
        List<String> stringList = new ArrayList<>(Arrays.asList(strings));
        boolean smokersWelcome = (stringList.contains("smokersWelcome"));
        boolean childrenWelcome = (stringList.contains("childrenWelcome"));
        boolean petsWelcome = (stringList.contains("petsWelcome"));
        boolean petsToFeed = (stringList.contains("petsToFeed"));
        boolean plantsToWater = (stringList.contains("plantsToWater"));
        return new HouseRules(smokersWelcome, childrenWelcome, petsWelcome, petsToFeed, plantsToWater);
    }
}

@Component
class StringToHouseRulesConverter implements Converter<String, HouseRules> {
    @Autowired
    StringArrayToHouseRulesConverter stringArrayToHouseRulesConverter;
    @Override
    public HouseRules convert(String string) {
        return stringArrayToHouseRulesConverter.convert(new String[] {string});
    }
}

@Component
class StringArrayToHouseAmenitiesConverter implements Converter<String[], HouseAmenities> {
    @Override
    public HouseAmenities convert(String[] strings) {
        List<String> stringList = new ArrayList<>(Arrays.asList(strings));
        boolean parking = (stringList.contains("parking"));
        boolean heatingSystem = (stringList.contains("heatingSystem"));
        boolean coolingSystem = (stringList.contains("coolingSystem"));
        boolean shower = (stringList.contains("shower"));
        boolean freezer = (stringList.contains("freezer"));
        boolean microwave = (stringList.contains("microwave"));
        boolean oven = (stringList.contains("oven"));
        boolean barbecue = (stringList.contains("barbecue"));
        boolean dishwasher = (stringList.contains("dishwasher"));
        boolean washingMachine = (stringList.contains("washingMachine"));
        boolean swimmingPool = (stringList.contains("swimmingPool"));
        boolean disabledAccessible = (stringList.contains("disabledAccessible"));
        return new HouseAmenities(0, 0, 0, parking, heatingSystem, coolingSystem, shower,
                freezer, microwave, oven, barbecue, dishwasher, washingMachine, swimmingPool, disabledAccessible);
    }
}

@Component
class StringToHouseAmenitiesConverter implements Converter<String, HouseAmenities> {
    @Autowired
    StringArrayToHouseAmenitiesConverter stringArrayToHouseAmenitiesConverter;
    @Override
    public HouseAmenities convert(String string) {
        return stringArrayToHouseAmenitiesConverter.convert(new String[] {string});
    }
}

@Component
class MultipartFileToBlobConverter implements Converter<MultipartFile, Blob> {
    @Override
    public Blob convert(MultipartFile multipartFile) {
        try {
            return new SerialBlob(multipartFile.getBytes());
        } catch (SQLException | IOException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }
}