package via.pro3.controller;

import org.springframework.web.bind.annotation.*;
import via.pro3.model.Animal;
import via.pro3.repositories.AnimalRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/animals")
public class AnimalRegistrationController {

    private final AnimalRepository animalRepo;

    public AnimalRegistrationController(AnimalRepository animalRepo) {
        this.animalRepo = animalRepo;
    }

    @PostMapping
    public String registerAnimal(@RequestBody Animal animal) {
        try {
            animalRepo.save(animal);
            return "Animal registered successfully.";
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    @GetMapping
    public List<Animal> getAllAnimals() throws SQLException {
        return animalRepo.getAll();
    }

    // Path variable is INT, mapped to registrationNumber
    @GetMapping("/{registrationNumber}")
    public Animal getAnimal(@PathVariable int registrationNumber) throws SQLException {
        return animalRepo.getByRegistrationNumber(registrationNumber);
    }

    @GetMapping("/date/{date}")
    public List<Animal> getAnimalsByDate(@PathVariable String date) throws SQLException {
        return animalRepo.getByDate(LocalDate.parse(date));
    }

    @GetMapping("/origin/{origin}")
    public List<Animal> getAnimalsByOrigin(@PathVariable String origin) throws SQLException {
        return animalRepo.getByOrigin(origin);
    }
}