package com.alabtaal.library.repo;

import com.alabtaal.library.entity.BookEntity;
import com.alabtaal.library.entity.VolumeEntity;
import com.alabtaal.library.payload.response.SearchVolumeResponse;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VolumeRepo extends JpaRepository<VolumeEntity, UUID> {

  List<VolumeEntity> findAllByOrderByIdAsc();

  List<VolumeEntity> findAllByBookOrderByIdAsc(final BookEntity book);

  @Query(
      nativeQuery = true,
      value = "SELECT * FROM Volumes v where v.book_id =:id")
  List<VolumeEntity> findAllByBookId(@Param("id") final UUID id);

  @Query(
      nativeQuery = true,
      value = "SELECT V.VOLUME_ID\n" +
          "      ,V.BOOK_ID\n" +
          "      ,V.VOLUME_NAME\n" +
          "      ,B.BOOK_NAME\n" +
          "      ,'Shelf:' || S.SHELF_NAME || ' - Rack:' || R.RACK_NAME RACK_NAME\n" +
          "      ,V.REMARKS\n" +
          "  FROM VOLUMES V,\n" +
          "      BOOKS B,\n" +
          "      RACKS R,\n" +
          "      SHELVES S\n" +
          " WHERE     V.BOOK_ID = B.BOOK_ID(+)\n" +
          "       AND V.RACK_ID = R.RACK_ID(+)\n" +
          "       AND R.SHELF_ID = S.SHELF_ID\n")
  List<SearchVolumeResponse> searchAllVolumes();

  @Query(
      nativeQuery = true,
      value = "SELECT V.VOLUME_ID,\n" +
          "      V.BOOK_ID,\n" +
          "      V.VOLUME_NAME,\n" +
          "      B.BOOK_NAME,\n" +
          "      'Shelf:' || S.SHELF_NAME || ' - Rack:' || R.RACK_NAME RACK_NAME,\n" +
          "      V.REMARKS\n" +
          "  FROM VOLUMES V,\n" +
          "      BOOKS B,\n" +
          "      RACKS R,\n" +
          "      SHELVES S\n" +
          " WHERE     V.BOOK_ID = B.BOOK_ID(+)\n" +
          "       AND V.RACK_ID = R.RACK_ID(+)\n" +
          "       AND R.SHELF_ID = S.SHELF_ID\n" +
          "       AND V.BOOK_ID = :BOOK_ID")
  List<SearchVolumeResponse> searchVolumeByBookId(@Param("BOOK_ID") final UUID id);
}
