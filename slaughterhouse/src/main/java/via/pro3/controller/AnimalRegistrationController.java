package via.pro3.controller;

import org.springframework.web.bind.annotation.*;
import via.pro3.model.Animal;
import via.pro3.service.AnimalService;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalRegistrationController {

    private final AnimalService animalService;

    public AnimalRegistrationController(AnimalService animalService) {
        this.animalService = animalService;
    }

    @PostMapping
    public String registerAnimal(@RequestBody Animal animal) {
        try {
            animalService.registerAnimal(animal);
            return "Animal registered successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping("/{registrationNumber}")
    public Animal getAnimal(@PathVariable String registrationNumber) throws SQLException {
        return animalService.getByRegistrationNumber(registrationNumber);
    }

    @GetMapping("/date/{date}")
    public List<Animal> getAnimalsByDate(@PathVariable String date) throws SQLException {
        return animalService.getByDate(LocalDate.parse(date));
    }

    @GetMapping("/origin/{origin}")
    public List<Animal> getAnimalsByOrigin(@PathVariable String origin) throws SQLException {
        return animalService.getByOrigin(origin);
    }
}
