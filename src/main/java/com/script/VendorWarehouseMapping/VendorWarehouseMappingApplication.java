package com.script.VendorWarehouseMapping;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.script.VendorWarehouseMapping.services.VendorWarehouseApiService;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@SpringBootApplication
@Log4j2
public class VendorWarehouseMappingApplication {

	private final VendorWarehouseApiService vendorWarehouseApiService;

    public VendorWarehouseMappingApplication(VendorWarehouseApiService vendorWarehouseApiService) {
        this.vendorWarehouseApiService = vendorWarehouseApiService;
    }

    public static void main(String[] args) throws Exception {
	ConfigurableApplicationContext context = SpringApplication.run(VendorWarehouseMappingApplication.class, args);
	var app = context.getBean(VendorWarehouseMappingApplication.class);
        if (args.length < 1) throw new Exception("File name missing!");
	var fileName = args[0];
	var warehouseId = args[1];
		if(warehouseId == null) throw new RuntimeException("warehouseId id null");

	final Path path = Paths.get(fileName);
	final String name = path.toFile().getName();
		System.out.println("processing file "+ name);
        app.doOperation(path, Long.valueOf(warehouseId));
	int exitCode = SpringApplication.exit(context, () -> 0);
        System.exit(exitCode);

}

	public void doOperation(Path path, Long warehouseId) throws FileNotFoundException {

		try (Reader reader = Files.newBufferedReader(Paths.get(path.toUri()))) {
			var csvReader = new CSVReader(reader);
			var manufacturerIds = csvReader.readAll().stream()
					.map(line -> Long.parseLong(line[0]))
					.toList();

			for(var manufacturerId :manufacturerIds) {

				System.out.println("Response from partner app service:  " + manufacturerId);
				//var response = vendorWarehouseApiService.removeDuplicateVendor(manufacturerId, warehouseId);
				//System.out.println("Response from partner app service: "+ response);
			}
				csvReader.close();

		} catch (IOException | CsvException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
