package com.playlist.adapter;

import com.playlist.adapter.external.LegacyVinylCatalog;
import com.playlist.core.Track;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VinylCatalogAdapter implements TrackCatalog {

  private final LegacyVinylCatalog legacyCatalog;

  public VinylCatalogAdapter(LegacyVinylCatalog legacyCatalog) {
    if (legacyCatalog == null) {
      throw new IllegalArgumentException("legacyCatalog não pode ser null");
    }
    this.legacyCatalog = legacyCatalog;
  }

  @Override
  public List<Track> findAll() {
    List<Track> tracks = new ArrayList<>();
    for (String record : legacyCatalog.fetchAllRecords()) {
      parseRecord(record).ifPresent(tracks::add);
    }
    return List.copyOf(tracks);
  }

  @Override
  public Optional<Track> findById(String id) {
    if (id == null || id.isBlank()) {
      return Optional.empty();
    }
    String record = legacyCatalog.findRecordByCatalogNumber(id);
    if (record == null) {
      return Optional.empty();
    }
    return parseRecord(record);
  }

  private static Optional<Track> parseRecord(String record) {
    String[] fields = record.split("\\|", -1);
    if (fields.length != 5) {
      return Optional.empty();
    }

    String id = fields[0].trim();
    String title = normalizeWhitespace(fields[1]);
    String durationField = fields[3].trim();
    String premiumField = fields[4].trim();

    if (id.isEmpty() || title.isEmpty() || !durationField.matches("\\d+")) {
      return Optional.empty();
    }

    int durationSeconds = (int) (Long.parseLong(durationField) / 1000);
    String artist = convertArtist(fields[2]);
    boolean premium = premiumField.equalsIgnoreCase("Y");

    return Optional.of(new Track(id, toTitleCase(title), artist, durationSeconds, premium));
  }

  private static String convertArtist(String rawArtist) {
    String[] parts = rawArtist.split(",", 2);
    String surname = toTitleCase(normalizeWhitespace(parts[0]));
    String name = parts.length > 1 ? toTitleCase(normalizeWhitespace(parts[1])) : "";
    return name.isEmpty() ? surname : name + " " + surname;
  }

  private static String normalizeWhitespace(String value) {
    return value.trim().replaceAll("\\s+", " ");
  }

  private static String toTitleCase(String value) {
    if (value.isEmpty()) {
      return value;
    }
    String[] words = value.split(" ");
    StringBuilder result = new StringBuilder();
    for (String word : words) {
      if (word.isEmpty()) {
        continue;
      }
      if (result.length() > 0) {
        result.append(' ');
      }
      result.append(Character.toUpperCase(word.charAt(0)));
      result.append(word.substring(1).toLowerCase());
    }
    return result.toString();
  }
}