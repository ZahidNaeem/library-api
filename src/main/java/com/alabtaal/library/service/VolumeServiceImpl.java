package com.alabtaal.library.service;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.payload.response.SearchVolumeResponse;
import com.alabtaal.library.repo.VolumeRepo;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VolumeServiceImpl implements VolumeService {

  private static final Logger LOG = LoggerFactory.getLogger(VolumeServiceImpl.class);

  private final VolumeRepo volumeRepo;

  @Override
  public List<VolumeEntity> findAll() {
    return volumeRepo.findAllByOrderByIdAsc();
  }

  @Override
  public List<VolumeEntity> findAllByBook(final BookEntity book) {
    return volumeRepo.findAllByBookOrderByIdAsc(book);
  }

  @Override
  public List<SearchVolumeResponse> findAllSearchResponses() {
    return volumeRepo.searchAllVolumes();
  }

  @Override
  public List<SearchVolumeResponse> findAllByBookId(UUID id) {
//        return volumeRepo.findAllByBookId(id);
    return volumeRepo.searchVolumeByBookId(id);
  }

  @Override
  public VolumeEntity findById(UUID id) {
    return volumeRepo.findById(id)
        .orElse(new VolumeEntity());
  }

  @Override
  public boolean exists(UUID id) {
    return volumeRepo.existsById(id);
  }

  @Override
  public VolumeEntity save(VolumeEntity volume) {
    return volumeRepo.saveAndFlush(volume);
  }

  @Override
  public List<VolumeEntity> save(Set<VolumeEntity> volumes) {
    return volumeRepo.saveAll(volumes);
  }

  @Override
  public void delete(VolumeEntity volume) {
    volumeRepo.delete(volume);
  }

  @Override
  public void delete(Set<VolumeEntity> volumes) {
    volumeRepo.deleteAll(volumes);
  }

  @Override
  public void deleteById(UUID id) {
    volumeRepo.deleteById(id);
  }

  @Override
  public void deleteAll() {
    volumeRepo.deleteAll();
  }

  @Override
  public void deleteAllInBatch() {
    volumeRepo.deleteAllInBatch();
  }

  @Override
  public void deleteInBatch(Set<VolumeEntity> volumes) {
    volumeRepo.deleteInBatch(volumes);
  }
}
