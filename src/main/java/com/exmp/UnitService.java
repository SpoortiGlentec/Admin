package com.exmp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UnitService {

    @Autowired
    private UnitRepository unitRepository;

    @Value("${image.folder.path}")
    private String imageFolderPath;

    public List<Unit> getAllUnits() {
        return unitRepository.findAll();
    }

    public void addUnit(Unit unit) {
        unitRepository.save(unit);
    }

    public Optional<Unit> getUnitById(int unitId) {
        return unitRepository.findById(unitId);
    }

    public void updateUnit(Unit unit) {
        unitRepository.save(unit);
    }

    public List<Unit> getAllInactiveUnits() {
        return unitRepository.findByStatus("inactive");
    }

    public void deleteUnit(int unitId) {
        unitRepository.deleteById(unitId);
    }

    public List<Unit> getAllActiveUnits() {
        return unitRepository.findByStatus("active");
    }

    public void activateUnit(int unitId) {
        unitRepository.findById(unitId).ifPresent(unit -> {
            unit.setStatus("active");
            unitRepository.save(unit);
        });
    }

    public List<String> getAllImageNames() {
        File folder = new File(imageFolderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return List.of();
        }
        return Arrays.stream(Optional.ofNullable(folder.listFiles((dir, name) -> name.matches(".*\\.(jpg|png|jpeg)$")))
                .orElse(new File[0]))
                .map(File::getName)
                .collect(Collectors.toList());
    }
}
