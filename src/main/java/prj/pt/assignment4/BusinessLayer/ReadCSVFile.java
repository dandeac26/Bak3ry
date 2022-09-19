package prj.pt.assignment4.BusinessLayer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReadCSVFile {
    public static List<BaseProduct> getData(Path path) {
        List<BaseProduct> products;

        Set<String> titles = new HashSet<>();
        try {
            products = Files.lines(path)
                    .skip(1)
                    .filter(line -> !titles.contains(line.split(",")[0]))
                    .map(line -> {
                        String[] fields = line.split(",");
                        titles.add(fields[0]);
                        return new BaseProduct(fields[0], Double.parseDouble(fields[1]), Integer.parseInt(fields[2]),
                                Integer.parseInt(fields[3]), Integer.parseInt(fields[4]), Integer.parseInt(fields[5]), Integer.parseInt(fields[6]));

                    }).distinct().toList();
        } catch (IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
        return new ArrayList<>(products);
    }
}
