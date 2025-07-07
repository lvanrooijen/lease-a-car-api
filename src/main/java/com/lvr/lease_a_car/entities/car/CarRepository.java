package com.lvr.lease_a_car.entities.car;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
  List<Car> findAllByIsDeletedFalse();

  public Optional<Car> findByIdAndIsDeletedFalse(Long id);

  Optional<Car> findByMakeAndModelAndVersionAndIsDeletedFalse(
      String make, String model, String version);
}
