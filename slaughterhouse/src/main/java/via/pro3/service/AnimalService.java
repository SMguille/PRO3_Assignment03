package via.pro3.service;

import org.springframework.stereotype.Service;
import via.pro3.model.Animal;
import via.pro3.repositories.AnimalRepository;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Service
public class AnimalService {

    private final AnimalRepository repository;

    public AnimalService(AnimalRepository repository) {
        this.repository = repository;
    }

    public void registerAnimal(Animal animal) throws SQLException {
        repository.save(animal);
    }

    public Animal getByRegistrationNumber(String registrationNumber) throws SQLException {
        return repository.getByRegistrationNumber(registrationNumber);
    }

    public List<Animal> getByDate(LocalDate date) throws SQLException {
        return repository.getByDate(date);
    }

    public List<Animal> getByOrigin(String origin) throws SQLException {
        return repository.getByOrigin(origin);
    }
}
